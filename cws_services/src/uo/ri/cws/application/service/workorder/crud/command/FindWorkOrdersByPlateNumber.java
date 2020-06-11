package uo.ri.cws.application.service.workorder.crud.command;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.VehicleRepository;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.workorder.WorkOrderDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.WorkOrder;

public class FindWorkOrdersByPlateNumber
		implements Command<List<WorkOrderDto>> {

	private String plate;
	private WorkOrderRepository woRepo = Factory.repository.forWorkOrder();
	private VehicleRepository vehRepo = Factory.repository.forVehicle();

	public FindWorkOrdersByPlateNumber(String plate) {
		this.plate = plate;
	}

	@Override
	public List<WorkOrderDto> execute() throws BusinessException
	{
		validate();
		List<WorkOrder> workOrders = woRepo.findByPlate(plate);
		List<WorkOrderDto> res = new ArrayList<WorkOrderDto>();
		for (WorkOrder workOrder : workOrders)
		{
			WorkOrderDto dto = new WorkOrderDto();
			dto.date = workOrder.getDate();
			dto.description = workOrder.getDescription();
			dto.id = workOrder.getId();
			dto.status = workOrder.getStatus().toString();
			dto.vehicleId = workOrder.getVehicle().getId();
			dto.version = workOrder.getVersion();
			res.add(dto);
		}
		return res;
	}

	private void validate() throws BusinessException
	{
		if (this.plate == null || this.plate.isEmpty())
		{
			throw new BusinessException("The plate cannot be null or empty");
		}
		checkInDb();
	}

	private void checkInDb() throws BusinessException
	{
		if (!vehRepo.findByPlate(plate).isPresent())
			throw new BusinessException("The vehicle with platenumber " + plate
					+ " is not in the database");
	}

}
