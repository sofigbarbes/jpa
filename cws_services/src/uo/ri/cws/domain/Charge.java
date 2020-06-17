package uo.ri.cws.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import alb.util.assertion.Argument;
import uo.ri.cws.domain.Associations.Charges;
import uo.ri.cws.domain.Invoice.InvoiceStatus;

@Entity
@Table(name = "TCHARGES", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "INVOICE_ID", "PAYMENTMEAN_ID" }) })
public class Charge extends BaseEntity {

	@ManyToOne
	private Invoice invoice;
	@ManyToOne
	private PaymentMean paymentMean;
	private double amount;

	public Charge() {
	}

	public Charge(Invoice invoice, PaymentMean paymentMean, double amount) {
		validatePaymentDate(paymentMean);
		this.amount = amount;
		paymentMean.pay(amount);
		this.invoice = invoice;
		Charges.link(invoice, this, paymentMean);
	}

	private void validatePaymentDate(PaymentMean paymentMean)
	{
		if (!paymentMean.isValid())
			throw new IllegalStateException("The payment is not valid");
	}

	public Invoice _getInvoice()
	{
		return invoice;
	}

	public void _setInvoice(Invoice invoice)
	{
		this.invoice = invoice;
	}

	public void setPaymentMean(PaymentMean paymentMean)
	{
		this.paymentMean = paymentMean;
	}

	public double getAmount()
	{
		return amount;
	}

	public void setAmount(double amount)
	{
		this.amount = amount;
	}

	/**
	 * Unlinks this charge and restores the value to the payment mean
	 *
	 * @throws IllegalStateException if the invoice is already settled
	 */
	public void rewind()
	{
		Argument.isTrue(this.invoice.getStatus() == InvoiceStatus.PAID,
				"The invoice is already settled");
		paymentMean.pay(-amount);
		Charges.unlink(this);
	}

	public void _setPaymentMean(PaymentMean paymentMean)
	{
		this.paymentMean = paymentMean;
	}

	public PaymentMean getPaymentMean()
	{
		return this.paymentMean;
	}

	public Invoice getInvoice()
	{
		return this.invoice;
	}

}
