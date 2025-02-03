package hu.cubix.hr.BalazsPeregi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.cubix.hr.BalazsPeregi.dto.HolidayFilterDto;
import hu.cubix.hr.BalazsPeregi.dto.HolidayRequestDto;
import hu.cubix.hr.BalazsPeregi.mapper.HolidayRequestMapper;
import hu.cubix.hr.BalazsPeregi.model.HolidayRequest;
import hu.cubix.hr.BalazsPeregi.model.HolidayRequestStatus;
import hu.cubix.hr.BalazsPeregi.service.HolidayRequestService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/holiday")
public class HolidayRequestRestController {

	@Autowired
	private HolidayRequestMapper holidayMapper;

	@Autowired
	private HolidayRequestService holidayService;

	@PostMapping
	public ResponseEntity<HolidayRequestDto> placeNewRequest(@RequestBody @Valid HolidayRequestDto newRequest) {
		newRequest.setStatus(HolidayRequestStatus.NEW);
		HolidayRequest holidayRequest = holidayMapper.dtoToHolidayRequest(newRequest);
		HolidayRequest savedRequest = holidayService.placeNewRequest(newRequest.getEmployeeId(),
				newRequest.getManagerId(), holidayRequest);
		if (savedRequest == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(holidayMapper.holidayRequestToDto(savedRequest));
	}

	@GetMapping()
	public ResponseEntity<List<HolidayRequestDto>> getAll() {
		return ResponseEntity.ok(holidayMapper.holidaysToDtos(holidayService.getAll()));
	}

	@PutMapping("/accept")
	public ResponseEntity<HolidayRequestDto> acceptRequest(@RequestParam long requestId) {
		HolidayRequest acceptedRequest = holidayService.acceptRequest(requestId);
		if (acceptedRequest == null) {
			return ResponseEntity.badRequest().build();
		} else {
			return ResponseEntity.ok(holidayMapper.holidayRequestToDto(acceptedRequest));
		}
	}

	@PutMapping("/reject")
	public ResponseEntity<HolidayRequestDto> rejectRequest(@RequestParam long requestId) {
		HolidayRequest acceptedRequest = holidayService.rejectRequest(requestId);
		if (acceptedRequest == null) {
			return ResponseEntity.badRequest().build();
		} else {
			return ResponseEntity.ok(holidayMapper.holidayRequestToDto(acceptedRequest));
		}
	}

	@PutMapping("/{requestId}")
	public ResponseEntity<HolidayRequestDto> updateRequest(@PathVariable long requestId,
			@RequestBody @Valid HolidayRequestDto request) {
		HolidayRequest updatedRequest = holidayService.updateRequest(requestId, request.getManagerId(),
				holidayMapper.dtoToHolidayRequest(request));
		if (updatedRequest == null) {
			return ResponseEntity.badRequest().build();
		} else {
			return ResponseEntity.ok(holidayMapper.holidayRequestToDto(updatedRequest));
		}
	}

	@DeleteMapping("/{requestId}")
	public ResponseEntity<HolidayRequestDto> deleteRequest(@PathVariable long requestId) {
		return holidayService.deleteRequest(requestId) ? ResponseEntity.ok().build()
				: ResponseEntity.badRequest().build();
	}

	@PostMapping(params = "page")
	public ResponseEntity<List<HolidayRequestDto>> getAll(@RequestBody HolidayFilterDto holidayFilter,
			Pageable pageable) {
		return ResponseEntity
				.ok(holidayMapper.holidaysToDtos(holidayService.findDynamically(holidayFilter, pageable).getContent()));
	}
}
