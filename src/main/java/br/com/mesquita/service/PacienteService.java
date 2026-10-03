package br.com.mesquita.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.mesquita.model.Paciente;
import br.com.mesquita.repository.PacienteRepository;

import java.util.List;



@Service
public class PacienteService {

	private final PacienteRepository pacienteRepository;
	private final PasswordEncoder passwordEncoder;

	public PacienteService(PacienteRepository pacienteRepository, PasswordEncoder passwordEncoder) {
		this.pacienteRepository = pacienteRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public List<Paciente> listar() {
		return pacienteRepository.findAll();
	}

	public Long salvar(Paciente paciente) {
		
		if (paciente.getRole() == null || paciente.getRole().trim().isEmpty()) {
			paciente.setRole("ROLE_USUARIO");
		}
		
		if (paciente.getId() != null) {
			Paciente pacienteBanco = pacienteRepository.findById(paciente.getId()).orElse(null);
			
		  if(pacienteBanco != null) {
			  
			  if (paciente.getSenha() == null || paciente.getSenha().trim().isEmpty()) {
				  paciente.setSenha(pacienteBanco.getSenha());
			  } else {
				  paciente.setSenha(passwordEncoder.encode(paciente.getSenha()));
			  }
		  }
		} else {
			if (paciente.getSenha() != null && !paciente.getSenha().trim().isEmpty()) {
				paciente.setSenha(passwordEncoder.encode(paciente.getSenha()));
			}
		}
		
		return pacienteRepository.save(paciente).getId();
	}

	public Paciente buscarPorId(Long id) {
		return pacienteRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado: " + id));
	}
}