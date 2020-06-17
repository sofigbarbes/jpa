package uo.ri.cws.application.service.training.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import alb.util.date.Dates;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.CourseRepository;
import uo.ri.cws.application.repository.VehicleTypeRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.training.CourseDto;
import uo.ri.cws.application.util.BusinessCheck;
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
		validate();
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
		BusinessCheck.exists(vehicleType,
				"The vehicleType with id " + key + " is not in the database");

	}

	private void checkNotInDB() throws BusinessException
	{
		Optional<Course> c = courseRepo.findByCode(dto.code);
		BusinessCheck.isFalse(c.isPresent(), "The course with code " + dto.code
				+ " is already in the database");

	}

	private void validate() throws BusinessException
	{
		BusinessCheck.isNotEmpty(dto.code, "The code cant be empty");
		BusinessCheck.isNotNull(dto.code, "The code cant be null");
		BusinessCheck.isNotEmpty(dto.name, "The name cant be empty");
		BusinessCheck.isNotNull(dto.name, "The name cant be null");
		BusinessCheck.isNotEmpty(dto.description,
				"The description cant be empty");
		BusinessCheck.isNotNull(dto.description,
				"The description cant be null");
		BusinessCheck.isTrue(dto.hours >= 0, "The hours cant be negative");
		BusinessCheck.isTrue(dto.startDate.before(Dates.today()),
				"Back to the future is not allowed here");
		BusinessCheck.isTrue(dto.endDate.before(dto.startDate),
				"Back to the future is not allowed here");
	}
}
