package hu.cubix.hr.BalazsPeregi.service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.data.jpa.domain.Specification;

import hu.cubix.hr.BalazsPeregi.model.Company_;
import hu.cubix.hr.BalazsPeregi.model.Employee;
import hu.cubix.hr.BalazsPeregi.model.Employee_;
import hu.cubix.hr.BalazsPeregi.model.Position;
import hu.cubix.hr.BalazsPeregi.model.Position_;
import jakarta.persistence.criteria.Join;

public class EmployeeSpecifications {
	public static Specification<Employee> hasId(long id) {
		return (root, cq, cb) -> cb.equal(root.get(Employee_.id), id);
	}

	public static Specification<Employee> nameLike(String prefix) {
		return (root, cq, cb) -> cb.like(cb.lower(root.get(Employee_.name)), "%" + prefix.toLowerCase() + "%");
	}

	public static Specification<Employee> positionIs(String position) {
		return (root, cq, cb) -> {
			Join<Employee, Position> join = root.join(Employee_.position);
			return cb.equal(cb.lower(join.get(Position_.name)), position.toLowerCase());
		};
	}

	public static Specification<Employee> salaryIs(int salary) {
		return (root, cq, cb) -> cb.between(root.get(Employee_.salary), (int) Math.floor(salary * 0.95),
				(int) Math.ceil(salary * 1.05));
	}

	public static Specification<Employee> companyNameLike(String prefix) {
//		return (root, cq, cb) -> {
//			Join<Employee, Company> join = root.join(Employee_.company);
//			return cb.like(cb.lower(join.get(Company_.name)), "%" + prefix.toLowerCase() + "%");
//		};
		return (root, cq, cb) -> cb.like(cb.lower(root.get(Employee_.company).get(Company_.name)),
				"%" + prefix.toLowerCase() + "%");
	}

	public static Specification<Employee> entryDate(LocalDateTime startTime) {
		LocalDateTime startOfDay = LocalDateTime.of(startTime.toLocalDate(), LocalTime.MIDNIGHT);
		return (root, cq, cb) -> cb.between(root.get(Employee_.startTime), startOfDay, startOfDay.plusDays(1));
	}
}
