package hu.cubix.hr.BalazsPeregi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import hu.cubix.hr.BalazsPeregi.dto.HolidayFilterDto;
import hu.cubix.hr.BalazsPeregi.model.Employee;
import hu.cubix.hr.BalazsPeregi.model.HolidayRequest;
import hu.cubix.hr.BalazsPeregi.model.HolidayRequestStatus;
import hu.cubix.hr.BalazsPeregi.repository.HolidayRepo;
import jakarta.transaction.Transactional;

@Service
public class HolidayRequestService {

	@Autowired
	private AbstractEmployeeService employeeService;

	@Autowired
	private HolidayRepo holidayRepo;

	@Transactional
	public HolidayRequest placeNewRequest(Long employeeId, Long managerId, HolidayRequest holidayRequest) {
		Employee requester = employeeService.findById(employeeId);
		Employee approver = employeeService.findById(managerId);
		if (requester == null || approver == null) {
			return null;
		}
		holidayRequest.setEmployee(requester);
		holidayRequest.setManager(approver);
		return holidayRepo.save(holidayRequest);
	}

	public List<HolidayRequest> getAll() {
		return holidayRepo.findAll();
	}

	@Transactional
	public HolidayRequest acceptRequest(long requestId) {
		Optional<HolidayRequest> request = holidayRepo.findById(requestId);
		if (request.orElse(null) == null) {
			return null;
		}
		request.get().setStatus(HolidayRequestStatus.ACCEPTED);
		return request.get();
	}

	@Transactional
	public HolidayRequest rejectRequest(long requestId) {
		Optional<HolidayRequest> request = holidayRepo.findById(requestId);
		if (request.orElse(null) == null) {
			return null;
		}
		request.get().setStatus(HolidayRequestStatus.REJECTED);
		return request.get();
	}

	@Transactional
	public HolidayRequest updateRequest(long requestId, long managerId, HolidayRequest request) {
		Optional<HolidayRequest> requestToUpdate = holidayRepo.findById(requestId);
		if (requestToUpdate.orElse(null) == null || requestToUpdate.get().isAccepted()) {
			return null;
		}
		Employee manager = employeeService.findById(managerId);
		requestToUpdate.get().setCreationDate(request.getCreationDate());
		requestToUpdate.get().setEndDate(request.getEndDate());
		requestToUpdate.get().setStartDate(request.getStartDate());
		requestToUpdate.get().setManager(manager);
		return requestToUpdate.get();
	}

	public Page<HolidayRequest> findDynamically(HolidayFilterDto holidayFilter, Pageable pageable) {

		Specification<HolidayRequest> specs = Specification.where(null);
		if (holidayFilter != null) {
			if (holidayFilter.getStatus() != null) {
				specs = specs.and(HolidaySpecifications.hasStatus(holidayFilter.getStatus()));
			}
			if (StringUtils.hasLength(holidayFilter.getEmployeeName())) {
				specs = specs.and(HolidaySpecifications.employeeNameLike(holidayFilter.getEmployeeName()));
			}
			if (StringUtils.hasLength(holidayFilter.getManagerName())) {
				specs = specs.and(HolidaySpecifications.managerNameLike(holidayFilter.getManagerName()));
			}

			if (holidayFilter.getCreationDateStart() != null && holidayFilter.getCreationDateEnd() != null) {
				specs = specs.and(HolidaySpecifications.creationDate(holidayFilter.getCreationDateStart(),
						holidayFilter.getCreationDateEnd()));
			}
			if (holidayFilter.getStartDate() != null && holidayFilter.getEndDate() != null) {
				specs = specs.and(HolidaySpecifications.holidayTimeInterval(holidayFilter.getStartDate(),
						holidayFilter.getEndDate()));
			}
		}

		return holidayRepo.findAll(specs, pageable);
	}

	@Transactional
	public boolean deleteRequest(long requestId) {
		Optional<HolidayRequest> holiday = holidayRepo.findById(requestId);
		if (holiday.orElse(null) == null || holiday.get().isAccepted()) {
			return false;
		}
		holidayRepo.deleteById(requestId);
		return true;
	}

}
