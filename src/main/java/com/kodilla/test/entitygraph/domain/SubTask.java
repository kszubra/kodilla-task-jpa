package com.kodilla.test.entitygraph.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class SubTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @OneToMany(targetEntity = User.class, mappedBy = "subtask")
    private Set<User> users = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    public SubTask(Long id, String name, TaskStatus status, Task task) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.task = task;
    }
}
