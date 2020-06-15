package uo.ri.cws.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import alb.util.assertion.Argument;

@Entity
@Table(name = "TENROLLMENTS")
public class Enrollment extends BaseEntity {

	private static final int ATTENDANCE_TO_PASS = 85;

	@ManyToOne
	private Course course;
	@ManyToOne
	private Mechanic mechanic;

	private int attendance;
	private boolean passed;

	Enrollment() {
	}

	public Course getCourse()
	{
		return course;
	}

	void _setCourse(Course course)
	{
		this.course = course;
	}

	public Mechanic getMechanic()
	{
		return mechanic;
	}

	void _setMechanic(Mechanic mechanic)
	{
		this.mechanic = mechanic;
	}

	public Enrollment(Mechanic mechanic, Course course, int attendance,
			boolean passed) {
		validateAttendance(attendance);
		this.attendance = attendance;
		this.passed = passed;
		Argument.isNotNull(course);
		Argument.isNotNull(mechanic);
		Associations.Enroll.link(mechanic, this, course);
	}

	private void validateAttendance(int attendance)
	{
		if ((attendance < ATTENDANCE_TO_PASS))
		{
			throw new IllegalArgumentException(
					"Attendance should be greater than " + ATTENDANCE_TO_PASS);
		}

	}

	public int getAttendedHoursFor(VehicleType truck)
	{
		return attendance * course.getDedication(truck) / 100;
	}

	public boolean isPassed()
	{
		return passed;
	}

	public int getAttendance()
	{
		return attendance;
	}

}
