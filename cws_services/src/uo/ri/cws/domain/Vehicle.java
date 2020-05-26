package uo.ri.cws.domain;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "TVEHICLES")
public class Vehicle extends BaseEntity {

	@OneToMany(mappedBy = "vehicle")
	private Set<WorkOrder> workOrders = new HashSet<WorkOrder>();

	@ManyToOne
	private VehicleType vehicleType;

	@ManyToOne
	private Client client;
	@Column(unique = true)
	private String plateNumber;
	@Column(name = "brand")
	private String make;
	private String model;

	public Vehicle() {

	}

	public Vehicle(String plateNumber, String make, String model) {
		super();
		this.plateNumber = plateNumber;
		this.make = make;
		this.model = model;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public Client getClient() {
		return client;
	}

	public void _setClient(Client client) {
		this.client = client;
	}

	public String getModel() {
		return model;
	}

	public Set<WorkOrder> _getWorkOrders() {
		return this.workOrders;
	}

	public Set<WorkOrder> getWorkOrders() {
		return new HashSet<WorkOrder>(workOrders);
	}

	public void _setWorkOrders(Set<WorkOrder> workOrders) {
		this.workOrders = workOrders;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public void _setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

}
