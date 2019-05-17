package br.com.airton.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "phone")
public class Phone {
	
	
	
	public Phone(Long number, Long area_code, String country_code) {
		super();
		this.number = number;
		this.area_code = area_code;
		this.country_code = country_code;
	}

	public Phone(){
		
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_telefone;

    @Column
    private Long number;

    @Column
    private Long area_code;
    
    @Column
    private String country_code;

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public Long getArea_code() {
		return area_code;
	}

	public void setArea_code(Long area_code) {
		this.area_code = area_code;
	}

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	public Long getId_telefone() {
		return id_telefone;
	}

	public void setId_telefone(Long id_telefone) {
		this.id_telefone = id_telefone;
	}

}
