package hu.cubix.hr.BalazsPeregi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import hu.cubix.hr.BalazsPeregi.dto.JobSalaryDto;
import hu.cubix.hr.BalazsPeregi.mapper.CompanyMapper;
import hu.cubix.hr.BalazsPeregi.mapper.EmployeeMapper;
import hu.cubix.hr.BalazsPeregi.mapper.JobSalaryMapper;
import hu.cubix.hr.BalazsPeregi.model.Company;
import hu.cubix.hr.BalazsPeregi.model.JobSalary;
import hu.cubix.hr.BalazsPeregi.service.CompanyService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/companies")
public class CompanyRestController {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CompanyMapper companyMapper;

	@Autowired
	private EmployeeMapper employeeMapper;

	@Autowired
	private JobSalaryMapper jobSalaryMapper;

	@GetMapping
	public List<CompanyDto> queryAll(@RequestParam Optional<Boolean> full) {
		if (full.orElse(false)) {
			return companyMapper.companiesToDtos(companyService.findAllWithEmployees());
		} else {
			return companyMapper.companiesToSummaryDtos(companyService.findAll());
		}
	}

	@GetMapping(params = "page")
	public List<CompanyDto> queryAll(@RequestParam Optional<Boolean> full, Pageable pageable) {
		Page<Company> companies = full.orElse(false) ? companyService.findAllWithFetch(pageable)
				: companyService.findAll(pageable);
		System.out.println("companies.getTotalPages(): " + companies.getTotalPages());
		System.out.println("companies.getNumber(): " + companies.getNumber());
		System.out.println("companies.getTotalElements(): " + companies.getTotalElements());
		System.out.println("companies.getNumberOfElements(): " + companies.getNumberOfElements());
		System.out.println("companies.isFirst(): " + companies.isFirst());
		System.out.println("companies.isLast(): " + companies.isLast());
		System.out.println("companies.hasPrevious(): " + companies.hasPrevious());
		System.out.println("companies.hasNext(): " + companies.hasNext());
		if (full.orElse(false)) {
			return companyMapper.companiesToDtos(companies.getContent());
		} else {
			return companyMapper.companiesToSummaryDtos(companies.getContent());
		}
	}

	@PostMapping
	public ResponseEntity<CompanyDto> addCompany(@RequestBody CompanyDto newCompany) {
		Company company = companyMapper.dtoToCompany(newCompany);
		if (companyService.findById(company.getId()) != null) {
			return ResponseEntity.badRequest().build();
		}
		Company savedCompany = companyService.save(company);
		if (savedCompany == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(companyMapper.companyToDto(savedCompany));
	}

	@GetMapping("/{id}")
	public CompanyDto findCompany(@PathVariable long id, @RequestParam Optional<Boolean> full) {
		if (full.orElse(false)) {
			return companyMapper.companyToDto(companyService.findByIdWithFetch(id));
		} else {
			return companyMapper.companyToSummaryDto(companyService.findById(id));
		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<CompanyDto> updateCompany(@PathVariable long id, @RequestBody CompanyDto newCompanyDto) {
		newCompanyDto.setId(id);
		Company company = companyService.findById(id);
		if (company != null) {
			Company modifiedCompany = companyMapper.dtoToCompany(newCompanyDto);
			return ResponseEntity.ok(companyMapper.companyToDto(companyService.save(modifiedCompany)));
		}
		return ResponseEntity.badRequest().build();
	}

	@DeleteMapping("/{id}")
	public void removeCompany(@PathVariable long id) {
		companyService.delete(id);
	}

	@PostMapping("/addEmployee/{companyId}")
	public ResponseEntity<CompanyDto> addEmployeeToCompany(@PathVariable long companyId,
			@RequestBody @Valid EmployeeDto newEmployee) {
		boolean succ = companyService.addEmployeeToCompany(companyId, employeeMapper.dtoToEmployee(newEmployee));
		if (!succ) {
			return ResponseEntity.badRequest().build();
		} else {
			return ResponseEntity.ok(companyMapper.companyToDto(companyService.findByIdWithFetch(companyId)));
		}
	}

	@DeleteMapping("/removeEmployee/{companyId}/{employeeId}")
	public ResponseEntity<CompanyDto> removeEmployeeFromCompany(@PathVariable long companyId,
			@PathVariable long employeeId) {
		if (companyService.removeEmployeeFromCompany(companyId, employeeId)) {
			return ResponseEntity.ok(companyMapper.companyToDto(companyService.findByIdWithFetch(companyId)));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/replaceAllEmployees/{companyId}")
	public ResponseEntity<CompanyDto> replaceAllEmployeesInCompany(@PathVariable long companyId,
			@RequestBody @Valid List<EmployeeDto> newEmployees) {

		if (companyService.replaceAllEmployeesInCompany(companyId, employeeMapper.dtosToEmployees(newEmployees))) {
			return ResponseEntity.ok(companyMapper.companyToDto(companyService.findByIdWithFetch(companyId)));
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@GetMapping(value = "/employee", params = "salary")
	public List<CompanyDto> queryAllWithSalaryHigher(@RequestParam Integer salary) {
		List<Company> companies = companyService.findCompaniesWithAtLeastOneEmployeeWithSalaryHigherThan(salary);
		return companyMapper.companiesToDtos(companies);
	}

	@GetMapping(value = "/employee", params = "numOfEmployees")
	public List<CompanyDto> queryAllWithNumOfEmployeesHigherThan(@RequestParam Integer numOfEmployees) {
		List<Company> companies = companyService.findCompaniesWithAtLeastNumOfEmployees(numOfEmployees);
		return companyMapper.companiesToDtos(companies);
	}

	@GetMapping(value = "/employee", params = "companyId")
	public List<JobSalaryDto> queryAvgSalaries(@RequestParam Integer companyId) {
		List<JobSalary> avgSalaries = companyService.queryAvgSalaries(companyId);
		List<JobSalaryDto> salariesToDto = jobSalaryMapper.salariesToDto(avgSalaries);
		return salariesToDto;
	}
}
