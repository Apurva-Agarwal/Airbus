package com.example.airbus.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)

public class User implements Serializable {

	private static final long serialVersionUID = 2193926775940580904L;

	private Integer id;

	private String firstName;

	private String lastName;

	private String email;

	private String password;

	private String role;

	private String country;
	
	private String token;
}
