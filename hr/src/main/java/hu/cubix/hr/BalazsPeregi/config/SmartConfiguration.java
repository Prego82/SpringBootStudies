package hu.cubix.hr.BalazsPeregi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import hu.cubix.hr.BalazsPeregi.service.EmployeeService;
import hu.cubix.hr.BalazsPeregi.service.SmartEmployeeService;

@Configuration
@Profile("smart")
public class SmartConfiguration {

	@Bean
	EmployeeService getSalaryService() {
		return new SmartEmployeeService();
	}
}
