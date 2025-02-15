package uo.ri.cws.ui.manager.mechanic.action;

import java.util.Optional;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicDto;
import uo.ri.cws.ui.util.Printer;

public class UpdateMechanicAction implements Action {

	@Override
	public void execute() throws BusinessException {
		
		// Ask the user for the mechanic id
		String id = Console.readString("Mechanic id"); 
		
		// Invoke the service and show the data
		MechanicCrudService as = Factory.service.forMechanicCrudService();

		Optional<MechanicDto> res = as.findMechanicById(id);
		if ( ! res.isPresent() ) {
			throw new BusinessException("There is no mechanic with that id");
		}
		MechanicDto m = res.get();
		Printer.printMechanic(m);
		
		// Ask for new data
		m.name = Console.readString("Name"); 
		m.surname = Console.readString("Surname"); // Doi is the identity, cannot be changed
		
		// Update
		as.updateMechanic( m );
		
		// Show the result
		Console.println("The mechanic has been updated");
	}

}
