package com.example.web.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.web.Model.Size;
import com.example.web.Services.SizeService;


@Controller
@RequestMapping("/sizes")
public class SizeController {
	@Autowired
	private SizeService sizeService;
	
	@GetMapping
	public String viewAllSizes(Model model) {
		List<Size> listSizes = sizeService.listAll();
		model.addAttribute("sizes", listSizes);
		return "sizes/index";
	}

	@GetMapping("/create")
	public String showNewSizePage(Model model) {
		Size size = new Size();
		model.addAttribute("size", size);
		return "sizes/create";
	}

	@PostMapping("/save")
	public String saveSize(@ModelAttribute("size") Size size) {
		sizeService.save(size);
		return "redirect:/sizes";
	}

	@GetMapping("/edit/{id}")
	public String showEditSizePage(@PathVariable("id") Long id, Model model) {
		Size size = sizeService.get(id);

		if (size == null) {
			return "notfound";
			
		} else {
			model.addAttribute("sizes", sizeService.listAll());
			model.addAttribute("size", size);
			return "sizes/edit";
		}
	}

	@GetMapping("/delete/{id}")
	public String deleteSize(@PathVariable("id") Long id) {
		Size size = sizeService.get(id);
		if (size == null) {
			return "notfound";
		} else {
			sizeService.delete(id);
			return "redirect:/sizes";
		}
	}
}
