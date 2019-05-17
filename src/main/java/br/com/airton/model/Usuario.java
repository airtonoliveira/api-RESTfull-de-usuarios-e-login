package br.com.airton.model;

import br.com.airton.request.UsuarioRequest;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user")
public class Usuario{
		
	public Usuario(String firstName, String lastName, String email,
			String password, Set<Phone> phones, Date created_at) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.phones = phones;
		this.created_at=created_at;
	}

	public Usuario(UsuarioRequest requestUser) {
		super();
		this.firstName = requestUser.getFirstName();
		this.lastName = requestUser.getLastName();
		this.email = requestUser.getEmail();
		this.password = requestUser.getPassword();
		this.phones = requestUser.getPhones();
	}


	
	public Usuario(){
		
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_user;

	@NotNull
    @Column
    private String firstName;

	@NotNull
    @Column
    private String lastName;

	@NotNull
    @Column
    private String email;

	@NotNull
    @Column
    private String password;

	@NotNull
	@Column
	private Date created_at;

	@Column
	private Date last_login;

	@NotNull
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_phone",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_phone")
    )
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

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getLast_login() {
		return last_login;
	}

	public void setLast_login(Date last_login) {
		this.last_login = last_login;
	}
}
