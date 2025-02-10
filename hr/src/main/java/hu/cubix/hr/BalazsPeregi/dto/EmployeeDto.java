package hu.cubix.hr.BalazsPeregi.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

//@JsonView(Views.Detailed.class)
public class EmployeeDto {

	@PositiveOrZero
	private long id;
	@NotEmpty
	private String name;
	@NotEmpty
	private String username;
	@NotEmpty
	private String password;
	@NotEmpty
	private String position;
	@Positive
	private int salary;
	@Past
	private LocalDateTime startTime;

	private long managerId;

	private String job;

	public EmployeeDto() {

	}

	public EmployeeDto(long id, String name, String username, String password, String position, int salary,
			LocalDateTime startTime, long managerId) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.position = position;
		this.salary = salary;
		this.startTime = startTime;
		this.managerId = managerId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public long getManagerId() {
		return managerId;
	}

	public void setManagerId(long managerId) {
		this.managerId = managerId;
	}

}
