package uo.ri.cws.application.service.training.commands;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.CertificateRepository;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.repository.VehicleTypeRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Certificate;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.VehicleType;

public class GenerateCertificates implements Command<Integer> {

	private CertificateRepository certRepo = Factory.repository
			.forCertificate();
	private MechanicRepository mecRepo = Factory.repository.forMechanic();
	private VehicleTypeRepository vtRepo = Factory.repository.forVehicleType();

	@Override
	public Integer execute() throws BusinessException
	{
		int certificates = 0;
		List<Mechanic> mechanics = mecRepo.findEnrolledPassedMechanics();
		List<VehicleType> vehicleTypes = vtRepo
				.findVehicleTypesWithDedication();
		Certificate c = new Certificate();
		for (Mechanic m : mechanics)
		{
			for (VehicleType vt : vehicleTypes)
			{
				if (!checkExistsCertif(m.getId(), vt.getId()))
				{
					c = new Certificate(m, vt);
					certRepo.add(c);
					certificates++;
				}
			}
		}

		return certificates;
	}

	private boolean checkExistsCertif(String idMechanic, String idVehicleType)
	{
		Optional<Certificate> aux = certRepo
				.findByMechanicAndVehicleType(idMechanic, idVehicleType);
		return aux.isPresent();
	}

}
