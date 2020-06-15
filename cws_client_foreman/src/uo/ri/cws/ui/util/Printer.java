package uo.ri.cws.ui.util;

import alb.util.console.Console;
import uo.ri.cws.application.service.training.CertificateDto;
import uo.ri.cws.application.service.vehicle.VehicleDto;
import uo.ri.cws.application.service.workorder.WorkOrderDto;

public class Printer {

	public static void printCertifiedMechanic(CertificateDto c) {
	
		Console.printf("%s\t%-10.10s %-25.25s %-25.25s\n",  
				c.mechanic.id
				, c.mechanic.dni
				, c.mechanic.name  
				, c.vehicleType.name
			);
	}

	public static void printWorkOrderDetail(WorkOrderDto wo) {

		Console.printf("%s for vehicle %s\n\t%-25.25s\n\t%tm/%<td/%<tY\n\t%s\n",  
				wo.id
				, wo.vehicleId
				, wo.description
				, wo.date
				, wo.status
			);
	}

	public static void printVehicleDetail(VehicleDto v) {

		Console.printf("%s\t%-8.8s\t%s\t%s\n",  
				v.id
				, v.plate
				, v.make
				, v.model
			);
	}

}
