package uo.ri.cws.domain;

import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "TSPAREPARTS")
public class SparePart extends BaseEntity {

	@OneToMany(mappedBy = "sparePart")
	private Set<Substitution> substitutions = new HashSet<Substitution>();
	@Column(unique = true)
	private String code;
	private String description;
	private double price;

	public SparePart() {
	}

	public SparePart(String code, String description, double price) {
		super();
		this.code = code;
		this.description = description;
		this.price = price;
	}

	public Set<Substitution> getSubstitutions()
	{
		return new HashSet<Substitution>(substitutions);
	}

	Set<Substitution> _getSubstitutions()
	{
		return substitutions;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	public Set<Substitution> getSustituciones()
	{
		return this.getSubstitutions();
	}

}
