package hu.cubix.hr.BalazsPeregi;

import java.time.LocalDateTime;

@Deprecated
public class Employee {
	private long id;
	private String name;
	private String job;
	private int salary;
	private LocalDateTime startTime;

	public Employee(long id, String name, String job, int salary, LocalDateTime startTime) {
		super();
		this.id = id;
		this.name = name;
		this.job = job;
		this.salary = salary;
		this.startTime = startTime;
	}

	public Employee() {
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

	@Override
	public String toString() {
		return String.format("Employee [id=%s, job=%s, salary=%s, startTime=%s]", id, job, salary, startTime);
	}
}
