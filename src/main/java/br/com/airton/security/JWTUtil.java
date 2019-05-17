package br.com.airton.security;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.airton.response.MessageResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
	
	public final String[] PUBLIC_MATCHERS = {};
	public final String[] PUBLIC_MATCHERS_GET = {};
	public final String[] PUBLIC_MATCHERS_POST = {
			"/signin",
			"/signup"
	};
	public final String[] PRIVATE_MATCHERS = {
			"/me"
	};
		
	private String secret = "P1T4NG";

	private Long expiration = 300000L;
	
	public String generateToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}
	
	public boolean tokenValido(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			String username = claims.getSubject();
			Date expirationDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			if (username != null && expirationDate != null && now.before(expirationDate)) {
				return true;
			}
		}
		return false;
	}

	public String getUsername(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			return claims.getSubject();
		}
		return null;
	}
	
	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		}
		catch (Exception e) {
			return null;
		}
	}
	
	public String json(MessageResponse response) {
	   	 ObjectMapper Obj = new ObjectMapper(); 
	   	 try {
				String jsonStr = Obj.writeValueAsString(response);
				return jsonStr;
	   	 } catch (JsonProcessingException e) {
	   		 return "{\"message\": ERRO, "
	   	                + "\"errorCode\": -- "
	   	                + "}";
			}             
	   }

}
