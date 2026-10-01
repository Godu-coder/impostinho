package br.com.mesquita.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mesquita.model.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

	List<Consulta> findByMedicoUsernameOrPacienteUsername(String usernameMedico, String usernamePaciente);
}
