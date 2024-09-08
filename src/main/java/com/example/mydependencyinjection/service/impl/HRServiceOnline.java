package com.example.mydependencyinjection.service.impl;

import com.example.mydependencyinjection.service.HRService;
import com.example.mydependencyinjection.util.annotations.MyAutowired;
import com.example.mydependencyinjection.util.annotations.MyComponent;
import lombok.NoArgsConstructor;

@MyComponent
public class HRServiceOnline implements HRService {
    String test = "test";

    @MyAutowired
    public HRServiceOnline() {
        System.out.println("was HRServiceOnline");
    }

    @Override
    public void lookResume(String resume) {
        System.out.println("перенапрвляю ваше резюме работодателю");
    }
}
