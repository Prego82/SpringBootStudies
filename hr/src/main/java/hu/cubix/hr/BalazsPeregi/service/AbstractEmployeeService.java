package hu.cubix.hr.BalazsPeregi.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.cubix.hr.BalazsPeregi.model.Employee;
import hu.cubix.hr.BalazsPeregi.repository.EmployeeRepository;
import jakarta.transaction.Transactional;

@Service
public abstract class AbstractEmployeeService implements EmployeeService {

	@Autowired
	private EmployeeRepository repo;

	@Transactional
	public Employee save(Employee employee) {
		return repo.save(employee);
	}

	public List<Employee> findAll() {
		return repo.findAll();
	}

	public Employee findById(long id) {
		return repo.findById(id).orElse(null);
	}

	@Transactional
	public void delete(long id) {
		repo.deleteById(id);
	}

	public List<Employee> findBySalaryGreaterThan(Integer salary) {
		return repo.findBySalaryGreaterThan(salary);
	}

	public List<Employee> findByJob(String job) {
		return repo.findByJob(job);
	}

	public List<Employee> findByPrefix(String prefix) {
		return repo.findByNameIgnoreCaseStartingWith(prefix);
	}

	public List<Employee> findByStartTimeIsBetween(LocalDateTime from, LocalDateTime to) {
		return repo.findByStartTimeIsBetween(from, to);
	}

	@Override
	public int getPayRaisePercent(Employee employee) {
		return 0;
	}

}
