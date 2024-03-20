package dev.kush.securityall.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;
    private String password;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "role_id")
    private Roles role;

}
