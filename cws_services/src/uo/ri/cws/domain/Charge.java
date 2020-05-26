package uo.ri.cws.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import uo.ri.cws.domain.Associations.Charges;

@Entity
@Table(name = "TCHARGES", uniqueConstraints = { @UniqueConstraint(columnNames = { "INVOICE_ID", "PAYMENTMEAN_ID" }) })
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
		// store the amount
		// increment the paymentMean accumulated ( paymentMean.pay( -amount) )
		// link invoice, this and paymentMean
	}

	private void validatePaymentDate(PaymentMean paymentMean) {
		if (!paymentMean.isValid())
			throw new IllegalStateException("The payment is not valid");
	}

	public Invoice _getInvoice() {
		return invoice;
	}

	public void _setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public void setPaymentMean(PaymentMean paymentMean) {
		this.paymentMean = paymentMean;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * Unlinks this charge and restores the value to the payment mean
	 *
	 * @throws IllegalStateException if the invoice is already settled
	 */
	public void rewind() {
		// assert the invoice is not in PAID status
		// decrement the payment mean accumulated ( paymentMean.pay( -amount) )
		// unlink invoice, this and paymentMean
	}

	public void _setPaymentMean(PaymentMean paymentMean) {
		this.paymentMean = paymentMean;
	}

	public PaymentMean getPaymentMean() {
		return this.paymentMean;
	}

	public Invoice getInvoice() {
		return this.invoice;
	}

}
