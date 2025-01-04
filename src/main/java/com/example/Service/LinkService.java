package com.example.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Entity.Link;
import com.example.Repository.LinkRepository;

@Service
public class LinkService {

	@Autowired
	LinkRepository linkRepository;
	
	public List<Link> findAll(){
		return linkRepository.findAll();
	}
	
	public Link findByLink(String link) {
		return linkRepository.findByLink(link);
	}
	

	public Link findByShortenCode(String shortenCode) {
		return linkRepository.findByShortenCode(shortenCode);
	}
	
	public void save(Link link) {
		linkRepository.save(link);
	}
	public Link findById(int id) {
		return linkRepository.findById(id).orElseThrow();
	}
	
	public void delele(Link link) {
		linkRepository.delete(link);
	}
	
	public void deleteAll(){
		 linkRepository.deleteAll();
	}
}
