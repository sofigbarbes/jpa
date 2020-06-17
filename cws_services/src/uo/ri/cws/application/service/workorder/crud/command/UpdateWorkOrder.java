package uo.ri.cws.application.service.workorder.crud.command;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.workorder.WorkOrderDto;
import uo.ri.cws.application.util.BusinessCheck;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.WorkOrder;
import uo.ri.cws.domain.WorkOrder.WorkOrderStatus;

public class UpdateWorkOrder implements Command<Void> {

	private WorkOrderDto dto;

	private WorkOrderRepository repo = Factory.repository.forWorkOrder();

	public UpdateWorkOrder(WorkOrderDto oWo) {
		this.dto = oWo;
	}

	@Override
	public Void execute() throws BusinessException
	{
		checkStatus();

		WorkOrder m = repo.findById(dto.id).get();
		repo.remove(m);
		m.setDescription(dto.description);
		repo.add(m);
		return null;
	}

	private void checkStatus() throws BusinessException
	{
		BusinessCheck.isTrue(
				dto.status == WorkOrderStatus.ASSIGNED.toString()
						|| dto.status == WorkOrderStatus.OPEN.toString(),
				"The workOrder should be open or assigned");

	}

}
