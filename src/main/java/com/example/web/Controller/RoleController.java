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

import com.example.web.Model.Role;
import com.example.web.Services.RoleService;



@Controller
@RequestMapping("/roles")
public class RoleController {
	@Autowired
	private RoleService roleService;

	@GetMapping
	public String viewAllRole(Model model) {
		List<Role> listRole = roleService.listAll();
		model.addAttribute("roles", listRole);
		return "role/index";
	}

	@GetMapping("/create")
	public String showNewRolePage(Model model) {
		Role role = new Role();
		model.addAttribute("role", role);
		return "role/create";
	}

	@PostMapping("/save")
	public String saveRole(@ModelAttribute("role") Role role) {
		roleService.save(role);
		return "redirect:/roles";
	}

	@GetMapping("/edit/{id}")
	public String showEditRolePage(@PathVariable("id") Long id, Model model) {
		Role role = roleService.get(id);

		if (role == null) {
			return "notfound";

		} else {
			model.addAttribute("role", role);
			return "role/edit";
		}
	}

	@GetMapping("/delete/{id}")
	public String deleteRole(@PathVariable("id") Long id) {
		Role role = roleService.get(id);
		if (role == null) {
			return "notfound";
		} else {
			roleService.delete(id);
			return "redirect:/roles";
		}
	}

}
