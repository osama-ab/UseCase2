package com.example.commandeservice.Configuration;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("mes-config")
@RefreshScope
public class ApplicationPropertiesConfiguration {



    private int commandeslast ;


    public int getcommandeslast() {
        return commandeslast;
    }

    public void setcommandeslast(int commandeslast) {
        this.commandeslast = commandeslast;
    }
}
