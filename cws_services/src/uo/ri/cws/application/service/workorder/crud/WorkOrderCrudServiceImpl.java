package uo.ri.cws.application.service.workorder.crud;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.workorder.WorkOrderCrudService;
import uo.ri.cws.application.service.workorder.WorkOrderDto;
import uo.ri.cws.application.service.workorder.crud.command.AddWorkOrder;
import uo.ri.cws.application.service.workorder.crud.command.FindWorkOrderById;
import uo.ri.cws.application.service.workorder.crud.command.FindWorkOrdersByPlate;
import uo.ri.cws.application.service.workorder.crud.command.FindWorkOrdersByPlateNumber;
import uo.ri.cws.application.service.workorder.crud.command.RemoveWorkOrder;
import uo.ri.cws.application.service.workorder.crud.command.UpdateWorkOrder;
import uo.ri.cws.application.util.command.CommandExecutor;

public class WorkOrderCrudServiceImpl implements WorkOrderCrudService {

	private CommandExecutor executor = Factory.executor.forExecutor();

	@Override
	public List<WorkOrderDto> findWorkOrderByPlate(String plate)
			throws BusinessException
	{
		return executor.execute(new FindWorkOrdersByPlate(plate));
	}

	@Override
	public WorkOrderDto addWorkOrder(String plate, String description)
			throws BusinessException
	{
		return executor.execute(new AddWorkOrder(plate, description));
	}

	@Override
	public WorkOrderDto removeWorkOrder(String woId) throws BusinessException
	{
		return executor.execute(new RemoveWorkOrder(woId));
	}

	@Override
	public List<WorkOrderDto> findWorkOrdersByPlateNumber(String plate)
			throws BusinessException
	{
		return executor.execute(new FindWorkOrdersByPlateNumber(plate));
	}

	@Override
	public WorkOrderDto findWorkOrderById(String woId) throws BusinessException
	{
		return executor.execute(new FindWorkOrderById(woId));
	}

	@Override
	public void updateWorkOrder(WorkOrderDto oWo) throws BusinessException
	{
		executor.execute(new UpdateWorkOrder(oWo));
	}

}
