package com.example.sapp3.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;
}
