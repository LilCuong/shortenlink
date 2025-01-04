package com.example.Service;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class RandomCodeGenerator {

	private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	public static String generateRandomCode(int lenth) {
		Random random = new Random();
		StringBuilder sb = new StringBuilder(lenth);
		
		for(int i = 0; i < lenth; i ++) {
			int index = random.nextInt(BASE62.length());
			sb.append(BASE62.charAt(index));
		}
		
		return sb.toString();
		
		
	}
	
	
}
