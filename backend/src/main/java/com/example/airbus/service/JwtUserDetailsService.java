package com.example.airbus.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.airbus.entity.UserEntity;
import com.example.airbus.repository.UserRepository;
import com.example.airbus.dto.UserPrincipal;


@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
    UserRepository userRepository;

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		if ("javainuse".equals(username)) {
//			return new User("javainuse", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
//					new ArrayList<>());
//		} else {
//			throw new UsernameNotFoundException("User not found with username: " + username);
//		}
//	}
	
	@Override
    public UserPrincipal loadUserByUsername(String email) throws UsernameNotFoundException {

    	UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }
       
        List<GrantedAuthority> authorities = new ArrayList<>();
       
        Integer userId = userEntity.getId();
        UserPrincipal userPrincipal = new UserPrincipal(email, userEntity.getPassword(), authorities);
        userPrincipal.setUserType(userEntity.getRole());
       
        return userPrincipal;
    }

}