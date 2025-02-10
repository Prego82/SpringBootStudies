package hu.cubix.hr.BalazsPeregi.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import hu.cubix.hr.BalazsPeregi.model.Company;
import hu.cubix.hr.BalazsPeregi.model.Employee;
import hu.cubix.hr.BalazsPeregi.repository.EmployeeRepository;
import hu.cubix.hr.BalazsPeregi.service.CompanyService;
import hu.cubix.hr.BalazsPeregi.service.InitDbService;

@SpringBootTest
@AutoConfigureTestDatabase
public class CompanyServiceIntegrationTest {

	@Autowired
	CompanyService service;

	@Autowired
	EmployeeRepository employeeRepo;

	@Autowired
	InitDbService initService;

	@BeforeEach
	void init() {
		initService.clearDb();
		initService.insertTestData();
	}

	@Test
	void testFindAllWithFetch() {
		List<Company> companies = service.findAllWithEmployees();
		assertThat(companies.size()).isEqualTo(5);
		assertThat(companies.get(0).getName()).isEqualTo("Apple");
		assertThat(companies.get(0).getEmployees().get(0).getName()).isEqualTo("Little Jonny");
		assertThat(companies.get(0).getEmployees().get(1).getName()).isEqualTo("Teszt Elek");

		assertThat(companies.get(1).getName()).isEqualTo("IBM");
		assertThat(companies.get(1).getEmployees().get(0).getName()).isEqualTo("Middle Jonny");

		assertThat(companies.get(2).getName()).isEqualTo("Intel");
		assertThat(companies.get(2).getEmployees().get(0).getName()).isEqualTo("Middle Jonny Jr.");

		assertThat(companies.get(3).getName()).isEqualTo("Google");
		assertThat(companies.get(3).getEmployees().get(0).getName()).isEqualTo("Senior Jonny");

		assertThat(companies.get(4).getName()).isEqualTo("AMD");
		assertThat(companies.get(4).getEmployees()).extracting(Employee::getName).containsExactlyInAnyOrder("Old Jonny",
				"Menedzs Elek");
	}

	@Test
	void testFindByIdWithFetch() {
		List<Company> companies = service.findAllWithEmployees();
		long id = companies.get(0).getId();
		Company company = service.findByIdWithFetch(id);
		assertThat(company.getName()).isEqualTo("Apple");
		assertThat(company.getEmployees()).extracting(Employee::getName).containsExactlyInAnyOrder("Little Jonny",
				"Teszt Elek");
	}

	@Test
	void testAddEmployeeToCompany() {
		// Arrange
		List<Employee> employees = employeeRepo.findAll();
		int randomEmployeeIndex = 6;
		List<Company> companies = service.findAll();
		int randomCompanyIndex = 0;
		long companyId = companies.get(randomCompanyIndex).getId();

		// Act
		service.addEmployeeToCompany(companyId, employees.get(randomEmployeeIndex));
		// Assert
		Company modifiedCompany = service.findByIdWithFetch(companyId);
		assertThat(modifiedCompany.getName()).isEqualTo("Apple");
		assertThat(modifiedCompany.getEmployees().size()).isEqualTo(3);
		assertThat(modifiedCompany.getEmployees()).extracting(Employee::getName)
				.containsExactlyInAnyOrder("Little Jonny", "Teszt Elek", "Menedzs Elek");
	}

	@Test
	void testRemoveEmployeeFromCompany() {
		// Arrange
		List<Employee> employees = employeeRepo.findAll();
		int randomEmployeeIndex = 6;
		List<Company> companies = service.findAll();
		int randomCompanyIndex = 4;
		long companyId = companies.get(randomCompanyIndex).getId();
		long employeeId = employees.get(randomEmployeeIndex).getId();

		// Act
		service.removeEmployeeFromCompany(companyId, employeeId);
		// Assert
		Company modifiedCompany = service.findByIdWithFetch(companyId);
		assertThat(modifiedCompany.getName()).isEqualTo("AMD");
		assertThat(modifiedCompany.getEmployees().size()).isEqualTo(1);
		assertThat(modifiedCompany.getEmployees().get(0).getName()).isEqualTo("Old Jonny");
	}

	@Test
	void testreplaceAllEmployeesInCompany() {
		// Arrange
		List<Employee> employees = employeeRepo.findAll();
		List<Company> companies = service.findAll();
		int randomCompanyIndex = 4;
		long companyId = companies.get(randomCompanyIndex).getId();

		// Act
		service.replaceAllEmployeesInCompany(companyId, employees);
		// Assert
		Company modifiedCompany = service.findByIdWithFetch(companyId);
		assertThat(modifiedCompany.getName()).isEqualTo("AMD");
		assertThat(modifiedCompany.getEmployees().size()).isEqualTo(7);
		assertThat(modifiedCompany.getEmployees()).extracting(Employee::getName).containsExactlyInAnyOrder(
				"Little Jonny", "Middle Jonny", "Middle Jonny Jr.", "Senior Jonny", "Old Jonny", "Teszt Elek",
				"Menedzs Elek");
	}
}
