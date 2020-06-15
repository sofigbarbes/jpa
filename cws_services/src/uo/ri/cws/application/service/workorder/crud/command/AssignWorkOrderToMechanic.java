package uo.ri.cws.application.service.workorder.crud.command;

import java.util.List;
import java.util.Optional;

import alb.util.assertion.Argument;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.CertificateRepository;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Certificate;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.WorkOrder;

public class AssignWorkOrderToMechanic implements Command<Void> {

	private String workOrderId;
	private String mechanicId;

	private WorkOrderRepository woRepo = Factory.repository.forWorkOrder();
	private MechanicRepository mecRepo = Factory.repository.forMechanic();
	private CertificateRepository certRepo = Factory.repository
			.forCertificate();

	public AssignWorkOrderToMechanic(String woId, String mechanicId) {
		this.workOrderId = woId;
		this.mechanicId = mechanicId;
	}

	@Override
	public Void execute() throws BusinessException
	{
		validate();
		checkInDB();
		checkCertifiedMechanic();

		WorkOrder wo = woRepo.findById(workOrderId).get();
		Mechanic mec = mecRepo.findById(mechanicId).get();

		woRepo.remove(wo);
		wo.assignTo(mec);
		woRepo.add(wo);

		return null;
	}

	private void checkCertifiedMechanic() throws BusinessException
	{
		List<Certificate> certsForWorkOrder = certRepo
				.findByWorkOrderId(workOrderId);
		for (Certificate c : certsForWorkOrder)
		{
			
			if (c.getMechanic().getId().equals(mechanicId))
				return;
			else
				break;
		}
		throw new BusinessException(
				"Please, select a certified mechanic for the workOrder");
	}

	private void checkInDB() throws BusinessException
	{
		Optional<WorkOrder> wo = woRepo.findById(workOrderId);
		if (!wo.isPresent())
		{
			throw new BusinessException(
					"The workOrder specified is not in the database");
		}
		Optional<Mechanic> mec = mecRepo.findById(mechanicId);
		if (!mec.isPresent())
		{
			throw new BusinessException(
					"The mechanic specified is not in the database");
		}
	}

	private void validate()
	{
		Argument.isNotEmpty(workOrderId,
				"The workOrder id should not be empty");
		Argument.isNotEmpty(mechanicId, "The mechanic id should not be empty");
		Argument.isNotNull(workOrderId, "The workOrder id should not be null");
		Argument.isNotNull(mechanicId, "The mechanic id should not be null");

	}

}
