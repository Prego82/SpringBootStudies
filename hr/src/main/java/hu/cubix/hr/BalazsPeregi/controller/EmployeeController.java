package hu.cubix.hr.BalazsPeregi.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import hu.cubix.hr.BalazsPeregi.Employee;

@Controller
public class EmployeeController {

	private Map<Long, Employee> employees = new HashMap<>();

	{
		employees.put(1L, new Employee(1, "Little Jonny", "Junior", 1000, LocalDateTime.of(2022, 11, 19, 0, 0)));
		employees.put(2L, new Employee(2, "Middle Jonny", "Mid", 2000, LocalDateTime.of(2022, 5, 19, 0, 0)));
		employees.put(3L, new Employee(3, "Middle Jonny Jr.", "Mid2", 2000, LocalDateTime.of(2022, 1, 1, 0, 0)));
		employees.put(4L, new Employee(4, "Senio Jonny", "Senior", 5000, LocalDateTime.of(2019, 1, 1, 0, 0)));
		employees.put(5L, new Employee(5, "Old Jonny", "Architect", 10000, LocalDateTime.of(2014, 1, 1, 0, 0)));
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
