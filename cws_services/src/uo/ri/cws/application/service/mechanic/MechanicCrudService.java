package uo.ri.cws.application.service.mechanic;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.service.BusinessException;

/**
 * This service is intended to be used by the Manager It follows the ISP
 * principle (@see SOLID principles from RC Martin)
 */
public interface MechanicCrudService {

	/**
	 * Add a new mechanic to the system with the data specified in the dto. The
	 * id value will be ignored
	 * 
	 * @param mecanico dto
	 * @return the dto with the id filed updated to the UUID generated
	 * @throws BusinessException if there already exist another mechanic with
	 *                           the same dni
	 */
	MechanicDto addMechanic(MechanicDto mecanico) throws BusinessException;

	/**
	 * @param idMecanico
	 * @throws BusinessException if the mechanic does not exist
	 */
	void deleteMechanic(String idMecanico) throws BusinessException;

	/**
	 * Updates values for the mechanic specified by the id field, just name and
	 * surname will be updated
	 * 
	 * @param mechanic dto, the id field, name and surname cannot be null
	 * @throws BusinessException if the mechanic does not exist
	 */
	void updateMechanic(MechanicDto mechanic) throws BusinessException;

	/**
	 * @param id of the mechanic
	 * @return the dto for the mechanic or null if there is none with the id
	 *         DOES NOT @throws BusinessException
	 */
	Optional<MechanicDto> findMechanicById(String id) throws BusinessException;

	/**
	 * @return the list of all mechanics registered in the system without
	 *         regarding their contract status or if they have contract or not.
	 *         It might be an empty list if there is no mechanic
	 * 
	 *         DOES NOT @throws BusinessException
	 */
	List<MechanicDto> findAllMechanics() throws BusinessException;

}
