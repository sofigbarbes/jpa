package uo.ri.cws.application.repository;

import java.util.Date;
import java.util.List;

import uo.ri.cws.domain.Intervention;

public interface InterventionRepository extends Repository<Intervention> {

	/**
	 * @param id,       refers to the mechanic id
	 * @param startDate
	 * @param endDate
	 * @return a list with all interventions done by the mechanic in between the
	 *         to dates (both included), or an empty list if there are none
	 */
	List<Intervention> findByMechanicIdBetweenDates(Long id, Date startDate,
			Date endDate);

	/**
	 * Finds interventions for a specific workOrder.
	 * 
	 * @param id
	 * @return a list with all the interventions for that workOrder
	 */
	List<Intervention> findByWorkOrderId(String id);

}
