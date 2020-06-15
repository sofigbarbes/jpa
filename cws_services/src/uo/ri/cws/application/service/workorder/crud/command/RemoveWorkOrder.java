package uo.ri.cws.application.service.workorder.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.WorkOrder;

public class RemoveWorkOrder implements Command<Void> {

	String id;

	WorkOrderRepository woRepo = Factory.repository.forWorkOrder();

	public RemoveWorkOrder(String woId) {
		this.id = woId;
	}

	@Override
	public Void execute() throws BusinessException {
		validate();
		Optional<WorkOrder> woToRemoveOp = woRepo.findById(id);
		
		checkInDb(woToRemoveOp);
		
		WorkOrder woToRemove = woToRemoveOp.get();
		woRepo.remove(woToRemove);
		return null;

	}

	private void checkInDb(Optional<WorkOrder> woToRemoveOp)
			throws BusinessException {
		if (!woToRemoveOp.isPresent()) {
			throw new BusinessException(
					"The workOrder with id " + id + " is not in the database");
		}
	}

	private void validate() throws BusinessException {
		if (this.id == null || this.id.isEmpty()) {
			throw new BusinessException("The id cannot be null or empty");
		}
	}

}
