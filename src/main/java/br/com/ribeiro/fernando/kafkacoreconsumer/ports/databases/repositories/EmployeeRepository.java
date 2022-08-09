package br.com.ribeiro.fernando.kafkacoreconsumer.ports.databases.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ribeiro.fernando.kafkacoreconsumer.domain.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
