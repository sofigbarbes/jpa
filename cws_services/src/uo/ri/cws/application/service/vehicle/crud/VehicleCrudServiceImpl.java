package uo.ri.cws.application.service.vehicle.crud;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.vehicle.VehicleCrudService;
import uo.ri.cws.application.service.vehicle.VehicleDto;
import uo.ri.cws.application.service.vehicle.crud.commands.AddVehicle;
import uo.ri.cws.application.service.vehicle.crud.commands.FindVehicleByPlate;
import uo.ri.cws.application.util.command.CommandExecutor;

public class VehicleCrudServiceImpl implements VehicleCrudService {

	private CommandExecutor executor = Factory.executor.forExecutor();

	@Override
	public Optional<VehicleDto> findVehicleByPlate(String plate)
			throws BusinessException
	{
		return executor.execute(new FindVehicleByPlate(plate));
	}

	@Override
	public VehicleDto addVehicle(VehicleDto vehicleDto) throws BusinessException
	{
		return executor.execute(new AddVehicle(vehicleDto));
	}

}
