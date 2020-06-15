package uo.ri.cws.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import alb.util.assertion.Argument;

@Entity
@Table(name = "TCERTIFICATES")
public class Certificate extends BaseEntity {
	private Date date;
	@ManyToOne
	private VehicleType vehicleType;
	@ManyToOne
	private Mechanic mechanic;

	Certificate() {
	}

	public Certificate(Mechanic mechanic, VehicleType vehicleType) {
		Argument.isNotNull(mechanic);
		Argument.isNotNull(vehicleType);
		Associations.Certify.link(mechanic, this, vehicleType);
		this.date = new Date();
	}

	public Date getDate() {
		return new Date(date.getTime());
	}

	void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

	void _setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

}
