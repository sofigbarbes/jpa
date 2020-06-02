package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;

public class UpdateMechanic implements Command<Void> {

	private MechanicDto dto;
	MechanicRepository repo = Factory.repository.forMechanic();

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
		if (dto.dni == null || dto.dni == "") {
			throw new BusinessException("The dni cant be null or empty");
		}
		if (dto.name == null || dto.name == "") {
			throw new BusinessException("The name cant be null or empty");
		}
		if (dto.surname == null || dto.surname == "") {
			throw new BusinessException("The surname cant be null or empty");
		}
		checkInDatabase();
	}

	private void checkInDatabase() throws BusinessException {
		Optional<Mechanic> mec = repo.findByDni(dto.dni);
		if (mec == null) {
			throw new BusinessException("The mechanic with dni " + dto.dni + " is not in the database");
		}
	}

}
