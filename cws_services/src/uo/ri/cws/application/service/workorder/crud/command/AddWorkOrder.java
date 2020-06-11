package uo.ri.cws.application.service.workorder.crud.command;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.VehicleRepository;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.workorder.WorkOrderDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Vehicle;
import uo.ri.cws.domain.WorkOrder;

public class AddWorkOrder implements Command<WorkOrderDto> {

	private String plate;
	private String description;
	VehicleRepository vehRepo = Factory.repository.forVehicle();
	WorkOrderRepository woRepo = Factory.repository.forWorkOrder();

	public AddWorkOrder(String plate, String description) {
		this.plate = plate;
		this.description = description;
	}

	@Override
	public WorkOrderDto execute() throws BusinessException {
		validateNulls();
		
		Vehicle vehicle = vehRepo.findByPlate(plate).get();
		WorkOrder w = new WorkOrder(vehicle, description);
		WorkOrderDto res = new WorkOrderDto();
		
		res.date = w.getDate();
		res.description = w.getDescription();
		res.id = w.getId();
		res.status = w.getStatus().toString();
		res.vehicleId = w.getVehicle().getId();
		res.version = w.getVersion();
		
		woRepo.add(w);
		
		return res;
	}

	private void validateNulls() throws BusinessException {
		if (plate == null || plate.isEmpty()) {
			throw new BusinessException("The plate should not be empty");
		}
		if (description == null || description.isEmpty()) {
			throw new BusinessException("The description should not be empty");
		}
	}

}
