package com.example.Controller.Admin;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.Entity.Admin;
import com.example.Entity.Domain;
import com.example.Entity.Link;
import com.example.Service.DomainService;
import com.example.Service.LinkService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/admin")
public class LinkController {

	@Autowired
	LinkService linkService;
	
	@Autowired
	DomainService domainService;
	
	@GetMapping("/link")
	public String Link(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		Admin loggedInAdmin = (Admin) session.getAttribute("loggedInAdmin");
		
		if(loggedInAdmin == null) {
			return "redirect:/admin/login";
		}
		
		List<Link> links = linkService.findAll();
		model.addAttribute("links", links);
		
		Domain domain = domainService.findById(1);
		model.addAttribute("domain", domain);
		
		model.addAttribute("loggedInAdmin", loggedInAdmin);
		
		return "Admin/link";
	}
	
	@PostMapping("/domain/save")
	public String postMethodName(@RequestParam("domain") String domain, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		Admin loggedInAdmin = (Admin) session.getAttribute("loggedInAdmin");
		if(loggedInAdmin == null) {
			return "redirect:/admin/login";
		}
		
		Domain getDomain = domainService.findById(1);
		try {
			getDomain.setDomain(domain);
			domainService.save(getDomain);
			redirectAttributes.addFlashAttribute("success", "Cập nhật tên miền thành công!");
		}catch (Exception e) {
			redirectAttributes.addFlashAttribute("danger", "Cập nhật tên miền thất bại!");
		}
		return "redirect:/admin/link";
	}
	
	@GetMapping("/link/delete/{id}")
	public String deleteLink(@PathVariable int id, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		Admin loggedInAdmin = (Admin) session.getAttribute("loggedInAdmin");
		if(loggedInAdmin == null) {
			return "redirect:/admin/login";
		}
		
		Link link = linkService.findById(id);
		try {
			linkService.delele(link);
			redirectAttributes.addFlashAttribute("success", "Xóa link thành công!");
			return "redirect:/admin/link";
		}catch (Exception e) {
			// TODO: handle exception
			redirectAttributes.addFlashAttribute("danger", "Xóa link thất bại!");
			return "redirect:/admin/link";
		}
	}
		@GetMapping("/link/delete-all")
		public String deleteAll(RedirectAttributes redirectAttributes, HttpServletRequest request) {
			HttpSession session = request.getSession();
			Admin loggedInAdmin = (Admin) session.getAttribute("loggedInAdmin");
			if(loggedInAdmin == null) {
				return "redirect:/admin/login";
			}
			
			try {
				linkService.deleteAll();
				redirectAttributes.addFlashAttribute("success", "Xóa thành công!");
			}catch (Exception e) {
				redirectAttributes.addFlashAttribute("danger", "Xóa thất bại!");
			}
			return "redirect:/admin/link";
		}
	}
	

