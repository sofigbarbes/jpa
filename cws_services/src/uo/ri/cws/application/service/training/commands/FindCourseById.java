package uo.ri.cws.application.service.training.commands;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.CourseRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.training.CourseDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Course;

public class FindCourseById implements Command<Optional<CourseDto>> {

	private String courseId;
	private CourseRepository courseRepo = Factory.repository.forCourse();

	public FindCourseById(String cId) {
		this.courseId = cId;
	}

	@Override
	public Optional<CourseDto> execute() throws BusinessException
	{
		Optional<Course> course = courseRepo.findById(courseId);
		if (!course.isPresent())
		{
			return null;
		}
		Course c = course.get();
		CourseDto dto = new CourseDto();
		dto.code = c.getCode();
		dto.description = c.getDescription();
		dto.endDate = c.getEndDate();
		dto.hours = c.getDuration();
		dto.id = c.getId();
		dto.name = c.getName();
		dto.startDate = c.getStartDate();
		dto.version = c.getVersion();
		return Optional.ofNullable(dto);
	}

}
