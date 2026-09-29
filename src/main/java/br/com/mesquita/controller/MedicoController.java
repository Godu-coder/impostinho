package br.com.mesquita.controller;

import java.util.List;

import org.hibernate.PropertyValueException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.mesquita.model.Medico;
import br.com.mesquita.service.MedicoService;

@Controller
@RequestMapping("/medico")
@PreAuthorize("hasAnyRole('ATENDENTE', 'ADMIN')")
public class MedicoController {
	
	MedicoService medicoService;
	
	public MedicoController(MedicoService medicoService) {
		this.medicoService = medicoService;
	}
	
	@GetMapping("/listar")
	String listarMedicos(Model model) {
		List<Medico> listaMedico = medicoService.buscaPorStatus(true);
		model.addAttribute("listaM", listaMedico);
		model.addAttribute("medico", new Medico());
		return "medico/listar";
	}
	
	@GetMapping("/listar/demitidos")
	String listarMedicosInativos(Model model) {
		List<Medico> listaMedico = medicoService.buscaPorStatus(false);
		model.addAttribute("listaM", listaMedico);
		model.addAttribute("medico", new Medico());
		return "medico/listarDemitidos";
	}
	
	@GetMapping("/cadastro")
	
	String cadastrarMedicos(Model model) {
		model.addAttribute("medico", new Medico());
		return "medico/cadastro";
	}
	
	@PostMapping("salvar") 
	String cadastrarMedicos(@ModelAttribute Medico medico, Model model) {
		try {
			medicoService.salvar(medico);
		} catch(PropertyValueException _) {
			model.addAttribute("medico", medico);
			model.addAttribute("mensagemErro", "Senha inválida.");
			return "medico/cadastro";
		}
	   return "redirect:/medico/listar";
	}
	
	@GetMapping("/editar")
	public String editarMedico(@RequestParam Long id, Model model) {
	    Medico medico = medicoService.buscarPorId(id);
	    model.addAttribute("medico", medico);
	    return "medico/editar"; 

	}

	@GetMapping("/demitir")
	String demitirMedicos(@RequestParam("id") Long id) {
       medicoService.demitir(id);
	   return "redirect:/medico/listar";
	}
}