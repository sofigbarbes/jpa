package uo.ri.cws.application.service.workorder.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.VehicleRepository;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.workorder.WorkOrderDto;
import uo.ri.cws.application.util.BusinessCheck;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Vehicle;
import uo.ri.cws.domain.WorkOrder;

public class AddWorkOrder implements Command<WorkOrderDto> {

	private WorkOrderDto dto;
	private VehicleRepository vehRepo = Factory.repository.forVehicle();
	private WorkOrderRepository woRepo = Factory.repository.forWorkOrder();

	public AddWorkOrder(WorkOrderDto dto) {
		this.dto = dto;
	}

	@Override
	public WorkOrderDto execute() throws BusinessException
	{
		validate();

		Vehicle vehicle = vehRepo.findById(dto.vehicleId).get();
		WorkOrder w = new WorkOrder(vehicle, dto.description);
		WorkOrderDto res = new WorkOrderDto();
		woRepo.add(w);
		w = woRepo.findByVehicleAndDescription(dto.vehicleId, dto.description);
		res.date = w.getDate();
		res.description = w.getDescription();
		dto.id = w.getId();
		res.id = w.getId();
		res.status = w.getStatus().toString();
		res.vehicleId = w.getVehicle().getId();
		res.version = w.getVersion();

		return res;
	}

	private void validate() throws BusinessException
	{
		BusinessCheck.isNotEmpty(dto.vehicleId,
				"The vehicle Id should not be empty");
		BusinessCheck.isNotEmpty(dto.description,
				"Please, write a description for your workOrder");
		Optional<Vehicle> ov = vehRepo.findById(dto.vehicleId);
		BusinessCheck.exists(ov, "The vehicle specified does not exist.");

	}

}
