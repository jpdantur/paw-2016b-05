package edu.tp.paw.model;

public class User {

	private String username;
	private long id;

	public User(String username, long id) {
		super();
		this.username = username;
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [username=" + username + ", id=" + id + "]";
	}
	
	
	

}
