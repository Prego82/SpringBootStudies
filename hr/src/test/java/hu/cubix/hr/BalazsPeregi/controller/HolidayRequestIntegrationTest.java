package hu.cubix.hr.BalazsPeregi.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import hu.cubix.hr.BalazsPeregi.dto.HolidayFilterDto;
import hu.cubix.hr.BalazsPeregi.dto.HolidayRequestDto;
import hu.cubix.hr.BalazsPeregi.model.Employee;
import hu.cubix.hr.BalazsPeregi.model.HolidayRequestStatus;
import hu.cubix.hr.BalazsPeregi.service.AbstractEmployeeService;
import hu.cubix.hr.BalazsPeregi.service.InitDbService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class HolidayRequestIntegrationTest {
	private static final String API_HOLIDAY = "/api/holiday";
	@Autowired
	private WebTestClient webTestClient;

	@Autowired
	private InitDbService initService;

	@Autowired
	private AbstractEmployeeService employeeService;

	@BeforeEach
	void init() {
		initService.clearDb();
		initService.insertTestData();
	}

	@Test
	void addHolidayWithBadEmployeeId() {
		List<Employee> managers = employeeService.findByJob("Manager");
		HolidayRequestDto newHoliday = new HolidayRequestDto(0l, 0l, LocalDateTime.of(2025, 11, 19, 0, 0),
				LocalDateTime.of(2025, 11, 28, 0, 0), LocalDateTime.now(), managers.get(0).getId(),
				HolidayRequestStatus.NEW);
		webTestClient.post().uri(API_HOLIDAY).bodyValue(newHoliday).exchange().expectStatus().isBadRequest();
	}

	@Test
	void addHolidayWithBadManagerId() {
		List<Employee> employees = employeeService.findByJob("Junior");
		HolidayRequestDto newHoliday = new HolidayRequestDto(0l, employees.get(0).getId(),
				LocalDateTime.of(2025, 11, 19, 0, 0), LocalDateTime.of(2025, 11, 28, 0, 0), LocalDateTime.now(), 0L,
				HolidayRequestStatus.NEW);
		webTestClient.post().uri(API_HOLIDAY).bodyValue(newHoliday).exchange().expectStatus().isBadRequest();
	}

	@Test
	void addHoliday() {
		Employee manager = employeeService.findByJob("Manager").get(0);
		Employee employee = employeeService.findByJob("Junior").get(0);
		HolidayRequestDto newHoliday = new HolidayRequestDto(0l, employee.getId(), LocalDateTime.of(2025, 11, 19, 0, 0),
				LocalDateTime.of(2025, 11, 28, 0, 0), LocalDateTime.now(), manager.getId(), HolidayRequestStatus.NEW);
		webTestClient.post().uri(API_HOLIDAY).bodyValue(newHoliday).exchange().expectStatus().isOk();
		List<HolidayRequestDto> allHolidays = getAllHolidays();
		assertThat(allHolidays.size()).isGreaterThan(0);
	}

	@Test
	void listAllHolidayRequests() {
		webTestClient.get().uri(API_HOLIDAY).exchange().expectStatus().isOk();
	}

	@Test
	void acceptNonExistentHolidayRequests() {
		long holidayId = 1000l;
		webTestClient.put()
				.uri(uriBuilder -> uriBuilder.path(API_HOLIDAY + "/accept").queryParam("requestId", holidayId).build())
				.exchange().expectStatus().isBadRequest();
	}

	@Test
	void acceptHolidayRequests() {
		addHoliday();
		long holidayId = getAllHolidays().get(0).getId();
		webTestClient.put()
				.uri(uriBuilder -> uriBuilder.path(API_HOLIDAY + "/accept").queryParam("requestId", holidayId).build())
				.exchange().expectStatus().isOk();
		assertThat(getAllHolidays().get(0).getStatus()).isEqualTo(HolidayRequestStatus.ACCEPTED);
	}

	@Test
	void rejetcNonExistentHolidayRequests() {
		long holidayId = 1000l;
		webTestClient.put()
				.uri(uriBuilder -> uriBuilder.path(API_HOLIDAY + "/reject").queryParam("requestId", holidayId).build())
				.exchange().expectStatus().isBadRequest();
	}

	@Test
	void rejectHolidayRequests() {
		addHoliday();
		long holidayId = getAllHolidays().get(0).getId();
		webTestClient.put()
				.uri(uriBuilder -> uriBuilder.path(API_HOLIDAY + "/reject").queryParam("requestId", holidayId).build())
				.exchange().expectStatus().isOk();
		assertThat(getAllHolidays().get(0).getStatus()).isEqualTo(HolidayRequestStatus.REJECTED);
	}

	@Test
	void updateNonExistentHolidayRequests() {
		Employee manager = employeeService.findByJob("Manager").get(0);
		Employee employee = employeeService.findByJob("Junior").get(0);
		long holidayId = 1000l;
		HolidayRequestDto updatedHoliday = new HolidayRequestDto(holidayId, employee.getId(),
				LocalDateTime.of(2025, 11, 19, 0, 0), LocalDateTime.of(2025, 11, 28, 0, 0), LocalDateTime.now(),
				manager.getId(), HolidayRequestStatus.NEW);
		webTestClient.put()
				.uri(uriBuilder -> uriBuilder.path(API_HOLIDAY).path("/{requestId}").build(String.valueOf(holidayId)))
				.bodyValue(updatedHoliday).exchange().expectStatus().isBadRequest();
	}

	@Test
	void updateAcceptedHolidayRequests() {
		addHoliday();
		Employee manager = employeeService.findByJob("Manager").get(0);
		Employee employee = employeeService.findByJob("Junior").get(0);
		long holidayId = getAllHolidays().get(0).getId();
		webTestClient.put()
				.uri(uriBuilder -> uriBuilder.path(API_HOLIDAY + "/accept").queryParam("requestId", holidayId).build())
				.exchange().expectStatus().isOk();
		HolidayRequestDto updatedHoliday = new HolidayRequestDto(holidayId, employee.getId(),
				LocalDateTime.of(2026, 11, 19, 0, 0), LocalDateTime.of(2026, 11, 28, 0, 0), LocalDateTime.now(),
				manager.getId(), HolidayRequestStatus.NEW);
		webTestClient.put()
				.uri(uriBuilder -> uriBuilder.path(API_HOLIDAY).path("/{requestId}").build(String.valueOf(holidayId)))
				.bodyValue(updatedHoliday).exchange().expectStatus().isBadRequest();
	}

	@Test
	void updateHolidayRequests() {
		addHoliday();
		Employee manager = employeeService.findByJob("Manager").get(0);
		Employee employee = employeeService.findByJob("Junior").get(0);
		long holidayId = getAllHolidays().get(0).getId();
		HolidayRequestDto updatedHoliday = new HolidayRequestDto(holidayId, employee.getId(),
				LocalDateTime.of(2026, 11, 19, 0, 0), LocalDateTime.of(2026, 11, 28, 0, 0), LocalDateTime.now(),
				manager.getId(), HolidayRequestStatus.NEW);
		webTestClient.put()
				.uri(uriBuilder -> uriBuilder.path(API_HOLIDAY).path("/{requestId}").build(String.valueOf(holidayId)))
				.bodyValue(updatedHoliday).exchange().expectStatus().isOk();
		HolidayRequestDto modifiedHoliday = getAllHolidays().get(0);
		assertThat(modifiedHoliday.getStartDate()).isEqualTo(LocalDateTime.of(2026, 11, 19, 0, 0));
		assertThat(modifiedHoliday.getEndDate()).isEqualTo(LocalDateTime.of(2026, 11, 28, 0, 0));
	}

	@Test
	void deleteNonExistentHolidayRequests() {
		long holidayId = 1000l;
		webTestClient.delete()
				.uri(uriBuilder -> uriBuilder.path(API_HOLIDAY).path("/{requestId}").build(String.valueOf(holidayId)))
				.exchange().expectStatus().isBadRequest();
	}

	@Test
	void deleteAcceptedHolidayRequests() {
		addHoliday();
		long holidayId = getAllHolidays().get(0).getId();
		webTestClient.put()
				.uri(uriBuilder -> uriBuilder.path(API_HOLIDAY + "/accept").queryParam("requestId", holidayId).build())
				.exchange().expectStatus().isOk();
		webTestClient.delete()
				.uri(uriBuilder -> uriBuilder.path(API_HOLIDAY).path("/{requestId}").build(String.valueOf(holidayId)))
				.exchange().expectStatus().isBadRequest();
	}

	@Test
	void deleteHolidayRequests() {
		addHoliday();
		long holidayId = getAllHolidays().get(0).getId();
		webTestClient.delete()
				.uri(uriBuilder -> uriBuilder.path(API_HOLIDAY).path("/{requestId}").build(String.valueOf(holidayId)))
				.exchange().expectStatus().isOk();
	}

	@Test
	void sortByStartDateWithPagination() {
		Employee manager = employeeService.findByJob("Manager").get(0);
		Employee employee = employeeService.findByJob("Junior").get(0);
		HolidayRequestDto newHoliday = new HolidayRequestDto(0l, employee.getId(), LocalDateTime.of(2125, 11, 19, 0, 0),
				LocalDateTime.of(2125, 11, 28, 0, 0), LocalDateTime.now(), manager.getId(), HolidayRequestStatus.NEW);
		webTestClient.post().uri(API_HOLIDAY).bodyValue(newHoliday).exchange().expectStatus().isOk();

		HolidayRequestDto newHoliday2 = new HolidayRequestDto(0l, employee.getId(),
				LocalDateTime.of(2125, 03, 19, 0, 0), LocalDateTime.of(2125, 03, 28, 0, 0), LocalDateTime.now(),
				manager.getId(), HolidayRequestStatus.NEW);
		webTestClient.post().uri(API_HOLIDAY).bodyValue(newHoliday2).exchange().expectStatus().isOk();

		HolidayRequestDto newHoliday3 = new HolidayRequestDto(0l, employee.getId(),
				LocalDateTime.of(2125, 04, 19, 0, 0), LocalDateTime.of(2125, 04, 28, 0, 0), LocalDateTime.now(),
				manager.getId(), HolidayRequestStatus.NEW);
		webTestClient.post().uri(API_HOLIDAY).bodyValue(newHoliday3).exchange().expectStatus().isOk();

		HolidayFilterDto holidayFilter = new HolidayFilterDto();
		List<HolidayRequestDto> allHolidays = webTestClient.post()
				.uri(uriBuilder -> uriBuilder.path(API_HOLIDAY).queryParam("page", "0").queryParam("size", "3")
						.queryParam("sort", "startDate").build())
				.bodyValue(holidayFilter).exchange().expectStatus().isOk().expectBodyList(HolidayRequestDto.class)
				.returnResult().getResponseBody();
		HolidayRequestDto modifiedHoliday1 = allHolidays.get(0);
		assertThat(modifiedHoliday1.getStartDate()).isEqualTo(LocalDateTime.of(2125, 03, 19, 0, 0));
		HolidayRequestDto modifiedHoliday2 = allHolidays.get(1);
		assertThat(modifiedHoliday2.getStartDate()).isEqualTo(LocalDateTime.of(2125, 04, 19, 0, 0));
		HolidayRequestDto modifiedHoliday3 = allHolidays.get(2);
		assertThat(modifiedHoliday3.getStartDate()).isEqualTo(LocalDateTime.of(2125, 11, 19, 0, 0));
	}

	@Test
	void sortByEndDateWithPagination() {
		Employee manager = employeeService.findByJob("Manager").get(0);
		Employee employee = employeeService.findByJob("Junior").get(0);
		HolidayRequestDto newHoliday = new HolidayRequestDto(0l, employee.getId(), LocalDateTime.of(2125, 11, 19, 0, 0),
				LocalDateTime.of(2125, 11, 28, 0, 0), LocalDateTime.now(), manager.getId(), HolidayRequestStatus.NEW);
		webTestClient.post().uri(API_HOLIDAY).bodyValue(newHoliday).exchange().expectStatus().isOk();

		HolidayRequestDto newHoliday2 = new HolidayRequestDto(0l, employee.getId(),
				LocalDateTime.of(2125, 03, 19, 0, 0), LocalDateTime.of(2125, 10, 28, 0, 0), LocalDateTime.now(),
				manager.getId(), HolidayRequestStatus.NEW);
		webTestClient.post().uri(API_HOLIDAY).bodyValue(newHoliday2).exchange().expectStatus().isOk();

		HolidayRequestDto newHoliday3 = new HolidayRequestDto(0l, employee.getId(),
				LocalDateTime.of(2125, 04, 19, 0, 0), LocalDateTime.of(2125, 9, 28, 0, 0), LocalDateTime.now(),
				manager.getId(), HolidayRequestStatus.NEW);
		webTestClient.post().uri(API_HOLIDAY).bodyValue(newHoliday3).exchange().expectStatus().isOk();

		HolidayFilterDto holidayFilter = new HolidayFilterDto();
		List<HolidayRequestDto> allHolidays = webTestClient.post()
				.uri(uriBuilder -> uriBuilder.path(API_HOLIDAY).queryParam("page", "0").queryParam("size", "3")
						.queryParam("sort", "endDate").build())
				.bodyValue(holidayFilter).exchange().expectStatus().isOk().expectBodyList(HolidayRequestDto.class)
				.returnResult().getResponseBody();
		HolidayRequestDto modifiedHoliday1 = allHolidays.get(0);
		assertThat(modifiedHoliday1.getEndDate()).isEqualTo(LocalDateTime.of(2125, 9, 28, 0, 0));
		HolidayRequestDto modifiedHoliday2 = allHolidays.get(1);
		assertThat(modifiedHoliday2.getEndDate()).isEqualTo(LocalDateTime.of(2125, 10, 28, 0, 0));
		HolidayRequestDto modifiedHoliday3 = allHolidays.get(2);
		assertThat(modifiedHoliday3.getEndDate()).isEqualTo(LocalDateTime.of(2125, 11, 28, 0, 0));
	}

	@Test
	void filterByStatus() {
		Employee manager = employeeService.findByJob("Manager").get(0);
		Employee employee = employeeService.findByJob("Junior").get(0);
		HolidayRequestDto newHoliday = new HolidayRequestDto(0l, employee.getId(), LocalDateTime.of(2125, 11, 19, 0, 0),
				LocalDateTime.of(2125, 11, 28, 0, 0), LocalDateTime.now(), manager.getId(), HolidayRequestStatus.NEW);
		long idOfAcceptedRequest = webTestClient.post().uri(API_HOLIDAY).bodyValue(newHoliday).exchange().expectStatus()
				.isOk().expectBody(HolidayRequestDto.class).returnResult().getResponseBody().getId();

		HolidayRequestDto newHoliday2 = new HolidayRequestDto(0l, employee.getId(),
				LocalDateTime.of(2125, 03, 19, 0, 0), LocalDateTime.of(2125, 10, 28, 0, 0), LocalDateTime.now(),
				manager.getId(), HolidayRequestStatus.NEW);
		webTestClient.post().uri(API_HOLIDAY).bodyValue(newHoliday2).exchange().expectStatus().isOk();

		HolidayRequestDto newHoliday3 = new HolidayRequestDto(0l, employee.getId(),
				LocalDateTime.of(2125, 04, 19, 0, 0), LocalDateTime.of(2125, 9, 28, 0, 0), LocalDateTime.now(),
				manager.getId(), HolidayRequestStatus.NEW);
		webTestClient.post().uri(API_HOLIDAY).bodyValue(newHoliday3).exchange().expectStatus().isOk();

		webTestClient.put().uri(uriBuilder -> uriBuilder.path(API_HOLIDAY + "/accept")
				.queryParam("requestId", idOfAcceptedRequest).build()).exchange().expectStatus().isOk();

		HolidayFilterDto holidayFilter = new HolidayFilterDto();
		holidayFilter.setStatus(HolidayRequestStatus.ACCEPTED);
		List<HolidayRequestDto> allHolidays = webTestClient.post()
				.uri(uriBuilder -> uriBuilder.path(API_HOLIDAY).queryParam("page", "0").queryParam("size", "3")
						.queryParam("sort", "endDate").build())
				.bodyValue(holidayFilter).exchange().expectStatus().isOk().expectBodyList(HolidayRequestDto.class)
				.returnResult().getResponseBody();
		assertThat(allHolidays.size()).isEqualTo(1);
		HolidayRequestDto modifiedHoliday1 = allHolidays.get(0);
		assertThat(modifiedHoliday1.getEndDate()).isEqualTo(LocalDateTime.of(2125, 11, 28, 0, 0));
		assertThat(modifiedHoliday1.getStatus()).isEqualTo(HolidayRequestStatus.ACCEPTED);
	}

	@Test
	void filterByEmployeeName() {
		Employee manager = employeeService.findByJob("Manager").get(0);
		Employee employee = employeeService.findByJob("Junior").get(0);
		HolidayRequestDto newHoliday = new HolidayRequestDto(0l, employee.getId(), LocalDateTime.of(2125, 11, 19, 0, 0),
				LocalDateTime.of(2125, 11, 28, 0, 0), LocalDateTime.now(), manager.getId(),
				HolidayRequestStatus.ACCEPTED);
		webTestClient.post().uri(API_HOLIDAY).bodyValue(newHoliday).exchange().expectStatus().isOk();

		HolidayRequestDto newHoliday2 = new HolidayRequestDto(0l, employee.getId(),
				LocalDateTime.of(2125, 03, 19, 0, 0), LocalDateTime.of(2125, 10, 28, 0, 0), LocalDateTime.now(),
				manager.getId(), HolidayRequestStatus.NEW);
		webTestClient.post().uri(API_HOLIDAY).bodyValue(newHoliday2).exchange().expectStatus().isOk();

		HolidayRequestDto newHoliday3 = new HolidayRequestDto(0l, employee.getId(),
				LocalDateTime.of(2125, 04, 19, 0, 0), LocalDateTime.of(2125, 9, 28, 0, 0), LocalDateTime.now(),
				manager.getId(), HolidayRequestStatus.NEW);
		webTestClient.post().uri(API_HOLIDAY).bodyValue(newHoliday3).exchange().expectStatus().isOk();

		HolidayFilterDto holidayFilter = new HolidayFilterDto();
		holidayFilter.setEmployeeName(employee.getName());
		List<HolidayRequestDto> allHolidays = webTestClient.post()
				.uri(uriBuilder -> uriBuilder.path(API_HOLIDAY).queryParam("page", "0").queryParam("size", "3")
						.queryParam("sort", "endDate").build())
				.bodyValue(holidayFilter).exchange().expectStatus().isOk().expectBodyList(HolidayRequestDto.class)
				.returnResult().getResponseBody();
		HolidayRequestDto modifiedHoliday1 = allHolidays.get(0);
		assertThat(modifiedHoliday1.getEmployeeId()).isEqualTo(employee.getId());
		HolidayRequestDto modifiedHoliday2 = allHolidays.get(1);
		assertThat(modifiedHoliday2.getEmployeeId()).isEqualTo(employee.getId());
		HolidayRequestDto modifiedHoliday3 = allHolidays.get(2);
		assertThat(modifiedHoliday3.getEmployeeId()).isEqualTo(employee.getId());
	}

	@Test
	void filterByManagerName() {
		Employee manager = employeeService.findByJob("Manager").get(0);
		Employee employee = employeeService.findByJob("Junior").get(0);
		HolidayRequestDto newHoliday = new HolidayRequestDto(0l, employee.getId(), LocalDateTime.of(2125, 11, 19, 0, 0),
				LocalDateTime.of(2125, 11, 28, 0, 0), LocalDateTime.now(), manager.getId(),
				HolidayRequestStatus.ACCEPTED);
		webTestClient.post().uri(API_HOLIDAY).bodyValue(newHoliday).exchange().expectStatus().isOk();

		HolidayRequestDto newHoliday2 = new HolidayRequestDto(0l, employee.getId(),
				LocalDateTime.of(2125, 03, 19, 0, 0), LocalDateTime.of(2125, 10, 28, 0, 0), LocalDateTime.now(),
				manager.getId(), HolidayRequestStatus.NEW);
		webTestClient.post().uri(API_HOLIDAY).bodyValue(newHoliday2).exchange().expectStatus().isOk();

		HolidayRequestDto newHoliday3 = new HolidayRequestDto(0l, employee.getId(),
				LocalDateTime.of(2125, 04, 19, 0, 0), LocalDateTime.of(2125, 9, 28, 0, 0), LocalDateTime.now(),
				manager.getId(), HolidayRequestStatus.NEW);
		webTestClient.post().uri(API_HOLIDAY).bodyValue(newHoliday3).exchange().expectStatus().isOk();

		HolidayFilterDto holidayFilter = new HolidayFilterDto();
		holidayFilter.setManagerName(manager.getName());
		List<HolidayRequestDto> allHolidays = webTestClient.post()
				.uri(uriBuilder -> uriBuilder.path(API_HOLIDAY).queryParam("page", "0").queryParam("size", "3")
						.queryParam("sort", "endDate").build())
				.bodyValue(holidayFilter).exchange().expectStatus().isOk().expectBodyList(HolidayRequestDto.class)
				.returnResult().getResponseBody();
		HolidayRequestDto modifiedHoliday1 = allHolidays.get(0);
		assertThat(modifiedHoliday1.getManagerId()).isEqualTo(manager.getId());
		HolidayRequestDto modifiedHoliday2 = allHolidays.get(1);
		assertThat(modifiedHoliday2.getManagerId()).isEqualTo(manager.getId());
		HolidayRequestDto modifiedHoliday3 = allHolidays.get(2);
		assertThat(modifiedHoliday3.getManagerId()).isEqualTo(manager.getId());
	}

	@Test
	void filterByCreationDate() {
		Employee manager = employeeService.findByJob("Manager").get(0);
		Employee employee = employeeService.findByJob("Junior").get(0);
		HolidayRequestDto newHoliday = new HolidayRequestDto(0l, employee.getId(), LocalDateTime.of(2125, 11, 19, 0, 0),
				LocalDateTime.of(2125, 11, 28, 0, 0), LocalDateTime.of(2025, 01, 01, 0, 0), manager.getId(),
				HolidayRequestStatus.ACCEPTED);
		webTestClient.post().uri(API_HOLIDAY).bodyValue(newHoliday).exchange().expectStatus().isOk();

		HolidayRequestDto newHoliday2 = new HolidayRequestDto(0l, employee.getId(),
				LocalDateTime.of(2125, 03, 19, 0, 0), LocalDateTime.of(2125, 10, 28, 0, 0),
				LocalDateTime.of(2024, 12, 01, 0, 0), manager.getId(), HolidayRequestStatus.NEW);
		webTestClient.post().uri(API_HOLIDAY).bodyValue(newHoliday2).exchange().expectStatus().isOk();

		HolidayRequestDto newHoliday3 = new HolidayRequestDto(0l, employee.getId(),
				LocalDateTime.of(2125, 04, 19, 0, 0), LocalDateTime.of(2125, 9, 28, 0, 0),
				LocalDateTime.of(2024, 11, 01, 0, 0), manager.getId(), HolidayRequestStatus.NEW);
		webTestClient.post().uri(API_HOLIDAY).bodyValue(newHoliday3).exchange().expectStatus().isOk();

		HolidayFilterDto holidayFilter = new HolidayFilterDto();
		holidayFilter.setCreationDateStart(LocalDateTime.of(2024, 10, 01, 0, 0));
		holidayFilter.setCreationDateEnd(LocalDateTime.of(2024, 12, 31, 0, 0));
		List<HolidayRequestDto> allHolidays = webTestClient.post()
				.uri(uriBuilder -> uriBuilder.path(API_HOLIDAY).queryParam("page", "0").queryParam("size", "3")
						.queryParam("sort", "endDate").build())
				.bodyValue(holidayFilter).exchange().expectStatus().isOk().expectBodyList(HolidayRequestDto.class)
				.returnResult().getResponseBody();
		assertThat(allHolidays.size()).isEqualTo(2);
		HolidayRequestDto modifiedHoliday1 = allHolidays.get(0);
		assertThat(modifiedHoliday1.getCreationDate()).isEqualTo(LocalDateTime.of(2024, 12, 01, 0, 0));
		HolidayRequestDto modifiedHoliday2 = allHolidays.get(1);
		assertThat(modifiedHoliday2.getCreationDate()).isEqualTo(LocalDateTime.of(2024, 12, 01, 0, 0));
	}

	@Test
	void filterByTimeInterval() {
		Employee manager = employeeService.findByJob("Manager").get(0);
		Employee employee = employeeService.findByJob("Junior").get(0);
		HolidayRequestDto newHoliday = new HolidayRequestDto(0l, employee.getId(), LocalDateTime.of(2125, 11, 19, 0, 0),
				LocalDateTime.of(2125, 11, 28, 0, 0), LocalDateTime.of(2025, 01, 01, 0, 0), manager.getId(),
				HolidayRequestStatus.ACCEPTED);
		webTestClient.post().uri(API_HOLIDAY).bodyValue(newHoliday).exchange().expectStatus().isOk();

		HolidayRequestDto newHoliday2 = new HolidayRequestDto(0l, employee.getId(),
				LocalDateTime.of(2125, 03, 19, 0, 0), LocalDateTime.of(2125, 10, 28, 0, 0),
				LocalDateTime.of(2024, 12, 01, 0, 0), manager.getId(), HolidayRequestStatus.NEW);
		webTestClient.post().uri(API_HOLIDAY).bodyValue(newHoliday2).exchange().expectStatus().isOk();

		HolidayRequestDto newHoliday3 = new HolidayRequestDto(0l, employee.getId(),
				LocalDateTime.of(2125, 04, 19, 0, 0), LocalDateTime.of(2125, 9, 28, 0, 0),
				LocalDateTime.of(2024, 11, 01, 0, 0), manager.getId(), HolidayRequestStatus.NEW);
		webTestClient.post().uri(API_HOLIDAY).bodyValue(newHoliday3).exchange().expectStatus().isOk();

		HolidayFilterDto holidayFilter = new HolidayFilterDto();
		holidayFilter.setStartDate(LocalDateTime.of(2125, 3, 10, 0, 0));
		holidayFilter.setEndDate(LocalDateTime.of(2125, 10, 29, 0, 0));
		List<HolidayRequestDto> allHolidays = webTestClient.post()
				.uri(uriBuilder -> uriBuilder.path(API_HOLIDAY).queryParam("page", "0").queryParam("size", "3")
						.queryParam("sort", "endDate").build())
				.bodyValue(holidayFilter).exchange().expectStatus().isOk().expectBodyList(HolidayRequestDto.class)
				.returnResult().getResponseBody();
		assertThat(allHolidays.size()).isEqualTo(2);
		HolidayRequestDto modifiedHoliday1 = allHolidays.get(0);
		assertThat(modifiedHoliday1.getStartDate()).isEqualTo(LocalDateTime.of(2125, 03, 19, 0, 0));
		assertThat(modifiedHoliday1.getEndDate()).isEqualTo(LocalDateTime.of(2125, 10, 28, 0, 0));
		HolidayRequestDto modifiedHoliday2 = allHolidays.get(1);
		assertThat(modifiedHoliday2.getStartDate()).isEqualTo(LocalDateTime.of(2125, 04, 19, 0, 0));
		assertThat(modifiedHoliday2.getEndDate()).isEqualTo(LocalDateTime.of(2125, 9, 28, 0, 0));
	}

	private List<HolidayRequestDto> getAllHolidays() {
		List<HolidayRequestDto> allHolidays = webTestClient.get().uri(API_HOLIDAY).exchange().expectStatus().isOk()
				.expectBodyList(HolidayRequestDto.class).returnResult().getResponseBody();
		Collections.sort(allHolidays, Comparator.comparing(HolidayRequestDto::getId));
		return allHolidays;
	}

}
