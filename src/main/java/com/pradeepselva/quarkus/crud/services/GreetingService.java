package com.pradeepselva.quarkus.crud.services;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GreetingService{

    public String hello() {
        return "Hello RESTEasy";
    }

    public String helloName(String name) { return String.format("Hello %s", name); }
}