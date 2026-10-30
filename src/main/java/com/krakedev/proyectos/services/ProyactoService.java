package com.krakedev.proyectos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.krakedev.proyectos.entidades.Proyecto;
import com.krakedev.proyectos.repositories.EmpleadoRepository;
import com.krakedev.proyectos.repositories.ProyectoRepository;
import com.krakedev.proyectos.repositories.TareaRepository;

@Service
public class ProyactoService {
	
	private final ProyectoRepository proyectoRepo;

	
	
	public ProyactoService(ProyectoRepository proyectoRepo, EmpleadoRepository empleadoRepo,
			TareaRepository tareaRepo) {
		super();
		this.proyectoRepo = proyectoRepo;
	}



	public Proyecto crear(Proyecto proyecto) {
		return proyectoRepo.save(proyecto);
	}
	
	public List<Proyecto> listar(){
		return proyectoRepo.findAll();
	}
	
	public Proyecto buscar(int id) {
		Optional<Proyecto> encontrado = proyectoRepo.findById(id);
		return encontrado.orElse(null);
	}
	
	public Proyecto actualizar(int id, Proyecto proyecto) {
		Proyecto data = buscar(id);
		if(data == null) {
			return null;
		}
		data.setNombre(proyecto.getNombre());
		data.setDescripcion(proyecto.getDescripcion());
		data.setFechaInicio(proyecto.getFechaInicio());
		data.setTareas(proyecto.getTareas());
		return proyectoRepo.save(data);
	}
	
	public boolean eliminar(int id) {
		Proyecto data = buscar(id);
		if(data == null) {
			return false;
		}
		proyectoRepo.deleteById(id);
		return true;
	}
	
	
	
	

}
