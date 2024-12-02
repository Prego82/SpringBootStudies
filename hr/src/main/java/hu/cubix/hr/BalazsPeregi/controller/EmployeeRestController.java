package hu.cubix.hr.BalazsPeregi.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

@RestController
@RequestMapping("/api/employees")
public class EmployeeRestController {

	private Map<Long, EmployeeDto> employees = new HashMap<>();

	{
		employees.put(1L, new EmployeeDto(1, "Junior", 1000, LocalDateTime.of(2022, 11, 19, 0, 0)));
		employees.put(2L, new EmployeeDto(2, "Mid", 2000, LocalDateTime.of(2022, 5, 19, 0, 0)));
		employees.put(3L, new EmployeeDto(3, "Mid2", 2000, LocalDateTime.of(2022, 1, 1, 0, 0)));
		employees.put(4L, new EmployeeDto(4, "Senior", 5000, LocalDateTime.of(2019, 1, 1, 0, 0)));
		employees.put(5L, new EmployeeDto(5, "Architect", 10000, LocalDateTime.of(2014, 1, 1, 0, 0)));
	}

	@GetMapping
	public List<EmployeeDto> queryAll() {
		return new ArrayList<>(employees.values());
	}

	@PostMapping
	public ResponseEntity<EmployeeDto> addEmployee(@RequestBody EmployeeDto newEmployeeDto) {
		if (employees.containsKey(newEmployeeDto.getId())) {
			return ResponseEntity.badRequest().build();
		}
		employees.put(newEmployeeDto.getId(), newEmployeeDto);
		return ResponseEntity.ok(newEmployeeDto);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EmployeeDto> findEmployee(@PathVariable long id) {
		EmployeeDto employeeDto = employees.get(id);
		if (employeeDto == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(employeeDto);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable long id, @RequestBody EmployeeDto newEmployeeDto) {
		newEmployeeDto.setId(id);
		if (employees.containsKey(id)) {
			employees.put(id, newEmployeeDto);
			return ResponseEntity.ok(newEmployeeDto);
		}
		return ResponseEntity.badRequest().build();
	}

	@DeleteMapping("/{id}")
	public void removeEmployee(@PathVariable long id) {
		employees.remove(id);
	}

	@GetMapping("/salary") // inkább(params = "salaryHigherThan") akkor nem kell a /salary link mögé
	public List<EmployeeDto> findEmployeeBySalary(@RequestParam int salaryHigherThan) {
		return employees.values().stream().filter(e -> e.getSalary() > salaryHigherThan).collect(Collectors.toList());
	}
}
