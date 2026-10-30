package com.krakedev.proyectos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krakedev.proyectos.entidades.Empleado;
import com.krakedev.proyectos.services.EmpleadoService;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

	private final EmpleadoService service;

	public EmpleadoController(EmpleadoService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Empleado empleado) {
		try {
			Empleado creado = service.crear(empleado);
			return ResponseEntity.status(HttpStatus.CREATED).body(creado);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear pelicula");
		}
	}

	@GetMapping
	public ResponseEntity<?> listar() {
		try {
			List<Empleado> empleados = service.listar();
			return ResponseEntity.ok(empleados);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al listar peliculas");
		}
	}

	@GetMapping("/id")
	public ResponseEntity<?> buscarPorId(@PathVariable int id) {
		try {
			Empleado encontrado = service.buscar(id);
			if (encontrado == null) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(encontrado);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al buscar la peliculas por el id");
		}
	}

	@PutMapping("/id")
	public ResponseEntity<?> actualizar(@PathVariable int id, @RequestBody Empleado empleado) {
		try {
			Empleado actualizado = service.actualizar(id, empleado);
			if (actualizado == null) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(actualizado);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al actualizar la peliculas por el id");
		}
	}

	@DeleteMapping("/id")
	public ResponseEntity<?> eliminar(@PathVariable int id) {
		try {
			boolean eliminado = service.eliminar(id);
			if (!eliminado) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(eliminado);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al eliminar la peliculas por el id");
		}
	}
}
