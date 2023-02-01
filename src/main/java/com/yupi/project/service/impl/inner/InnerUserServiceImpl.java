package com.yupi.project.service.impl.inner;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupi.project.common.ErrorCode;
import com.yupi.project.exception.BusinessException;
import com.yupi.project.mapper.UserMapper;
import com.yupi.yupicommon.model.entity.User;
import com.yupi.yupicommon.servie.InnerUserService;

import org.apache.dubbo.config.annotation.Service;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
@Service
public class InnerUserServiceImpl implements InnerUserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public User getInvokeUser(String accessKey) {
        if(Strings.isBlank(accessKey))
        {
            throw  new BusinessException(ErrorCode.PARAMS_ERROR);
        }

       QueryWrapper<User> queryWrapper=new QueryWrapper<User>();
        queryWrapper.eq("accessKey",accessKey);

        return   userMapper.selectOne(queryWrapper);

    }
}
