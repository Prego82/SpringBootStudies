package hu.cubix.hr.BalazsPeregi.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import hu.cubix.hr.BalazsPeregi.dto.JobSalaryDto;
import hu.cubix.hr.BalazsPeregi.model.JobSalary;

@Mapper(componentModel = "spring")
public interface JobSalaryMapper {

	JobSalaryDto toDto(JobSalary jobSalary);

	public List<JobSalaryDto> salariesToDto(List<JobSalary> salaries);

}
