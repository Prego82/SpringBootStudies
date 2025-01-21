package hu.cubix.hr.BalazsPeregi.model;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/**
 * Model class
 */
@Entity
public class Employee {

	@Id
	@GeneratedValue
	private long id;
	private String name;
	@ManyToOne
	private Position position;
	private int salary;
	private LocalDateTime startTime;
	@ManyToOne
	private Company company;

	public Employee() {
	}

	public Employee(long id, String name, Position position, int salary, LocalDateTime startTime) {
		super();
		this.id = id;
		this.name = name;
		this.position = position;
		this.salary = salary;
		this.startTime = startTime;
	}

	public Employee(String name, Position position, int salary, LocalDateTime startTime) {
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

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Employee other = (Employee) obj;
		return id == other.id;
	}

}
