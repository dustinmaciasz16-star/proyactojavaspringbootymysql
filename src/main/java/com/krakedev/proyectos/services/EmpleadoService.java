package com.krakedev.proyectos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.krakedev.proyectos.entidades.Empleado;
import com.krakedev.proyectos.repositories.EmpleadoRepository;

@Service
public class EmpleadoService {
	
	private final EmpleadoRepository empleadoRepo;
	
	

	public EmpleadoService(EmpleadoRepository empleadoRepo) {
		this.empleadoRepo = empleadoRepo;
	}

	public Empleado crear(Empleado empleado) {
		return empleadoRepo.save(empleado);
	}
	
	public List<Empleado> listar(){
		return empleadoRepo.findAll();
	}
	
	public Empleado buscar(int id) {
		Optional<Empleado> encontrado = empleadoRepo.findById(id);
		return encontrado.orElse(null);
	}
	
	public Empleado actualizar(int id, Empleado empleado) {
		Empleado data = buscar(id);
		if(data == null) {
			return null;
		}
		data.setNombre(empleado.getNombre());
		data.setCargo(empleado.getCargo());
		data.setTareas(empleado.getTareas());
		return empleadoRepo.save(data);
	}
	
	public boolean eliminar(int id) {
		Empleado data = buscar(id);
		if(data == null) {
			return false;
		}
		empleadoRepo.deleteById(id);
		return true;
	}
}
