package com.yupi.yupicommon.servie;



import com.yupi.yupicommon.model.entity.UserInterfaceInfo;

/**
* @author xiu
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
* @createDate 2022-12-03 15:48:08
*/
public interface InnerUserInterfaceInfoService {
    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean b);





    /**
     * 调用接口统计次数
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId,long userId);
}
