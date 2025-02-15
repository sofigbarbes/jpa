package uo.ri.cws.ui.util;

import alb.util.console.Console;
import uo.ri.cws.application.service.mechanic.MechanicDto;
import uo.ri.cws.application.service.workorder.WorkOrderDto;

public class Printer {

	public static void printWorkOrder(WorkOrderDto rep) {
		
		Console.printf("\t%s \t%-40.40s \t%td/%<tm/%<tY \t%-12.12s \t%.2f\n",  
				rep.id
				, rep.description 
				, rep.date
				, rep.status
				, rep.total
		);
	}

	public static void printMechanic(MechanicDto m) {

		Console.printf("\t%s %-10.10s %-25.25s %-25.25s\n",  
				m.id
				, m.dni
				, m.name
				, m.surname
			);
	}

}
