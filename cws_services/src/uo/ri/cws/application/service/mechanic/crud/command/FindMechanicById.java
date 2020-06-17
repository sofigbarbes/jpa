package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;

public class FindMechanicById implements Command<Optional<MechanicDto>> {

	private String id;
	private MechanicRepository repo = Factory.repository.forMechanic();

	public FindMechanicById(String id) {
		this.id = id;
	}

	public Optional<MechanicDto> execute() throws BusinessException {
		Optional<Mechanic> mec = repo.findById(id);
		if (!mec.isPresent()) {
			throw new BusinessException(
					"There is no mechanic with that id");
		}
		Mechanic m = mec.get();
		MechanicDto res = new MechanicDto();
		res.dni = m.getDni();
		res.id = m.getId();
		res.name = m.getName();
		res.surname = m.getSurname();

		return Optional.ofNullable(res);
	}

}
