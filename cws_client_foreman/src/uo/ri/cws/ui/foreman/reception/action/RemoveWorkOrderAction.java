package uo.ri.cws.ui.foreman.reception.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.workorder.WorkOrderCrudService;
import uo.ri.cws.application.service.workorder.WorkOrderDto;

public class RemoveWorkOrderAction implements Action {

	@Override
	public void execute() throws Exception {
		String woId = Console.readString("Work order id");

		WorkOrderCrudService as = Factory.service.forWorkOrderCrudService();
		WorkOrderDto w = as.removeWorkOrder(woId);

		Console.println("\nThe work order " + w.id + " has been deleted");
	}

}
