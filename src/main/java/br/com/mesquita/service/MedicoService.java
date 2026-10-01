package br.com.mesquita.service;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.hibernate.PropertyValueException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.mesquita.model.Medico;
import br.com.mesquita.repository.MedicoRepository;
import io.micrometer.common.util.StringUtils;

@Service
public class MedicoService {

	private final MedicoRepository medicoRepository;
	private final PasswordEncoder passwordEncoder;

	public MedicoService(MedicoRepository medicoRepository, PasswordEncoder passwordEncoder) {
		this.medicoRepository = medicoRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public List<Medico> listar() {
		return medicoRepository.findAll();
	}

	public void demitir(Long id) {
		Optional<Medico> medico = medicoRepository.findById(id);
		medico.ifPresent(m -> {
			m.setAtivo(false);
			m.setDataDemissao(LocalDate.now(Clock.systemDefaultZone()));
			medicoRepository.save(medico.get());
		});
	}

	public Long salvar(Medico medico) throws PropertyValueException {
		Medico medicoBanco = buscarPorId(medico.getId());
		if (medico.getId() != null) {
			

			if (!medicoBanco.getAtivo()) { // lógica antiga
				medico.setAtivo(true);
			}
			if (StringUtils.isBlank(medico.getSenha())) { // médico está sendo atualizado e a senha não foi alterada
				medico.setSenha(medicoBanco.getSenha());
			} else {
				medico.setSenha(passwordEncoder.encode(medico.getSenha()));
			}
		} else {
			medico.setRole("ROLE_USUARIO");
			medico.setSenha(passwordEncoder.encode(medico.getSenha()));
			medico.setUsername(medicoBanco.getUsername());
		}

		if (StringUtils.isBlank(medico.getSenha())) {
			throw new PropertyValueException("Senha não pode estar vazia.", "Medico", "Senha");
		}

		return medicoRepository.save(medico).getId();
	}

	public Medico buscarPorId(Long id) {
		return medicoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Médico não encontrado: " + id));
	}

	public List<Medico> buscaPorStatus(boolean estaAtivo) {
		return medicoRepository.findAllByAtivo(estaAtivo);
	}
}