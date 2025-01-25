package hu.cubix.hr.BalazsPeregi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.SortDefault;

import hu.cubix.hr.BalazsPeregi.model.Company;
import hu.cubix.hr.BalazsPeregi.model.JobSalary;

public interface CompanyRepository extends JpaRepository<Company, Long> {
	@Query("SELECT DISTINCT c FROM Company c JOIN c.employees e WHERE e.salary > :salary")
	List<Company> findCompaniesWithAtLeastOneEmployeeWithSalaryHigherThan(@Param("salary") Integer salary);

	@Query("SELECT DISTINCT c FROM Company c WHERE SIZE(c.employees) > :numOfEmployees")
	List<Company> findCompaniesWithMoreThanParameterEmployees(@Param("numOfEmployees") Integer numOfEmployees);

	@Query("SELECT new hu.cubix.hr.BalazsPeregi.model.JobSalary(e.position.name, AVG(e.salary)) FROM Company c JOIN c.employees e WHERE c.id = :companyId GROUP BY e.position.name ORDER BY AVG(e.salary) DESC")
	List<JobSalary> listAvgSalaryOfEmployees(@Param("companyId") Integer companyId);
	// itt a JobSalary lehet egy interface is a két attributum getterjének. jpa
	// legenerálja

	@Override
	Page<Company> findAll(@SortDefault("id") Pageable pageable);

	@Query("SELECT DISTINCT c FROM Company c LEFT JOIN FETCH c.employees LEFT JOIN FETCH c.form LEFT JOIN FETCH c.employees.position")
	List<Company> findAllWithFetch();

	@Query("SELECT DISTINCT c FROM Company c LEFT JOIN FETCH c.employees LEFT JOIN FETCH c.form LEFT JOIN FETCH c.employees.position")
	Page<Company> findAllWithFetch(@SortDefault("id") Pageable pageable);

	@Query("SELECT DISTINCT c FROM Company c LEFT JOIN FETCH c.employees LEFT JOIN FETCH c.form LEFT JOIN FETCH c.employees.position WHERE c.id=:id")
	Company findByIdWithFetch(long id);
}
