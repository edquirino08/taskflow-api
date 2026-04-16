package com.taskflow.config;

import org.glassfish.jersey.internal.inject.AbstractBinder;

import com.taskflow.service.UserService;

import jakarta.inject.Singleton;

public class DependencyBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bindAsContract(UserService.class).in(Singleton.class);
    }
}
