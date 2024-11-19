package hu.cubix.hr.BalazsPeregi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "raises")
@Component
public class EmployeeConfigurationProperty {

	private int defaultRaisePercentage;
	private int minRaisePercentage;
	private int midRaisePercentage;
	private int maxRaisePercentage;
	private long minMonths;
	private long midMonths;
	private long maxMonths;

	public int getDefaultRaisePercentage() {
		return defaultRaisePercentage;
	}

	public void setDefaultRaisePercentage(int defaultRaisePercentage) {
		this.defaultRaisePercentage = defaultRaisePercentage;
	}

	public int getMinRaisePercentage() {
		return minRaisePercentage;
	}

	public void setMinRaisePercentage(int minRaisePercentage) {
		this.minRaisePercentage = minRaisePercentage;
	}

	public int getMidRaisePercentage() {
		return midRaisePercentage;
	}

	public void setMidRaisePercentage(int midRaisePercentage) {
		this.midRaisePercentage = midRaisePercentage;
	}

	public int getMaxRaisePercentage() {
		return maxRaisePercentage;
	}

	public void setMaxRaisePercentage(int maxRaisePercentage) {
		this.maxRaisePercentage = maxRaisePercentage;
	}

	public long getMinMonths() {
		return minMonths;
	}

	public void setMinMonths(long minMonths) {
		this.minMonths = minMonths;
	}

	public long getMidMonths() {
		return midMonths;
	}

	public void setMidMonths(long midMonths) {
		this.midMonths = midMonths;
	}

	public long getMaxMonths() {
		return maxMonths;
	}

	public void setMaxMonths(long maxMonths) {
		this.maxMonths = maxMonths;
	}

}
