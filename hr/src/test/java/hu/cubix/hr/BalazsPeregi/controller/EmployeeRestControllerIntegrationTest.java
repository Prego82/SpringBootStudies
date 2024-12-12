package hu.cubix.hr.BalazsPeregi.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import hu.cubix.hr.BalazsPeregi.dto.EmployeeDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class EmployeeRestControllerIntegrationTest {

	private static final String API_EMPLOYEES = "/api/employees";
	@Autowired
	WebTestClient webTestClient;

	@Test
	void testAddEmployee() {
		List<EmployeeDto> employeesBefore = getAllEmployee();
		long newId = 6;
		if (employeesBefore.size() > 0) {
			newId = employeesBefore.get(employeesBefore.size() - 1).getId() + 1;
		}
		EmployeeDto newEmployee = new EmployeeDto(newId, "New Employee", "Junior", 1000,
				LocalDateTime.of(2022, 11, 19, 0, 0));
		addEmployee(newEmployee);
		List<EmployeeDto> employeesAfter = getAllEmployee();
		assertThat(employeesAfter.subList(0, employeesBefore.size())).usingRecursiveFieldByFieldElementComparator()
				.containsExactlyElementsOf(employeesBefore);
		assertThat(employeesAfter.get(employeesAfter.size() - 1)).usingRecursiveComparison().isEqualTo(newEmployee);
	}

	@Test
	void testAddExistingEmployee() {
		List<EmployeeDto> employeesBefore = getAllEmployee();
		int randomIndex = ThreadLocalRandom.current().nextInt(employeesBefore.size());
		EmployeeDto newEmployee = new EmployeeDto(employeesBefore.get(randomIndex).getId(),
				employeesBefore.get(randomIndex).getName(), employeesBefore.get(randomIndex).getJob(),
				employeesBefore.get(randomIndex).getSalary(), employeesBefore.get(randomIndex).getStartTime());
		webTestClient.post().uri(API_EMPLOYEES).bodyValue(newEmployee).exchange().expectStatus().isBadRequest();
	}

	@Test
	void testUpdateEmployee() {
		List<EmployeeDto> employeesBefore = getAllEmployee();
		int randomIndex = ThreadLocalRandom.current().nextInt(employeesBefore.size());
		int newSalary = 99999;
		EmployeeDto modifiedEmployee = new EmployeeDto(employeesBefore.get(randomIndex).getId(),
				employeesBefore.get(randomIndex).getName(), employeesBefore.get(randomIndex).getJob(), newSalary,
				employeesBefore.get(randomIndex).getStartTime());
		updateEmployee(modifiedEmployee);
		List<EmployeeDto> employeesAfter = getAllEmployee();
		assertThat(employeesAfter.get(randomIndex).getSalary()).isEqualTo(newSalary);
	}

	@Test
	void testUpdateNotExistingEmployee() {
		List<EmployeeDto> employeesBefore = getAllEmployee();
		long highestId = employeesBefore.stream().max(Comparator.comparing(EmployeeDto::getId)).get().getId();
		EmployeeDto modifiedEmployee = new EmployeeDto(highestId + 10, "Teszt Elek", "Teszter", 1000,
				LocalDateTime.of(2014, 1, 1, 0, 0));
		webTestClient.put().uri(API_EMPLOYEES + "/{id}", modifiedEmployee.getId()).bodyValue(modifiedEmployee)
				.exchange().expectStatus().isBadRequest();
	}

	@Test
	void testRaisePercent() {
		List<EmployeeDto> employeesBefore = getAllEmployee();
		int randomIndex = ThreadLocalRandom.current().nextInt(employeesBefore.size());
		EmployeeDto employee = new EmployeeDto(employeesBefore.get(randomIndex).getId(),
				employeesBefore.get(randomIndex).getName(), employeesBefore.get(randomIndex).getJob(),
				employeesBefore.get(randomIndex).getSalary(), employeesBefore.get(randomIndex).getStartTime());
		double raisedSalaryPercent = queryRaisePercent(employee);
		// What should I assert here?
	}

	@Test
	void testRaisePercentNotExistingEmployee() {
		List<EmployeeDto> employeesBefore = getAllEmployee();
		long highestId = employeesBefore.stream().max(Comparator.comparing(EmployeeDto::getId)).get().getId();
		EmployeeDto randomEmployee = new EmployeeDto(highestId + 10, "Teszt Elek", "Teszter", 1000,
				LocalDateTime.of(2014, 1, 1, 0, 0));
		assertThat(webTestClient.post().uri(API_EMPLOYEES + "/raisePercentage").bodyValue(randomEmployee).exchange()
				.expectStatus().isOk().expectBody(Double.class).returnResult().getResponseBody()).isEqualTo(-1d);
	}

	private void addEmployee(EmployeeDto newEmployee) {
		webTestClient.post().uri(API_EMPLOYEES).bodyValue(newEmployee).exchange().expectStatus().isOk();
	}

	private void updateEmployee(EmployeeDto updatedEmployee) {
		webTestClient.put().uri(API_EMPLOYEES + "/{id}", updatedEmployee.getId()).bodyValue(updatedEmployee).exchange()
				.expectStatus().isOk();
	}

	private double queryRaisePercent(EmployeeDto employee) {
		return webTestClient.post().uri(API_EMPLOYEES + "/raisePercentage").bodyValue(employee).exchange()
				.expectStatus().isOk().expectBody(Double.class).returnResult().getResponseBody();
	}

	private List<EmployeeDto> getAllEmployee() {
		List<EmployeeDto> allEmployees = webTestClient.get().uri(API_EMPLOYEES).exchange().expectStatus().isOk()
				.expectBodyList(EmployeeDto.class).returnResult().getResponseBody();
		Collections.sort(allEmployees, Comparator.comparing(EmployeeDto::getId));
		return allEmployees;
	}

}
