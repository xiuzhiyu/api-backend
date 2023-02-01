package com.yupi.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.yupi.project.annotation.AuthCheck;
import com.yupi.project.common.*;
import com.yupi.project.constant.CommonConstant;
import com.yupi.project.exception.BusinessException;
import com.yupi.project.model.dto.interfaceinfo.InterfaceInfoAddRequest;
import com.yupi.project.model.dto.interfaceinfo.InterfaceInfoInvokeRequest;
import com.yupi.project.model.dto.interfaceinfo.InterfaceInfoQueryRequest;
import com.yupi.project.model.dto.interfaceinfo.InterfaceInfotUpdateRequest;
import com.yupi.project.model.enums.InterfaceInfoStatusEnum;
import com.yupi.project.service.InterfaceInfoService;
import com.yupi.project.service.UserService;


import com.yupi.yupiclientsdk.client.YupiClient;
import com.yupi.yupicommon.model.entity.InterfaceInfo;
import com.yupi.yupicommon.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 帖子接口
 *
 * @author yupi
 */
@RestController
@RequestMapping("/InterfaceInfo")
@Slf4j
public class InterfaceInfoController {

    @Resource
    private InterfaceInfoService InterfaceInfoService;

    @Resource
    private UserService userService;
    @Resource
    private YupiClient yuClient;

    // region 增删改查

    /**
     * 创建
     *
     * @param InterfaceInfoAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addInterfaceInfo(@RequestBody InterfaceInfoAddRequest InterfaceInfoAddRequest, HttpServletRequest request) {
        if (InterfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo InterfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(InterfaceInfoAddRequest, InterfaceInfo);
        // 校验
        InterfaceInfoService.validInterfaceInfo(InterfaceInfo, true);
        User loginUser = userService.getLoginUser(request);
        InterfaceInfo.setUserId(loginUser.getId());
        boolean result = InterfaceInfoService.save(InterfaceInfo);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newInterfaceInfoId = InterfaceInfo.getId();
        return ResultUtils.success(newInterfaceInfoId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = InterfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可删除
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = InterfaceInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新
     *
     * @param InterfaceInfoUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateInterfaceInfo(@RequestBody InterfaceInfotUpdateRequest InterfaceInfoUpdateRequest,
                                            HttpServletRequest request) {
        if (InterfaceInfoUpdateRequest == null || InterfaceInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo InterfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(InterfaceInfoUpdateRequest, InterfaceInfo);
        // 参数校验
        InterfaceInfoService.validInterfaceInfo(InterfaceInfo, false);
        User user = userService.getLoginUser(request);
        long id = InterfaceInfoUpdateRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = InterfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可修改
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = InterfaceInfoService.updateById(InterfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<InterfaceInfo> getInterfaceInfoById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo InterfaceInfo = InterfaceInfoService.getById(id);
        return ResultUtils.success(InterfaceInfo);
    }

    /**
     * 获取列表（仅管理员可使用）
     *
     * @param InterfaceInfoQueryRequest
     * @return
     */
    @AuthCheck(mustRole = "admin")
    @GetMapping("/list")
    public BaseResponse<List<InterfaceInfo>> listInterfaceInfo(InterfaceInfoQueryRequest InterfaceInfoQueryRequest) {
        InterfaceInfo InterfaceInfoQuery = new InterfaceInfo();
        if (InterfaceInfoQueryRequest != null) {
            BeanUtils.copyProperties(InterfaceInfoQueryRequest, InterfaceInfoQuery);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(InterfaceInfoQuery);
        List<InterfaceInfo> InterfaceInfoList = InterfaceInfoService.list(queryWrapper);
        return ResultUtils.success(InterfaceInfoList);
    }

    /**
     * 分页获取列表
     *
     * @param InterfaceInfoQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<InterfaceInfo>> listInterfaceInfoByPage(InterfaceInfoQueryRequest InterfaceInfoQueryRequest, HttpServletRequest request) {
        if (InterfaceInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo InterfaceInfoQuery = new InterfaceInfo();
        BeanUtils.copyProperties(InterfaceInfoQueryRequest, InterfaceInfoQuery);
        long current = InterfaceInfoQueryRequest.getCurrent();
        long size = InterfaceInfoQueryRequest.getPageSize();
        String sortField = InterfaceInfoQueryRequest.getSortField();
        String sortOrder = InterfaceInfoQueryRequest.getSortOrder();
        String description = InterfaceInfoQuery.getDescription();
        // content 需支持模糊搜索
        InterfaceInfoQuery.setDescription(null);
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(InterfaceInfoQuery);
        queryWrapper.like(StringUtils.isNotBlank(description), "content", description);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<InterfaceInfo> InterfaceInfoPage = InterfaceInfoService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(InterfaceInfoPage);
    }

    // endregion
    /**
     * 发布
     *
     * @param onlineinterfaceinfo
     * @param request
     * @return
     */
    @PostMapping("/online")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> OnlineInterfaceInfo(@RequestBody IdRequest idRequest,HttpServletRequest request)
    {
        //判断参数是否异常
        if(idRequest==null||idRequest.getId()<=0)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //判断该接口是否存在
        long id = idRequest.getId();
        InterfaceInfo oldInterfaceInfo = InterfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        //判断接口是否可以调用
        com.yupi.yupiclientsdk.model.User user=new com.yupi.yupiclientsdk.model.User();
        user.setUserName("test");
        String username = yuClient.getNameByUrlPost(user);
        if(StringUtils.isBlank(username))
        {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"接口不存在！");

        }


        // 仅本人或管理员可修改
        InterfaceInfo interfaceInfo=new InterfaceInfo();
        interfaceInfo.setId(id);
        interfaceInfo.setStatus(InterfaceInfoStatusEnum.ONLINE.getValue() );
        boolean result = InterfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);

    }
    // endregion
    /**
     * 发布
     *
     * @param offlineinterfaceinfo
     * @param request
     * @return
     */
    @PostMapping("/offline")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> OfflineInterfaceInfo(@RequestBody IdRequest idRequest,HttpServletRequest request)
    {
        //判断参数是否异常
        if(idRequest==null||idRequest.getId()<=0)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //判断该接口是否存在
        long id = idRequest.getId();
        InterfaceInfo oldInterfaceInfo = InterfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }



        // 仅本人或管理员可修改
        InterfaceInfo interfaceInfo=new InterfaceInfo();
        interfaceInfo.setId(id);
        interfaceInfo.setStatus(InterfaceInfoStatusEnum.OFFLINE.getValue() );
        boolean result = InterfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);

    }
    // endregion
    /**
     * 接口测试
     *
     * @param InterfaceInfoInvokeRequest
     * @param request
     * @return
     */
    @PostMapping("/invole")
    public BaseResponse<Object> invokeInterfaceInfo(@RequestBody InterfaceInfoInvokeRequest interfaceInfoInvokeRequest, HttpServletRequest request)
    {
        //判断参数是否异常
        if(interfaceInfoInvokeRequest==null||interfaceInfoInvokeRequest.getId()<=0)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //判断该接口是否存在
        long id = interfaceInfoInvokeRequest.getId();
        //获取当前调用参数
        String userParmas = interfaceInfoInvokeRequest.getUserRequestParams();
          //获取当前调用接口信息
        InterfaceInfo oldInterfaceInfo = InterfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
//判断当前接口状态
        if(oldInterfaceInfo.getStatus()==InterfaceInfoStatusEnum.OFFLINE.getValue())
        {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"接口已关闭");
        }
        //获取当前调用用户信息
        User loginUser = userService.getLoginUser(request);
        Gson gson=new Gson();
        //获取api签名认证

        String accessKey=loginUser.getAccessKey();
        String secretKey=loginUser.getSecretKey();
        YupiClient tempClient=new YupiClient(accessKey,secretKey);
        //封装调用用户信息
        com.yupi.yupiclientsdk.model.User user = gson.fromJson(userParmas, com.yupi.yupiclientsdk.model.User.class);
        //调用接口，返回信息
        String nameByUrlPost = tempClient.getNameByUrlPost(user);


        return ResultUtils.success(nameByUrlPost);

    }


}
