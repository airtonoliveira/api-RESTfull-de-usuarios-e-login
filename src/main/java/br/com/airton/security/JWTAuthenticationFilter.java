package br.com.airton.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import br.com.airton.Util.DesafioUtil;
import br.com.airton.Util.UserSS;
import br.com.airton.exceptions.InvalidFieldsException;
import br.com.airton.model.Usuario;
import br.com.airton.response.MessageResponse;
import br.com.airton.service.UsuarioService;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter   {

	private AuthenticationManager authenticationManager;   
    private JWTUtil jwtUtil;
    private UsuarioService usuarioService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, UsuarioService usuarioService) {
    	setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
    	setFilterProcessesUrl("/signin");
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.usuarioService = usuarioService;
    }

	@Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {

		try {
			UserSS creds = new ObjectMapper()
	                .readValue(req.getInputStream(), UserSS.class);
			
			
			if(!DesafioUtil.isCamposObrigatoriosAutenticacaoPreenchidos(creds)){
	            throw new InvalidFieldsException();
	        }
			
	        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>());
	        
	        Authentication auth = authenticationManager.authenticate(authToken);
	        return auth;
		}
		catch (Exception e) {
			if(e instanceof UnrecognizedPropertyException) {
				 res.setStatus(HttpStatus.BAD_REQUEST.value());
		         res.setContentType("application/json"); 
		         try {
					res.getWriter().append(jwtUtil.json(MessageResponse.ERR_INVALID_FIELDS));
				} catch (IOException e1) {
					throw new RuntimeException(e1.getMessage());
				}		        
			}else if(e instanceof InvalidFieldsException) {
				res.setStatus(HttpStatus.BAD_REQUEST.value());
		         res.setContentType("application/json"); 
		         try {
					res.getWriter().append(jwtUtil.json(MessageResponse.ERR_MISSING_FIELDS));
				} catch (IOException e1) {
					throw new RuntimeException(e1.getMessage());
				}
			}else if(e instanceof IOException) {
				 res.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
		         res.setContentType("application/json"); 
		         try {
					res.getWriter().append(jwtUtil.json(MessageResponse.ERR_FATAL));
				} catch (IOException e1) {
					throw new RuntimeException(e1.getMessage());
				}
			}else if(e instanceof BadCredentialsException) {
				res.setStatus(HttpStatus.UNAUTHORIZED.value());
		         res.setContentType("application/json"); 
		         try {
					res.getWriter().append(jwtUtil.json(MessageResponse.ERR_INVALID_EMAIL_OR_PASSWORD));
				} catch (IOException e1) {
					throw new RuntimeException(e1.getMessage());
				}
			}
			
			 return null;
		}
	}
	
	
	
	@Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
	
		String username = ((UserSS) auth.getPrincipal()).getUsername();
        String token = jwtUtil.generateToken(username);
        res.addHeader("Authorization", token);
        res.addHeader("access-control-expose-headers", "Authorization");
        res.setContentType("application/json"); 
        try {
        	usuarioService.updateUserLastLogin(username);
        	ObjectMapper Obj = new ObjectMapper(); 
			res.getWriter().append((Obj.writeValueAsString(usuarioService.getUsuarioResponseForSucessfullSignin(token))));
		} catch (IOException e1) {
			throw new RuntimeException(e1.getMessage());
		}
	}	
	
	private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {
		 
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
                throws IOException, ServletException {
            response.setStatus(401);
            response.setContentType("application/json"); 
            response.getWriter().append(jwtUtil.json(MessageResponse.ERR_INVALID_EMAIL_OR_PASSWORD));
        }
        
        
    }

}
