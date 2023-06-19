package com.example.web.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.web.Model.User;
import com.example.web.Services.RoleService;
import com.example.web.Services.UserService;


@Controller
@RequestMapping("/users")
@ComponentScan("com.example.web.Services")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@GetMapping
	public String viewAllUser(Model model) {
		List<User> listUsers = userService.listAll();
		model.addAttribute("users", listUsers);
		return "users/index";
	}
	
	@GetMapping("/new")
	public String showNewUserPage(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("roles", roleService.listAll());
		return "users/create";
	}
	
	@PostMapping("/save")
	public String saveUser(@ModelAttribute("user") User user) 
			throws IOException{
		if (user.getPassword().isEmpty()) {
			user.setPassword(userService.get(user.getId()).getPassword());
		}else {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		user.setEnabled(true);
		userService.save(user);
		return "redirect:/users";
	}
	
	
	@GetMapping("/edit/{id}")
	public String showEditUserPage(@PathVariable("id") Long id, Model model) {
		User user = userService.get(id);
		
		if(user == null) {
			return "notfound";
		} else {
			model.addAttribute("user", user);
			model.addAttribute("roles", roleService.listAll());
			return "users/edit";
		}
	}
	
	@GetMapping("/delete/{id}")
	public String deleteRUser(@PathVariable("id") Long id) {
		User user = userService.get(id);
		if(user == null) {
			return "notfound";
		} else {
			userService.delete(id);
			return "redirect:/users";
		}
	}
}
