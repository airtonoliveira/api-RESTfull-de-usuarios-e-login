package br.com.airton.controllers;

import br.com.airton.Util.DesafioUtil;
import br.com.airton.exceptions.TokenExpired;
import br.com.airton.model.UserSS;
import br.com.airton.model.Usuario;
import br.com.airton.repository.UsuarioRepository;
import br.com.airton.request.UsuarioRequest;
import br.com.airton.response.*;
import br.com.airton.security.TokenAuthenticationService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;


@Component("usuarioController")
@RestController
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

    @GetMapping("/teste")
    public @ResponseBody String teste(){

        return "Teste";

    }

    @PostMapping(value="/signup")
    public ResponseEntity<IResponse> signup(@Valid @RequestBody UsuarioRequest requestUser){

        Usuario usuario = new Usuario(requestUser);

        System.out.println("avaliando Usuário....");
        if(!DesafioUtil.isCamposObrigatoriosUsuarioPreenchidos(usuario)){
            return sendError(MessageResponse.ERR_MISSING_FIELDS);
        }

        if(!usuarioRepository.findAllByEmail(usuario.getEmail()).isEmpty()){
            return sendError(MessageResponse.ERR_EMAIL_ALREADY_EXISTS);
        }

        usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
        usuario.setCreated_at(new Date());
        Usuario user = usuarioRepository.save(usuario);
        UsuarioETokenResponse userResponse =
                new UsuarioETokenResponse(new UsuarioLogadoResponse(user),
                        TokenAuthenticationService.addAuthentication(null, usuario.getEmail()));


        return new ResponseEntity<>(userResponse, new HttpHeaders(),HttpStatus.CREATED);

    }

    @PostMapping(value="/signin")
    public ResponseEntity<IResponse> signin(@Valid @RequestBody UserSS usuarioParaSignin){

        System.out.println("avaliando autenticacao....");
        if(!DesafioUtil.isCamposObrigatoriosAutenticacaoPreenchidos(usuarioParaSignin)){
            return sendError(MessageResponse.ERR_MISSING_FIELDS);
        }

        List<Usuario> busca = usuarioRepository.findAllByEmail(usuarioParaSignin.getEmail());
        if(busca.isEmpty()){
            return sendError(MessageResponse.ERR_INVALID_EMAIL_OR_PASSWORD);
        }

        Usuario usuarioCadastrado = busca.get(0);

        if(!new BCryptPasswordEncoder().matches(usuarioParaSignin.getPassword(),usuarioCadastrado.getPassword())){
            return sendError(MessageResponse.ERR_INVALID_EMAIL_OR_PASSWORD);
        }

        UsuarioETokenResponse userResponse =
                new UsuarioETokenResponse(
                        new UsuarioLogadoResponse(usuarioCadastrado),
                            TokenAuthenticationService.addAuthentication(null, usuarioCadastrado.getEmail()));

        usuarioCadastrado.setLast_login(new Date());
        usuarioRepository.save(usuarioCadastrado);
        return new ResponseEntity<>(userResponse, new HttpHeaders(),HttpStatus.OK);

        //return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value="/me")
    public ResponseEntity<IResponse> me(HttpServletRequest request){
        Usuario usuarioCadastrado = new Usuario();
        String email=null;

        try {
            email = TokenAuthenticationService.getUserFromAuthenticationToken(request);
            System.out.println("EMAIL:" + email);
        }catch (AccessDeniedException acde){
            return sendError(MessageResponse.ERR_UNAUTHORIZED);
        }catch (TokenExpired tke){
            return sendError(MessageResponse.ERR_INVALID_SESSION);
        }
        List<Usuario> busca = usuarioRepository.findAllByEmail(email);
        if(busca!=null && !busca.isEmpty()) {
            usuarioCadastrado = busca.get(0);
        }

        UsuarioLoginInfoResponse userResponse =
                new UsuarioLoginInfoResponse(usuarioCadastrado);

        return new ResponseEntity<>(userResponse, new HttpHeaders(),HttpStatus.OK);

    }

    private ResponseEntity<IResponse> sendError(MessageResponse error){
        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }



}

