package com.bqt.newspaper.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table
public class User {

    @Id
    @Column(name = "username", nullable = false, length = 100)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "fullName", nullable = false, length = 100)
    private String fullName;

    @Column(name = "thumbnail", length = 64)
    private String thumbnail = "peaa3cc2f6-51c8-47bb-a1ad-21d8f3a939b1";

    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;
}
