package com.lc.tax.controller;

import com.lc.tax.service.IZsJksMapperService;
import com.lc.tax.serviceImpl.ZsJksMapperServiceImpl;
import javafx.application.Application;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestConn {

    public static void main(String[] args) {
        ApplicationContext context = new
                ClassPathXmlApplicationContext(new String[] {"classpath:spring-*.xml"});
        String spuuid = "1DD2021081FE67BAC04E53A82193AFAB";
        IZsJksMapperService iZsJksMapperService =(IZsJksMapperService) context.getBean("zsJksMapperService");
        System.out.println(iZsJksMapperService.selectByPrimaryKey(spuuid).getZsuuid());

    }



}
