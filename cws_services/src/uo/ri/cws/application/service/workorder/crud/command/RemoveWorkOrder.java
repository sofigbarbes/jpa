package uo.ri.cws.application.service.workorder.crud.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.InterventionRepository;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Intervention;
import uo.ri.cws.domain.WorkOrder;

public class RemoveWorkOrder implements Command<Void> {

	String id;

	WorkOrderRepository woRepo = Factory.repository.forWorkOrder();
	InterventionRepository intRepo = Factory.repository.forIntervention();

	public RemoveWorkOrder(String woId) {
		this.id = woId;
	}

	@Override
	public Void execute() throws BusinessException
	{
		validate();
		Optional<WorkOrder> woToRemoveOp = woRepo.findById(id);

		checkInDb(woToRemoveOp);
		checkNoInterventions();
		WorkOrder woToRemove = woToRemoveOp.get();
		woRepo.remove(woToRemove);
		return null;

	}

	private void checkNoInterventions() throws BusinessException
	{
		List<Intervention> interv = new ArrayList<Intervention>();
		interv = intRepo.findByWorkOrderId(id);
		if(interv.size()!=0) {
			throw new BusinessException("Workorder cannot be deleted: existing interventions");
		}
	}

	private void checkInDb(Optional<WorkOrder> woToRemoveOp)
			throws BusinessException
	{
		if (!woToRemoveOp.isPresent())
		{
			throw new BusinessException(
					"The workOrder with id " + id + " is not in the database");
		}
	}

	private void validate() throws BusinessException
	{
		if (this.id == null || this.id.isEmpty())
		{
			throw new BusinessException("The id cannot be null or empty");
		}
	}

}
