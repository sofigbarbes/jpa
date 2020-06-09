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

public class FindWorkOrdersByPlate implements Command<List<WorkOrderDto>> {

	private String plate;
	WorkOrderRepository repo = Factory.repository.forWorkOrder();
	VehicleRepository vehRepo = Factory.repository.forVehicle();

	public FindWorkOrdersByPlate(String plate) {
		this.plate = plate;
	}

	@Override
	public List<WorkOrderDto> execute() throws BusinessException {
		validate();
		List<WorkOrder> workOrders = repo.findByPlate(plate);
		List<WorkOrderDto> res = new ArrayList<WorkOrderDto>();
		for (WorkOrder w : workOrders) {
			WorkOrderDto dto = new WorkOrderDto();
			dto.date = w.getDate();
			dto.description = w.getDescription();
			dto.id = w.getId();
			dto.invoiceId = w.getInvoice().getId();
			//dto.mechanicId = w.getMechanic().getId();
			dto.status = w.getStatus().toString();
			dto.total = w.getAmount();
			dto.vehicleId = w.getVehicle().getId();
			dto.version = w.getVersion();
			res.add(dto);
		}
		return res;
	}

	private void validate() throws BusinessException {
		if(plate==null || plate.isEmpty()) {
			throw new BusinessException("The plate cant be null or empty");
		}
		checkInDb();
	}

	private void checkInDb() throws BusinessException {
		if(!vehRepo.findByPlate(plate).isPresent()) {
			throw new BusinessException("There is no vehicle with that plate");
		}
		
	}

}
