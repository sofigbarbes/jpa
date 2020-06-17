package uo.ri.cws.application.service.training.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.CourseRepository;
import uo.ri.cws.application.repository.EnrollmentRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Course;
import uo.ri.cws.domain.Enrollment;

public class DeleteCourse implements Command<Void> {

	private String courseId;
	private CourseRepository courseRepo = Factory.repository.forCourse();
	private EnrollmentRepository enrRepo = Factory.repository.forEnrollment();

	public DeleteCourse(String id) {
		this.courseId = id;
	}

	@Override
	public Void execute() throws BusinessException
	{
		validate();
		Optional<Course> courseToRemoveOp = courseRepo.findById(courseId);

		checkInDb(courseToRemoveOp);
		checkNoInterventions();
		Course courseToRemove = courseToRemoveOp.get();
		courseRepo.remove(courseToRemove);
		return null;

	}

	private void checkNoInterventions() throws BusinessException
	{
		List<Enrollment> enr = new ArrayList<Enrollment>();
		enr = enrRepo .findByCourseId(courseId);
		if (enr.size() != 0)
		{
			throw new BusinessException(
					"Course cannot be deleted: it has mechanics enrolled");
		}
	}

	private void checkInDb(Optional<Course> courseToRemove)
			throws BusinessException
	{
		if (!courseToRemove.isPresent())
		{
			throw new BusinessException("The course with id " + courseId
					+ " is not in the database");
		}
	}

	private void validate() throws BusinessException
	{
		if (this.courseId == null || this.courseId.isEmpty())
		{
			throw new BusinessException("The id cannot be null or empty");
		}
	}

}
