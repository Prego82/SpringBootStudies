package hu.cubix.hr.BalazsPeregi.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hu.cubix.hr.BalazsPeregi.dto.HolidayRequestDto;
import hu.cubix.hr.BalazsPeregi.model.HolidayRequest;

@Mapper(componentModel = "spring")
public interface HolidayRequestMapper {
	@Mapping(target = "employee", ignore = true)
	@Mapping(target = "manager", ignore = true)
	public HolidayRequest dtoToHolidayRequest(HolidayRequestDto holidayRequest);

	@Mapping(source = "employee.id", target = "employeeId")
	@Mapping(source = "manager.id", target = "managerId")
	public HolidayRequestDto holidayRequestToDto(HolidayRequest holidayRequest);

	public List<HolidayRequestDto> holidaysToDtos(List<HolidayRequest> holidays);
}
