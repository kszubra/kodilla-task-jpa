package com.kodilla.test.entitygraph;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import com.kodilla.test.entitygraph.domain.User;

@SpringBootTest
class JpaTaskTestSuite {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Test
    void shouldPersistUsers() {
        //Given
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        User user = new User(null, "Jan", "Kowalski", null, null);

        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.flush();
        entityManager.getTransaction().commit();

        //When
        Long id = user.getId();
        User loadedUser = entityManager.find(User.class, id);
        entityManager.close();

        //Then
        Assertions.assertEquals(user, loadedUser);
    }
}
