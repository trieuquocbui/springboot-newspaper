package com.bqt.newspaper.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
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
@Builder
public class User {
    @Id
    @Column(nullable = false, length = 100)
    private String username;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(length = 64)
    private String thumbnail;

    @Column(nullable = false,length = 20)
    private SocialProvider socialProvider;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    private Role role;
}
