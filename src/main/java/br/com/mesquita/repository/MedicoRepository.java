package br.com.mesquita.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mesquita.model.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
	
	//Um findAll com um limite determinado na controller
	Page<Medico> findAll(Pageable pagina);
	
	public List<Medico> findAllByAtivo(boolean ativo); 
}

