package uo.ri.cws.application.service.invoice.create.command;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.workorder.WorkOrderDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.WorkOrder;

public class FindWorkOrdersByClientDni implements Command<List<WorkOrderDto>> {

	private String dni;
	private WorkOrderRepository repo = Factory.repository.forWorkOrder();
	private ClientRepository repoClient = Factory.repository.forClient();

	/**
	 * Find not invoiced workorders of the client with the dni specified as
	 * parameter
	 * 
	 * @param dni
	 */
	public FindWorkOrdersByClientDni(String dni) {
		this.dni = dni;
	}

	@Override
	public List<WorkOrderDto> execute() throws BusinessException {
		validate();
		List<WorkOrder> workOrders = null;
		List<WorkOrderDto> res = new ArrayList<WorkOrderDto>();

		workOrders = repo.findNotInvoicedByClientDni(dni);
		for (WorkOrder w : workOrders) {
			WorkOrderDto dto = new WorkOrderDto();
			dto.date = w.getDate();
			dto.description = w.getDescription();
			dto.id = w.getId();
			dto.mechanicId = w.getMechanic().getId();
			dto.status = w.getStatus().name();
			dto.total = w.getAmount();
			dto.vehicleId = w.getVehicle().getId();
			dto.version = w.getVersion();
			res.add(dto);

		}
		return res;

	}

	private void validate() throws BusinessException {
		if (!repoClient.findByDni(dni).isPresent())
			throw new BusinessException("There is no client with dni " + dni);
	}

}
