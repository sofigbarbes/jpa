package uo.ri.cws.application.service.workorder;

import java.util.List;

import uo.ri.cws.application.service.BusinessException;

/**
 * This service is intended to be used by the Mechanic
 * It follows the ISP principle (@see SOLID principles from RC Martin)
 */
public interface WorkOrderCrudService {

	List<WorkOrderDto> findWorkOrderByPlate(String plate) throws BusinessException;
	
	/**
	 * Add a new workorder to the system with the data specified in the dto.
	 * 		The id value will be ignored
	 * @param String plate number of the vehicle
	 * @param String description of the WorkOrder
	 * @return the dto with the id filed updated to the UUID generated
	 * @throws BusinessException if there already exist another 
	 * 		workorder for the same vehicle at the same date
	 */

	WorkOrderDto addWorkOrder(String plate, String description) throws BusinessException;
	
	
}
