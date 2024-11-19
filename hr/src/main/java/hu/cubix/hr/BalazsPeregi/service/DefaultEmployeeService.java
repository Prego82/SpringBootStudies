package hu.cubix.hr.BalazsPeregi.service;

import hu.cubix.hr.BalazsPeregi.Employee;

public class DefaultEmployeeService implements EmployeeService {

	@Override
	public int getPayRaisePercent(Employee employee) {
		return 5;
	}

}
