package hu.cubix.hr.BalazsPeregi.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hu.cubix.hr.BalazsPeregi.dto.EmployeeDto;
import hu.cubix.hr.BalazsPeregi.model.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

	@Mapping(source = "position.name", target = "position")
	@Mapping(source = "company.name", target = "job")
	@Mapping(source = "manager.id", target = "managerId")
	public EmployeeDto employeeToDto(Employee employee);

	public List<EmployeeDto> employeesToDtos(List<Employee> employees);

	@InheritInverseConfiguration
	public Employee dtoToEmployee(EmployeeDto employee);

	public List<Employee> dtosToEmployees(List<EmployeeDto> employees);

}
