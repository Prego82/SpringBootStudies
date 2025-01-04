package hu.cubix.hr.BalazsPeregi.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

import hu.cubix.hr.BalazsPeregi.dto.EmployeeDto;
import hu.cubix.hr.BalazsPeregi.mapper.EmployeeMapper;
import hu.cubix.hr.BalazsPeregi.model.Employee;
import hu.cubix.hr.BalazsPeregi.service.AbstractEmployeeService;
import hu.cubix.hr.BalazsPeregi.service.SalaryService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
public class EmployeeRestController {

	@Autowired
	private AbstractEmployeeService employeeService;

	@Autowired
	private EmployeeMapper employeeMapper;

	@Autowired
	SalaryService salaryService;

	@GetMapping
	public List<EmployeeDto> queryAll() {
		return employeeMapper.employeesToDtos(employeeService.findAll());
	}

	@PostMapping
	public ResponseEntity<EmployeeDto> addEmployee(@RequestBody @Valid EmployeeDto newEmployeeDto) {
		Employee employee = employeeMapper.dtoToEmployee(newEmployeeDto);
		if (employeeService.findById(employee.getId()) != null) {
			return ResponseEntity.badRequest().build();
		}
		Employee savedEmployee = employeeService.save(employee);
		return ResponseEntity.ok(employeeMapper.employeeToDto(savedEmployee));
	}

	@GetMapping("/{id}")
	public ResponseEntity<EmployeeDto> findEmployee(@PathVariable long id) {
		Employee employee = employeeService.findById(id);
		if (employee == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(employeeMapper.employeeToDto(employee));
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable long id,
			@RequestBody @Valid EmployeeDto newEmployeeDto) {
		if (employeeService.findById(id) == null) {
			return ResponseEntity.badRequest().build();
		}
		newEmployeeDto.setId(id);
		Employee employee = employeeMapper.dtoToEmployee(newEmployeeDto);
		Employee updatedEmployee = employeeService.save(employee);
		return ResponseEntity.ok(employeeMapper.employeeToDto(updatedEmployee));
	}

	@DeleteMapping("/{id}")
	public void removeEmployee(@PathVariable long id) {
		employeeService.delete(id);
	}

	@GetMapping("/salary")
	public List<EmployeeDto> findEmployeeBySalary(@RequestParam int salaryHigherThan) {
		return employeeMapper.employeesToDtos(employeeService.findAll().stream()
				.filter(e -> e.getSalary() > salaryHigherThan).collect(Collectors.toList()));
	}

	@PostMapping("/raisePercentage")
	public double findEmployeeBySalary(@RequestBody EmployeeDto employeeDto) {
		Employee employee = employeeService.findById(employeeDto.getId());
		if (employee == null) {
			return -1d;
		} else {
			return salaryService.getRaisePercent(employee);
		}
	}

	@GetMapping(params = { "job", "!prefix", "!startDateFrom", "!startDateTo" })
	public List<EmployeeDto> findEmployeeByJob(@RequestParam(required = true) String job) {
		return employeeMapper.employeesToDtos(employeeService.findByJob(job));
	}

	@GetMapping(params = { "!job", "prefix", "!startDateFrom", "!startDateTo" })
	public List<EmployeeDto> findEmployeeByPrefix(@RequestParam(required = true) String prefix) {
		return employeeMapper.employeesToDtos(employeeService.findByPrefix(prefix));
	}

	@GetMapping(params = { "!job", "!prefix", "startDateFrom", "startDateTo" })
	public List<EmployeeDto> findEmployeeByPrefix(
			@RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime startDateFrom,
			@RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime startDateTo) {
		return employeeMapper.employeesToDtos(employeeService.findByStartTimeIsBetween(startDateFrom, startDateTo));
	}
}
