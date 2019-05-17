package br.com.airton.security;

import br.com.airton.Util.DesafioUtil;
import br.com.airton.exceptions.TokenExpired;
import io.jsonwebtoken.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class TokenAuthenticationService {

	  static final String SECRET = "P1T4NG";

	  static final String TOKEN_PREFIX = "Bearer";

	  public static final String HEADER_STRING = "Authorization";



	  public static String addAuthentication(HttpServletResponse response, String username) {



	      String JWT = Jwts.builder()
	              .setSubject(username)
	              //.setExpiration(Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()))
	              .setExpiration(DesafioUtil.addMinutesToDate(3, new Date()))
				  .signWith(SignatureAlgorithm.HS512, SECRET)
	              .compact();

	      //String tokenJWT = TOKEN_PREFIX + " " + JWT;
		  String tokenJWT = JWT;

	      if(response!=null) {
			  System.out.println("AUTENTICACAO COM SUCESSO....RESPONSE NOT NULL");
			  response.addHeader(HEADER_STRING, tokenJWT);
		  }

	      return tokenJWT;

	  }

	  public static Authentication getAuthentication(HttpServletRequest request) throws ExpiredJwtException, TokenExpired{
	      String token = request.getHeader(HEADER_STRING);
		  String user;
	      if (token != null) {
	    	  if(isTokenExpired(token)){
					throw new TokenExpired("Token Expirado");
				}
	      	try {
				user = Jwts.parser()
						.setSigningKey(SECRET)
						.parseClaimsJws(token)
						.getBody()
						.getSubject();
			}catch (ExpiredJwtException ex){
	      		throw ex;
			}
	          if (user  != null) {
	              return new UsernamePasswordAuthenticationToken(user , null, Collections.emptyList() );
	          }
	      }
	      return null;
	  }

	public static String getUserFromAuthenticationToken(HttpServletRequest request) throws TokenExpired, AccessDeniedException {
		String token = request.getHeader(HEADER_STRING);
		String user = null;
		if (token != null) {
				if(isTokenExpired(token)){
					throw new TokenExpired("Token Expirado");
				}else{
					user = getClaimsFromToken(token).getSubject();
				}
			if (user  != null) {
				return user;
			}
		}else{
			throw new AccessDeniedException("Access Denied.");
		}

		return null;
	}

	public static Boolean isTokenExpired(String token) {
		Date expiration = getExpirationDateFromToken(token);
		if(expiration==null){
			return true;
		}
		return expiration.before(new Date());
	}

	public  static Date getExpirationDateFromToken(String token) {
		Date expiration;
		try {
			final Claims claims = getClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (Exception e) {
			expiration = null;
		}
		return expiration;
	}

	private  static Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser()
					.setSigningKey(SECRET)
					.parseClaimsJws(token)
					.getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}
	  public static void main(String[] args) {

	}

}
