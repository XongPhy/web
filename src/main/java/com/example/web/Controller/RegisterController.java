package com.example.web.Controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.web.Model.User;
import com.example.web.Services.RoleService;

import com.example.web.Services.SendMailService;
import com.example.web.Services.UserService;

import com.example.web.Utils.Utilities;
import net.bytebuddy.utility.RandomString;

@Controller
public class RegisterController {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleService roleService;
	@Autowired
	private SendMailService sendMailService;
	@Autowired
	private UserService userService;
	
	@GetMapping("/register")
	public String showNewUser(Model model) {
		User user =new User();
		model.addAttribute("user",user);
		model.addAttribute("roles",roleService.listAll());
		return"auth/register";
	}
	
	@PostMapping("/process_register")
	public String processRegister(User user ,HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
		String encodePaddword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodePaddword);
		user.addRoles(roleService.getbyName("USER"));
		user.setVerificationCode(RandomString.make(30));
		userService.save(user);
		sendMailService.sendVerificationEmail(user, Utilities.getSiteURL(request));
		
		return "auth/register_success";
	}
	@GetMapping("/verify")
	public String verifyUser(@Param("code") String code, Model model) {
		if (userService.verify(code)) {
			model.addAttribute("message", "Congratulations, your account has been verified.");
		} else {
			model.addAttribute("error", "Sorry, we could not verify account. It maybe already verified,\n"
					+ "        or verification code is incorrect.");
		}
		return "auth/result_Verify_form";
	}
}
