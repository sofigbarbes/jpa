package uo.ri.cws.application.repository;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.domain.Course;
import uo.ri.cws.domain.Dedication;

public interface CourseRepository extends Repository<Course> {

	Optional<Course> findByCode(String code);

	void addDedication(String courseId, Dedication dedication);

	List<Course> findAll();

}
