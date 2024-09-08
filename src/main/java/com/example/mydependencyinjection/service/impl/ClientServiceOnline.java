package com.example.mydependencyinjection.service.impl;

import com.example.mydependencyinjection.service.ClientService;
import com.example.mydependencyinjection.util.annotations.MyAutowired;
import com.example.mydependencyinjection.util.annotations.MyComponent;

@MyComponent
public class ClientServiceOnline implements ClientService {
    String test = "test";

    @MyAutowired
    public ClientServiceOnline() {
        System.out.println("was ClientServiceOnline");
    }

    @Override
    public void submitResume(String resume) {
        System.out.println("Отправляю свое резюме рекуртеру");
    }
}
