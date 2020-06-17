package uo.ri.cws.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import alb.util.assertion.Argument;

@Entity
@Table(name = "TCOURSES")
public class Course extends BaseEntity {

	@Column(unique = true)
	private String code;
	private String name, description;
	@Column(name = "HOURS")
	private int duration;
	private Date startDate, endDate;

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
	private Set<Dedication> dedications = new HashSet<Dedication>();
	@OneToMany(mappedBy = "course")
	private Set<Enrollment> enrollments = new HashSet<Enrollment>();

	Course() {
	}

	public Course(String code) {
		Argument.isNotEmpty(code);
		this.code = code;
	}

	public Course(String code, String name, String description, Date startDate,
			Date endDate, int duration) {
		validate(code, name, description, startDate, endDate, duration);

		this.code = code;
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.duration = duration;
	}

	private void validate(String code, String name, String description,
			Date startDate, Date endDate, int duration)
	{
		Argument.isNotEmpty(code);
		Argument.isNotEmpty(name);
		Argument.isNotEmpty(description);
		Argument.isTrue(duration > 0);
		Argument.isNotNull(startDate);
		Argument.isNotNull(endDate);
		Argument.isTrue(endDate.after(startDate));

	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setDuration(int duration)
	{
		this.duration = duration;
	}

	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}

	public String getCode()
	{
		return code;
	}

	public String getName()
	{
		return name;
	}

	public String getDescription()
	{
		return description;
	}

	public int getDuration()
	{
		return duration;
	}

	public Set<Enrollment> getEnrollments()
	{
		return new HashSet<>(enrollments);
	}

	Set<Enrollment> _getEnrollments()
	{
		return enrollments;
	}

	public Date getStartDate()
	{
		return new Date(startDate.getTime());
	}

	public Date getEndDate()
	{
		return new Date(endDate.getTime());
	}

	public void addDedications(Map<VehicleType, Integer> percentages)
	{
		List<VehicleType> vehicleTypes = new ArrayList<VehicleType>();
		List<Integer> p = new ArrayList<Integer>();
		if (!dedications.isEmpty())
		{
			throw new IllegalStateException(
					"The course already has dedications");
		}
		int total = 0;
		for (Entry<VehicleType, Integer> dedication : percentages.entrySet())
		{
			checkPercentageOver100(total, dedication.getValue());
			vehicleTypes.add(dedication.getKey());
			p.add(dedication.getValue());
			total += dedication.getValue();
		}
		Argument.isTrue(total == 100,
				"The sum of the percentages should be 100");

		for (int i = 0; i < vehicleTypes.size(); i++)
		{
			Dedication d = new Dedication(vehicleTypes.get(i), p.get(i));
			Associations.Dedicate.link(this, d);
		}
	}

	private void checkPercentageOver100(int total, Integer value)
	{
		if (total + value > 100)
		{
			throw new IllegalArgumentException(
					"Cannot add dedication (percentage over 100)");
		}
	}

	public Set<Dedication> getDedications()
	{
		return new HashSet<>(dedications);
	}

	Set<Dedication> _getDedications()
	{
		return dedications;
	}

	public void clearDedications()
	{
		while (dedications.size() != 0)
		{
			for (Dedication dedication : dedications)
			{
				Associations.Dedicate.unlink(dedication);
				break;
			}
		}
	}

	public int getDedication(VehicleType truck)
	{
		int hours = duration;
		int percentage = 0;
		for (Dedication dedication : dedications)
		{
			if (dedication.getVehicleType().equals(truck))
			{
				percentage = dedication.getHours();
			}
		}

		return hours * percentage / 100;
	}

	public boolean isDedicatedTo(VehicleType type)
	{
		for (Dedication d : dedications)
		{
			if (d.getVehicleType().equals(type))
				return true;
		}
		return false;
	}

	public int getHours()
	{
		return duration;
	}

}
