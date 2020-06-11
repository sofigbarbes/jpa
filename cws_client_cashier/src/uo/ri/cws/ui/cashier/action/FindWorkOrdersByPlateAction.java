package uo.ri.cws.ui.cashier.action;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.workorder.WorkOrderCrudService;
import uo.ri.cws.application.service.workorder.WorkOrderDto;
import uo.ri.cws.ui.util.Printer;

public class FindWorkOrdersByPlateAction implements Action {

	@Override
	public void execute() throws Exception {
		WorkOrderCrudService cs = Factory.service.forWorkOrderCrudService();

		String plate = Console.readString("Client plate:");

		Console.println("\nWorkOrders for plate " + plate + "\n");

		List<WorkOrderDto> reps = cs.findWorkOrderByPlate(plate);

		if (reps.size() == 0) {
			Console.printf("There is no work orders for that plate\n");
			return;
		}

		for (WorkOrderDto rep : reps) {
			Printer.printWorkOrder(rep);
		}
	}

}
