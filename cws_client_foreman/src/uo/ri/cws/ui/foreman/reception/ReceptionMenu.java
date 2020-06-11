package uo.ri.cws.ui.foreman.reception;

import alb.util.menu.BaseMenu;
import alb.util.menu.NotYetImplementedAction;
import uo.ri.cws.ui.foreman.reception.action.ListWorkOrderByPlateActions;
import uo.ri.cws.ui.foreman.reception.action.RegisterWorkOrderAction;
import uo.ri.cws.ui.foreman.reception.action.RemoveWorkOrderAction;
import uo.ri.cws.ui.foreman.reception.action.UpdateWorkOrderAction;

public class ReceptionMenu extends BaseMenu {

	public ReceptionMenu() {
		menuOptions = new Object[][] { 
			{"Foreman > Vehicle reception", null},
			
			{"Register work order", 	RegisterWorkOrderAction.class }, 
			{"Update workorder", 		UpdateWorkOrderAction.class },
			{"Remove workorder", 		RemoveWorkOrderAction.class },
			{"", null},
			{"List work orders", 		ListWorkOrderByPlateActions.class }, 
			{"View work order detail", 	NotYetImplementedAction.class },
			{"", null},
			{"List certified mechanics",NotYetImplementedAction.class }, 
			{"Assign a work order",  	NotYetImplementedAction.class },
		};
	}

}
