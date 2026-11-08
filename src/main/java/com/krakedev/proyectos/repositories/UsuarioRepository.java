package com.krakedev.proyectos.repositories;
import com.krakedev.proyectos.entidades.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuarios, Integer>{
	Optional<Usuarios> findByUsername(String username);
}
