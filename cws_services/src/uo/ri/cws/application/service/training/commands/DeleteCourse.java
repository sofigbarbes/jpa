package uo.ri.cws.application.service.training.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.CourseRepository;
import uo.ri.cws.application.repository.EnrollmentRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessCheck;
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
		enr = enrRepo.findByCourseId(courseId);
		BusinessCheck.isTrue(enr.size() == 0,
				"Course cannot be deleted: it has mechanics enrolled");

	}

	private void checkInDb(Optional<Course> courseToRemove)
			throws BusinessException
	{
		BusinessCheck.exists(courseToRemove,
				"The course with id " + courseId + " is not in the database");
	}

	private void validate() throws BusinessException
	{
		BusinessCheck.isNotEmpty(this.courseId, "The id cannot be empty");
		BusinessCheck.isNotNull(this.courseId, "The id cannot be null");
	}

}
