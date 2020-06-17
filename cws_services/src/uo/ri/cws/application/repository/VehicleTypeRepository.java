package uo.ri.cws.application.repository;

import java.util.List;

import uo.ri.cws.domain.VehicleType;

public interface VehicleTypeRepository extends Repository<VehicleType> {

	List<VehicleType> findAll();

	/**
	 * Finds the vehicle types which have a dedication in some course
	 * @return a list with the vehicle types.
	 */
	List<VehicleType> findVehicleTypesWithDedication();

}
