package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TPAYMENTMEANS")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PaymentMean extends BaseEntity {

	// herencia, no hay id
	@ManyToOne
	protected Client client;

	@OneToMany(mappedBy = "paymentMean")
	private Set<Charge> charges = new HashSet<>();

	protected double accumulated = 0.0;

	public PaymentMean() {
	}

	public Client getClient()
	{
		return client;
	}

	public Set<Charge> getCharges()
	{
		return new HashSet<>(charges);
	}

	void _setCharges(Set<Charge> charges)
	{
		this.charges = charges;
	}

	public double getAccumulated()
	{
		return accumulated;
	}

	public void pay(double importe)
	{
		this.accumulated += importe;
	}

	Set<Charge> _getCharges()
	{
		return charges;
	}

	public abstract boolean isValid();

	void _setClient(Client client)
	{
		this.client = client;
	}

}
