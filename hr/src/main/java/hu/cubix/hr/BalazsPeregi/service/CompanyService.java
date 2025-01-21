package hu.cubix.hr.BalazsPeregi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import hu.cubix.hr.BalazsPeregi.model.Company;
import hu.cubix.hr.BalazsPeregi.model.CompanyForm;
import hu.cubix.hr.BalazsPeregi.model.Employee;
import hu.cubix.hr.BalazsPeregi.model.JobSalary;
import hu.cubix.hr.BalazsPeregi.repository.CompanyFormRepository;
import hu.cubix.hr.BalazsPeregi.repository.CompanyRepository;
import hu.cubix.hr.BalazsPeregi.repository.EmployeeRepository;
import jakarta.transaction.Transactional;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository repo;

	@Autowired
	private EmployeeRepository employeeRepo;

	@Autowired
	private CompanyFormRepository companyFormRepo;

	@Transactional
	public Company save(Company company) {
		CompanyForm compForm = findCompanyFormByName(company.getForm().getName());
		if (compForm == null) {
			return null;
		}
		company.setForm(compForm);
		return repo.save(company);
	}

	public List<Company> findAll() {
		return repo.findAll();
	}

	public Page<Company> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}

	public Company findById(long id) {
		return repo.findById(id).orElse(null);
	}

	@Transactional
	public void delete(long id) {
		repo.deleteById(id);
	}

	@Transactional
	public void addEmployeeToCompany(Company company, Employee newEmployee) {
		newEmployee.setCompany(company);
		employeeRepo.save(newEmployee);
	}

	@Transactional
	public boolean removeEmployeeFromCompany(long companyId, long employeeId) {
		Optional<Employee> employee = employeeRepo.findById(employeeId);
		Optional<Company> company = repo.findById(companyId);
		if (employee.isPresent() && company.isPresent()) {
			company.get().removeEmployee(employee.get());
			employeeRepo.save(employee.get());
			return true;
		}
		return false;
	}

	@Transactional
	public boolean replaceAllEmployeesInCompany(long companyId, List<Employee> employees) {
		Optional<Company> company = repo.findById(companyId);
		if (company.isEmpty()) {
			return false;
		}
		// Detach all existing employees
		company.get().getEmployees().forEach(emp -> {
			emp.setCompany(null);
			employeeRepo.save(emp);
		});
		company.get().getEmployees().clear();
		// Add employees to the company
		employees.forEach(emp -> {
			company.get().addEmployee(emp);
			employeeRepo.save(emp);
		});
		return true;
	}

	public List<Company> findCompaniesWithAtLeastOneEmployeeWithSalaryHigherThan(Integer salary) {
		return repo.findCompaniesWithAtLeastOneEmployeeWithSalaryHigherThan(salary);
	}

	public List<Company> findCompaniesWithAtLeastNumOfEmployees(Integer numOfEmployees) {
		return repo.findCompaniesWithMoreThanParameterEmployees(numOfEmployees);
	}

	public List<JobSalary> queryAvgSalaries(Integer companyId) {
		return repo.listAvgSalaryOfEmployees(companyId);
	}

	public CompanyForm findCompanyFormByName(String companyFormName) {
		return companyFormRepo.findByName(companyFormName);
	}

}
