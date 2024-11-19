package hu.cubix.hr.BalazsPeregi.service;

import hu.cubix.hr.BalazsPeregi.Employee;

public interface EmployeeService {
	/**
	 * Returns what percentage pay raise should the given employee get
	 * @param employee
	 * @return
	 */
	int getPayRaisePercent(Employee employee);
}
