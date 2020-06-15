package uo.ri.cws.ui.foreman.reception.actions;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.workorder.WorkOrderCrudService;
import uo.ri.cws.application.service.workorder.WorkOrderDto;
import uo.ri.cws.ui.util.Printer;

public class ListWorkOrderByPlateActions implements Action {

	@Override
	public void execute() throws Exception {

		String plate = Console.readString("Plate number");

		WorkOrderCrudService as = Factory.service.forWorkOrderCrudService();
		List<WorkOrderDto> wos = as.findWorkOrdersByPlateNumber(plate);

		Console.println("Work orders for vehicle " + plate);
		for (WorkOrderDto wo : wos) {
			Printer.printWorkOrderDetail(wo);
		}
	}

}
