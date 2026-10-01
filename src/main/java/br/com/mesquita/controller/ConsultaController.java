package br.com.mesquita.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.mesquita.model.Consulta;
import br.com.mesquita.model.Medico;
import br.com.mesquita.model.Paciente;
import br.com.mesquita.service.ConsultaService;
import br.com.mesquita.service.MedicoService;
import br.com.mesquita.service.PacienteService;

@Controller
@RequestMapping("/consulta")
public class ConsultaController {

	ConsultaService consultaService;
	MedicoService medicoService;
	PacienteService pacienteService;
	
	public ConsultaController(ConsultaService consultaService, MedicoService medicoService, PacienteService pacienteService) {
		this.consultaService = consultaService;
		this.medicoService = medicoService;
		this.pacienteService = pacienteService;
	}
	
	@GetMapping("/listar")
	String listarConsultas(Model model, Authentication authentication){
		List<Consulta> listaConsulta = consultaService.listar(authentication);
		model.addAttribute("listaC", listaConsulta);
		return "consulta/listar";
	}
	
	@GetMapping("/cadastro")
	@PreAuthorize("hasAnyRole('ATENDENTE', 'ADMIN')")
	String cadastrarConsulta(Model model){
		List<Medico> listaMedico = medicoService.listar();
		List<Paciente> listaPaciente = pacienteService.listar();
		model.addAttribute("listaMedico", listaMedico);
		model.addAttribute("listaPaciente", listaPaciente);
		model.addAttribute("consulta", new Consulta());
		return "consulta/cadastro";
	}
	
	@PostMapping("/salvar")
	@PreAuthorize("hasAnyRole('ATENDENTE', 'ADMIN')")
	public String salvar(@ModelAttribute Consulta consulta, @RequestParam Long medicoId, @RequestParam Long pacienteId) {
		consultaService.salvar(consulta, medicoId, pacienteId);
		return "redirect:/consulta/listar";
	}
	
	@GetMapping("/ficha")
	public String ficha(Model model) {
		List<Paciente> listaPaciente = pacienteService.listar();
		model.addAttribute("listaP", listaPaciente);
	return "consulta/ficha";
	}
}
