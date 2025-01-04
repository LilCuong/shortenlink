package com.example.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Entity.Domain;

@Repository
public interface DomainRepository extends JpaRepository<Domain, Integer> {

}
