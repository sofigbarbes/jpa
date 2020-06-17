package uo.ri.cws.application.repository;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.domain.Enrollment;

public interface EnrollmentRepository  extends Repository<Enrollment> {

	List<Enrollment> findByCourseId(String courseId);

	/**
	 * Finds the hours enrolled by a mechanic in that specific type of vehicle
	 * @param mecId
	 * @param vtId
	 * @return int hours enrolled by that mechanic
	 */
	Optional<Integer> getHoursEnrolled(String mecId, String vtId);

}
