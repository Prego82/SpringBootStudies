package hu.cubix.hr.BalazsPeregi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.cubix.hr.BalazsPeregi.dto.CompanyDto;
import hu.cubix.hr.BalazsPeregi.dto.EmployeeDto;

@RestController
@RequestMapping("/api/companies")
public class CompanyRestController {

	private Map<Long, CompanyDto> companies = new HashMap<>();

	{
		companies.put(1L, new CompanyDto(1, "123", "Apple", "USA"));
		companies.put(2L, new CompanyDto(2, "456", "IBM", "India"));
		companies.put(3L, new CompanyDto(3, "789", "Intel", "China"));
		companies.put(4L, new CompanyDto(4, "147", "Google", "Canada"));
	}

	@GetMapping
	public List<CompanyDto> queryAll(@RequestParam Optional<Boolean> full) {
		if (full.orElse(false)) {
			return new ArrayList<>(companies.values());
		} else {
			return companies.values().stream().map(c -> companyWithoutEmployees(c)).toList();
		}
	}

	@PostMapping
	public ResponseEntity<CompanyDto> addCompany(@RequestBody CompanyDto newCompany) {
		if (companies.containsKey(newCompany.getId())) {
			return ResponseEntity.badRequest().build();
		}
		companies.put(newCompany.getId(), newCompany);
		return ResponseEntity.ok(newCompany);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CompanyDto> findCompany(@PathVariable long id, @RequestParam Optional<Boolean> full) {
		CompanyDto companyDto = companies.get(id);
		if (companyDto == null) {
			return ResponseEntity.notFound().build();
		} else if (full.orElse(false)) {
			return ResponseEntity.ok(companyDto);
		} else {
			return ResponseEntity.ok(companyWithoutEmployees(companyDto));
		}
	}

	private static CompanyDto companyWithoutEmployees(CompanyDto company) {
		return new CompanyDto(company.getId(), company.getRegistrationNumber(), company.getName(),
				company.getAddress());
	}

	@PutMapping("/{id}")
	public ResponseEntity<CompanyDto> updateCompany(@PathVariable long id, @RequestBody CompanyDto newCompanyDto) {
		newCompanyDto.setId(id);
		if (companies.containsKey(id)) {
			companies.put(id, newCompanyDto);
			return ResponseEntity.ok(newCompanyDto);
		}
		return ResponseEntity.badRequest().build();
	}

	@DeleteMapping("/{id}")
	public void removeCompany(@PathVariable long id) {
		companies.remove(id);
	}

	@PostMapping("/addEmployee/{companyId}")
	public ResponseEntity<CompanyDto> addEmployeeToCompany(@PathVariable long companyId,
			@RequestBody EmployeeDto newEmployee) {
		if (!companies.containsKey(companyId)) {
			return ResponseEntity.notFound().build();
		}
		companies.get(companyId).addEmployee(newEmployee);
		return ResponseEntity.ok(companies.get(companyId));
	}

	@DeleteMapping("/removeEmployee/{companyId}/{employeeId}")
	public ResponseEntity<CompanyDto> removeEmployeeFromCompany(@PathVariable long companyId,
			@PathVariable long employeeId) {
		if (!companies.containsKey(companyId)) {
			return ResponseEntity.notFound().build();
		}
		companies.get(companyId).removeEmployee(employeeId);
		return ResponseEntity.ok(companies.get(companyId));
	}

	@PutMapping("/replaceAllEmployees/{companyId}")
	public ResponseEntity<CompanyDto> replaceAllEmployeesInCompany(@PathVariable long companyId,
			@RequestBody List<EmployeeDto> newEmployees) {
		if (!companies.containsKey(companyId)) {
			return ResponseEntity.notFound().build();
		}
		companies.get(companyId).setEmployees(newEmployees);
		return ResponseEntity.ok(companies.get(companyId));
	}
}
