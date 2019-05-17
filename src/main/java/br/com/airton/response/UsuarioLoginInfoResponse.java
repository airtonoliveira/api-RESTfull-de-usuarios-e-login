package br.com.airton.response;

import br.com.airton.Util.DesafioUtil;
import br.com.airton.model.Phone;
import br.com.airton.model.Usuario;

import java.util.Set;

public class UsuarioLoginInfoResponse implements IResponse{

    public UsuarioLoginInfoResponse(Usuario usuario){
        this.firstName=usuario.getFirstName();
        this.lastName = usuario.getLastName();
        this.email = usuario.getEmail();
        this.phones = usuario.getPhones();
        this.setCreated_at(DesafioUtil.formatar(usuario.getCreated_at()));
        this.setLast_login(DesafioUtil.formatar(usuario.getLast_login()));
    }


    private String firstName;

    private String lastName;

    private String email;

    private Set<Phone> phones;

    private String created_at;

    private String last_login;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Phone> getPhones() {
        return phones;
    }

    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getLast_login() {
        return last_login;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }
}
