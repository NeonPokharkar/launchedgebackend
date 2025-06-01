package org.launch.edge.repositories;

import org.launch.edge.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByUserName(String userName);

    @Query(value = "SELECT * FROM USERS WHERE USERNAME = :username AND PASSWORD = :pass", nativeQuery = true)
    List<User> checkMatchingUserNameAndPass(String username, String pass);
}
