package com.example.Controller.Admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.Entity.Admin;
import com.example.Service.AdminService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService adminService;
	
	@GetMapping("/admin")
	public String admin(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		Admin loggedInAdmin = (Admin) session.getAttribute("loggedInAdmin");
		if(loggedInAdmin == null) {
			return "redirect:/admin/login";
		}
		model.addAttribute("loggedInAdmin", loggedInAdmin);
		List<Admin> admins = adminService.findAll();
		model.addAttribute("admins", admins);
		Admin admin = new Admin();
		model.addAttribute("admin", admin);
	
		return "Admin/admin";
	}
	

	@GetMapping("/admin/{id}")
	public String update(HttpServletRequest request, Model model, @PathVariable int id) {
		HttpSession session = request.getSession();
		Admin loggedInAdmin = (Admin) session.getAttribute("loggedInAdmin");
		
			if(loggedInAdmin != null) {
			model.addAttribute("loggedInAdmin", loggedInAdmin);
			List<Admin> admins = adminService.findAll();
			model.addAttribute("admins", admins);
			Admin adminGet = adminService.findById(id);
			Admin admin = new Admin();
			if (adminGet != null) {
				admin = adminGet;
			}
			model.addAttribute("admin", admin);
			return "Admin/admin";
		}else {
			return "redirect:/admin/login";
		}
		
	
	}
	
	
	@PostMapping("/admin/save")
	public String saveAdmin(HttpServletRequest request, 
	                        @ModelAttribute Admin admin, 
	                        RedirectAttributes redirectAttributes) {
		HttpSession session = request.getSession();
		Admin loggedInAdmin = (Admin) session.getAttribute("loggedInAdmin");
		if(loggedInAdmin == null) {
			return "redirect:/admin/login";
		}
		
	    try {
	    	
	        Admin existingAdmin = adminService.findByUsername(admin.getUsername());
	        if (existingAdmin != null && existingAdmin.getId() != admin.getId()) {
	            redirectAttributes.addFlashAttribute("danger", "Tên tài khoản này đã được sử dụng!");
	            return "redirect:/admin/admin";
	        }

	        // Nếu điều kiện trên không thỏa mãn, lưu thông tin admin
	        adminService.save(admin);
	        redirectAttributes.addFlashAttribute("success", "Lưu thành công!");
	        return "redirect:/admin/admin";
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("danger", "Lưu thất bại!");
	        return "redirect:/admin/admin";
	    }
	}
	
	@GetMapping("/admin/delete/{id}")
	public String deleteById(HttpServletRequest request, @PathVariable int id, RedirectAttributes redirectAttributes, Model model) {
		
		HttpSession session = request.getSession();
		Admin loggedInAdmin = (Admin) session.getAttribute("loggedInAdmin");
		if(loggedInAdmin == null) {
			return "redirect:/admin/login";
		}
		try {
			
			Admin getAdmin = adminService.findByUsername(loggedInAdmin.getUsername());
			if(getAdmin != null && getAdmin.getId() == id) {
				redirectAttributes.addFlashAttribute("danger", "Không thể xóa tài khoản đang đăng nhập!");
				return "redirect:/admin/admin";
			}
			adminService.deleteById(id);
			redirectAttributes.addFlashAttribute("success", "Xóa thành công!");
			return "redirect:/admin/admin";
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("danger", "Xóa thất bại!");
			return "redirect:/admin/admin";
		}
	}

}
