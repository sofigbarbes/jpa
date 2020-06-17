package uo.ri.cws.application.service.vehicle.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.repository.VehicleRepository;
import uo.ri.cws.application.repository.VehicleTypeRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.vehicle.VehicleDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Client;
import uo.ri.cws.domain.Vehicle;
import uo.ri.cws.domain.VehicleType;

public class AddVehicle implements Command<VehicleDto> {

	private VehicleDto vehicleDto;
	private VehicleRepository vehRepo = Factory.repository.forVehicle();
	private ClientRepository clientRepo = Factory.repository.forClient();
	private VehicleTypeRepository typeRepo = Factory.repository.forVehicleType();

	public AddVehicle(VehicleDto vehicleDto) {
		this.vehicleDto = vehicleDto;
	}

	@Override
	public VehicleDto execute() throws BusinessException
	{
		validate();
		checkNotInDB();

		Vehicle v = new Vehicle(vehicleDto.plate, vehicleDto.make,
				vehicleDto.model);

		Optional<VehicleType> vt = typeRepo.findById(vehicleDto.vehicleTypeId);
		checkVehicleType(vt);

		v.setVehicleType(vt.get());

		Optional<Client> c = clientRepo.findById(vehicleDto.clientId);
		checkClient(c);

		v.setClient(c.get());

		vehRepo.add(v);
		vehicleDto.id = vehRepo.findByPlate(vehicleDto.plate).get().getId();
		return vehicleDto;
	}

	private void checkVehicleType(Optional<VehicleType> vt)
			throws BusinessException
	{
		if (!vt.isPresent())
		{
			throw new BusinessException("The vehicle type id "
					+ vehicleDto.vehicleTypeId + " is not in the database");
		}
	}

	private void checkClient(Optional<Client> c) throws BusinessException
	{
		if (!c.isPresent())
		{
			throw new BusinessException("The client id " + vehicleDto.clientId
					+ " is not in the database");
		}
	}

	private void validate() throws BusinessException
	{
		if (vehicleDto.plate == null || vehicleDto.plate.isEmpty())
		{
			throw new BusinessException(
					"The plate should not be null or empty");
		}
		if (vehicleDto.make == null || vehicleDto.make.isEmpty())
		{
			throw new BusinessException("The make should not be null or empty");
		}
		if (vehicleDto.model == null || vehicleDto.model.isEmpty())
		{
			throw new BusinessException(
					"The model should not be null or empty");
		}
		if (vehicleDto.vehicleTypeId == null
				|| vehicleDto.vehicleTypeId.isEmpty())
		{
			throw new BusinessException(
					"The vehicleTypeId should not be null or empty");
		}
		if (vehicleDto.clientId == null || vehicleDto.clientId.isEmpty())
		{
			throw new BusinessException(
					"The clientId should not be null or empty");
		}
	}

	private void checkNotInDB() throws BusinessException
	{
		if (vehRepo.findByPlate(vehicleDto.plate).isPresent())
		{
			throw new BusinessException(
					"The vehicle is already present in the database");
		}
	}

}
