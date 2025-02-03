package hu.cubix.hr.BalazsPeregi.dto;

import java.time.LocalDateTime;

import hu.cubix.hr.BalazsPeregi.model.HolidayRequestStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public class HolidayRequestDto {
	private long id;
	@NotNull
	private Long employeeId;
	@FutureOrPresent
	private LocalDateTime startDate;
	@FutureOrPresent
	private LocalDateTime endDate;
	@PastOrPresent
	private LocalDateTime creationDate;
	@NotNull
	private Long managerId;
	private HolidayRequestStatus status;

	public HolidayRequestDto() {

	}

	public HolidayRequestDto(long id, Long employeeId, LocalDateTime startDate, LocalDateTime endDate,
			LocalDateTime creationDate, Long managerId, HolidayRequestStatus status) {
		this.id = id;
		this.employeeId = employeeId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.creationDate = creationDate;
		this.managerId = managerId;
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
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

	public Long getManagerId() {
		return managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

	public HolidayRequestStatus getStatus() {
		return status;
	}

	public void setStatus(HolidayRequestStatus status) {
		this.status = status;
	}
}
