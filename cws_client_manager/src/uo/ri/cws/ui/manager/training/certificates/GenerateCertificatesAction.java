package uo.ri.cws.ui.manager.training.certificates;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.training.CertificateService;

public class GenerateCertificatesAction implements Action {

	@Override
	public void execute() throws Exception {
		
		Console.println("Generating certificates...");
		
		CertificateService cs = Factory.service.forCertificateService();
		int qty = cs.generateCertificates();
		
		Console.println(qty + " certificates generated");
	}

}
