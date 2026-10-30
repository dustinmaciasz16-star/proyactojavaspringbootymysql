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

import com.krakedev.proyectos.entidades.Tarea;
import com.krakedev.proyectos.services.TareaService;

@RestController
@RequestMapping("/api/tareas")
public class tareaController {

	private final TareaService service;

	public tareaController(TareaService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<?> guardar(@RequestBody Tarea tarea) {
		try {
			Tarea guardada = service.guardar(tarea);

			return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al guardar tarea: " + e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<?> listar() {
		try {
			List<Tarea> tareas = service.listar();
			return ResponseEntity.ok(tareas);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al listar tareas: " + e.getMessage());
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> buscar(@PathVariable int id) {
		try {
			Tarea tarea = service.buscar(id);

			if (tarea == null) {
				return ResponseEntity.notFound().build();
			}

			return ResponseEntity.ok(tarea);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al buscar tarea: " + e.getMessage());
		}
	}

	@PutMapping("/id")
	public ResponseEntity<?> actualizar(@PathVariable int id, @RequestBody Tarea tarea) {
		try {
			Tarea actualizado = service.actualizar(id, tarea);
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
