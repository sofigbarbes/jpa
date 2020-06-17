package uo.ri.cws.application.service.vehicle.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.repository.VehicleRepository;
import uo.ri.cws.application.repository.VehicleTypeRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.vehicle.VehicleDto;
import uo.ri.cws.application.util.BusinessCheck;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Client;
import uo.ri.cws.domain.Vehicle;
import uo.ri.cws.domain.VehicleType;

public class AddVehicle implements Command<VehicleDto> {

	private VehicleDto vehicleDto;
	private VehicleRepository vehRepo = Factory.repository.forVehicle();
	private ClientRepository clientRepo = Factory.repository.forClient();
	private VehicleTypeRepository typeRepo = Factory.repository
			.forVehicleType();

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
		BusinessCheck.exists(vt, "The vehicle type id "
				+ vehicleDto.vehicleTypeId + " is not in the database");

	}

	private void checkClient(Optional<Client> c) throws BusinessException
	{
		BusinessCheck.exists(c, "The client id " + vehicleDto.clientId
				+ " is not in the database");

	}

	private void validate() throws BusinessException
	{
		BusinessCheck.isNotNull(vehicleDto.plate,
				"The plate should not be null");
		BusinessCheck.isNotEmpty(vehicleDto.plate,
				"The plate should not be empty");
		BusinessCheck.isNotNull(vehicleDto.make,
				"The brand of the vehicle should not be null");
		BusinessCheck.isNotEmpty(vehicleDto.make,
				"The make should not be empty");
		BusinessCheck.isNotNull(vehicleDto.model,
				"The model should not be null");
		BusinessCheck.isNotEmpty(vehicleDto.model,
				"The model should not be empty");
		BusinessCheck.isNotNull(vehicleDto.vehicleTypeId,
				"The id of the vehicle type should not be null");
		BusinessCheck.isNotEmpty(vehicleDto.vehicleTypeId,
				"The id of the vehicle type should not be empty");
		BusinessCheck.isNotNull(vehicleDto.clientId,
				"The clientId should not be null");
		BusinessCheck.isNotEmpty(vehicleDto.clientId,
				"The clientId should not be empty");

	}

	private void checkNotInDB() throws BusinessException
	{
		BusinessCheck.isFalse(vehRepo.findByPlate(vehicleDto.plate).isPresent(),
				"The vehicle is already present in the database");

	}

}
