package com.example.mydependencyinjection.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Employer {
    String name;
    List<Client> clients;
}
