package hu.cubix.hr.BalazsPeregi.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;

import hu.cubix.hr.BalazsPeregi.config.DynamicEmployeeConfigurationProperty;
import hu.cubix.hr.BalazsPeregi.model.Employee;

public class SmartEmployeeService extends AbstractEmployeeService {

//	@Value("${defaultRaise}")
//	private int defaultRaisePercentage;
//	@Value("${minRaise}")
//	private int minRaisePercentage;
//	@Value("${midRaise}")
//	private int midRaisePercentage;
//	@Value("${maxRaise}")
//	private int maxRaisePercentage;
//
//	@Value("${minMonths}")
//	private long minMonths;
//	@Value("${midMonths}")
//	private long midMonths;
//	@Value("${maxMonths}")
//	private long maxMonths;

	@Autowired
//	private EmployeeConfigurationProperty config;
	private DynamicEmployeeConfigurationProperty config;

	@Override
	public int getPayRaisePercent(Employee employee) {
//		long elapsedTime = employee.getStartTime().until(LocalDateTime.now(), ChronoUnit.MONTHS);
//		if (elapsedTime < config.getMinMonths()) {
//			return config.getDefaultRaisePercentage();
//		} else if (elapsedTime >= config.getMinMonths() && elapsedTime < config.getMidMonths()) {
//			return config.getMinRaisePercentage();
//		} else if (elapsedTime >= config.getMidMonths() && elapsedTime < config.getMaxMonths()) {
//			return config.getMidRaisePercentage();
//		} else {
//			return config.getMaxRaisePercentage();
//		}
		long elapsedTime = employee.getStartTime().until(LocalDateTime.now(), ChronoUnit.MONTHS);
		for (DynamicEmployeeConfigurationProperty.Raise raise : config.getRaises()) {
			if (raise.getFrom() <= elapsedTime && elapsedTime < raise.getTo()) {
				return raise.getRaisePercentage();
			}
		}
		return 0;
	}

}
