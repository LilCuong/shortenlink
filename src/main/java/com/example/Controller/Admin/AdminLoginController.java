package com.example.Controller.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.Entity.Admin;
import com.example.Service.AdminService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminLoginController {
	
	@Autowired
	AdminService adminService;

	@GetMapping("/login")
	public String login() {
		return "Admin/login";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam("username") String username, @RequestParam("password") String password, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		Admin admin = adminService.findByUsername(username);
		
		
		if(admin == null) {
			redirectAttributes.addFlashAttribute("danger", "Không tìm thấy tài khoản admin!");
			return "redirect:/admin/login";
		}
		if(admin.getPassword().equals(password)) {
			HttpSession session = request.getSession();
			session.setAttribute("loggedInAdmin", admin);
			redirectAttributes.addFlashAttribute("success", "Đăng nhập thành công!");
			return "redirect:/admin/index";
		}
		redirectAttributes.addFlashAttribute("danger", "Sai mật khẩu!");
		return "redirect:/admin/login";
	}
}
