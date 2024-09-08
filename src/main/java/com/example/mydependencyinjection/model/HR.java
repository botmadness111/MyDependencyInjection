package com.example.mydependencyinjection.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class HR {
    String name;
    List<Client> clients;
}
