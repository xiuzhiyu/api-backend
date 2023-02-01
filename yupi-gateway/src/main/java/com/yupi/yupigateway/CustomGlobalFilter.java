package com.yupi.yupigateway;

import com.yupi.yupiclientsdk.utils.SignUtils;
import com.yupi.yupicommon.model.entity.InterfaceInfo;
import com.yupi.yupicommon.model.entity.User;
import com.yupi.yupicommon.servie.InnerInterfaceInfoService;
import com.yupi.yupicommon.servie.InnerUserInterfaceInfoService;
import com.yupi.yupicommon.servie.InnerUserService;
import jdk.nashorn.internal.ir.annotations.Reference;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 全局过滤
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {
    @Reference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;
    @Reference
    private InnerUserService innerUserService;
    @Reference
    private InnerInterfaceInfoService innerInterfaceInfoService;
    private static final List<String> IP_LIST_WhILT = Arrays.asList("127.0.0.1");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1.请求日志

        ServerHttpRequest request = exchange.getRequest();//获取请求对象
        String url=request.getPath().value();
        String method=request.getMethod().toString();
        log.info("请求唯一表识" + request.getId());
        log.info("请求路径" + request.getPath());
        log.info("请求参数" + request.getQueryParams());
        log.info("请求方法" + request.getMethod());
        log.info("请求来源地址" + request.getLocalAddress().getHostString());
        String ipAdress = request.getLocalAddress().getHostString();
        log.info("请求来源地址" + request.getRemoteAddress());
        //获取响应对象
        ServerHttpResponse response = exchange.getResponse();
        //2.(黑白名单)
        if (!IP_LIST_WhILT.contains(ipAdress)) {
            response.setStatusCode(HttpStatus.FORBIDDEN);//设置返回状态码（404）
            return response.setComplete();//直接返回，不会执行后面的方法

        }
        //3.用户鉴权（判断ak，sk是否合法）
        HttpHeaders headers = request.getHeaders();

        String accseeKey = headers.getFirst("accessKey");
        String sercetKey = headers.getFirst("sercetKey");
        String nonce = headers.getFirst("nonce");
        String body = headers.getFirst("body");
        String timeStamp = headers.getFirst("timeStamp");
        String sign = headers.getFirst("sign");
        User invokeUser =null;
        try {
            invokeUser= innerUserService.getInvokeUser(sercetKey);
        }catch (Exception e) {
            log.error("getInvokeUser error ",e);
        }
        if(invokeUser==null)
        {
          return handleNotAuth(response);
        }


//        if (!"lhj".equals(accseeKey)) {
//            return handleNotAuth(response);
//        }
        if (Long.parseLong(nonce) > 10000) {
            return handleNotAuth(response);
        }
        if (Long.parseLong(nonce) > 10000) {
            return handleNotAuth(response);
        }
        long FIVE_MINUTES = 60 * 5L;
        Long currentTime = System.currentTimeMillis() / 1000;
        if ((currentTime - Long.parseLong(timeStamp)) > FIVE_MINUTES) {
            return handleNotAuth(response);
        }
//        if(timeStamp)
//        {
//            //自己写
//        }
        String secretKey=invokeUser.getSecretKey();
        String sign1 = SignUtils.getSign(body, secretKey);
        if (!sign.equals(sign1)) {
            return handleNotAuth(response);

        }

        //4.请求的接口是否存在
        InterfaceInfo interfaceInfo = null;
        try {
            interfaceInfo= innerInterfaceInfoService.getInterfaceInfo(url, method);
        }catch (Exception e) {
            log.error("getInterfaceInfo error ",e);
        }
        if(interfaceInfo==null)
        {
            return handleNotAuth(response);
        }
        //5.请求转化，调用接口

        Mono<Void> filter = chain.filter(exchange);
        //6.相应日志
        return handleResponse(exchange, chain,interfaceInfo.getId(),invokeUser.getId());

    }

    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain,long interfaceInfoId,long userId) {
        try {
            ServerHttpResponse originalResponse = exchange.getResponse();
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();

            HttpStatus statusCode = (HttpStatus) originalResponse.getStatusCode();

            if (statusCode == HttpStatus.OK) {
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {

                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        //log.info("body instanceof Flux: {}", (body instanceof Flux));
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            //
                            return super.writeWith(fluxBody.map(dataBuffer -> {

                                //        //7.调用接口，接口次数加一
                                try {
                                    boolean b = innerUserInterfaceInfoService.invokeCount(interfaceInfoId, userId);
                                } catch (Exception e) {
                                    log.error("invoke error",e);
                                }

                                //        return filter;
                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                DataBufferUtils.release(dataBuffer);//释放掉内存
                                // 构建日志
                                StringBuilder sb2 = new StringBuilder(200);
                                sb2.append("<--- {} {} \n");
                                List<Object> rspArgs = new ArrayList<>();
                                rspArgs.add(originalResponse.getStatusCode());
                                //rspArgs.add(requestUrl);
                                //  log.info("响应" + response.getStatusCode());
                                String data = new String(content, StandardCharsets.UTF_8);//data
                                sb2.append(data);
                                //打印响应日志
                                log.info("响应结果" + data);
                                //        log.info("响应" + response.getStatusCode());

                                // log.info(sb2.toString(), rspArgs.toArray());//log.info("<-- {} {}\n", originalResponse.getStatusCode(), data);
                                return bufferFactory.wrap(content);
                            }));
                        } else {  //        //8.调用失败，返回一个错误的状态码
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            return chain.filter(exchange);//降级处理返回数据
        } catch (Exception e) {
            log.error("gateway log exception.\n" + e);
            return chain.filter(exchange);
        }

    }

    @Override
    public int getOrder() {
        return -1;
    }

    public Mono<Void> handleNotAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);//设置返回状态码（404）
        return response.setComplete();//直接返回，不会执行后面的方法

    }

    public Mono<Void> handleInvokeError(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);//设置返回状态码（404）
        return response.setComplete();//直接返回，不会执行后面的方法

    }

}
