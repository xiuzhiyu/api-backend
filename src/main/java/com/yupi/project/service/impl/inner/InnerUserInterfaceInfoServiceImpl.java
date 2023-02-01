package com.yupi.project.service.impl.inner;

import com.yupi.project.service.UserInterfaceInfoService;
import com.yupi.yupicommon.model.entity.UserInterfaceInfo;
import com.yupi.yupicommon.servie.InnerUserInterfaceInfoService;

import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
@Service
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {
    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;
    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean b) {

    }

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
       return userInterfaceInfoService.invokeCount(interfaceInfoId,userId);

    }
}
