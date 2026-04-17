package com.taskflow.repositories.user;

import com.taskflow.model.User;
import com.taskflow.repositories.exceptions.UserPersistenceException;

import jakarta.inject.Inject;
import jakarta.inject.Provider;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;

public class UserRepositoryImpl implements UserRepository {

    private final Provider<EntityManager> em;

    @Inject
    public UserRepositoryImpl(Provider<EntityManager> em) {
        this.em = em;
    }

    @Override
    public void createUser(User user) {
        EntityManager entityManager = em.get();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(user);
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new UserPersistenceException("Error creating user", e);
        }
    }
}
