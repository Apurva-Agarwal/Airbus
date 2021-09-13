package com.example.airbus.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.airbus.dto.User;
import com.example.airbus.entity.UserEntity;
import com.example.airbus.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	public List<User> listUsers() {
		List<UserEntity> userEntitylist = userRepository.findAll();
		System.out.println(userEntitylist.size());
		System.out.println(userEntitylist);
		List<User> users = new ArrayList<>();
		for (UserEntity userEntity : userEntitylist) {
			if (userEntity.getRole().equalsIgnoreCase("GENERAL")) {
				User user = new User();
				user.setId(userEntity.getId());
				user.setEmail(userEntity.getEmail());
				user.setFirstName(userEntity.getFirstName());
				user.setLastName(userEntity.getLastName());
				users.add(user);
			}
		}
		return users;

	}

	public void deleteUser(Integer userId) {
		Optional<UserEntity> userEntityOpt = userRepository.findById(userId);
		if (userEntityOpt.isPresent()) {
			userRepository.delete(userEntityOpt.get());
		}

	}

	public User updateUser(User user) {

		Optional<UserEntity> userEntityOpt = userRepository.findById(user.getId());
		if (userEntityOpt.isPresent()) {
			UserEntity existingUser = new UserEntity();
			existingUser.setEmail(user.getEmail());
			existingUser.setId(user.getId());
			existingUser.setFirstName(user.getFirstName());
			existingUser.setLastName(user.getLastName());
			existingUser.setRole("GENERAL");
			existingUser.setPassword(encoder.encode(user.getPassword()));
			existingUser.setCountry(user.getCountry());

			userRepository.save(existingUser);
		}
		return user;
	}

	public User createUser(User user) {

		UserEntity newUser = new UserEntity();
		newUser.setEmail(user.getEmail());
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setPassword(encoder.encode(user.getPassword()));
		newUser.setRole("GENERAL");
		newUser.setCountry(user.getCountry());
		userRepository.save(newUser);

		return user;
	}

	public User getUserById(Integer userId) {
		User user = new User();
		Optional<UserEntity> userEntityOpt = userRepository.findById(userId);
		if (userEntityOpt.isPresent()) {
			user.setId(userEntityOpt.get().getId());
			user.setEmail(userEntityOpt.get().getEmail());
			user.setFirstName(userEntityOpt.get().getFirstName());
			user.setLastName(userEntityOpt.get().getLastName());
		}
		return user;
	}

	public User findUserByEmail(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);
		User user = new User();
		user.setId(userEntity.getId());
		user.setRole(userEntity.getRole());
		user.setEmail(userEntity.getEmail());
		user.setFirstName(userEntity.getFirstName());
		user.setLastName(userEntity.getLastName());

		return user;
	}

}
