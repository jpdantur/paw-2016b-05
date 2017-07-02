package edu.tp.paw.webapp.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JWTTokenDTO {
	
	private String idToken;
	private String refreshToken;
	
	private JWTTokenDTO() {
		
	}
	
	public static JWTTokenDTO createWithTokens(final String idToken, final String refreshToken) {
		final JWTTokenDTO jwtTokenDTO = new JWTTokenDTO();
		jwtTokenDTO.idToken = idToken;
		jwtTokenDTO.refreshToken = refreshToken;
		return jwtTokenDTO;
	}

	public String getIdToken() {
		return idToken;
	}

	public void setIdToken(String idToken) {
		this.idToken = idToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Override
	public String toString() {
		return "JWTTokenDTO [idToken=" + idToken + ", refreshToken=" + refreshToken + "]";
	}
}
