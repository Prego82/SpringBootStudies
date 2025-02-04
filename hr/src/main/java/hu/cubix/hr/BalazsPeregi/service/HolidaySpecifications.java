package hu.cubix.hr.BalazsPeregi.service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.data.jpa.domain.Specification;

import hu.cubix.hr.BalazsPeregi.model.Employee_;
import hu.cubix.hr.BalazsPeregi.model.HolidayRequest;
import hu.cubix.hr.BalazsPeregi.model.HolidayRequestStatus;
import hu.cubix.hr.BalazsPeregi.model.HolidayRequest_;

public class HolidaySpecifications {

	public static Specification<HolidayRequest> hasStatus(HolidayRequestStatus status) {
		return (root, cq, cb) -> cb.equal(root.get(HolidayRequest_.status), status);
	}

	public static Specification<HolidayRequest> employeeNameLike(String prefix) {
		return (root, cq, cb) -> cb.like(cb.lower(root.get(HolidayRequest_.employee).get(Employee_.name)),
				prefix.toLowerCase() + "%");
	}

	public static Specification<HolidayRequest> managerNameLike(String prefix) {
		return (root, cq, cb) -> cb.like(cb.lower(root.get(HolidayRequest_.manager).get(Employee_.name)),
				prefix.toLowerCase() + "%");
	}

	public static Specification<HolidayRequest> creationDate(LocalDateTime startTime, LocalDateTime endTime) {
		LocalDateTime startOfDay = LocalDateTime.of(startTime.toLocalDate(), LocalTime.MIDNIGHT);
		LocalDateTime endOfDay = LocalDateTime.of(endTime.toLocalDate(), LocalTime.MAX);
		return (root, cq, cb) -> cb.between(root.get(HolidayRequest_.creationDate), startOfDay, endOfDay);
	}

	public static Specification<HolidayRequest> holidayTimeInterval(LocalDateTime startTime, LocalDateTime endTime) {
		LocalDateTime startOfDay = LocalDateTime.of(startTime.toLocalDate(), LocalTime.MIDNIGHT);
		LocalDateTime endOfDay = LocalDateTime.of(endTime.toLocalDate(), LocalTime.MAX);
		return (root, cq, cb) -> cb.and(cb.greaterThanOrEqualTo(root.get(HolidayRequest_.startDate), startOfDay),
				cb.lessThanOrEqualTo(root.get(HolidayRequest_.endDate), endOfDay));
	}
}
