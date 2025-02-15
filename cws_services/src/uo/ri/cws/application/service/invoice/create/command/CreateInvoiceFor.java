package uo.ri.cws.application.service.invoice.create.command;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoiceDto;
import uo.ri.cws.application.util.BusinessCheck;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.WorkOrder;
import uo.ri.cws.domain.WorkOrder.WorkOrderStatus;

public class CreateInvoiceFor implements Command<InvoiceDto> {

	private List<String> workOrderIds;
	private InvoiceRepository repo = Factory.repository.forInvoice();
	private WorkOrderRepository repoWo = Factory.repository.forWorkOrder();

	public CreateInvoiceFor(List<String> workOrderIds) {
		this.workOrderIds = workOrderIds;
	}

	@Override
	public InvoiceDto execute() throws BusinessException
	{
		validate();
		Long number = repo.getNextInvoiceNumber();

		Invoice invoice = new Invoice(number);
		for (String id : workOrderIds)
		{
			WorkOrder w = repoWo.findById(id).get();
			invoice.addWorkOrder(w);
		}
		repo.add(invoice);
		InvoiceDto dto = new InvoiceDto();
		dto.date = invoice.getDate();
		dto.number = invoice.getNumber();
		dto.vat = invoice.getVat();
		dto.version = invoice.getVersion();
		dto.id = invoice.getId();
		dto.total = invoice.getAmount();
		dto.status = invoice.getStatus().toString();
		return dto;

	}

	private void validate() throws BusinessException
	{
		for (String id : workOrderIds)
		{
			BusinessCheck.exists(repoWo.findById(id),
					"There is no workorder for id " + id);
			BusinessCheck.isTrue(
					repoWo.findById(id).get()
							.getStatus() == WorkOrderStatus.FINISHED,
					"The workorder is not finished yet!");

		}
	}

}
