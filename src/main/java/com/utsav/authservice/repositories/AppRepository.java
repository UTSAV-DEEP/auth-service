package com.utsav.authservice.repositories;

import com.utsav.authservice.model.entities.App;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface AppRepository extends JpaRepository<App, Long> {

    @Query(value = "SELECT a FROM App a WHERE a.token = :appToken and a.deleted = 0")
    App findAppByAppToken(@Param("appToken") String appToken);

}
