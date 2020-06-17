package uo.ri.cws.application.service.workorder.crud.command;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.CertificateRepository;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessCheck;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Certificate;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.WorkOrder;
import uo.ri.cws.domain.WorkOrder.WorkOrderStatus;

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
		checkStatus();
		checkCertifiedMechanic();

		WorkOrder wo = woRepo.findById(workOrderId).get();
		Mechanic mec = mecRepo.findById(mechanicId).get();

		woRepo.remove(wo);
		wo.assignTo(mec);
		woRepo.add(wo);

		return null;
	}

	private void checkStatus() throws BusinessException
	{
		BusinessCheck.isTrue(
				woRepo.findById(workOrderId).get()
						.getStatus() == WorkOrderStatus.OPEN,
				"The status should be open");
		BusinessCheck.isNotEmpty(workOrderId, "hola");
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
		BusinessCheck.exists(wo,
				"The workOrder specified is not in the database");

		Optional<Mechanic> mec = mecRepo.findById(mechanicId);
		BusinessCheck.exists(mec,
				"The mechanic specified is not in the database");
	}

	private void validate() throws BusinessException
	{
		BusinessCheck.isNotEmpty(workOrderId,
				"The workOrder id should not be empty");
		BusinessCheck.isNotEmpty(mechanicId,
				"The mechanic id should not be empty");
		BusinessCheck.isNotNull(workOrderId,
				"The workOrder id should not be null");
		BusinessCheck.isNotNull(mechanicId,
				"The mechanic id should not be null");

	}

}
