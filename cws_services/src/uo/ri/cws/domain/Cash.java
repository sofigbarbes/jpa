package uo.ri.cws.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TCASHES")
@DiscriminatorValue("TCASHES")
public class Cash extends PaymentMean {

	public Cash(Client client) {
		super();
		this.client = client;
		linkPay();
	}

	private void linkPay()
	{
		Associations.Pay.link(this, client);
	}

	public Cash() {
	}

	@Override
	public boolean isValid()
	{
		return true;
	}

}
