package hu.cubix.hr.BalazsPeregi.dto;

public class JobSalaryDto {
	private String job;
	private Double avgSalary;

	public String getJob() {
		return job;
	}

	public Double getAvgSalary() {
		return avgSalary;
	}

	public JobSalaryDto(String job, Double avgSalary) {
		this.job = job;
		this.avgSalary = avgSalary;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public void setAvgSalary(Double avgSalary) {
		this.avgSalary = avgSalary;
	}

}
