package br.com.airton.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import br.com.airton.response.MessageResponse;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private JWTUtil jwtUtil;
	
	private UserDetailsService userDetailsService;
	
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;		
	}
	
	@Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
		
		String header = request.getHeader("Authorization");
		
		if (header != null && !header.isEmpty()) {
			UsernamePasswordAuthenticationToken auth = getAuthentication(header);
			if (auth != null) {
				SecurityContextHolder.getContext().setAuthentication(auth);
			}else {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				response.setContentType("application/json"); 
		         try {
		        	 response.getWriter().append(jwtUtil.json(MessageResponse.ERR_INVALID_SESSION));
		        	 return;
				} catch (IOException e1) {
					throw new RuntimeException(e1.getMessage());
				}
			}
		}else {
		
			for(String path: jwtUtil.PRIVATE_MATCHERS) {
				if(path.equals(request.getServletPath())){
					response.setStatus(HttpStatus.UNAUTHORIZED.value());
					response.setContentType("application/json"); 
			         try {
			        	 response.getWriter().append(jwtUtil.json(MessageResponse.ERR_UNAUTHORIZED));
			        	 return;
					} catch (IOException e1) {
						throw new RuntimeException(e1.getMessage());
					}
				};
			}
			
		}
		
		chain.doFilter(request, response);
	}
	
	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		if (jwtUtil.tokenValido(token)) {
			String username = jwtUtil.getUsername(token);
			UserDetails user = userDetailsService.loadUserByUsername(username);
			return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		}
		return null;
	}
}
