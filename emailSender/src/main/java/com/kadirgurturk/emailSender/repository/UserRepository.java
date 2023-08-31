package com.kadirgurturk.emailSender.repository;

import com.kadirgurturk.emailSender.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmailIgnoreCase(String email);

    Boolean existsByEmail(String email);
}
