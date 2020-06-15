package uo.ri.cws.application.service.training.impl;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.training.CourseCrudService;
import uo.ri.cws.application.service.training.CourseDto;
import uo.ri.cws.application.service.vehicletype.VehicleTypeDto;

public class CourseCrudServiceImpl implements CourseCrudService {

	@Override
	public CourseDto registerNew(CourseDto dto) throws BusinessException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateCourse(CourseDto dto) throws BusinessException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCourse(String id) throws BusinessException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public List<CourseDto> findAllCourses() throws BusinessException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VehicleTypeDto> findAllVehicleTypes() throws BusinessException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<CourseDto> findCourseById(String cId)
			throws BusinessException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
