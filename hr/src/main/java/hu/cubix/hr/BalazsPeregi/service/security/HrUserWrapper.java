package hu.cubix.hr.BalazsPeregi.service.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import hu.cubix.hr.BalazsPeregi.model.Employee;

public class HrUserWrapper extends User {

	private Employee employee;

	public HrUserWrapper(String username, String password, Collection<? extends GrantedAuthority> authorities,
			Employee employee) {
		super(username, password, authorities);
		this.employee = employee;
	}

}
