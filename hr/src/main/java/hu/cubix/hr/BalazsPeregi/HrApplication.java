package hu.cubix.hr.BalazsPeregi;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import hu.cubix.hr.BalazsPeregi.model.Employee;
import hu.cubix.hr.BalazsPeregi.model.Position;
import hu.cubix.hr.BalazsPeregi.model.Qualification;
import hu.cubix.hr.BalazsPeregi.service.InitDbService;
import hu.cubix.hr.BalazsPeregi.service.SalaryService;

@SpringBootApplication
public class HrApplication implements CommandLineRunner {

	@Autowired
	SalaryService salaryService;

	@Autowired
	InitDbService init;

	private Employee junior = new Employee(1, "Little Jonny",
			new Position(0, "Junior", Qualification.HIGH_SCHOOL, 1000), 1000, LocalDateTime.of(2022, 11, 19, 0, 0));
	private Employee mid = new Employee(2, "Middle Jonny", new Position(1, "Mid", Qualification.COLLEGE, 2000), 2000,
			LocalDateTime.of(2022, 5, 19, 0, 0));
	private Employee mid2 = new Employee(3, "Middle Jonny Jr.", new Position(1, "Mid", Qualification.COLLEGE, 2000),
			2000, LocalDateTime.of(2022, 1, 1, 0, 0));
	private Employee senior = new Employee(4, "Senio Jonny", new Position(2, "Senior", Qualification.COLLEGE, 5000),
			5000, LocalDateTime.of(2019, 1, 1, 0, 0));
	private Employee architect = new Employee(5, "Old Jonny",
			new Position(3, "Architect", Qualification.UNIVERSITY, 1000), 10000, LocalDateTime.of(2014, 1, 1, 0, 0));

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
		init.clearDb();
		init.insertTestData();
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
