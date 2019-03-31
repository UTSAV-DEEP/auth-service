package com.utsav.authservice.repositories;

import com.utsav.authservice.model.entities.App;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AppRepository extends JpaRepository<App, Long> {



}
