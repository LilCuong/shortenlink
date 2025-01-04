package com.example.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Entity.Admin;
import com.example.Repository.AdminRepository;

@Service
public class AdminService {

	@Autowired
	AdminRepository adminRepository;
	
	public Admin findByUsername(String username) {
		return adminRepository.findByUsername(username);
	}
	
	public List<Admin> findAll(){
		return adminRepository.findAll();
	}
	
	public Admin findById(int id) {
		return adminRepository.findById(id).orElseThrow();
	}
	
	public void save(Admin admin) {
		adminRepository.save(admin);
	}
	public void deleteById(int id) {
		adminRepository.deleteById(id);
	}
}
