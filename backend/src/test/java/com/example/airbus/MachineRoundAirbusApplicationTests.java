package com.example.airbus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;

import com.example.airbus.dto.User;
import com.example.airbus.entity.UserEntity;
import com.example.airbus.repository.UserRepository;

@SpringBootTest
class MachineRoundAirbusApplicationTests {

	@Autowired
	UserRepository userRepository;

	// To get list of all users
	@Test
	public void testListUsers() {
		List<UserEntity> users = (List<UserEntity>) userRepository.findAll();
		assertThat(users).size().isGreaterThan(0);
	}

	// To create user
	@Test
	public void testCreateUser() {
		User user = new User();
		user.setEmail("testUser@gmail.com");
		user.setPassword("12345");
		user.setFirstName("Test");
		user.setLastName("user");
		user.setRole("GENERAL");
		user.setCountry("USA");
		assertNotNull(userRepository.findByEmail("testUser@gmail.com"));
	}

	// To get a specific user
	@Test
	public void testRead() {
		UserEntity user = userRepository.findById(1).get();
		assertEquals("admin", user.getFirstName());
	}

	// To update user
	@Test
	@Order(4)
	public void testUpdate() {
		UserEntity user = userRepository.findById(1).get();
		user.setCountry("UK");
		;
		userRepository.save(user);
		assertNotEquals("UK", userRepository.findById(1).get().getCountry());
	}

	// To delete user
	@Test
	@Order(5)
	public void testDelete() {
		userRepository.deleteById(1);
		assertThat(userRepository.existsById(1)).isFalse();
	}

}
