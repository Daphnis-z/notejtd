package com.daphnis.sbtdemo.service;

import com.daphnis.sbtdemo.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
public class AfterServiceStart implements CommandLineRunner,ApplicationRunner {

    @Autowired
    private ApplicationConfig applicationConfig;

    /**
     * excute after srvice start
     * @param applicationArguments
     */
    @Override
    public void run(ApplicationArguments applicationArguments){
        System.out.println("enter ApplicationRunner run..");
        sayHello();
    }

    /**
     * excute after srvice start
     * @param args
     */
    @Override
    public void run(String... args){
        System.out.println("enter CommandLineRunner run..");
    }

    private void sayHello(){
        System.out.println(applicationConfig.getHello());
    }

}

