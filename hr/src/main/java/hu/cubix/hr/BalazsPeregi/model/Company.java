package hu.cubix.hr.BalazsPeregi.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Company {

	@Id
	@GeneratedValue
	private long id;
	private String registrationNumber;
	private String name;
	private String address;
	@ManyToOne
	private CompanyForm form;
	@OneToMany(mappedBy = "company")
	private List<Employee> employees = new ArrayList<>();

	public Company() {

	}

	public Company(long id, String registrationNumber, String name, String address, CompanyForm form) {
		this.id = id;
		this.registrationNumber = registrationNumber;
		this.name = name;
		this.address = address;
		this.form = form;
	}

	public Company(String registrationNumber, String name, String address, CompanyForm form) {
		this.registrationNumber = registrationNumber;
		this.name = name;
		this.address = address;
		this.form = form;
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

	public CompanyForm getForm() {
		return form;
	}

	public void setForm(CompanyForm form) {
		this.form = form;
	}

	public void addEmployee(Employee newEmployee) {
		newEmployee.setCompany(this);
		employees.add(newEmployee);
	}

	public void removeEmployee(Employee employee) {
		employee.setCompany(null);
		employees.remove(employee);
	}
}
