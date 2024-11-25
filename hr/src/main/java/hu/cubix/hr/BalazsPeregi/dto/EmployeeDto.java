package hu.cubix.hr.BalazsPeregi.dto;

import java.time.LocalDateTime;

public class EmployeeDto {
	private long id;
	private String job;
	private int salary;
	private LocalDateTime startTime;

	public EmployeeDto() {

	}

	public EmployeeDto(long id, String job, int salary, LocalDateTime startTime) {
		this.id = id;
		this.job = job;
		this.salary = salary;
		this.startTime = startTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
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
}
