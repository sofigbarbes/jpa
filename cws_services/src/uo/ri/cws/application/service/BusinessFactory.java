package uo.ri.cws.application.service;

import java.util.List;

import uo.ri.cws.application.ServiceFactory;
import uo.ri.cws.application.service.client.ClientCrudService;
import uo.ri.cws.application.service.client.ClientHistoryService;
import uo.ri.cws.application.service.invoice.CreateInvoiceService;
import uo.ri.cws.application.service.invoice.SettleInvoiceService;
import uo.ri.cws.application.service.invoice.create.CreateInvoiceServiceImpl;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.crud.MechanicCrudServiceImpl;
import uo.ri.cws.application.service.sparepart.SparePartCrudService;
import uo.ri.cws.application.service.vehicle.VehicleCrudService;
import uo.ri.cws.application.service.vehicle.crud.VehicleCrudServiceImpl;
import uo.ri.cws.application.service.vehicletype.VehicleTypeCrudService;
import uo.ri.cws.application.service.workorder.CloseWorkOrderService;
import uo.ri.cws.application.service.workorder.ViewAssignedWorkOrdersService;
import uo.ri.cws.application.service.workorder.WorkOrderCrudService;
import uo.ri.cws.application.service.workorder.WorkOrderDto;
import uo.ri.cws.application.service.workorder.crud.WorkOrderCrudServiceImpl;

public class BusinessFactory implements ServiceFactory {

	@Override
	public MechanicCrudService forMechanicCrudService() {
		return new MechanicCrudServiceImpl();
	}

	@Override
	public CreateInvoiceService forCreateInvoiceService() {
		return new CreateInvoiceServiceImpl();
	}

	@Override
	public VehicleCrudService forVehicleCrudService() {
		return new VehicleCrudServiceImpl();
	}

	@Override
	public CloseWorkOrderService forClosingBreakdown() {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public VehicleTypeCrudService forVehicleTypeCrudService() {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public SparePartCrudService forSparePartCrudService() {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public SettleInvoiceService forSettleInvoiceService() {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public ClientCrudService forClienteCrudService() {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public ClientHistoryService forClientHistoryService() {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public WorkOrderCrudService forWorkOrderCrudService() {
		return new WorkOrderCrudServiceImpl();
	}

	@Override
	public ViewAssignedWorkOrdersService forViewAssignedWorkOrdersService() {
		throw new RuntimeException("Not yet implemented");
	}

}
