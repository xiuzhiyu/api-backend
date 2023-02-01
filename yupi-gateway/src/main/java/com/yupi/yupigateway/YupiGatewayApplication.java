package com.yupi.yupigateway;

import com.yupi.project.provider.DemoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableDubbo
public class YupiGatewayApplication {

    @DubboReference
    private DemoService demoService;

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(YupiGatewayApplication.class, args);
        YupiGatewayApplication application = context.getBean(YupiGatewayApplication.class);
        String result = application.doSayHello("world");
        System.out.println("result: " + result);
    }

    public String doSayHello(String name) {
        return demoService.sayHello(name);
    }
   // public static void main(String[] args) {
      //  SpringApplication.run(YupiGatewayApplication.class, args);
    //}


}
