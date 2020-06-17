package uo.ri.cws.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import alb.util.assertion.Argument;

@Entity
@Table(name = "TDEDICATIONS", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "COURSE_ID", "VEHICLETYPE_ID" }) })
public class Dedication extends BaseEntity {

	@ManyToOne
	private VehicleType vehicleType;
	@ManyToOne
	private Course course;
	@Column(name = "PERCENTAGE")
	private int hours;

	Dedication() {
	}

	Dedication(VehicleType vehicleType, Integer hours) {
		Argument.isNotNull(vehicleType);
		Argument.isTrue(hours > 0);
		this.hours = hours;
		Associations.Dedicate.link(this, vehicleType);
	}

	public Course getCourse()
	{
		return course;
	}

	void _setCourse(Course course)
	{
		this.course = course;
	}

	void _setVehicleType(VehicleType vehicleType)
	{
		this.vehicleType = vehicleType;
	}

	public VehicleType getVehicleType()
	{
		return vehicleType;
	}

	public int getHours()
	{
		return hours;
	}

	public Integer getPercentage()
	{
		return hours;
	}

}
