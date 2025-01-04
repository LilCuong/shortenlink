package com.example.Controller.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Entity.Admin;
import com.example.Service.AdminService;
import com.example.Service.LinkService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminHomeController {
	
	@Autowired
	LinkService linkService;
	
	@Autowired
	AdminService adminService;

	@GetMapping("index")
	public String Home(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		Admin loggedInAdmin = (Admin) session.getAttribute("loggedInAdmin");
		
		if(loggedInAdmin == null) {
			return "redirect:/admin/login";
		}
		model.addAttribute("loggedInAdmin", loggedInAdmin);
		
		int numberOfUrl = linkService.findAll().size();
		model.addAttribute("numberOfUrl", numberOfUrl);
		
		int numberOfAdmin = adminService.findAll().size();
		model.addAttribute("numberOfAdmin", numberOfAdmin);
		
		return "Admin/index";
	}
}
