package uo.ri.cws.application.service.training.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.repository.VehicleTypeRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.training.TrainingHoursRow;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Enrollment;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.VehicleType;

public class FindTrainingByVehicleTypeAndMechanic
		implements Command<List<TrainingHoursRow>> {

	private VehicleTypeRepository vtypeRepo = Factory.repository
			.forVehicleType();
	private MechanicRepository mecRepo = Factory.repository.forMechanic();

	@Override
	public List<TrainingHoursRow> execute() throws BusinessException
	{

		List<TrainingHoursRow> res = new ArrayList<TrainingHoursRow>();
		List<VehicleType> vehicleTypes = vtypeRepo.findAll();
		List<Mechanic> mechanics = mecRepo.findEnrolledMechanics();
		TrainingHoursRow row = new TrainingHoursRow();

		for (VehicleType vehicleType : vehicleTypes)
		{
			for (Mechanic mechanic : mechanics)
			{
				Set<Enrollment> enrollments = mechanic
						.getEnrollmentsFor(vehicleType);
				if (enrollments.size() > 0)
				{
					int hours = 0;
					for (Enrollment e : enrollments)
					{
						hours += e.getAttendedHoursFor(vehicleType);
					}
					row = new TrainingHoursRow();
					row.enrolledHours = hours;
					row.mechanicFullName = mechanic.getName() + " "
							+ mechanic.getSurname();
					row.vehicleTypeName = vehicleType.getName();
					res.add(row);
				}
			}
		}

		return res;
	}

}
