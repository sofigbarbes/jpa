package uo.ri.cws.application.service.workorder.crud.command;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.workorder.WorkOrderDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.WorkOrder;

public class ListUnfinishedWorkOrders implements Command<List<WorkOrderDto>> {

	WorkOrderRepository woRepo = Factory.repository.forWorkOrder();

	@Override
	public List<WorkOrderDto> execute() throws BusinessException
	{
		List<WorkOrder> worKorders = woRepo.findUnfinished();
		List<WorkOrderDto> res = new ArrayList<WorkOrderDto>();
		WorkOrderDto dto = new WorkOrderDto();
		for (WorkOrder w : worKorders)
		{
			dto = new WorkOrderDto();
			dto.date = w.getDate();
			dto.description = w.getDescription();
			dto.id = w.getId();
			// dto.invoiceId = w.getInvoice().getId();
			// dto.mechanicId = w.getMechanic().getId();
			dto.status = w.getStatus().name();
			dto.total = w.getAmount();
			dto.vehicleId = w.getVehicle().getId();
			dto.version = w.getVersion();
			res.add(dto);
		}
		return res;
	}

}
