package uo.ri.cws.application.repository;

import java.util.List;

import uo.ri.cws.domain.WorkOrder;

public interface WorkOrderRepository extends Repository<WorkOrder> {

	/**
	 * @param idsAveria, lista de los id de avería a recuperar
	 * @return lista con averias cuyo id aparece en idsAveria, o lista vacía si
	 *         no hay ninguna
	 */
	List<WorkOrder> findByIds(List<String> workOrderIds);

	/**
	 * Find workorders that has not been invoiced, by the lient dni.
	 * 
	 * @param dni of the client
	 * @return list with the workorders
	 */
	List<WorkOrder> findNotInvoicedByClientDni(String dni);

	/**
	 * Find workOrders for the plate specified as a parameter
	 * 
	 * @param plate
	 * @return
	 */
	List<WorkOrder> findByPlate(String plate);

	/**
	 * Find the worjOrder with vehicle_id and description specified as
	 * parameters
	 * 
	 * @param vehicleId
	 * @param description
	 * @return
	 */
	WorkOrder findByVehicleAndDescription(String vehicleId, String description);

	/**
	 * Find in process workOrders i.e with OPEN or ASSIGNED status
	 * 
	 * @return
	 */
	List<WorkOrder> findUnfinished();
}