package com.bqt.newspaer.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Role {

    @Id
    @Column(name = "role_id", nullable = false, length = 10)
    private String roleId;

    @Column(name = "name", nullable = false, length = 25)
    private String name;

    @OneToMany(mappedBy = "role")
    private List<User> users = new ArrayList<>();

}
