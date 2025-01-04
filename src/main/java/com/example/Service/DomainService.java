package com.example.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Entity.Domain;
import com.example.Repository.DomainRepository;

@Service
public class DomainService {

	@Autowired
	DomainRepository domainRepository;
	
	public Domain findById(int id) {
		return domainRepository.findById(id).orElseThrow();
	}
	
	public void save(Domain domain) {
		domainRepository.save(domain);
	}
}
