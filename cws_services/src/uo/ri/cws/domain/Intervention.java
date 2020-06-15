package uo.ri.cws.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "TINTERVENTIONS", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "WORKORDER_ID", "MECHANIC_ID", "DATE" }) })
public class Intervention extends BaseEntity {

	@ManyToOne
	private WorkOrder workOrder;

	@ManyToOne
	private Mechanic mechanic;

	@OneToMany(mappedBy = "intervention")
	private Set<Substitution> substitutions = new HashSet<>();

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	private int minutes;

	public Intervention() {

	}

	public Intervention(Mechanic mechanic, WorkOrder workOrder, int minutes) {
		super();
		this.workOrder = workOrder;
		this.mechanic = mechanic;
		this.minutes = minutes;
		this.date = new Date();

		Associations.Intervene.link(workOrder, this, mechanic);

	}

	public WorkOrder getWorkOrder() {
		return workOrder;
	}

	 void _setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

	 void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	public Set<Substitution> getSubstitutions() {
		return new HashSet<>(substitutions);
	}

	Set<Substitution> _getSubstitutions() {
		return (substitutions);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getMinutes() {
		return minutes;
	}

	/**
	 * Calculates the price of the intervention:
	 *
	 * @return
	 */
	public double getAmount() {
		double amSubstitutions = 0;

		for (Substitution subst : substitutions) {
			amSubstitutions += subst.getImporte();
		}

		double amWorkorder = 0;
		amWorkorder = workOrder.getVehicle().getVehicleType().getPricePerHour();
		amWorkorder = amWorkorder * minutes / 60;
		return amSubstitutions + amWorkorder;

	}

	public Set<Substitution> getSustitutions() {
		return this.getSubstitutions();
	}

}
