package uo.ri.cws.application.service.training.commands;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.VehicleTypeRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.vehicletype.VehicleTypeDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.VehicleType;

public class FindAllVehicleTypes implements Command<List<VehicleTypeDto>> {

	private VehicleTypeRepository vTypeRepo = Factory.repository
			.forVehicleType();

	
	@Override
	public List<VehicleTypeDto> execute() throws BusinessException
	{
		List<VehicleType> vehicleTypes = vTypeRepo.findAll();
		List<VehicleTypeDto> res = new ArrayList<>();

		for (VehicleType vehicle : vehicleTypes)
		{
			VehicleTypeDto dto = new VehicleTypeDto();
			dto.id = vehicle.getId();
			dto.minTrainigHours = vehicle.getMinTrainingHours();
			dto.name = vehicle.getName();
			dto.pricePerHour = vehicle.getPricePerHour();
			dto.version = vehicle.getVersion();
			res.add(dto);
		}
		return res;
	}

}
