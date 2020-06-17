package uo.ri.cws.application.service.training.commands;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.CertificateRepository;
import uo.ri.cws.application.repository.VehicleTypeRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicDto;
import uo.ri.cws.application.service.training.CertificateDto;
import uo.ri.cws.application.service.vehicletype.VehicleTypeDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Certificate;
import uo.ri.cws.domain.VehicleType;

public class FindCertificatedByVehicleType
		implements Command<List<CertificateDto>> {

	private CertificateRepository certRepo = Factory.repository
			.forCertificate();
	private VehicleTypeRepository vtypeRepo = Factory.repository
			.forVehicleType();

	@Override
	public List<CertificateDto> execute() throws BusinessException
	{
		List<Certificate> certificates = new ArrayList<Certificate>();
		List<VehicleType> vehicleTypes = vtypeRepo.findAll();
		for (VehicleType vehicleType : vehicleTypes)
		{
			certificates
					.addAll(certRepo.findByVehicleTypeId(vehicleType.getId()));
		}
		List<CertificateDto> res = new ArrayList<CertificateDto>();
		for (Certificate c : certificates)
		{
			CertificateDto dto = new CertificateDto();
			MechanicDto mecDto = new MechanicDto();
			VehicleTypeDto vtDto = new VehicleTypeDto();

			mecDto.dni = c.getMechanic().getDni();
			mecDto.id = c.getMechanic().getId();
			mecDto.name = c.getMechanic().getName();
			mecDto.surname = c.getMechanic().getSurname();
			mecDto.version = c.getMechanic().getVersion();

			vtDto.id = c.getVehicleType().getId();
			vtDto.minTrainigHours = c.getVehicleType().getMinTrainingHours();
			vtDto.name = c.getVehicleType().getName();
			vtDto.pricePerHour = c.getVehicleType().getPricePerHour();
			vtDto.version = c.getVehicleType().getVersion();

			dto.id = c.getId();
			dto.mechanic = mecDto;
			dto.obtainedAt = c.getDate();
			dto.vehicleType = vtDto;
			dto.version = c.getVersion();

			res.add(dto);
		}
		return res;
	}

}
