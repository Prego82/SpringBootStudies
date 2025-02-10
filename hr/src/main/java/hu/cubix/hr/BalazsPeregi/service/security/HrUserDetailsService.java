package hu.cubix.hr.BalazsPeregi.service.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hu.cubix.hr.BalazsPeregi.model.Employee;
import hu.cubix.hr.BalazsPeregi.repository.EmployeeRepository;

@Service
public class HrUserDetailsService implements UserDetailsService {

	@Autowired
	private EmployeeRepository employeeRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee employee = employeeRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
		return new HrUserWrapper(employee.getUsername(), employee.getPassword(),
				List.of(new SimpleGrantedAuthority("USER")), employee);
	}

}
