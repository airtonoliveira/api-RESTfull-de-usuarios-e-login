package br.com.airton.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import br.com.airton.response.MessageResponse;

@Component("appExceptionHandler")
@ControllerAdvice
@RestController
public class AppExceptionHandler  extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,HttpHeaders headers, HttpStatus status, WebRequest request) {
		return new ResponseEntity(MessageResponse.ERR_INVALID_FIELDS, new HttpHeaders(),status);
	}
	
	@ExceptionHandler(TokenExpired.class)
	public ResponseEntity<MessageResponse> tokenExpired(TokenExpired e, HttpServletRequest request) {
		
		MessageResponse err = MessageResponse.ERR_INVALID_SESSION;
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	

}
