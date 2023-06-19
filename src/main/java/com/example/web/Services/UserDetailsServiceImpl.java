package com.example.web.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.web.Model.CustomUserDetails;
import com.example.web.Model.User;
import com.example.web.Repositories.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userRepository.getUserByUsername(username);
		if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
         
        return new CustomUserDetails(user);
	}

}
