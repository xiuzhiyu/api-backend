package com.yupi.yupicommon.servie;

import com.baomidou.mybatisplus.extension.service.IService;

import com.yupi.yupicommon.model.entity.InterfaceInfo;

/**
 *
 */
public interface InnerInterfaceInfoService  {
    /**
     *  2.从数据库中查询模拟接口是否存在
     * @param path
     * @param method
     * @return
     */

    InterfaceInfo getInterfaceInfo(String path, String method);

    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean b);
}
