package hu.cubix.hr.BalazsPeregi.dto;

import java.time.LocalDateTime;

import hu.cubix.hr.BalazsPeregi.model.HolidayRequestStatus;

public class HolidayFilterDto {
	private HolidayRequestStatus status;
	private String employeeName;
	private String managerName;
	private LocalDateTime creationDateStart;
	private LocalDateTime creationDateEnd;
	private LocalDateTime startDate;
	private LocalDateTime endDate;

	public HolidayFilterDto() {
	}

	public HolidayRequestStatus getStatus() {
		return status;
	}

	public void setStatus(HolidayRequestStatus status) {
		this.status = status;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public LocalDateTime getCreationDateStart() {
		return creationDateStart;
	}

	public void setCreationDateStart(LocalDateTime creationDateStart) {
		this.creationDateStart = creationDateStart;
	}

	public LocalDateTime getCreationDateEnd() {
		return creationDateEnd;
	}

	public void setCreationDateEnd(LocalDateTime creationDateEnd) {
		this.creationDateEnd = creationDateEnd;
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
}
