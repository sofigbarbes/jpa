package uo.ri.cws.application.service.training.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.EnrollmentRepository;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.repository.VehicleTypeRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.training.TrainingHoursRow;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.VehicleType;

public class FindTrainingByVehicleTypeAndMechanic
		implements Command<List<TrainingHoursRow>> {

	private VehicleTypeRepository vtypeRepo = Factory.repository
			.forVehicleType();
	private EnrollmentRepository enrollRepo = Factory.repository
			.forEnrollment();
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
				Optional<Integer> hoursOptional = enrollRepo.getHoursEnrolled(
						mechanic.getId(), vehicleType.getId());

				if (hoursOptional.isPresent())
				{
					row = new TrainingHoursRow();
					row.enrolledHours = Integer
							.valueOf(String.valueOf(hoursOptional.get()));
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
