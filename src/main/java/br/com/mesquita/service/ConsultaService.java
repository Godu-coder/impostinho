package br.com.mesquita.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.mesquita.model.Consulta;
import br.com.mesquita.model.Medico;
import br.com.mesquita.model.Paciente;
import br.com.mesquita.repository.ConsultaRepository;

@Service
public class ConsultaService {

	private ConsultaRepository consultaRepository;
	private MedicoService medicoService;
	private PacienteService pacienteService;
	
	public ConsultaService(ConsultaRepository consultaRepository, MedicoService medicoService, PacienteService pacienteService) {
		this.consultaRepository = consultaRepository;
		this.medicoService = medicoService;
		this.pacienteService = pacienteService;
	}
	
	public List<Consulta> listar(Authentication authentication) {
		boolean verTodas = authentication.getAuthorities().stream()
				.anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")
						|| a.getAuthority().equals("ROLE_ATENDENTE"));

		if (verTodas) {
			return consultaRepository.findAll();
		}

		String username = authentication.getName();
		return consultaRepository.findByMedicoUsernameOrPacienteUsername(username, username);
	}
	
	public Long salvar(Consulta consulta, Long medicoId, Long pacienteId) {
		Medico medico = medicoService.buscarPorId(medicoId);
		Paciente paciente = pacienteService.buscarPorId(pacienteId);
		
		consulta.setMedico(medico);		
		consulta.setPaciente(paciente);

		return consultaRepository.save(consulta).getId();
	}
}
