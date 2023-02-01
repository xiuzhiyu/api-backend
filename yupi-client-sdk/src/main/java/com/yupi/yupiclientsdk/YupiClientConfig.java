package com.yupi.yupiclientsdk;

import com.yupi.yupiclientsdk.client.YupiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("yuapi.client")
@Data
@ComponentScan
public class YupiClientConfig {

    private String accessKey;
    private String secretKey;
    @Bean
   public YupiClient yupiClient()
    {
        return new YupiClient(accessKey,secretKey);
    }
}
