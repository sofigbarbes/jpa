package uo.ri.cws.ui.foreman.reception.actions;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.training.CertificateDto;
import uo.ri.cws.application.service.workorder.AssignWorkOrderService;
import uo.ri.cws.ui.util.Printer;

public class AssignWorkOrderAction implements Action {

	@Override
	public void execute() throws BusinessException
	{

		String woId = Console.readString("Work order id");
		AssignWorkOrderService as = Factory.service.forAssignWorkOrderService();
 
		List<CertificateDto> certs = as.findCertificatesByWorkOrderId(woId);

		Console.println("\nList of certified mechanics\n");
		for (CertificateDto m : certs)
		{
			Printer.printCertifiedMechanic(m);
		}
		String mId = Console.readString("Mechanic id");

		as.assignWorkOrderToMechanic(woId, mId);

		Console.println("\nAssignation done");
	}
}
