package hu.cubix.hr.BalazsPeregi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.cubix.hr.BalazsPeregi.config.Views;
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
	private EmployeeMapper employeeMapper;

	@GetMapping
	public MappingJacksonValue queryAll(@RequestParam Optional<Boolean> full) {
		List<Company> companies = companyService.findAll();
		List<CompanyDto> comapnyDtos = companyMapper.comapniesToDtos(companies);
		MappingJacksonValue mapping = new MappingJacksonValue(comapnyDtos);
		Class<?> viewClass = full.orElse(false) ? Views.Detailed.class : Views.Summary.class;
		mapping.setSerializationView(viewClass);
		return mapping;
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
	public MappingJacksonValue findCompany(@PathVariable long id, @RequestParam Optional<Boolean> full) {
		Company company = companyService.findById(id);
		CompanyDto comapnyDto = companyMapper.companyToDto(company);
		MappingJacksonValue mapping = new MappingJacksonValue(comapnyDto);
		Class<?> viewClass = full.orElse(false) ? Views.Detailed.class : Views.Summary.class;
		mapping.setSerializationView(viewClass);
		return mapping;
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
		companyService.addEmployeeToCompany(companyId, employeeMapper.dtoToEmployee(newEmployee));
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
		company.setEmployees(employeeMapper.dtosToEmployees(newEmployees));
		return ResponseEntity.ok(companyMapper.companyToDto(companyService.findById(companyId)));
	}
}
