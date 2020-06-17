package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.mechanic.MechanicDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;

public class FindAllMechanics implements Command<List<MechanicDto>> {

	private MechanicRepository repo = Factory.repository.forMechanic();

	public List<MechanicDto> execute() {
		List<Mechanic> mechanics = repo.findAll();
		List<MechanicDto> mechanicsDto = new ArrayList<>();
		for (Mechanic m : mechanics) {
			MechanicDto dto = new MechanicDto();
			dto.id = m.getId();
			dto.dni = m.getDni();
			dto.name = m.getName();
			dto.surname = m.getSurname();
			dto.version = m.getVersion();
			mechanicsDto.add(dto);
		}
		return mechanicsDto;
	}

}
