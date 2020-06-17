package uo.ri.cws.application.service.training.commands;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.CourseRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.training.CourseDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Course;

public class FindAllCourses implements Command<List<CourseDto>> {

	private CourseRepository courseRepo = Factory.repository.forCourse();

	@Override
	public List<CourseDto> execute() throws BusinessException
	{
		List<Course> courses = courseRepo.findAll();
		List<CourseDto> coursesDtos = new ArrayList<CourseDto>();
		for (Course course : courses)
		{
			CourseDto dto = new CourseDto();
			dto.id = course.getId();
			dto.code = course.getCode();
			dto.description = course.getDescription();
			dto.endDate = course.getEndDate();
			dto.hours = course.getHours();
			dto.name = course.getName();
			dto.startDate = course.getStartDate();
			dto.version = course.getVersion();
			coursesDtos.add(dto);
		}
		return coursesDtos;
	}

}
