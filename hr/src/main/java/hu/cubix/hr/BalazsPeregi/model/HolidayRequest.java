package hu.cubix.hr.BalazsPeregi.model;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class HolidayRequest {
	@Id
	@GeneratedValue
	private long id;
	@ManyToOne
	private Employee employee;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private LocalDateTime creationDate;
	@ManyToOne
	private Employee manager;
	private HolidayRequestStatus status;

	public HolidayRequest() {

	}

	public HolidayRequest(long id, Employee employee, LocalDateTime startDate, LocalDateTime endDate,
			LocalDateTime creationDate, Employee manager, HolidayRequestStatus status) {
		super();
		this.id = id;
		this.employee = employee;
		this.startDate = startDate;
		this.endDate = endDate;
		this.creationDate = creationDate;
		this.manager = manager;
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}

	public HolidayRequestStatus getStatus() {
		return status;
	}

	public void setStatus(HolidayRequestStatus status) {
		this.status = status;
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
		HolidayRequest other = (HolidayRequest) obj;
		return id == other.id;
	}

	public boolean isAccepted() {
		return status == HolidayRequestStatus.ACCEPTED;
	}

}
