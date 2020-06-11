package uo.ri.cws.ui.foreman.reception.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.vehicle.VehicleCrudService;
import uo.ri.cws.application.service.vehicle.VehicleDto;
import uo.ri.cws.application.service.workorder.WorkOrderCrudService;

public class RegisterWorkOrderAction implements Action {

	@Override
	public void execute() throws BusinessException {

		String description = Console.readString("Workorder description ");

		VehicleCrudService vehicleService = Factory.service
				.forVehicleCrudService();

		String plate = Console.readString("Vehicle platenumber ");

		if (!vehicleService.findVehicleByPlate(plate).isPresent()) {
			addVehicleAction(plate);
		}

		WorkOrderCrudService workOrderService = Factory.service
				.forWorkOrderCrudService();
		workOrderService.addWorkOrder(plate, description);

		Console.println("The workOrder was added to the database");
	}

	/**
	 * Asks the user for the data of the vehicle to be added, as it was not in
	 * the database
	 * 
	 * @param plate of the vehicle
	 * @throws BusinessException
	 */
	private void addVehicleAction(String plate) throws BusinessException {
		VehicleDto m = new VehicleDto();
		m.plate = plate;
		m.make = Console.readString("Make");
		m.model = Console.readString("Model");
		m.vehicleTypeId = Console.readString("Vehicle type id");
		m.clientId = Console.readString("Client id");

		VehicleCrudService as = Factory.service.forVehicleCrudService();
		m = as.addVehicle(m);

	}

}
