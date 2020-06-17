package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicDto;
import uo.ri.cws.application.util.BusinessCheck;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;

public class UpdateMechanic implements Command<Void> {

	private MechanicDto dto;
	private MechanicRepository repo = Factory.repository.forMechanic();

	public UpdateMechanic(MechanicDto dto) {
		this.dto = dto;
	}

	public Void execute() throws BusinessException {
		validate();
		Mechanic m = repo.findById(dto.id).get();
		m.setName(dto.name);
		m.setSurname(dto.surname);
		repo.remove(m);
		repo.add(m);

		return null;
	}

	private void validate() throws BusinessException {
		BusinessCheck.isNotEmpty(dto.dni, "The dni should not be empty");
		BusinessCheck.isNotEmpty(dto.name, "The name should not be empty");
		BusinessCheck.isNotEmpty(dto.surname, "The name should not be empty");
		BusinessCheck.isNotNull(dto.dni, "The dni should not be null");
		BusinessCheck.isNotNull(dto.name, "The name should not be null");
		BusinessCheck.isNotNull(dto.surname, "The surname should not be null");
		checkInDatabase();
	}

	private void checkInDatabase() throws BusinessException {
		Optional<Mechanic> mec = repo.findByDni(dto.dni);
		if (mec == null) {
			throw new BusinessException("The mechanic with dni " + dto.dni + " is not in the database");
		}
	}

}
