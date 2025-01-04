package hu.cubix.hr.BalazsPeregi.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.cubix.hr.BalazsPeregi.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	List<Employee> findBySalaryGreatherThan(Integer salary);

	List<Employee> findByJob(String job);

	List<Employee> findByNameIgnoreCaseStartingWith(String prefix);

	List<Employee> findByStartTimeIsBetween(LocalDateTime from, LocalDateTime to);

}
