package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicDto;
import uo.ri.cws.application.util.BusinessCheck;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;

public class AddMechanic implements Command<MechanicDto> {

	private MechanicRepository repo = Factory.repository.forMechanic();
	private MechanicDto dto;

	public AddMechanic(MechanicDto mecanico) {
		this.dto = mecanico;
	}

	public MechanicDto execute() throws BusinessException
	{
		validateNulls();
		checkNotInDB();
		Mechanic mechanic = new Mechanic(dto.dni, dto.name, dto.surname);
		repo.add(mechanic);
		dto.id = mechanic.getId();
		return dto;
	}

	private void checkNotInDB() throws BusinessException
	{
		Optional<Mechanic> mec = repo.findByDni(dto.dni);
		BusinessCheck.isFalse(mec.isPresent(), "The mechanic with dni "
				+ dto.dni + " is already in the database");

	}

	private void validateNulls() throws BusinessException
	{
		BusinessCheck.isNotEmpty(dto.dni, "The dni should not be empty");
		BusinessCheck.isNotEmpty(dto.name, "The name should not be empty");
		BusinessCheck.isNotEmpty(dto.surname, "The name should not be empty");
		BusinessCheck.isNotNull(dto.dni, "The dni should not be null");
		BusinessCheck.isNotNull(dto.name, "The name should not be null");
		BusinessCheck.isNotNull(dto.surname, "The surname should not be null");

	}

}
