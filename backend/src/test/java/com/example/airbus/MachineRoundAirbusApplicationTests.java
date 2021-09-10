package com.example.airbus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.airbus.entity.UserEntity;
import com.example.airbus.repository.UserRepository;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)

class MachineRoundAirbusApplicationTests {

	@Autowired
	UserRepository userRepository;

	@Test
	@Order(1)
	public void testListUsers() {
		List<UserEntity> users = (List<UserEntity>) userRepository.findAll();
		assertThat(users).size().isGreaterThan(0);
	}

	@Test
	@Order(2)
	public void testCreateUser() {
		UserEntity user = new UserEntity();
		user.setEmail("testUser@gmail.com");
		user.setPassword("12345");
		user.setFirstName("Test");
		user.setLastName("user");
		user.setRole("GENERAL");
		user.setCountry("USA");
		userRepository.save(user);
		assertNotNull(userRepository.findByEmail("testUser@gmail.com"));
	}

	@Test
	@Order(3)
	public void testUpdate() {
		UserEntity user = userRepository.findByEmail("testUser@gmail.com");
		user.setCountry("UK");
		userRepository.save(user);
		assertNotEquals("GM", userRepository.findByEmail("testUser@gmail.com").getCountry());
	}
	@Test
	@Order(4)
	public void testUpdate1() {
		UserEntity user = userRepository.findByEmail("testUser@gmail.com");
		user.setCountry("UK");
		userRepository.save(user);
		assertEquals("UK", userRepository.findByEmail("testUser@gmail.com").getCountry());
	}

	@Test
	@Order(5)
	public void testDelete() {
		UserEntity user = userRepository.findByEmail("testUser@gmail.com");
		userRepository.deleteById(user.getId());
		assertThat(userRepository.existsById(user.getId())).isFalse();
	}

}
