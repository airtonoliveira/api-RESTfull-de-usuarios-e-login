package br.com.airton.request;

import java.util.Set;

import br.com.airton.model.Phone;

public class UsuarioRequest {

    private Long id_user;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<Phone> phones;

    //GET E SET

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastname(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }

    public Set<Phone> getPhones() {
        return phones;
    }

    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }

    // TO STRING

    @Override
    public String toString() {
        return "Usuario [id_user=" + id_user + ", firstName=" + firstName
                + ", lastname=" + lastName + ", email=" + email + ", password="
                + getPassword() + ", phones=" + phones + "]";
    }

}
