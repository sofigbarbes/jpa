package uo.ri.cws.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import alb.util.date.Dates;

@Entity
@Table(name = "TCREDITCARDS")
@DiscriminatorValue("TCREDITCARDS")
public class CreditCard extends PaymentMean {

	@Column(unique = true)
	private String number;
	private String type;
	private Date validThru;

	public CreditCard() {
	}

	public CreditCard(String number, String type, Date validThru) {
		super();
		this.number = number;
		this.type = type;
		this.validThru = validThru;
	}

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public Date getValidThru()
	{
		return validThru;
	}

	public void setValidThru(Date validThru)
	{
		this.validThru = validThru;
	}

	@Override
	public boolean isValid()
	{
		return (this.validThru.after(Dates.today()));
	}

}
