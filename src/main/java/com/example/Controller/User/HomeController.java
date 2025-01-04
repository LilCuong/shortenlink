package com.example.Controller.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.Entity.Domain;
import com.example.Entity.Link;
import com.example.Service.DomainService;
import com.example.Service.LinkService;
import com.example.Service.RandomCodeGenerator;


@Controller
public class HomeController {
	
	@Autowired
	LinkService linkService;
	
	@Autowired
	DomainService domainService;
	
	@Autowired
	RandomCodeGenerator randomCodeGenerator;

	@GetMapping({"/index", "" , "/"})
	public String home() {
		return "User/index";
	}
	
	@PostMapping("/create-link")
	public String createLink(@RequestParam("link") String link, RedirectAttributes redirectAttributes) {
		Link getLink = linkService.findByLink(link);
		Domain domain = domainService.findById(1);
		String domainAddress = domain.getDomain();
		String shortCode;
        do {
            shortCode = randomCodeGenerator.generateRandomCode(4);  
        } while (linkService.findByShortenCode(shortCode) != null);  
        
		if(getLink != null ) {
			if(!getLink.getDomain().equals(domain.getDomain())) {
				getLink.setDomain(domainAddress);
				getLink.setShortenCode(shortCode);
				getLink.setShortenLink(domainAddress + shortCode);
				linkService.save(getLink);
				redirectAttributes.addFlashAttribute("getLink", getLink);
				redirectAttributes.addFlashAttribute("success", "Đã tạo link thành công!");
				return "redirect:/index";
			}
			redirectAttributes.addFlashAttribute("getLink", getLink);
			redirectAttributes.addFlashAttribute("success", "Đã tạo link thành công!");
			return "redirect:/index";
		}
		
		Link newLink = new Link();
		
        newLink.setLink(link);
        newLink.setShortenCode(shortCode);
        newLink.setDomain(domainAddress);
		newLink.setShortenLink(domainAddress + shortCode);
		linkService.save(newLink);
		redirectAttributes.addFlashAttribute("getLink", newLink);
		redirectAttributes.addFlashAttribute("success", "Đã tạo link thành công!");
		return "redirect:/index";
	}
	
	@GetMapping("/{shortenCode}")
	public String redirectToOriginalLink(@PathVariable String shortenCode, RedirectAttributes redirectAttributes) {
		Link link = linkService.findByShortenCode(shortenCode);
		if(link != null) {
			return "redirect:" + link.getLink();
		}else {
			redirectAttributes.addFlashAttribute("danger", "Link không tồn tại!");
			return "redirect:/index";
		}
	}
}
