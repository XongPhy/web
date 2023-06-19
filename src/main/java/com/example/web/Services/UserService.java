package com.example.web.Services;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.web.Model.User;
import com.example.web.Repositories.UserRepository;

import com.example.web.Exception.UserNotFoundException;

@Service
@Transactional
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	public User findByUsername(String username) {
	    return userRepository.getUserByUsername(username);
	}
	public List<User> listAll() {
		return userRepository.findAll();
	}

	public User save( User user ) {
		userRepository.save(user);
		return user;
	}
	
	public User get(long id) {
		return userRepository.findById(id).orElse(null);
	}

	public void delete(long id) {
		userRepository.deleteById(id);
	}
	public void updateResetPassword(String token, String email ) throws UserNotFoundException {
		User user = userRepository.getUserByEmail(email);
		if(user != null) {
			user.setTokenforgotpassword(token);
			user.setTimeexpire(null);
			LocalDateTime now =LocalDateTime.now();
			LocalDateTime newDateTime = now.plusSeconds(20);
			user.setTimeexpire(newDateTime);
			userRepository.save(user);
		} else {
			throw new UserNotFoundException("Don't exist user have email!" +email);
		}
	}
	
	public User getUserByTokenforgotpassWord(String token) {
        return userRepository.getUserBytokenforgotpassword(token);
    }
	
	public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setTokenforgotpassword(null);
        userRepository.save(user);
    }
	
	public boolean verify(String verificationCode) {
		User user = userRepository.findByVerificationCode(verificationCode);
		if (user == null || user.isEnabled()) {
			return false;
		} else {
			user.setVerificationCode(null);
			user.setEnabled(true);
			userRepository.save(user);
			return true;
		}
	}
}
