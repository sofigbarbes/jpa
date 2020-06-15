package uo.ri.cws.application.service.workorder.impl;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.training.CertificateDto;
import uo.ri.cws.application.service.workorder.AssignWorkOrderService;
import uo.ri.cws.application.service.workorder.WorkOrderDto;
import uo.ri.cws.application.service.workorder.crud.command.ListUnfinishedWorkOrders;
import uo.ri.cws.application.util.command.CommandExecutor;

public class AssignWorkOrderServiceImpl
		implements AssignWorkOrderService {

	private CommandExecutor executor = Factory.executor.forExecutor();


	@Override
	public void assignWorkOrderToMechanic(String woId, String mechanicId)
			throws BusinessException
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<CertificateDto> findCertificatesByVehicleTypeId(String id)
			throws BusinessException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkOrderDto> findUnfinishedWorkOrders()
			throws BusinessException
	{
		return executor.execute(new ListUnfinishedWorkOrders());
	}

}
