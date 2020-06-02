package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;

public class AddMechanic implements Command<MechanicDto> {

	MechanicRepository repo = Factory.repository.forMechanic();
	private MechanicDto dto;

	public AddMechanic(MechanicDto mecanico) {
		this.dto = mecanico;
	}

	public MechanicDto execute() throws BusinessException {
		validateNulls();
		checkNotInDB();
		Mechanic mechanic = new Mechanic(dto.dni, dto.name, dto.surname);
		repo.add(mechanic);
		dto.id = mechanic.getId();
		return dto;
	}

	private void checkNotInDB() throws BusinessException {
		Optional<Mechanic> mec = repo.findByDni(dto.dni);
		if (mec != null) {
			throw new BusinessException("The mechanic with dni " + dto.dni + " is already in the database");
		}
	}

	private void validateNulls() throws BusinessException {
		if (dto.dni == null || dto.dni == "") {
			throw new BusinessException("The dni cant be null or empty");
		}
		if (dto.name == null || dto.name == "") {
			throw new BusinessException("The name cant be null or empty");
		}
		if (dto.surname == null || dto.surname == "") {
			throw new BusinessException("The surname cant be null or empty");
		}
	}

}
