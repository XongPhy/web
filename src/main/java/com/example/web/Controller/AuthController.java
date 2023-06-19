package com.example.web.Controller;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.web.Exception.UserNotFoundException;
import com.example.web.Model.User;
import com.example.web.Services.UserService;
import com.example.web.Utils.Utilities;
import net.bytebuddy.utility.RandomString;

@Controller
public class AuthController {
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private UserService userService;

	@GetMapping("/forgot_password")
	public String showForgotPasswordForm() {
		return "auth/forgot_password_form";
	}
	
	public void sendEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("javaS5@cntt.com", "Song Phi");
		helper.setTo(recipientEmail);

		String subject = "Link password";

		String content = "<p>Chao`,</p>" + "<p>You have requested to reset your password.</p>"
				+ "<p>Click the link below to change your password:</p>" + "<p><a href=\"" + link
				+ "\">Change my password</a></p>" + "<br>" + "<p>Ignore this email if you do remember your password, "
				+ "or you have not made the request.</p>";

		helper.setSubject(subject);

		helper.setText(content, true);

		javaMailSender.send(message);
	}
	
	@PostMapping("/forgot_password")
	public String processForgotPassword(HttpServletRequest request, Model model) {
		String email = request.getParameter("email");
		String token = RandomString.make(30);
		
		try {
			userService.updateResetPassword(token, email);
			String resetPasswordLink = Utilities.getSiteURL(request) + "/reset_password?token=" + token;
			sendEmail(email, resetPasswordLink);
			model.addAttribute("message", "We have sent a reset password link to your email. Please check.");

		} catch (UserNotFoundException ex) {
			model.addAttribute("error", ex.getMessage());
		} catch (UnsupportedEncodingException | MessagingException e) {
			model.addAttribute("error", "Error while sending email");
		}

		return "auth/forgot_password_form";
	}
	
	@GetMapping("/reset_password")
	public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
		User user = userService.getUserByTokenforgotpassWord(token);
		model.addAttribute("token", token);

		if(user == null || user.getTimeexpire().isBefore(LocalDateTime.now())) {
			model.addAttribute("message","Invalid Token");
			return "auth/notfound";
		}
		return "auth/reset_password_form";
	}

	@PostMapping("/reset_password")
	public String processResetPassword(HttpServletRequest request, Model model) {
		String token = request.getParameter("token");
		String password = request.getParameter("password");

		User user = userService.getUserByTokenforgotpassWord(token);
		model.addAttribute("title", "Reset your password");
		
		if (user == null) {
			model.addAttribute("message", "Invalid Token");
			return "auth/messages";
		}
		
		else {
			userService.updatePassword(user, password);
			model.addAttribute("message", "You have successfully changed your password.");
		}

		return "auth/reset_password_form";
	}
	public void sendEmailService(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("javaS5@cntt.com", "Song Phi");
		helper.setTo(recipientEmail);

		String subject = "Link password";

		String content = "<p>Chao`,</p>" + "<p>You have requested to reset your password.</p>"
				+ "<p>Click the link below to change your password:</p>" + "<p><a href=\"" + link
				+ "\">Change my password</a></p>" + "<br>" + "<p>Ignore this email if you do remember your password, "
				+ "or you have not made the request.</p>";

		helper.setSubject(subject);

		helper.setText(content, true);

		javaMailSender.send(message);
	}
}
