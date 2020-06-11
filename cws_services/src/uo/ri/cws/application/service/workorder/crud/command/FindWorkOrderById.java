package uo.ri.cws.application.service.workorder.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.workorder.WorkOrderDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.WorkOrder;

public class FindWorkOrderById implements Command<WorkOrderDto> {

	private String id;
	private WorkOrderRepository woRepo = Factory.repository.forWorkOrder();

	public FindWorkOrderById(String woId) {
		this.id = woId;
	}

	@Override
	public WorkOrderDto execute() throws BusinessException
	{
		WorkOrderDto res = new WorkOrderDto();
		Optional<WorkOrder> op = woRepo.findById(id);
		checkInDb(op);
		WorkOrder w = op.get();
		res.date = w.getDate();
		res.description = w.getDescription();
		res.id = w.getId();

		res.status = w.getStatus().toString();
		res.vehicleId = w.getVehicle().getId();
		res.version = w.getVersion();
		return res;

	}

	private void checkInDb(Optional<WorkOrder> optional)
			throws BusinessException
	{
		if (!optional.isPresent())
			throw new BusinessException("The workOrder is not in the database");

	}

}
