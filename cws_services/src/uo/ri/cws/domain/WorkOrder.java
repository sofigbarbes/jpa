package uo.ri.cws.domain;

import java.lang.Thread.State;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import alb.util.date.Dates;

@Entity
@Table(name = "TWORKORDERS")
public class WorkOrder extends BaseEntity {
	public enum WorkOrderStatus {
		OPEN, ASSIGNED, FINISHED, INVOICED
	}

	@ManyToOne
	private Mechanic mechanic;

	@ManyToOne
	private Vehicle vehicle;

	@ManyToOne
	private Invoice invoice;

	@OneToMany(mappedBy = "workOrder")
	private Set<Intervention> interventions = new HashSet<>();

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	private String description;
	private double amount = 0.0;
	@Enumerated(EnumType.STRING)
	private WorkOrderStatus status = WorkOrderStatus.OPEN;

	WorkOrder() {
	}

	public WorkOrder(Vehicle vehicle) {
		super();
		this.date = Dates.now();
		Associations.Order.link(vehicle, this);
	}

	public WorkOrder(Vehicle vehicle, String description) {
		this(vehicle);
		this.description = description;
		Associations.Order.link(vehicle, this);

	}

	public Set<Intervention> getInterventions() {
		return new HashSet<>(interventions);
	}

	public Set<Intervention> _getInterventions() {
		return interventions;
	}

	public void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void _setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void _setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Date getDate() {
		return new Date(date.getTime());
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAmount() {
		return amount;
	}

	public WorkOrderStatus getStatus() {
		return status;
	}

	/**
	 * Changes it to INVOICED state given the right conditions This method is called
	 * from Invoice.addWorkOrder(...)
	 *
	 * @see State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not FINISHED, or - The
	 *                               work order is not linked with the invoice
	 */
	public void markAsInvoiced() {
		throwIfStatusNot(WorkOrderStatus.FINISHED);
		throwIfNotLinkedWithInvoice();
		this.status = WorkOrderStatus.INVOICED;
		computeAmount();

	}

	private void throwIfNotLinkedWithInvoice() {
		if (this.invoice == null) {
			throw new IllegalStateException("Not assigned");
		}
	}

	/**
	 * Changes it to FINISHED state given the right conditions and computes the
	 * amount
	 *
	 * @see State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not in ASSIGNED state,
	 *                               or - The work order is not linked with a
	 *                               mechanic
	 */
	public void markAsFinished() {
		throwIfStatusNot(WorkOrderStatus.ASSIGNED);
		checkLinkedToMechanic();
		this.status = WorkOrderStatus.FINISHED;
		computeAmount();

	}

	private void computeAmount() {
		this.amount = 0;
		for (Intervention intervention : interventions) {
			this.amount += intervention.getAmount();
		}
	}

	/**
	 * Changes it back to FINISHED state given the right conditions This method is
	 * called from Invoice.removeWorkOrder(...)
	 *
	 * @see State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not INVOICED, or - The
	 *                               work order is still linked with the invoice
	 */
	public void markBackToFinished() {
		if (this.status != WorkOrderStatus.INVOICED) {
			throw new IllegalStateException("The workorder is not invoiced");
		}

		if (this.mechanic != null) {
			throw new IllegalArgumentException("The workorder is still linked to a mechanic");
		}
		this.status = WorkOrderStatus.FINISHED;

	}

	/**
	 * Links (assigns) the work order to a mechanic and then changes its status to
	 * ASSIGNED
	 *
	 * @see State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not in OPEN status, or -
	 *                               The work order is already linked with another
	 *                               mechanic
	 */
	public void assignTo(Mechanic mechanic) {
		throwIfStatusNot(WorkOrderStatus.OPEN);
		checkNotLinkedToMechanic();

		Associations.Assign.link(mechanic, this);
		this.status = WorkOrderStatus.ASSIGNED;
	}

	private void checkNotLinkedToMechanic() {
		if (this.mechanic != null) {
			throw new IllegalArgumentException("The workroder is already linked");
		}
	}

	private void checkLinkedToMechanic() {
		if (this.mechanic == null) {
			throw new IllegalArgumentException("The workorder is already linked");
		}
	}

	private void throwIfStatusNot(WorkOrderStatus status) {
		if (this.status != status) {
			throw new IllegalArgumentException("The workorder is not " + status);
		}
	}

	/**
	 * Unlinks (deassigns) the work order and the mechanic and then changes its
	 * status back to OPEN
	 *
	 * @see State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not in ASSIGNED status
	 */
	public void desassign() {

	}

	/**
	 * In order to assign a work order to another mechanic is first have to be moved
	 * back to OPEN state and unlinked from the previous mechanic.
	 *
	 * @see State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not in FINISHED status
	 */
	public void reopen() {
		throwIfStatusNot(WorkOrderStatus.FINISHED);
		this.status = WorkOrderStatus.OPEN;
		Associations.Assign.unlink(mechanic, this);
	}

	public Mechanic getMechanic() {
		return this.mechanic;
	}

}
