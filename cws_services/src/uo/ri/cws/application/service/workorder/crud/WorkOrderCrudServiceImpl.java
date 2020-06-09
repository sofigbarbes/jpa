package uo.ri.cws.application.service.workorder.crud;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.workorder.WorkOrderCrudService;
import uo.ri.cws.application.service.workorder.WorkOrderDto;
import uo.ri.cws.application.service.workorder.crud.command.FindWorkOrdersByPlate;
import uo.ri.cws.application.util.command.CommandExecutor;

public class WorkOrderCrudServiceImpl implements WorkOrderCrudService {
	private CommandExecutor executor = Factory.executor.forExecutor();

	@Override
	public List<WorkOrderDto> findWorkOrderByPlate(String plate) throws BusinessException {
		return executor.execute(new FindWorkOrdersByPlate(plate));
	}

}
