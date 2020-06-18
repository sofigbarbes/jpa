package uo.ri.cws.application.service.training.commands;

import java.util.Optional;

import alb.util.date.Dates;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.CourseRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.training.CourseDto;
import uo.ri.cws.application.util.BusinessCheck;
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
		BusinessCheck.hasVersion(c, dto.version);
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
		BusinessCheck.isTrue(dto.startDate.before(Dates.today()),
				"The course has already started - Can't be modified");

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
		BusinessCheck.isTrue(dto.startDate.after(Dates.today()),
				"Back to the future is not allowed here");
		BusinessCheck.isTrue(dto.endDate.before(dto.startDate),
				"Back to the future is not allowed here");
		checkInDatabase();
	}

	private void checkInDatabase() throws BusinessException
	{
		Optional<Course> course = courseRepo.findByCode(dto.code);
		BusinessCheck.exists(course,
				"The course with code " + dto.code + " is not in the database");

	}

}
