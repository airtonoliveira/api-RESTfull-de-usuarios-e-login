package br.com.airton.controllers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.airton.Util.DesafioUtil;
import br.com.airton.Util.UserSS;
import br.com.airton.model.Usuario;
import br.com.airton.repository.UsuarioRepository;
import br.com.airton.request.UsuarioRequest;
import br.com.airton.response.IResponse;
import br.com.airton.response.MessageResponse;
import br.com.airton.response.TokenResponse;
import br.com.airton.response.UsuarioLoginInfoResponse;
import br.com.airton.security.JWTUtil;


@Component("usuarioController")
@RestController
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;
    
	@Autowired
	private JWTUtil jwtUtil;

    @PostMapping(value="/signup")
    public ResponseEntity<IResponse> signup(@Valid @RequestBody UsuarioRequest requestUser){

        Usuario usuario = new Usuario(requestUser);

        if(!DesafioUtil.isCamposObrigatoriosUsuarioPreenchidos(usuario)){
            return sendError(MessageResponse.ERR_MISSING_FIELDS, HttpStatus.BAD_REQUEST);
        }

        if(!usuarioRepository.findAllByEmail(usuario.getEmail()).isEmpty()){
            return sendError(MessageResponse.ERR_EMAIL_ALREADY_EXISTS, HttpStatus.OK);
        }

        usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
        usuario.setCreated_at(new Date());
        usuarioRepository.save(usuario);
        String token = jwtUtil.generateToken(usuario.getEmail());
        TokenResponse userResponse = new TokenResponse(token);
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWTUtil.AUTHORIZATION, token);
        return new ResponseEntity<>(userResponse,headers ,HttpStatus.CREATED);

    }

    @PostMapping(value="/signin")
    public void signin(@Valid @RequestBody UserSS usuarioParaSignin){

    }

    @RequestMapping(value="/me")
    public ResponseEntity<IResponse> me(HttpServletRequest request){
        String email=null;

        email = jwtUtil.getUsername(request.getHeader(JWTUtil.AUTHORIZATION));
      
        Usuario usuarioCadastrado = usuarioRepository.findByEmail(email);

        UsuarioLoginInfoResponse userResponse =
                new UsuarioLoginInfoResponse(usuarioCadastrado);

        return new ResponseEntity<>(userResponse, new HttpHeaders(),HttpStatus.OK);

    }

    private ResponseEntity<IResponse> sendError(MessageResponse error, HttpStatus status){
        return new ResponseEntity<>(error, new HttpHeaders(), status);
    }



}

