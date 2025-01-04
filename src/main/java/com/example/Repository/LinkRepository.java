package com.example.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Entity.Link;

@Repository
public interface LinkRepository extends JpaRepository<Link, Integer> {
	Link findByLink(String link);
	Link findByShortenCode(String shortenCode);
}
