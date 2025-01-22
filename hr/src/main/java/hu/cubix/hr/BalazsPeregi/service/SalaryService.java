package hu.cubix.hr.BalazsPeregi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.cubix.hr.BalazsPeregi.model.Employee;
import hu.cubix.hr.BalazsPeregi.repository.EmployeeRepository;
import hu.cubix.hr.BalazsPeregi.repository.PositionDetailsByCompanyRepository;
import hu.cubix.hr.BalazsPeregi.repository.PositionRepository;
import jakarta.transaction.Transactional;

@Service
public class SalaryService {
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private PositionDetailsByCompanyRepository positionDetailsRepo;
	@Autowired
	private PositionRepository positionRepo;
	@Autowired
	private EmployeeRepository employeeRepo;

	public SalaryService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public void setNewSalary(Employee employee) {
		double raisePercentage = getRaisePercent(employee) / 100.0;
		double raiseAmount = employee.getSalary() * raisePercentage;
		employee.setSalary((int) (employee.getSalary() + raiseAmount));
	}

	public double getRaisePercent(Employee employee) {
		return employeeService.getPayRaisePercent(employee);
	}

	@Transactional
	public void correctMinSalariesToGlobalMinOfPosition(String positionName) {
		employeeRepo.updateSalariesToPositionMin(positionName);
	}

	@Transactional
	public void correctMinSalariesToCompanyMinOfPosition(long companyId, String positionName) {
		employeeRepo.updateSalariesToCompanyMin(companyId, positionName);
	}

}
