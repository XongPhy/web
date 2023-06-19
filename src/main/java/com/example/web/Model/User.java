package com.example.web.Model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;



@Entity
@Table(name = "users")
public class User {
	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, length = 255)
	private String username;
	@Column(nullable = false, length = 255)
	private String email;
	@Column(nullable = false, length = 255)
	private String password;
	private boolean enabled;
	@Column(nullable = true,length = 255)
	private String tokenforgotpassword;
	@Column(nullable = true)
	private LocalDateTime timeexpire;
	@Column(name = "verification_code", length = 255)
	private String verificationCode;
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(
			name = "users_roles", 
			joinColumns = @JoinColumn(name = "user_id"), 
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public void addRoles(Role role) {
		this.roles.add(role);
	}
	public User() {
		roles = new HashSet<>();
	}

	public String getTokenforgotpassword() {
		return tokenforgotpassword;
	}

	public void setTokenforgotpassword(String tokenforgotpassword) {
		this.tokenforgotpassword = tokenforgotpassword;
	}

	public LocalDateTime getTimeexpire() {
		return timeexpire;
	}

	public void setTimeexpire(LocalDateTime timeexpire) {
		this.timeexpire = timeexpire;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	
	
	

}
