package com.yupi.yupicommon.servie;




import com.yupi.yupicommon.model.entity.User;



/**
 * 用户服务
 *
 * @author yupi
 */
public interface InnerUserService  {
    /**
     * 从数据库中查询是否分配给用户密钥（ak，sk）
     * @param accessKey
     * @param
     * @return
     */

    User getInvokeUser(String accessKey);


}
