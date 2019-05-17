package br.com.airton.response;

public class TokenResponse implements IResponse{

    public TokenResponse(String token){
        this.token=token;
    }

    public TokenResponse(){

    }

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
