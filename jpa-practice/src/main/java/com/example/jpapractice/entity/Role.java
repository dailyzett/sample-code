package com.example.jpapractice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    private String id;
    private String name;

    @ElementCollection
    @CollectionTable(name = "role_perm", joinColumns = @JoinColumn(name = "role_id"))
    @Column(name = "perm")
    private Set<String> permissions;
}
