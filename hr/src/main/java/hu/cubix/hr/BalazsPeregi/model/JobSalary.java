package hu.cubix.hr.BalazsPeregi.model;

public class JobSalary {
	private String job;
	private Double avgSalary;

	public JobSalary(String job, Double avgSalary) {
		this.job = job;
		this.avgSalary = avgSalary;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Double getAvgSalary() {
		return avgSalary;
	}

	public void setAvgSalary(Double avgSalary) {
		this.avgSalary = avgSalary;
	}

}
