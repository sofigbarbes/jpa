package uo.ri.cws.application.service.workorder.crud.command;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.CertificateRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicDto;
import uo.ri.cws.application.service.training.CertificateDto;
import uo.ri.cws.application.service.vehicletype.VehicleTypeDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Certificate;

public class FindCertificatesByWorkOrderId implements Command<List<CertificateDto>> {
	
	

	private String workOrderId;
	private CertificateRepository certRepo = Factory.repository
			.forCertificate();

	
	public FindCertificatesByWorkOrderId(String id) {
		this.workOrderId = id;
	}

	@Override
	public List<CertificateDto> execute() throws BusinessException
	{
		List<CertificateDto> res = new ArrayList<CertificateDto>();
		List<Certificate> certificates = new ArrayList<Certificate>();

		certificates = certRepo.findByWorkOrderId(workOrderId);

		for (Certificate certificate : certificates)
		{
			CertificateDto dto = new CertificateDto();
			MechanicDto mecDto = new MechanicDto();
			VehicleTypeDto vtDto = new VehicleTypeDto();

			convertMechanicToDto(mecDto, certificate);
			convertVehicleToDto(vtDto, certificate);

			dto.id = certificate.getId();
			dto.obtainedAt = certificate.getDate();
			dto.mechanic = mecDto;
			dto.vehicleType = vtDto;

			res.add(dto);
		}

		return res;
	}

	private void convertVehicleToDto(VehicleTypeDto vtDto,
			Certificate certificate)
	{
		vtDto.id = certificate.getVehicleType().getId();
		vtDto.minTrainigHours = certificate.getVehicleType()
				.getMinTrainingHours();
		vtDto.name = certificate.getVehicleType().getName();
		vtDto.pricePerHour = certificate.getVehicleType().getPricePerHour();
		vtDto.version = certificate.getVehicleType().getVersion();

	}

	private void convertMechanicToDto(MechanicDto mecDto,
			Certificate certificate)
	{
		mecDto.dni = certificate.getMechanic().getDni();
		mecDto.id = certificate.getMechanic().getId();
		mecDto.name = certificate.getMechanic().getName();
		mecDto.surname = certificate.getMechanic().getSurname();
		mecDto.version = certificate.getVersion();

	}


}
