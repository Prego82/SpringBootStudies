package hu.cubix.hr.BalazsPeregi.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import hu.cubix.hr.BalazsPeregi.model.Employee;

@Service
public abstract class AbstractEmployeeService implements EmployeeService {

	private Map<Long, Employee> employees = new HashMap<>();

	{
		employees.put(1L, new Employee(1, "Kis Miksa", "Junior", 1000, LocalDateTime.of(2022, 11, 19, 0, 0)));
		employees.put(2L, new Employee(2, "Közép Hunor", "Mid", 2000, LocalDateTime.of(2022, 5, 19, 0, 0)));
		employees.put(3L, new Employee(3, "Ifj. Közép Hunor", "Mid2", 2000, LocalDateTime.of(2022, 1, 1, 0, 0)));
		employees.put(4L, new Employee(4, "Nagy László", "Senior", 5000, LocalDateTime.of(2019, 1, 1, 0, 0)));
		employees.put(5L, new Employee(5, "Mérnök Géza", "Architect", 10000, LocalDateTime.of(2014, 1, 1, 0, 0)));
	}

	public void save(Employee employee) {
		employees.put(employee.getId(), employee);
	}

	public List<Employee> findAll() {
		return new ArrayList<>(employees.values());
	}

	public Employee findById(long id) {
		return employees.get(id);
	}

	public void delete(long id) {
		employees.remove(id);
	}

	@Override
	public int getPayRaisePercent(Employee employee) {
		return 0;
	}

}
