package br.com.mesquita.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.mesquita.model.Consulta;
import br.com.mesquita.repository.ConsultaRepository;
import br.com.mesquita.security.AcessoUsuario;

@Service
public class ConsultaService {

	private ConsultaRepository consultaRepository;
	
	public ConsultaService(ConsultaRepository consultaRepository) {
		this.consultaRepository = consultaRepository;
	}
	
	public List<Consulta> listar(AcessoUsuario usuario, boolean passadas) {
		List<Consulta> lista;

		if (usuario.isAdmin() || usuario.isAtendente()) {
			lista = consultaRepository.findAll();
		} else {
			lista = consultaRepository.findByMedicoIdOrPacienteId(usuario.getId(), usuario.getId());
		}

		LocalDateTime agora = LocalDateTime.now();

		return lista.stream()
				.filter(c -> passadas ? c.getHorario().isBefore(agora) : !c.getHorario().isBefore(agora))
				.toList();
	}
	
	public Long salvar(Consulta consulta) {
		LocalDateTime horarioatual = LocalDateTime.now();
		if(consulta.getHorario() == null|| horarioatual.isAfter(consulta.getHorario())) {
			throw new IllegalArgumentException("A data da consulta está incorreta");
		}
		return consultaRepository.save(consulta).getId();
	}
}
