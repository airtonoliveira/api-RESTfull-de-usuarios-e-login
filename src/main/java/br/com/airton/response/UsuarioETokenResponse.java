package br.com.airton.response;

public class UsuarioETokenResponse implements IResponse{

	private String token;
    private UsuarioLogadoResponse user;
   
    public UsuarioETokenResponse(){

    }

    public UsuarioETokenResponse(UsuarioLogadoResponse usuario, String token){
        this.setUser(usuario);
        this.token=token;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public UsuarioLogadoResponse getUser() {
        return user;
    }

    public void setUser(UsuarioLogadoResponse user) {
        this.user = user;
    }
}
