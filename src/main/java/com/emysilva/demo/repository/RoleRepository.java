package com.emysilva.demo.repository;

import com.emysilva.demo.model.Book;
import com.emysilva.demo.model.ERole;
import com.emysilva.demo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
