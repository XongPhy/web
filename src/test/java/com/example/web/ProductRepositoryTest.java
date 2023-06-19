package com.example.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import com.example.web.Model.Product;
import com.example.web.Repositories.ProductRepository;

import com.example.web.Repositories.RoleRepository;
import com.example.web.Repositories.SizeRepository;
import com.example.web.Repositories.UserRepository;

import com.example.web.Model.User;

import com.example.web.Model.Role;
import com.example.web.Model.Size;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
@ComponentScan("com.example.web.Security")
public class ProductRepositoryTest {

	
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private SizeRepository sizeRepository;
	@Test
	public void TestCreate()  {
		
		Size sizeM = new Size();
	    sizeM.setName("M");
	    sizeRepository.save(sizeM);
	    
	    Size sizeL = new Size();
	    sizeL.setName("L");
	    sizeRepository.save(sizeL);
	    
	    Size sizeXL = new Size();
	    sizeXL.setName("XL");
	    sizeRepository.save(sizeXL);
	    
	    Size sizeXXL = new Size();
	    sizeXXL.setName("XXL");
	    sizeRepository.save(sizeXXL);
		
		Product product = new Product();
		product.setName("LEVENTS 1 MILLION TEE");
		product.setBrand("Levents"	);
		product.setPrice ((long) 250000);
		product.setSizes(Arrays.asList(sizeM, sizeL, sizeXL,sizeXXL));
		productRepository.save(product);
		
		Product product1 = new Product();
		product1.setName("HALF ZIP HOODIE - TASMAN");	
		product1.setBrand("NowSaiGon");
		product1.setPrice((long) 350000);
		product1.setSizes(Arrays.asList(sizeM, sizeL, sizeXL,sizeXXL));
		productRepository.save(product1);
		
		Product product2 = new Product();
		product2.setName("ROAD RUNNER WASH BLACK");	
		product2.setBrand("DirtyCoins");
		product2.setPrice((long) 150000);
		product2.setSizes(Arrays.asList(sizeM, sizeL, sizeXL,sizeXXL));
		productRepository.save(product2);
		
		Product product3 = new Product();
		product3.setName("UNIFORM SHIRT");	
		product3.setBrand("Fragile");
		product3.setPrice((long) 200000);
		product3.setSizes(Arrays.asList(sizeM, sizeL, sizeXL,sizeXXL));
		productRepository.save(product3);
		
		Role roleUser = new Role();
		roleUser.setName("USER");
		roleRepository.save(roleUser);
		
		Role roleCreater = new Role();
		roleCreater.setName("CREATER");
		roleRepository.save(roleCreater);
		
		Role roleEditor = new Role();
		roleEditor.setName("EDITOR");
		roleRepository.save(roleEditor);
		
		Role roleAdmin = new Role();
		roleAdmin.setName("ADMIN");
		roleRepository.save(roleAdmin);
		
		User user1 = new User();
		user1.setUsername("nhut");
		user1.setEmail("nhut@gmail.com");
		user1.setEnabled(true);
		user1.setPassword(passwordEncoder.encode("123"));
		user1.addRoles(roleUser);
		userRepository.save(user1);
		
		User user2 = new User();
		user2.setUsername("nhut1");
		user2.setEmail("nhut1@gmail.com");
		user2.setEnabled(true);
		user2.setPassword(passwordEncoder.encode("123"));
		user2.addRoles(roleUser);
		userRepository.save(user2);
		
		User creater1 = new User();
		creater1.setUsername("phi");
		creater1.setEmail("phi@gmail.com");
		creater1.setEnabled(true);
		creater1.setPassword(passwordEncoder.encode("1234"));
		creater1.addRoles(roleCreater);
		userRepository.save(creater1);
		
		User creater2 = new User();
		creater2.setUsername("phi1");
		creater2.setEmail("phi1@gmail.com");
		creater2.setEnabled(true);
		creater2.setPassword(passwordEncoder.encode("1234"));
		creater2.addRoles(roleCreater);
		userRepository.save(creater2);
		
		User editor1 = new User();
		editor1.setUsername("nhat");
		editor1.setEmail("nhat@gmail.com");
		editor1.setEnabled(true);
		editor1.setPassword(passwordEncoder.encode("12345"));
		editor1.addRoles(roleEditor);
		userRepository.save(editor1);
		
		User editor2 = new User();
		editor2.setUsername("nhat1");
		editor2.setEmail("nhat1@gmail.com");
		editor2.setEnabled(true);
		editor2.setPassword(passwordEncoder.encode("12345"));
		editor2.addRoles(roleEditor);
		userRepository.save(editor2);
		
		User admin1 = new User();
		admin1.setUsername("tam");
		admin1.setEmail("tam@gmail.com");
		admin1.setEnabled(true);
		admin1.setPassword(passwordEncoder.encode("123456"));
		admin1.addRoles(roleAdmin);
		userRepository.save(admin1);
		
		User admin2 = new User();
		admin2.setUsername("tam1");
		admin2.setEmail("tam1@gmail.com");
		admin2.setEnabled(true);
		admin2.setPassword(passwordEncoder.encode("123456"));
		admin2.addRoles(roleAdmin);
		userRepository.save(admin2);
		
		assertThat("1").isEqualTo("1");

	}
}
