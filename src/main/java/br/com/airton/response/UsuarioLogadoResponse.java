package br.com.airton.response;

import br.com.airton.model.Phone;
import br.com.airton.model.Usuario;

import java.util.Date;
import java.util.Set;

public class UsuarioLogadoResponse implements IResponse{

    public UsuarioLogadoResponse(Usuario usuario){
        this.firstName=usuario.getFirstName();
        this.lastName = usuario.getLastName();
        this.email = usuario.getEmail();
        this.phones = usuario.getPhones();
    }


    private String firstName;

    private String lastName;

    private String email;

    private Set<Phone> phones;

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

}
