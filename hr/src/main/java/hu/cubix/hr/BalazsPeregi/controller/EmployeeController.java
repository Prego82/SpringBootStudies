package hu.cubix.hr.BalazsPeregi.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import hu.cubix.hr.BalazsPeregi.Employee;

@Controller
public class EmployeeController {

	private List<Employee> employees = new ArrayList<>();

	{
		employees.add(new Employee(1, "Junior", 1000, LocalDateTime.of(2022, 11, 19, 0, 0)));
		employees.add(new Employee(2, "Mid", 2000, LocalDateTime.of(2022, 5, 19, 0, 0)));
		employees.add(new Employee(3, "Mid2", 2000, LocalDateTime.of(2022, 1, 1, 0, 0)));
		employees.add(new Employee(4, "Senior", 5000, LocalDateTime.of(2019, 1, 1, 0, 0)));
		employees.add(new Employee(5, "Architect", 10000, LocalDateTime.of(2014, 1, 1, 0, 0)));
	}

	@GetMapping("/")
	public String index(Map<String, Object> model) {
		model.put("employees", employees);
		model.put("newEmployee", new Employee());
		return "index";

	}

	@PostMapping("/employee")
	public String addEmployee(Employee employee) {
		employees.add(employee);
		return "redirect:/";
	}
}
