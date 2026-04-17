package com.taskflow.config;

import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;

import com.taskflow.repositories.user.UserRepository;
import com.taskflow.repositories.user.UserRepositoryImpl;
import com.taskflow.service.UserService;

import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;

public class DependencyBinder extends AbstractBinder {

    private final JpaUnit jpaUnit;

    public DependencyBinder(JpaUnit jpaUnit) {
        this.jpaUnit = jpaUnit;
    }

    @Override
    protected void configure() {
        bind(jpaUnit).to(JpaUnit.class);

        bindFactory(EntityManagerFactoryProvider.class)
                .to(EntityManager.class)
                .in(RequestScoped.class);

        bindAsContract(UserService.class).in(Singleton.class);
        bind(UserRepositoryImpl.class).to(UserRepository.class).in(Singleton.class);
    }
}