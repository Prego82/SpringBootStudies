package hu.cubix.hr.BalazsPeregi.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

	public List<Employee> findAllDynamically(Employee employee) {
		long id = employee.getId();
		String name = employee.getName();
		String positionName = employee.getPosition() == null ? null : employee.getPosition().getName();
		int salary = employee.getSalary();
		LocalDateTime startTime = employee.getStartTime();
		String companyName = employee.getCompany() == null ? null : employee.getCompany().getName();

		Specification<Employee> specs = Specification.where(null);
		if (id > 0) {
			specs = specs.and(EmployeeSpecifications.hasId(id));
		}
		if (StringUtils.hasLength(name)) {
			specs = specs.and(EmployeeSpecifications.nameLike(name));
		}

		if (StringUtils.hasLength(positionName)) {
			specs = specs.and(EmployeeSpecifications.positionIs(positionName));
		}

		if (salary > 0) {
			specs = specs.and(EmployeeSpecifications.salaryIs(salary));
		}

		if (startTime != null) {
			specs = specs.and(EmployeeSpecifications.entryDate(startTime));
		}

		if (StringUtils.hasLength(companyName)) {
			specs = specs.and(EmployeeSpecifications.companyNameLike(companyName));
		}

		return repo.findAll(specs);
	}

	@Override
	public int getPayRaisePercent(Employee employee) {
		return 0;
	}

}
