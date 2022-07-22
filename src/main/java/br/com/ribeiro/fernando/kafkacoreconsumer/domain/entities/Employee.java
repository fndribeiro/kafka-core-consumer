package br.com.ribeiro.fernando.kafkacoreconsumer.domain.entities;

import java.util.Objects;

import br.com.ribeiro.fernando.kafkacoreconsumer.domain.valueobjects.EmployeeType;

public class Employee {

	private String name;
	private String email;
	private EmployeeType type;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public EmployeeType getType() {
		return type;
	}

	public void setType(EmployeeType type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, name, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(email, other.email) && Objects.equals(name, other.name) && type == other.type;
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + ", email=" + email + ", type=" + type + "]";
	}
	
}
