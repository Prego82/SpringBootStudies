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
	private String position;
	@Positive
	private int salary;
	@Past
	private LocalDateTime startTime;

	private String job;

	public EmployeeDto() {

	}

	public EmployeeDto(long id, String name, String position, int salary, LocalDateTime startTime) {
		super();
		this.id = id;
		this.name = name;
		this.position = position;
		this.salary = salary;
		this.startTime = startTime;
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

}
