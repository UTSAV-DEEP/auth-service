package com.utsav.authservice.repositories;

import com.utsav.authservice.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT u FROM User u WHERE u.app.token = :appToken and u.deleted = 0")
    List<User> findUserByAppToken(@Param("appToken") String appToken);

    @Query(value = "select u from User u where u.id = :id and u.deleted = 0")
    User findUserById(@Param("id") long id);

    @Query(value = "select u from User u where u.email = :email and u.deleted = 0")
    User findUserByEmail(@Param("email") String email);

    @Query(value = "SELECT u FROM User u WHERE u.deleted = 0")
    List<User> findAllUsers();

    @Query(value = "SELECT u FROM User u WHERE u.deleted = 0 and u.app.name = :appName")
    List<User> findAllUsersOfApp(String appName);

}
