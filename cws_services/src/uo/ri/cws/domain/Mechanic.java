package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TMECHANICS")
public class Mechanic extends BaseEntity {
	@Column(unique=true)
	private String dni;
	private String surname;
	private String name;

	@OneToMany(mappedBy = "mechanic")
	private Set<WorkOrder> workorders = new HashSet<WorkOrder>();
	@OneToMany(mappedBy = "mechanic")
	private Set<Intervention> interventions = new HashSet<Intervention>();

	public Mechanic() {

	}

	public Mechanic(String dni, String name, String surname) {
		super();
		this.dni = dni;
		this.surname = surname;
		this.name = name;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<WorkOrder> getWorkorders() {
		return new HashSet<WorkOrder>(workorders);
	}

	public Set<WorkOrder> _getWorkorders() {
		return workorders;
	}

	public Set<Intervention> getInterventions() {
		return new HashSet<Intervention>(interventions);
	}

	public Set<Intervention> _getInterventions() {
		return interventions;
	}

	public Set<WorkOrder> getAssigned() {
		return new HashSet<WorkOrder>(workorders);
	}

}
