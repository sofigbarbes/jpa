package uo.ri.cws.application.service.workorder;

import java.util.List;

import uo.ri.cws.application.service.BusinessException;

/**
 * This service is intended to be used by the Mechanic
 * It follows the ISP principle (@see SOLID principles from RC Martin)
 */
public interface WorkOrderCrudService {

	List<WorkOrderDto> findWorkOrderByPlate(String plate) throws BusinessException;

	
}
