package hu.cubix.hr.BalazsPeregi.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.cubix.hr.BalazsPeregi.model.Employee;
import hu.cubix.hr.BalazsPeregi.model.Position;
import hu.cubix.hr.BalazsPeregi.repository.EmployeeRepository;
import hu.cubix.hr.BalazsPeregi.repository.PositionRepository;
import jakarta.transaction.Transactional;

@Service
public abstract class AbstractEmployeeService implements EmployeeService {

	@Autowired
	private EmployeeRepository repo;

	@Autowired
	private PositionRepository posRepo;

	@Transactional
	public Employee save(Employee employee) {
		Position position = posRepo.findByName(employee.getPosition().getName());
		if (position == null) {
			return null;
		}
		employee.setPosition(position);
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
		return repo.findByPositionName(job);
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
