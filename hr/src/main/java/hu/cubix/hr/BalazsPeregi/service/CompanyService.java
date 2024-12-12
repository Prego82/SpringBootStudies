package hu.cubix.hr.BalazsPeregi.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import hu.cubix.hr.BalazsPeregi.model.Company;

@Service
public class CompanyService {
	private Map<Long, Company> companies = new HashMap<>();

	{
		companies.put(1L, new Company(1, "123", "Apple", "USA"));
		companies.put(2L, new Company(2, "456", "IBM", "India"));
		companies.put(3L, new Company(3, "789", "Intel", "China"));
		companies.put(4L, new Company(4, "147", "Google", "Canada"));
	}

	public void save(Company company) {
		companies.put(company.getId(), company);
	}

	public List<Company> findAll(Optional<Boolean> listEmployees) {
		if (listEmployees.orElse(false)) {
			return new ArrayList<>(companies.values());
		} else {
			return companies.values().stream().map(c -> companyWithoutEmployees(c)).toList();
		}
	}

	public Company findById(long id) {
		return companies.get(id);
	}

	public void delete(long id) {
		companies.remove(id);
	}

	public static Company companyWithoutEmployees(Company company) {
		return new Company(company.getId(), company.getRegistrationNumber(), company.getName(), company.getAddress());
	}
}
