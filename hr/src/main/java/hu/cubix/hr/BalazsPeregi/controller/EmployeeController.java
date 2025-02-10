package hu.cubix.hr.BalazsPeregi.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import hu.cubix.hr.BalazsPeregi.model.Employee;
import hu.cubix.hr.BalazsPeregi.model.Position;
import hu.cubix.hr.BalazsPeregi.model.Qualification;

@Controller
public class EmployeeController {

	private PasswordEncoder encoder;

	private Map<Long, Employee> employees = new HashMap<>();

	public EmployeeController(PasswordEncoder encoder) {
		this.encoder = encoder;
		initEmployees();
	}

	private void initEmployees() {
		employees.put(1L,
				new Employee(1, "Little Jonny", "user", encoder.encode("pass"),
						new Position(0, "Junior", Qualification.HIGH_SCHOOL, 1000), 1000,
						LocalDateTime.of(2022, 11, 19, 0, 0), null));
		employees.put(2L, new Employee(2, "Middle Jonny", "user", encoder.encode("pass"),
				new Position(1, "Mid", Qualification.COLLEGE, 2000), 2000, LocalDateTime.of(2022, 5, 19, 0, 0), null));
		employees.put(3L, new Employee(3, "Middle Jonny Jr.", "user", encoder.encode("pass"),
				new Position(1, "Mid", Qualification.COLLEGE, 2000), 2000, LocalDateTime.of(2022, 1, 1, 0, 0), null));
		employees.put(4L,
				new Employee(4, "Senio Jonny", "user", encoder.encode("pass"),
						new Position(2, "Senior", Qualification.COLLEGE, 5000), 5000,
						LocalDateTime.of(2019, 1, 1, 0, 0), null));
		employees.put(5L,
				new Employee(5, "Old Jonny", "user", encoder.encode("pass"),
						new Position(3, "Architect", Qualification.UNIVERSITY, 1000), 10000,
						LocalDateTime.of(2014, 1, 1, 0, 0), null));
	}

	@GetMapping("/")
	public String index(Map<String, Object> model) {
		model.put("employees", employees.values());
		model.put("newEmployee", new Employee());
		return "index";

	}

	@GetMapping("/{id}")
	public String modify(Map<String, Object> model, @PathVariable long id) {
		Employee employee = employees.get(id);
		if (employee != null) {
			model.put("selectedEmployee", employee);
		} else {
			return "index";
		}
		return "employeeForm";
	}

	@PostMapping("/save")
	public String save(Employee modifiedEmployee) {
		Employee employee = employees.get(modifiedEmployee.getId());
		if (employee != null) {
			employees.put(modifiedEmployee.getId(), modifiedEmployee);
		}
		return "redirect:/";
	}

	@PostMapping("/employee")
	public String addEmployee(Employee employee) {
		employees.put(employee.getId(), employee);
		return "redirect:/";
	}

	@GetMapping("/delete/{id}")
	public String deleteEmployee(@PathVariable long id) {
		employees.remove(id);
		return "redirect:/";
	}
}
