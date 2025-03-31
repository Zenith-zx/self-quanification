package com.futuapi.selfquantification.config;

import com.futu.openapi.FTAPI;
import com.futu.openapi.FTAPI_Conn_Qot;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 周欣
 * @date 2025/3/31 15:52
 */
@Configuration
public class FutuConfig {

    @Value("${futu.host:127.0.0.1}")
    private String host;

    @Value("${futu.port:11111}")
    private short port;

    private final static String CLIENT_ID = "javaclient";

    @PostConstruct
    public void init() {
        FTAPI.init();
    }

    @Bean
    public FTAPI_Conn_Qot futuConnQot() {
        FTAPI_Conn_Qot qot = new FTAPI_Conn_Qot();
        qot.setClientInfo(CLIENT_ID, 1);
        qot.initConnect(host, port, false);
        return qot;
    }

}
