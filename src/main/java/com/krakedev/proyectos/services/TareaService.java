package com.krakedev.proyectos.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.krakedev.proyectos.entidades.Empleado;
import com.krakedev.proyectos.entidades.Proyecto;
import com.krakedev.proyectos.entidades.Tarea;
import com.krakedev.proyectos.repositories.EmpleadoRepository;
import com.krakedev.proyectos.repositories.ProyectoRepository;
import com.krakedev.proyectos.repositories.TareaRepository;

import jakarta.transaction.Transactional;

@Service
public class TareaService {

	private final TareaRepository tareaRepo;
	private final ProyectoRepository proyectoRepo;
	private final EmpleadoRepository empleadoRepo;

	public TareaService(TareaRepository tareaRepo, ProyectoRepository proyectoRepo, EmpleadoRepository empleadoRepo) {
		this.tareaRepo = tareaRepo;
		this.proyectoRepo = proyectoRepo;
		this.empleadoRepo = empleadoRepo;
	}

	@Transactional
	public Tarea guardar(Tarea tarea) {
		if (tarea.getProyecto() == null) {
			throw new IllegalArgumentException("Debe indicar el proyecto de la tarea");
		}

		int proyectoId = tarea.getProyecto().getId();

		Proyecto proyecto = proyectoRepo.findById(proyectoId)
				.orElseThrow(() -> new IllegalArgumentException("No existe el proyecto con ID " + proyectoId));

		List<Empleado> empleados = new ArrayList<>();

		if (tarea.getEmpleados() != null) {
			for (Empleado empleadoRecibido : tarea.getEmpleados()) {
				Empleado empleado = empleadoRepo.findById(empleadoRecibido.getId()).orElseThrow(
						() -> new IllegalArgumentException("No existe el empleado con ID " + empleadoRecibido.getId()));

				empleados.add(empleado);
			}
		}

		tarea.setProyecto(proyecto);
		tarea.setEmpleados(empleados);

		return tareaRepo.save(tarea);
	}

	@Transactional
	public List<Tarea> listar() {
		return tareaRepo.findAll();
	}

	@Transactional
	public Tarea buscar(int id) {
		Optional<Tarea> encontrado = tareaRepo.findById(id);
		return encontrado.orElse(null);
	}

	@Transactional
	public Tarea actualizar(int id, Tarea tarea) {
		Tarea data = buscar(id);
		if (data == null) {
			return null;
		}
		data.setDescripcion(tarea.getDescripcion());
		data.setFachaLimite(tarea.getFachaLimite());
		data.setCostoEstimado(tarea.getCostoEstimado());
		data.setProyecto(tarea.getProyecto());
		data.setEmpleados(tarea.getEmpleados());
		return tareaRepo.save(data);
	}

	@Transactional
	public boolean eliminar(int id) {
		Tarea data = buscar(id);
		if (data == null) {
			return false;
		}
		tareaRepo.deleteById(id);
		return true;
	}
}
