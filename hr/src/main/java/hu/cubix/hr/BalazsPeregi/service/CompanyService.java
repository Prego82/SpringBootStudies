package hu.cubix.hr.BalazsPeregi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.cubix.hr.BalazsPeregi.model.Company;
import hu.cubix.hr.BalazsPeregi.model.Employee;
import hu.cubix.hr.BalazsPeregi.repository.CompanyRepository;
import jakarta.transaction.Transactional;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository repo;

	@Transactional
	public void save(Company company) {
		repo.save(company);
	}

	public List<Company> findAll() {
		return repo.findAll();
	}

	public Company findById(long id) {
		return repo.findById(id).orElse(null);
	}

	@Transactional
	public void delete(long id) {
		repo.deleteById(id);
	}

	@Transactional
	public void addEmployeeToCompany(long companyId, Employee newEmployee) {
		Company company = repo.findById(companyId).orElse(null);
		if (company != null) {
			company.addEmployee(newEmployee);
		}
		repo.save(company);
	}

}
