package com.example.mydependencyinjection.service;

import com.example.mydependencyinjection.model.Client;
import com.example.mydependencyinjection.util.annotations.ConstructorParameters;
import com.example.mydependencyinjection.util.annotations.MyAutowired;
import com.example.mydependencyinjection.util.annotations.MyComponent;

@MyComponent
@ConstructorParameters({ClientService.class, HRService.class, EmployerService.class})
public class WorkPresenter {

    private final ClientService clientService;
    private final HRService hrService;
    private final EmployerService employerService;

    @MyAutowired
    public WorkPresenter(ClientService clientService, HRService hrService, EmployerService employerService) {
        this.clientService = clientService;
        this.hrService = hrService;
        this.employerService = employerService;

        System.out.println("was WorkPresenter");

        clientService.submitResume("some resume");
        hrService.lookResume("some resume");
        employerService.lookResume("some resume");

    }

    public void present() {
        Client client = new Client("Andrey", 20, "Very Cool Man");
        clientService.submitResume(client.getResume());
        hrService.lookResume(client.getResume());
        employerService.lookResume(client.getResume());
    }
}
