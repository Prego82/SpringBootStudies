package hu.cubix.hr.BalazsPeregi.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import hu.cubix.hr.BalazsPeregi.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
	List<Employee> findBySalaryGreaterThan(Integer salary);

	List<Employee> findByPositionName(String positionName);

	List<Employee> findByNameIgnoreCaseStartingWith(String prefix);

	List<Employee> findByStartTimeIsBetween(LocalDateTime from, LocalDateTime to);

	@Query("UPDATE Employee e " + "SET e.salary = e.position.minSalary " + "WHERE "
			+ "e.position.name=:positionName AND " + "e.salary<e.position.minSalary")
	@Modifying
	void updateSalariesToPositionMin(String positionName);

	@Query("UPDATE Employee e " + "SET e.salary = "
			+ "(SELECT pd.minSalary FROM PositionDetailsByCompany pd WHERE pd.position.name=:positionName AND pd.company.id=:companyId) "
			+ "WHERE e.company.id=:companyId AND e.position.name=:positionName "
			+ "AND e.salary<(SELECT pd.minSalary FROM PositionDetailsByCompany pd WHERE pd.position.name=:positionName AND pd.company.id=:companyId)")
	@Modifying
	void updateSalariesToCompanyMin(long companyId, String positionName);

	Optional<Employee> findByUsername(String username);

}
