package com.bqt.newspaper.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table
@Builder
public class Role {

    @Id
    @Column(nullable = false, length = 10)
    private String id;

    @Column(nullable = false, length = 25)
    private String name;

    @OneToMany(mappedBy = "role")
    private List<User> users = new ArrayList<>();

}
