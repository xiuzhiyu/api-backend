package com.yupi.project.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupi.project.common.ErrorCode;
import com.yupi.project.exception.BusinessException;
import com.yupi.project.mapper.InterfaceInfoMapper;

import com.yupi.yupicommon.model.entity.InterfaceInfo;

import com.yupi.yupicommon.servie.InnerInterfaceInfoService;

import org.apache.dubbo.config.annotation.Service;
import org.apache.logging.log4j.util.Strings;

import javax.annotation.Resource;
@Service
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {
    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;
    @Override
    public InterfaceInfo getInterfaceInfo(String url, String method) {
        if(Strings.isBlank(url)||Strings.isBlank(method))
        {
            throw  new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        QueryWrapper<InterfaceInfo> queryWrapper=new QueryWrapper<InterfaceInfo>();
        queryWrapper.eq("url",method);
        queryWrapper.eq("url",method);
        return   interfaceInfoMapper.selectOne(queryWrapper);

    }

    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean b) {

    }
}
