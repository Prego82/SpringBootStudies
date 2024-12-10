package hu.cubix.hr.BalazsPeregi.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import hu.cubix.hr.BalazsPeregi.dto.EmployeeDto;
import hu.cubix.hr.BalazsPeregi.model.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
	public EmployeeDto employeeToDto(Employee employee);

	public List<EmployeeDto> employeesToDtos(List<Employee> employees);

	public Employee dtoToEmployee(EmployeeDto employee);

	public List<Employee> dtosToEmployees(List<EmployeeDto> employees);

}
