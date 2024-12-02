package hu.cubix.hr.BalazsPeregi.dto;

import java.util.ArrayList;
import java.util.List;

public class CompanyDto {
	private long id;
	private String registrationNumber;
	private String name;
	private String address;
	private List<EmployeeDto> employees = new ArrayList<>();

	public CompanyDto(long id, String registrationNumber, String name, String address) {
		super();
		this.id = id;
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

	public void addEmployee(EmployeeDto newEmployee) {
		employees.add(newEmployee);
	}

	public void removeEmployee(long employeeId) {
		for (int i = 0; i < employees.size(); i++) {
			if (employees.get(i).getId() == employeeId) {
				employees.remove(i);
			}
		}
	}

	public List<EmployeeDto> getEmployees() {
		return employees;
	}

	public void setEmployees(List<EmployeeDto> employees) {
		this.employees = employees;
	}

}
