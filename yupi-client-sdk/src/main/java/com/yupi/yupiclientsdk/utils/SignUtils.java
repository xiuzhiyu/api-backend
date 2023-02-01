package com.yupi.yupiclientsdk.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * 签名工具
 *
 */
public class SignUtils {
    public SignUtils() {
    }

    public static  String getSign(String body, String sercetKey)
    {
        // Digester md5 = new Digester(DigestAlgorithm.SHA256);
        Digester md5 = new Digester(DigestAlgorithm.SHA256);
        String context=body+"."+sercetKey;
        String sign= md5.digestHex(context);
        return sign;
    }
}
