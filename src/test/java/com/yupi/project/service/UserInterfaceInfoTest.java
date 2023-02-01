package com.yupi.project.service;

import com.yupi.yupicommon.servie.InnerUserInterfaceInfoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class UserInterfaceInfoTest {
    @Resource
    private InnerUserInterfaceInfoService userInterfaceInfoService;
    @Test
    public void invokeCount() {
        boolean b = userInterfaceInfoService.invokeCount(1l,1l);
        Assertions.assertTrue(b);
    }

    @Test
    void name() {

    }
}
