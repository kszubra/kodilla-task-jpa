package com.kodilla.test.entitygraph;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import com.kodilla.test.entitygraph.domain.SubTask;
import com.kodilla.test.entitygraph.domain.Task;
import com.kodilla.test.entitygraph.domain.User;
import com.kodilla.test.entitygraph.enumeration.TaskStatus;

@SpringBootTest
class TaskTestSuite {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Test
    void shouldNotNPlusOneProblemOccure() {
        //Given
        List<Long> savedTasks = initiateExampleData();
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //When
        System.out.println("****************** BEGIN OF FETCHING *******************");
        System.out.println("*** STEP 1 – query for tasks ***");


        TypedQuery<Task> query = entityManager.createQuery(
                "from Task "
                        + " where id in (" + taskIds(savedTasks) + ")",
                Task.class);

        EntityGraph<Task> graph = entityManager.createEntityGraph(Task.class);
        graph.addAttributeNodes("status", "name");
        graph.addSubgraph("subTasks").addSubgraph("users");
        graph.addSubgraph("users").addAttributeNodes("name", "surname");

        query.setHint("javax.persistence.fetchgraph", graph);

        List<Task> tasks = query.getResultList();

        for (Task task : tasks) {
            System.out.println("*** STEP 2 – read data from task ***");
            System.out.println(task);

            System.out.println("*** STEP 3 – read the name ***");
            System.out.println(task.getName());

            System.out.println("*** STEP 4 – read the status ***");
            System.out.println(task.getStatus());

            System.out.println("*** STEP 5 – read users ***");
            for (User user : task.getUsers()) {
                System.out.println("*** STEP 6 – read the user ***");
                System.out.println(user);
                System.out.println("*** STEP 7 – read name ***");
                System.out.println(user.getName());
                System.out.println("*** STEP 8 – read surname ***");
                System.out.println(user.getSurname());
            }

            System.out.println("*** STEP 9 – read subtasks ***");
            for (SubTask subtask : task.getSubTasks()) {
                System.out.println("*** STEP 10 – read the subtask ***");
                System.out.println(subtask);
                System.out.println("*** STEP 11 – read the subtask name ***");
                System.out.println(subtask.getName());

                System.out.println("*** STEP 12 – read the subtask status ***");
                System.out.println(subtask.getStatus());

                System.out.println("*** STEP 13 – read subtask users ***");
                for (User user : subtask.getUsers()) {
                    System.out.println("*** STEP 14 – read the user ***");
                    System.out.println(user);
                    System.out.println("*** STEP 15 – read name ***");
                    System.out.println(user.getName());
                    System.out.println("*** STEP 16 – read surname ***");
                    System.out.println(user.getSurname());
                }
            }

        }

        System.out.println("****************** END OF FETCHING *******************");
    }

    @Test
    void shouldNPlusOneProblemOccure() {
        //Given
        List<Long> savedTasks = initiateExampleData();
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //When
        System.out.println("****************** BEGIN OF FETCHING *******************");
        System.out.println("*** STEP 1 – query for tasks ***");

        List<Task> tasks =
                entityManager.createQuery(
                        "from Task "
                                + " where id in (" + taskIds(savedTasks) + ")",
                        Task.class).getResultList();

        for (Task task : tasks) {
            System.out.println("*** STEP 2 – read data from task ***");
            System.out.println(task);

            System.out.println("*** STEP 3 – read the name ***");
            System.out.println(task.getName());

            System.out.println("*** STEP 4 – read the status ***");
            System.out.println(task.getStatus());

            System.out.println("*** STEP 5 – read users ***");
            for (User user : task.getUsers()) {
                System.out.println("*** STEP 6 – read the user ***");
                System.out.println(user);
                System.out.println("*** STEP 7 – read name ***");
                System.out.println(user.getName());
                System.out.println("*** STEP 8 – read surname ***");
                System.out.println(user.getSurname());
            }

            System.out.println("*** STEP 9 – read subtasks ***");
            for (SubTask subtask : task.getSubTasks()) {
                System.out.println("*** STEP 10 – read the subtask ***");
                System.out.println(subtask);
                System.out.println("*** STEP 11 – read the subtask name ***");
                System.out.println(subtask.getName());

                System.out.println("*** STEP 12 – read the subtask status ***");
                System.out.println(subtask.getStatus());

                System.out.println("*** STEP 13 – read subtask users ***");
                for (User user : subtask.getUsers()) {
                    System.out.println("*** STEP 14 – read the user ***");
                    System.out.println(user);
                    System.out.println("*** STEP 15 – read name ***");
                    System.out.println(user.getName());
                    System.out.println("*** STEP 16 – read surname ***");
                    System.out.println(user.getSurname());
                }
            }

        }

        System.out.println("****************** END OF FETCHING *******************");
    }

    private String taskIds(List<Long> taskIds) {
        return taskIds.stream()
                .map(n -> "" + n)
                .collect(Collectors.joining(","));
    }

    private List<Long> initiateExampleData() {
        Task bringPeaceToGalaxy = new Task(null, "Bring peace to galaxy", TaskStatus.DONE);
        SubTask subtask11 = new SubTask(null, "task 1 subtask 1", TaskStatus.DONE, bringPeaceToGalaxy);
        SubTask subtask12 = new SubTask(null, "task 1 subtask 2", TaskStatus.IN_REVIEW, bringPeaceToGalaxy);
        SubTask subtask13 = new SubTask(null, "task 1 subtask 3", TaskStatus.IN_PROGRESS, bringPeaceToGalaxy);

        User user1 = new User(null, "AAA", "BBB", bringPeaceToGalaxy, subtask11);
        User user2 = new User(null, "CCC", "DDD", bringPeaceToGalaxy, subtask12);
        User user3 = new User(null, "EEE", "FFF", bringPeaceToGalaxy, subtask13);
        User user4 = new User(null, "GGG", "HHH", bringPeaceToGalaxy, subtask11);

        bringPeaceToGalaxy.getSubTasks().addAll(List.of(subtask11, subtask12, subtask13));
        bringPeaceToGalaxy.getUsers().addAll(List.of(user1, user2, user3, user4));
        subtask11.getUsers().addAll(List.of(user1, user4));
        subtask12.getUsers().addAll(List.of(user2));
        subtask13.getUsers().addAll(List.of(user3));

        Task task2 = new Task(null, "Task 2", TaskStatus.IN_PROGRESS);
        SubTask subtask21 = new SubTask(null, "task 2 subtask 1", TaskStatus.DONE, task2);
        SubTask subtask22 = new SubTask(null, "task 2 subtask 2", TaskStatus.IN_PROGRESS, task2);
        SubTask subtask23 = new SubTask(null, "task 2 subtask 3", TaskStatus.IN_PROGRESS, task2);

        User person5 = new User(null, "III", "JJJ", task2, subtask22);
        User person6 = new User(null, "KKK", "LLL", task2, subtask22);
        User person7 = new User(null, "MMM", "NNN", task2, subtask23);
        User person8 = new User(null, "OOO", "PPP", task2, subtask22);

        task2.getSubTasks().addAll(List.of(subtask21, subtask22, subtask23));
        task2.getUsers().addAll(List.of(person5, person6, person7, person8));
        subtask22.getUsers().addAll(List.of(person5, person6));
        subtask22.getUsers().addAll(List.of(person7));
        subtask23.getUsers().addAll(List.of(person8));

        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(user1);
        em.persist(user2);
        em.persist(user3);
        em.persist(user4);
        em.persist(person5);
        em.persist(person6);
        em.persist(person7);
        em.persist(person8);
        em.persist(subtask11);
        em.persist(subtask12);
        em.persist(subtask13);
        em.persist(subtask21);
        em.persist(subtask22);
        em.persist(subtask23);
        em.persist(bringPeaceToGalaxy);
        em.persist(task2);
        em.flush();
        em.getTransaction().commit();
        em.close();

        return List.of(bringPeaceToGalaxy.getId(), task2.getId());
    }
}
