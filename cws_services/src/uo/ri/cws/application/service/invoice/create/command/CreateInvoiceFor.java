package uo.ri.cws.application.service.invoice.create.command;

import java.util.List;

import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoiceDto;
import uo.ri.cws.application.util.command.Command;

public class CreateInvoiceFor implements Command<InvoiceDto> {

	private List<String> workOrderIds;

	public CreateInvoiceFor(List<String> workOrderIds) {
		this.workOrderIds = workOrderIds;
	}

	@Override
	public InvoiceDto execute() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
