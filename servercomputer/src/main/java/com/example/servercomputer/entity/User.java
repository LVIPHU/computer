package com.example.servercomputer.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.example.servercomputer.entity.entityenum.EGender;
import com.example.servercomputer.entity.entityenum.EStatusUser;

import io.swagger.models.auth.In;
import lombok.Data;

@Entity
@Table(name = "users")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank(message = "Email is mandatory")
	@Column(nullable = false, unique = true)
	private String email;
	@NotBlank(message = "Password is mandatory")
	@Column(nullable = false)
	private String password;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String address;
	private EGender gender;
	private LocalDate birthday;
	private EStatusUser status;
	private String resetPassToken;
	@ManyToMany()
	@JoinTable(name = "user_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<Order> orders;
	public User(){

	}
	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}