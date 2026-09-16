package org.example.cliniquepro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.cliniquepro.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
