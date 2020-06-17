package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.repository.EnrollmentRepository;
import uo.ri.cws.domain.Enrollment;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class EnrollmentJpaRepository extends BaseJpaRepository<Enrollment>
		implements EnrollmentRepository {

	@Override
	public List<Enrollment> findByCourseId(String courseId)
	{
		return Jpa.getManager()
				.createNamedQuery("Enrollment.findByCourseId", Enrollment.class)
				.setParameter(1, courseId).getResultList();

	}

	@Override
	public Optional<Integer> getHoursEnrolled(String mecId, String vtId)
	{
		return Jpa.getManager()
				.createNamedQuery("Enrollment.findHoursEnrolled", Integer.class)
				.setParameter(1, mecId).setParameter(2, vtId).getResultList()
				.stream().findFirst();

	}

}
