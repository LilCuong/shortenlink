package com.example.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "link")
public class Link {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "link", nullable = false, length = 500)
	private String link;
	
	@Column(name = "shorten_link", nullable = false, length = 500)
	private String shortenLink;
	
	@Column(name = "shorten_code", nullable = false, length = 500)
	private String shortenCode;
	
	@Column(name = "domain", nullable = true, length = 250)
	private String domain;
}
