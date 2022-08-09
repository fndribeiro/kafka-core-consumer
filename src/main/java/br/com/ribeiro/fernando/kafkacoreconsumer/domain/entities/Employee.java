package br.com.ribeiro.fernando.kafkacoreconsumer.domain.entities;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.lang.NonNull;

import br.com.ribeiro.fernando.kafkacoreconsumer.domain.valueobjects.EmployeeType;

@Entity
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private String email;
	private EmployeeType type;
	private Date createdAt = new Date();
	
	public Employee() {}

	public Employee(@NonNull String name, @NonNull String email, @NonNull EmployeeType type) {
		this.name = name;
		this.email = email;
		this.type = type;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public EmployeeType getType() {
		return type;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	@Override
	public int hashCode() {
		return Objects.hash(createdAt, email, id, name, type);
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
		return Objects.equals(createdAt, other.createdAt) && Objects.equals(email, other.email) && id == other.id
				&& Objects.equals(name, other.name) && type == other.type;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", email=" + email + ", type=" + type + ", createdAt="
				+ createdAt + "]";
	}
	
}
