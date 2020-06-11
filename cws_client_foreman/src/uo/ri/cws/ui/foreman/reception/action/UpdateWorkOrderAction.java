package uo.ri.cws.ui.foreman.reception.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.conf.Factory;
import uo.ri.cws.application.ServiceFactory;
import uo.ri.cws.application.service.workorder.WorkOrderCrudService;
import uo.ri.cws.application.service.workorder.WorkOrderDto;

public class UpdateWorkOrderAction implements Action {

	@Override
	public void execute() throws Exception
	{

		String woId = Console.readString("Work order id");

		WorkOrderCrudService as = Factory.service.forWorkOrderCrudService();
		WorkOrderDto oWo = as.findWorkOrderById(woId);
		
		String description = Console.readString("New description");
		oWo.description = description;
		as.updateWorkOrder(oWo);
		Console.println("\nUpdate done");
	}

}
