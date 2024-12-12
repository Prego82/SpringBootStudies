package hu.cubix.hr.BalazsPeregi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import hu.cubix.hr.BalazsPeregi.mapper.CompanyMapper;
import hu.cubix.hr.BalazsPeregi.mapper.EmployeeMapper;
import hu.cubix.hr.BalazsPeregi.model.Company;
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
	private EmployeeMapper employeMapper;

	@GetMapping
	public List<CompanyDto> queryAll(@RequestParam Optional<Boolean> full) {
		return companyMapper.comapniesToDtos(companyService.findAll(full));
	}

	@PostMapping
	public ResponseEntity<CompanyDto> addCompany(@RequestBody CompanyDto newCompany) {
		Company company = companyMapper.dtoToCompany(newCompany);
		if (companyService.findById(company.getId()) != null) {
			return ResponseEntity.badRequest().build();
		}
		companyService.save(company);
		return ResponseEntity.ok(newCompany);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CompanyDto> findCompany(@PathVariable long id, @RequestParam Optional<Boolean> full) {
		Company company = companyService.findById(id);
		if (company == null) {
			return ResponseEntity.notFound().build();
		} else if (full.orElse(false)) {
			return ResponseEntity.ok(companyMapper.companyToDto(company));
		} else {
			return ResponseEntity.ok(companyMapper.companyToDto(CompanyService.companyWithoutEmployees(company)));
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<CompanyDto> updateCompany(@PathVariable long id, @RequestBody CompanyDto newCompanyDto) {
		newCompanyDto.setId(id);
		Company company = companyService.findById(id);
		if (company != null) {
			companyService.save(company);
			return ResponseEntity.ok(newCompanyDto);
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
		Company company = companyService.findById(companyId);
		if (company == null) {
			return ResponseEntity.notFound().build();
		}
		company.addEmployee(employeMapper.dtoToEmployee(newEmployee));
		return ResponseEntity.ok(companyMapper.companyToDto(companyService.findById(companyId)));
	}

	@DeleteMapping("/removeEmployee/{companyId}/{employeeId}")
	public ResponseEntity<CompanyDto> removeEmployeeFromCompany(@PathVariable long companyId,
			@PathVariable long employeeId) {
		Company company = companyService.findById(companyId);
		if (company == null) {
			return ResponseEntity.notFound().build();
		}
		company.removeEmployee(employeeId);
		return ResponseEntity.ok(companyMapper.companyToDto(companyService.findById(companyId)));
	}

	@PutMapping("/replaceAllEmployees/{companyId}")
	public ResponseEntity<CompanyDto> replaceAllEmployeesInCompany(@PathVariable long companyId,
			@RequestBody @Valid List<EmployeeDto> newEmployees) {
		Company company = companyService.findById(companyId);
		if (company == null) {
			return ResponseEntity.notFound().build();
		}
		company.setEmployees(employeMapper.dtosToEmployees(newEmployees));
		return ResponseEntity.ok(companyMapper.companyToDto(companyService.findById(companyId)));
	}
}
