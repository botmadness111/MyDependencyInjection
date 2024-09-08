package com.example.mydependencyinjection;

import org.springframework.boot.SpringApplication;

import java.lang.reflect.InvocationTargetException;

public class MyDependencyInjectionApplication {
    public static void main(String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        SpringApplication.run(MyDependencyInjectionApplication.class, args);
        HelperDI helperDI = new HelperDI();

        helperDI.createInstanceClasses();

        //дальше можно добавить @Qualifaer, потому что сейчас берется один случайный из двух реализация интерйфосв
    }
}