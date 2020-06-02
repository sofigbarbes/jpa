package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TVEHICLETYPES")
public class VehicleType extends BaseEntity {

	@OneToMany(mappedBy = "vehicleType")
	private Set<Vehicle> vehicles = new HashSet<Vehicle>();
	@Column(unique=true)
	private String name;
	private double pricePerHour;

	public VehicleType() {

	}

	public VehicleType(String name, double pricePerHour) {
		super();
		this.name = name;
		this.pricePerHour = pricePerHour;
	}

	public Set<Vehicle> _getVehicles() {
		return (vehicles);
	}

	public Set<Vehicle> getVehicles() {
		return new HashSet<Vehicle>(vehicles);
	}

	public void _setVehicles(Set<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPricePerHour() {
		return pricePerHour;
	}

	public void setPricePerHour(double pricePerHour) {
		this.pricePerHour = pricePerHour;
	}

	public String getNombre() {
		return getName();
	}

}
