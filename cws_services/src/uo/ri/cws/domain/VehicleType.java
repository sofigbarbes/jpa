package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import alb.util.assertion.Argument;

@Entity
@Table(name = "TVEHICLETYPES")
public class VehicleType extends BaseEntity {

	@OneToMany(mappedBy = "vehicleType")
	private Set<Vehicle> vehicles = new HashSet<Vehicle>();
	@Column(unique = true)
	private String name;
	private double pricePerHour;
	private int minTrainingHours;

	@OneToMany(mappedBy = "vehicleType")
	private Set<Certificate> certificates = new HashSet<>();

	@OneToMany(mappedBy = "vehicleType")
	private Set<Dedication> dedications = new HashSet<>();

	public VehicleType() {

	}

	public VehicleType(String name) {
		super();
		Argument.isNotEmpty(name);
		this.name = name;
	}

	public VehicleType(String name, double pricePerHour) {
		super();
		this.name = name;
		this.pricePerHour = pricePerHour;
	}

	public int getMinTrainingHours()
	{
		return minTrainingHours;
	}

	Set<Vehicle> _getVehicles()
	{
		return (vehicles);
	}

	public Set<Vehicle> getVehicles()
	{
		return new HashSet<Vehicle>(vehicles);
	}

	void _setVehicles(Set<Vehicle> vehicles)
	{
		this.vehicles = vehicles;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public double getPricePerHour()
	{
		return pricePerHour;
	}

	public void setPricePerHour(double pricePerHour)
	{
		this.pricePerHour = pricePerHour;
	}

	public String getNombre()
	{
		return getName();
	}

	Set<Dedication> _getDedications()
	{
		return dedications;
	}

	public Set<Dedication> getDedications()
	{
		return new HashSet<>(dedications);
	}

	public Set<Certificate> getCertificates()
	{
		return new HashSet<>(certificates);
	}

	Set<Certificate> _getCertificates()
	{
		return certificates;
	}

}
