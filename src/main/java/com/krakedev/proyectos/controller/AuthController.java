package com.krakedev.proyectos.controller;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krakedev.proyectos.services.equals.UsuarioService;
import com.krakedev.proyectos.services.equals.TokenBlacklistService;
import com.krakedev.proyectos.entidades.Usuarios;
import com.krakedev.proyectos.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final UsuarioService usuario;
	private final TokenBlacklistService blackList;

	public AuthController(UsuarioService usuarios, TokenBlacklistService blackList) {
		this.usuario = usuarios;
		this.blackList = blackList;
		
	}

	@PostMapping("/registrar")
	public ResponseEntity<?> registrar(@RequestBody Usuarios user) {
		try {
			Usuarios userNuevo = usuario.registrar(user);
			return ResponseEntity.status(HttpStatus.CREATED).body(user);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al registrar usuario" + e.getMessage());
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
		String username = credenciales.get("username");
		String password = credenciales.get("password");

		try {
			Usuarios autenticado = usuario.autenticar(username, password);
			String token = JwtUtil.generarToken(autenticado.getUsername(), autenticado.getRol());
			return ResponseEntity.ok(Map.of("token", token));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña incorrecta");
		}
	}
	
	@GetMapping("/perfil")
	public ResponseEntity<?> verPerfil() {
		Authentication auth= SecurityContextHolder.getContext().getAuthentication();
		String usuario = auth.getName();
		String rol = auth.getAuthorities().iterator().next().getAuthority();
		
		return ResponseEntity.ok(Map.of("Mensaje","Bienbenido al sistema protegido por Spring Security", "Usuario", usuario, "rol", rol, "status", "Authenticado correctamente"));
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestHeader(value = "Authorization", required = false) String authHeader) {
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);
			blackList.invalidarToken(token);
			return ResponseEntity.ok(Map.of("Mensaje", "Sesión cerrada exitosamente. Token invalidado"));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token no proporcionado");
		}
	}
}
