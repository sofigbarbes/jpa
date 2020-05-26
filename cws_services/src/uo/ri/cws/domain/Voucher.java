package uo.ri.cws.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TVOUCHERS")
@DiscriminatorValue("TVOUCHERS")
public class Voucher extends PaymentMean {

	@Column(unique = true)
	private String code;
	private double available;
	private String description;

	public Voucher() {
	}

	public Voucher(String code, double available, String description) {
		super();
		this.code = code;
		this.available = available;
		this.description = description;
	}

	public Voucher(String code, double available) {
		super();
		this.code = code;
		this.available = available;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getAvailable() {
		return available;
	}

	public void setAvailable(double available) {
		this.available = available;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Augments the accumulated and decrements the available
	 *
	 * @throws IllegalStateException if not enough available to pay
	 */
	@Override
	public void pay(double amount) {
		if (this.available <= amount) {
			throw new IllegalStateException("Not enough money in the voucher");
		} else {
			this.available -= amount;
			this.accumulated += amount;
		}
	}

	@Override
	public boolean isValid() {
		return true;
	}

	public double getDisponible() {
		return getAvailable();
	}

	public void setDescripcion(String string) {
		setDescription(string);
	}

}
