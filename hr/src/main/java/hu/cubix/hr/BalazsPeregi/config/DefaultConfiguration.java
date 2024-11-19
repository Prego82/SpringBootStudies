package hu.cubix.hr.BalazsPeregi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import hu.cubix.hr.BalazsPeregi.service.DefaultEmployeeService;
import hu.cubix.hr.BalazsPeregi.service.EmployeeService;

@Configuration
@Profile("!smart")
public class DefaultConfiguration {

	@Bean
	EmployeeService getSalaryService() {
		return new DefaultEmployeeService();
	}
}
