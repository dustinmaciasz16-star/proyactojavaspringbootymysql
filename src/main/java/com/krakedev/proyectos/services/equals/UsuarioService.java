package com.krakedev.proyectos.services.equals;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.krakedev.proyectos.entidades.Usuarios;
import com.krakedev.proyectos.repositories.UsuarioRepository;

@Service
public class UsuarioService {
	private final UsuarioRepository usuarioRepository;

	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	// Registrar un usuario
	public Usuarios registrar(Usuarios usuario) {

		String contrasena = BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt());

		usuario.setPassword(contrasena);

		return usuarioRepository.save(usuario);

	}

	// Autenticar un usuario
	public Usuarios autenticar(String username, String password) {

		Optional<Usuarios> usuarioEncontrado = usuarioRepository.findByUsername(username);

		if (usuarioEncontrado.isEmpty()) {
			return null;
		}

		Usuarios usuario = usuarioEncontrado.get();

		if (BCrypt.checkpw(password, usuario.getPassword())) {
			return usuario;
		}

		return null;
	}
}
