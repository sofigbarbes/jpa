package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TCLIENTS")
public class Client extends BaseEntity {

	@Column(unique = true)
	private String dni;

	private String name;

	private String surname;

	private String email;

	private String phone;

	private Address address;

	@OneToMany(mappedBy = "client")
	private Set<Vehicle> vehicles = new HashSet<>();

	@OneToMany(mappedBy = "client")
	private Set<PaymentMean> paymentMeans = new HashSet<>();

	public Client() {

	}

	public Client(String dni, String name, String surname) {
		super();
		this.dni = dni;
		this.name = name;
		this.surname = surname;
	}

	public String getDni()
	{
		return dni;
	}

	public void setDni(String dni)
	{
		this.dni = dni;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getSurname()
	{
		return surname;
	}

	public void setSurname(String surname)
	{
		this.surname = surname;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public Address getAddress()
	{
		return address;
	}

	public void setAddress(Address address)
	{
		this.address = address;
	}

	public Set<Vehicle> getVehicles()
	{
		return new HashSet<>(vehicles);
	}

	Set<Vehicle> _getVehicles()
	{
		return vehicles;
	}

	void _setVehicles(Set<Vehicle> vehicles)
	{
		this.vehicles = vehicles;
	}

	public Set<PaymentMean> getPaymentMeans()
	{
		return new HashSet<>(paymentMeans);
	}

	Set<PaymentMean> _getPaymentMeans()
	{
		return paymentMeans;
	}

	void _setPaymentMeans(Set<PaymentMean> paymentMeans)
	{
		this.paymentMeans = paymentMeans;
	}

}
