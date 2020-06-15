package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.List;

import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.domain.WorkOrder;
import uo.ri.cws.domain.WorkOrder.WorkOrderStatus;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class WorkOrderJpaRepository extends BaseJpaRepository<WorkOrder>
		implements WorkOrderRepository {

	@Override
	public List<WorkOrder> findByIds(List<String> idsAveria)
	{
		return Jpa.getManager()
				.createNamedQuery("WorkOrder.findByIds", WorkOrder.class)
				.setParameter(1, idsAveria).getResultList();
	}

	@Override
	public List<WorkOrder> findNotInvoicedByClientDni(String dni)
	{
		return Jpa.getManager()
				.createNamedQuery("WorkOrder.findNotInvoicedByClientDni",
						WorkOrder.class)
				.setParameter(1, dni).setParameter(2, WorkOrderStatus.INVOICED)
				.getResultList();
	}

	@Override
	public List<WorkOrder> findByPlate(String plate)
	{
		return Jpa.getManager()
				.createNamedQuery("WorkOrder.findByPlate", WorkOrder.class)
				.setParameter(1, plate).getResultList();
	}

	@Override
	public WorkOrder findByVehicleAndDescription(String vehicleId,
			String description)
	{
		return Jpa.getManager()
				.createNamedQuery("WorkOrder.findByVehicleAndDescription",
						WorkOrder.class)
				.setParameter(1, vehicleId).setParameter(2, description)
				.getSingleResult();
	}

	@Override
	public List<WorkOrder> findUnfinished()
	{
		return Jpa.getManager()
				.createNamedQuery("WorkOrder.findUnfinished", WorkOrder.class)
				.setParameter(1, WorkOrderStatus.ASSIGNED)
				.setParameter(2, WorkOrderStatus.OPEN).getResultList();
	}

}
