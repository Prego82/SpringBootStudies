package hu.cubix.hr.BalazsPeregi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hu.cubix.hr.BalazsPeregi.model.Company;
import hu.cubix.hr.BalazsPeregi.model.JobSalary;

public interface CompanyRepository extends JpaRepository<Company, Long> {
	@Query("SELECT DISTINCT c FROM Company c JOIN c.employees e WHERE e.salary > :salary")
	List<Company> findCompaniesWithAtLeastOneEmployeeWithSalaryHigherThan(@Param("salary") Integer salary);

	@Query("SELECT DISTINCT c FROM Company c WHERE SIZE(c.employees) > :numOfEmployees")
	List<Company> findCompaniesWithMoreThanParameterEmployees(@Param("numOfEmployees") Integer numOfEmployees);

	@Query("SELECT new hu.cubix.hr.BalazsPeregi.model.JobSalary(e.job, AVG(e.salary)) FROM Company c JOIN c.employees e WHERE c.id = :companyId GROUP BY e.job ORDER BY AVG(e.salary) DESC")
	List<JobSalary> listAvgSalaryOfEmployees(@Param("companyId") Integer companyId);
}
