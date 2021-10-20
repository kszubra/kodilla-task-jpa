package com.kodilla.test.entitygraph.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.kodilla.test.entitygraph.enumeration.TaskStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @OneToMany(targetEntity = User.class, mappedBy = "task")
    private Set<User> users = new HashSet<>();

    @OneToMany(targetEntity = SubTask.class, mappedBy = "task")
    private Set<SubTask> subTasks = new HashSet<>();

    public Task(Long id, String name, TaskStatus status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }
}
