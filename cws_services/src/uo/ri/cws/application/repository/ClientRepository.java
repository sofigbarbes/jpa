package uo.ri.cws.application.repository;

import java.util.Optional;

import uo.ri.cws.domain.Client;

public interface ClientRepository extends Repository<Client> {

	/**
	 * @param dni
	 * @return the client identified by the dni or null if none
	 */
	Optional<Client> findByDni(String dni);

}
