package uo.ri.cws.domain;

import javax.persistence.*;

@Entity
@Table(name = "TSUBSTITUTIONS")
public class Substitution extends BaseEntity {

	@ManyToOne
	private Intervention intervention;

	@ManyToOne
	private SparePart sparePart;
	private int quantity;

	public Substitution() {
	}

	public Substitution(SparePart sparePart, Intervention intervention, int quantity) {
		super();
		validate(sparePart, intervention, quantity);

		this.intervention = intervention;
		this.sparePart = sparePart;
		this.quantity = quantity;

		Associations.Sustitute.link(sparePart, this, intervention);
	}

	public Intervention getIntervention() {
		return intervention;
	}

	 void _setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}

	public SparePart getSparePart() {
		return sparePart;
	}

	 void _setSparePart(SparePart sparePart) {
		this.sparePart = sparePart;
	}

	public int getQuantity() {
		return quantity;
	}

	public double getImporte() {
		return quantity * sparePart.getPrice();
	}

	private void validate(SparePart sparepart, Intervention intervention, int quantity) {
		if (quantity <= 0)
			throw new IllegalArgumentException("The quantity can't be negative");
	}

}
