package com.yupi.project.model.dto.interfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 接口测试
 *
 * @TableName product
 */
@Data
public class InterfaceInfoInvokeRequest implements Serializable {

    /**
     * 主键
     */

    private Long id;


    /**
     * 请求参数
     */
    private String userRequestParams;




}