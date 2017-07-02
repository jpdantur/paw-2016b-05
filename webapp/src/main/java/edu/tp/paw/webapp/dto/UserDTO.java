package edu.tp.paw.webapp.dto;

import javax.xml.bind.annotation.XmlRootElement;

import edu.tp.paw.model.User;

@XmlRootElement
public class UserDTO {
	
	private Long id;
	private String firstName;
	private String lastName;
	private String username;
	private String email;
	
	private UserDTO() {
		
	}
	
	public static UserDTO fromUser(final User user) {
		final UserDTO userDTO = new UserDTO();
		
		userDTO.id = user.getId();
		userDTO.firstName = user.getFirstName();
		userDTO.lastName = user.getLastName();
		userDTO.username = user.getUsername();
		userDTO.email = user.getEmail();
		
		return userDTO;
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", username=" + username
				+ ", email=" + email + "]";
	}
	
}
