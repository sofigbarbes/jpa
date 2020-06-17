package uo.ri.cws.application.service.training.commands;

import java.util.Optional;

import alb.util.date.Dates;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.CourseRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.training.CourseDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Course;

public class UpdateCourse implements Command<Void> {

	private CourseRepository courseRepo = Factory.repository.forCourse();
	private CourseDto dto;

	public UpdateCourse(CourseDto dto) {
		this.dto = dto;
	}

	@Override
	public Void execute() throws BusinessException
	{
		checkCanBeUpdated();
		validate();
		Course c = courseRepo.findById(dto.id).get();
		c.setDescription(dto.description);
		c.setDuration(dto.hours);
		c.setEndDate(dto.endDate);
		c.setName(dto.name);
		c.setStartDate(dto.startDate);
		courseRepo.remove(c);
		courseRepo.add(c);
 
		return null;
	} 

	private void checkCanBeUpdated() throws BusinessException
	{
		if (dto.startDate.before(Dates.now()))
		{
			throw new BusinessException(
					"The course has been/ is being imparted");
		}
	}

	private void validate() throws BusinessException
	{
		if (dto.description == null || dto.description == "")
		{
			throw new BusinessException(
					"The description cant be null or empty");
		}
		if (dto.name == null || dto.name == "")
		{
			throw new BusinessException("The name cant be null or empty");
		}
		checkInDatabase();
	}

	private void checkInDatabase() throws BusinessException
	{
		Optional<Course> course = courseRepo.findByCode(dto.code);
		if (course == null)
		{
			throw new BusinessException("The course with code " + dto.code
					+ " is not in the database");
		}
	}

}
