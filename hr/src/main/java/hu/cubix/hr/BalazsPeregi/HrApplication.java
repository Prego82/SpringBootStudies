package hu.cubix.hr.BalazsPeregi;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import hu.cubix.hr.BalazsPeregi.service.SalaryService;

@SpringBootApplication
public class HrApplication implements CommandLineRunner {

	@Autowired
	SalaryService salaryService;

	private Employee junior = new Employee(1, "Junior", 1000, LocalDateTime.of(2022, 11, 19, 0, 0));
	private Employee mid = new Employee(2, "Mid", 2000, LocalDateTime.of(2022, 5, 19, 0, 0));
	private Employee mid2 = new Employee(3, "Mid2", 2000, LocalDateTime.of(2022, 1, 1, 0, 0));
	private Employee senior = new Employee(4, "Senior", 5000, LocalDateTime.of(2019, 1, 1, 0, 0));
	private Employee architect = new Employee(5, "Architect", 10000, LocalDateTime.of(2014, 1, 1, 0, 0));

	public static void main(String[] args) {
		SpringApplication.run(HrApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.err.println("Salaries before raise: ");
		printAllEmployees();
		salaryService.setNewSalary(junior);
		salaryService.setNewSalary(mid);
		salaryService.setNewSalary(mid2);
		salaryService.setNewSalary(senior);
		salaryService.setNewSalary(architect);
		System.err.println("Salaries after raise: ");
		printAllEmployees();
	}

	/**
	 * Just prints out all employees
	 */
	private void printAllEmployees() {
		System.out.println(junior);
		System.out.println(mid);
		System.out.println(mid2);
		System.out.println(senior);
		System.out.println(architect);
	}

}
