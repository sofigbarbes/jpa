package uo.ri.cws.application.service.workorder;

import java.util.List;

import uo.ri.cws.application.service.BusinessException;

/**
 * This service is intended to be used by the Mechanic It follows the ISP
 * principle (@see SOLID principles from RC Martin)
 */
public interface WorkOrderCrudService {

	List<WorkOrderDto> findWorkOrderByPlate(String plate)
			throws BusinessException;

	/**
	 * Add a new workorder to the system with the data specified in the dto. The
	 * id value will be ignored
	 * 
	 * @param String plate number of the vehicle
	 * @param String description of the WorkOrder
	 * @return the dto with the id filed updated to the UUID generated
	 * @throws BusinessException if there already exist another workorder for
	 *                           the same vehicle at the same date
	 */

	WorkOrderDto addWorkOrder(String plate, String description)
			throws BusinessException;

	/**
	 * Removes a work Order from the database.
	 * 
	 * @param woId id of the workorder to remove
	 * @return
	 * @throws BusinessException if the workorder is not in the database
	 */
	WorkOrderDto removeWorkOrder(String woId) throws BusinessException;

	/**
	 * Finds the workOrders for a specified plate number
	 * 
	 * @param plate of the vehicle whose workorders we want to list
	 * @return
	 * @throws BusinessException if the plate number is not in the database
	 */
	List<WorkOrderDto> findWorkOrdersByPlateNumber(String plate)
			throws BusinessException;

	WorkOrderDto findWorkOrderById(String woId) throws BusinessException;

	/**
	 * Updates the workorder having the id specified by the user, changing its
	 * description. A workorder can be modified if the status is OPEN or
	 * ASSIGNED. Only description can be updated.
	 * 
	 * @param WorkOrder to be updated
	 * @throws BusinessException if the workOrder cannot be modified.
	 */
	void updateWorkOrder(WorkOrderDto oWo) throws BusinessException;

}
