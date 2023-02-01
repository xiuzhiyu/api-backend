package com.yupi.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.project.common.ErrorCode;
import com.yupi.project.exception.BusinessException;

import com.yupi.project.mapper.UserInterfaceInfoMapper;

import com.yupi.project.service.UserInterfaceInfoService;
import com.yupi.yupicommon.model.entity.UserInterfaceInfo;

import org.springframework.stereotype.Service;

/**
 * @author xiu
 * @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service实现
 * @createDate 2022-12-03 15:48:08
 */
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
        implements UserInterfaceInfoService {

    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean b) {

    }

    public boolean invokeCount(long interfaceInfoId, long userId) {
        //校验参数
        if(interfaceInfoId<=0||userId<=0)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);


        }
       UpdateWrapper<UserInterfaceInfo> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("interfaceInfoId",interfaceInfoId);
        updateWrapper.eq("userId",userId);
        updateWrapper.setSql("leftNum=leftNum-1,totalNum=totalNum+1");
        //大于0.ge大于等于0
        updateWrapper.gt("leftNum",0);
        return this.update(updateWrapper);

    }
}




