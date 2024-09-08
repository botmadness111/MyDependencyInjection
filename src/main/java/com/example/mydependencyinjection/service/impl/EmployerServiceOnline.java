package com.example.mydependencyinjection.service.impl;

import com.example.mydependencyinjection.service.EmployerService;
import com.example.mydependencyinjection.util.annotations.MyAutowired;
import com.example.mydependencyinjection.util.annotations.MyComponent;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@MyComponent
public class EmployerServiceOnline implements EmployerService {
    String test = "test";

    @MyAutowired
    public EmployerServiceOnline() {
        System.out.println("was EmployerServiceOnline");
    }

    @Override
    public void lookResume(String resume) {
        System.out.println("Вы приняты на работу!");
    }
}
