package hu.cubix.hr.BalazsPeregi.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Company {
	@Id
	@GeneratedValue
	private long id;
	private String registrationNumber;
	private String name;
	private String address;
	@OneToMany(mappedBy = "company")
	private List<Employee> employees = new ArrayList<>();

	public Company() {

	}

	public Company(long id, String registrationNumber, String name, String address) {
		this.id = id;
		this.registrationNumber = registrationNumber;
		this.name = name;
		this.address = address;
	}

	public Company(String registrationNumber, String name, String address) {
		this.registrationNumber = registrationNumber;
		this.name = name;
		this.address = address;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public void addEmployee(Employee newEmployee) {
		newEmployee.setCompany(this);
		employees.add(newEmployee);
	}

	public void removeEmployee(long employeeId) {
		for (int i = 0; i < employees.size(); i++) {
			if (employees.get(i).getId() == employeeId) {
				employees.remove(i);
			}
		}
	}
}
