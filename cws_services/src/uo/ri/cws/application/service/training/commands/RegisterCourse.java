package uo.ri.cws.application.service.training.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.CourseRepository;
import uo.ri.cws.application.repository.VehicleTypeRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.training.CourseDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Course;
import uo.ri.cws.domain.VehicleType;

public class RegisterCourse implements Command<CourseDto> {

	private CourseDto dto;
	private CourseRepository courseRepo = Factory.repository.forCourse();
	private VehicleTypeRepository vtRepo = Factory.repository.forVehicleType();

	public RegisterCourse(CourseDto dto) {
		this.dto = dto;
	}

	@Override
	public CourseDto execute() throws BusinessException
	{
		validateNulls();
		checkNotInDB();
		Course course = new Course(dto.code, dto.name, dto.description,
				dto.startDate, dto.endDate, dto.hours);
		courseRepo.add(course);
		dto.id = course.getId();

		Map<VehicleType, Integer> dedications = new HashMap<VehicleType, Integer>();
		for (Entry<String, Integer> entry : dto.percentages.entrySet())
		{
			checkVehicleTypeInDb(entry.getKey());
			VehicleType vehicleType = vtRepo.findById(entry.getKey()).get();

			dedications.put(vehicleType, entry.getValue());
		}
		course.addDedications(dedications);

		return dto;
	}

	private void checkVehicleTypeInDb(String key) throws BusinessException
	{
		Optional<VehicleType> vehicleType = vtRepo.findById(key);
		if (!vehicleType.isPresent())
		{
			throw new BusinessException("The vehicleType with id " + key
					+ " is not in the database");
		}

	}

	private void checkNotInDB() throws BusinessException
	{
		Optional<Course> c = courseRepo.findByCode(dto.code);
		if (c.isPresent())
		{
			throw new BusinessException("The course with code " + dto.code
					+ " is already in the database");
		}
	}

	private void validateNulls() throws BusinessException
	{
		if (dto.code == null || dto.code == "")
		{
			throw new BusinessException("The dni cant be null or empty");
		}
		if (dto.name == null || dto.name == "")
		{
			throw new BusinessException("The name cant be null or empty");
		}
		if (dto.description == null || dto.description == "")
		{
			throw new BusinessException(
					"The description cant be null or empty");
		}
		if (dto.hours < 0)
		{
			throw new BusinessException("The hours cant be like that");
		}
	}
}
