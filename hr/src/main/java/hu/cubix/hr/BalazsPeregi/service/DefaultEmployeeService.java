package hu.cubix.hr.BalazsPeregi.service;

import hu.cubix.hr.BalazsPeregi.model.Employee;

public class DefaultEmployeeService extends AbstractEmployeeService {

	@Override
	public int getPayRaisePercent(Employee employee) {
		return 5;
	}

}
