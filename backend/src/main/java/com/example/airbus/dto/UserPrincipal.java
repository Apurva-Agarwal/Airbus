package com.example.airbus.dto;

import java.util.Collection;
import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@EqualsAndHashCode(callSuper = true)
@JsonInclude(Include.NON_NULL)
@Data
public class UserPrincipal extends org.springframework.security.core.userdetails.User {
	private static final long serialVersionUID = 1L;

	private String userType;

	public UserPrincipal(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

}
