package hu.cubix.hr.BalazsPeregi.service;

import org.springframework.stereotype.Service;

import hu.cubix.hr.BalazsPeregi.Employee;

@Service
public class SalaryService {
	private EmployeeService employeeService;

	public SalaryService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public void setNewSalary(Employee employee) {
		double raisePercentage = employeeService.getPayRaisePercent(employee) / 100.0;
		double raiseAmount = employee.getSalary() * raisePercentage;
		employee.setSalary((int) (employee.getSalary() + raiseAmount));
	}

}
