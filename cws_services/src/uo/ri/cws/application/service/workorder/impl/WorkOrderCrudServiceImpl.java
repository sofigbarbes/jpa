package uo.ri.cws.application.service.workorder.impl;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.workorder.WorkOrderCrudService;
import uo.ri.cws.application.service.workorder.WorkOrderDto;
import uo.ri.cws.application.service.workorder.crud.command.AddWorkOrder;
import uo.ri.cws.application.service.workorder.crud.command.FindWorkOrderById;
import uo.ri.cws.application.service.workorder.crud.command.FindWorkOrdersByPlateNumber;
import uo.ri.cws.application.service.workorder.crud.command.RemoveWorkOrder;
import uo.ri.cws.application.service.workorder.crud.command.UpdateWorkOrder;
import uo.ri.cws.application.util.command.CommandExecutor;

public class WorkOrderCrudServiceImpl implements WorkOrderCrudService {

	private CommandExecutor executor = Factory.executor.forExecutor();

	@Override
	public List<WorkOrderDto> findWorkOrdersByPlateNumber(String plate)
			throws BusinessException
	{
		return executor.execute(new FindWorkOrdersByPlateNumber(plate));
	}

	@Override
	public void updateWorkOrder(WorkOrderDto oWo) throws BusinessException
	{
		executor.execute(new UpdateWorkOrder(oWo));
	}

	@Override
	public WorkOrderDto registerNew(WorkOrderDto dto) throws BusinessException
	{
		return executor.execute(new AddWorkOrder(dto));

	}

	@Override
	public void deleteWorkOrder(String id) throws BusinessException
	{
		executor.execute(new RemoveWorkOrder(id));

	}

	@Override
	public Optional<WorkOrderDto> findWorkOrderById(String woId)
			throws BusinessException
	{
		return executor.execute(new FindWorkOrderById(woId));

	}

	@Override
	public List<WorkOrderDto> findWorkOrdersByVehicleId(String id)
			throws BusinessException
	{
		return null;
	}

}
