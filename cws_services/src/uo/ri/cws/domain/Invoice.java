package uo.ri.cws.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import alb.util.assertion.Argument;
import alb.util.date.Dates;
import uo.ri.cws.domain.WorkOrder.WorkOrderStatus;

@Entity
@Table(name = "TINVOICES")
public class Invoice extends BaseEntity {
	public enum InvoiceStatus {
		NOT_YET_PAID, PAID
	}

	@OneToMany(mappedBy = "invoice")
	private Set<WorkOrder> workOrders = new HashSet<>();

	@OneToMany(mappedBy = "invoice")
	private Set<Charge> charges = new HashSet<>();
	@Column(unique = true)
	private Long number;
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	private double amount;
	private double vat;
	@Enumerated(EnumType.STRING)
	private InvoiceStatus status = InvoiceStatus.NOT_YET_PAID;

	public Invoice() {

	}

	public Invoice(Long number) {
		Argument.isNotNull(number);

		this.number = number;
		this.date = Dates.now();
	}

	public Invoice(Long number, Date date) {
		Argument.isNotNull(number);
		Argument.isNotNull(date);
		this.number = number;
		this.date = new Date(date.getTime());
	}

	public Invoice(Long number, List<WorkOrder> workOrders) {
		this(number, Dates.now());
		this.setStatus(InvoiceStatus.NOT_YET_PAID);
		for (WorkOrder workorder : workOrders)
		{
			if (workorder.getStatus() != WorkOrderStatus.FINISHED)
			{
				throw new IllegalStateException(
						"Cannot assigned an invoice to a non finished workorder");
			}
			Associations.ToInvoice.link(workorder, this);
			workorder.markAsInvoiced();

		}
		computeAmount();
	}

	public Invoice(long number, Date date, List<WorkOrder> workOrders) {
		this(number, workOrders);
		this.date = date;
		computeAmount();

	}

	Set<WorkOrder> _getWorkOrders()
	{
		return workOrders;
	}

	public Set<WorkOrder> getWorkOrders()
	{
		return new HashSet<>(workOrders);
	}

	void _setWorkOrders(Set<WorkOrder> workorders)
	{
		this.workOrders = workorders;
	}

	public Set<Charge> getCharges()
	{
		return new HashSet<>(charges);
	}

	Set<Charge> _getCharges()
	{
		return this.charges;
	}

	void _setCharges(Set<Charge> charges)
	{
		this.charges = charges;
	}

	public Long getNumber()
	{
		return number;
	}

	public void setNumber(Long number)
	{
		this.number = number;
	}

	public Date getDate()
	{
		return new Date(date.getTime());
	}

	Date _getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = new Date(date.getTime());
	}

	public double getAmount()
	{
		return amount;
	}

	public void setAmount(double amount)
	{
		this.amount = amount;
	}

	public double getVat()
	{
		return vat;
	}

	public void setVat(double vat)
	{
		this.vat = vat;
	}

	public InvoiceStatus getStatus()
	{
		return status;
	}

	public void setStatus(InvoiceStatus status)
	{
		this.status = status;
	}

	/**
	 * Computed amount and vat (vat depends on the date) Total amount will be
	 * the addition of all work order amounts plus VAT. VAT depends on the date
	 * in which the invoice was generated: if it is before 1/7/2012, 18% will
	 * apply, thereafter 21%
	 */
	private void computeAmount()
	{
		this.amount = 0;
		for (WorkOrder workorder : workOrders)
		{
			this.amount += workorder.getAmount();
		}
		if (this.date.before(Dates.fromString("1/7/2012")))
		{
			this.vat = 18;
		} else
		{
			this.vat = 21;
		}
		this.amount = amount + amount * vat / 100;
		this.amount = round(amount, 2);
	}

	private double round(double value, int decimals)
	{
		double integerPart, result;
		result = value;
		integerPart = Math.floor(result);
		result = (result - integerPart) * Math.pow(10, decimals);
		result = Math.round(result);
		result = (result / Math.pow(10, decimals)) + integerPart;
		return result;
	}

	/**
	 * Adds (double links) the workOrder to the invoice and updates the amount
	 * and vat
	 *
	 * @param workOrder
	 * @see State diagrams on the problem statement document
	 * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
	 */
	public void addWorkOrder(WorkOrder workOrder)
	{
		if (this.status != InvoiceStatus.NOT_YET_PAID)
		{
			throw new IllegalStateException(
					"The invoice has already been paid");
		}
		Associations.ToInvoice.link(workOrder, this);
		workOrder.markAsInvoiced();
		computeAmount();
	}

	/**
	 * Removes a work order from the invoice and recomputes amount and vat
	 *
	 * @param workOrder
	 * @see State diagrams on the problem statement document
	 * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
	 */
	public void removeWorkOrder(WorkOrder workOrder)
	{
		if (this.status != InvoiceStatus.NOT_YET_PAID)
		{
			throw new IllegalStateException(
					"The invoice status should be NOT_YET_PAID");
		}
		Associations.ToInvoice.unlink(workOrder, this);
		workOrder.markBackToFinished();
		computeAmount();

	}

	/**
	 * Marks the invoice as PAID, but
	 *
	 * @throws IllegalStateException if - Is already settled - Or the amounts
	 *                               paid with charges to payment means do not
	 *                               cover the total of the invoice
	 */
	public void settle()
	{
		if (this.status == InvoiceStatus.PAID)
		{
			throw new IllegalStateException("The invoice is already settled");
		}
		checkAmount();
		this.status = InvoiceStatus.PAID;
	}

	private void checkAmount()
	{
		double chargesAmount = 0;
		for (Charge c : charges)
		{
			chargesAmount += c.getAmount();
		}
		if (chargesAmount < this.amount)
		{
			throw new IllegalStateException(
					"the amounts paid with charges to payment "
							+ "means do not cover the total of the invoice");
		}
	}

}
