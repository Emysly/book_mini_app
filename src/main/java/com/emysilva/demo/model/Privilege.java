package com.emysilva.demo.model;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long privilegeId;

    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;

}

