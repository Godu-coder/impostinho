package br.com.mesquita.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.mesquita.model.Usuario;
import br.com.mesquita.service.AcessoUsuarioService;

@Controller
@RequestMapping("/usuario")
@PreAuthorize("hasRole('ADMIN')")
public class UsuarioController {

	AcessoUsuarioService acessoUsuarioService;

	public UsuarioController(AcessoUsuarioService acessoUsuarioService) {
		this.acessoUsuarioService = acessoUsuarioService;
	}

	@GetMapping("/listar")
	String listarUsuarios(Model model) {
		List<Usuario> listaUsuario = acessoUsuarioService.listar();
		model.addAttribute("listaU", listaUsuario);
		return "usuario/listar";
	}

	
	@GetMapping("/cadastro")
	String cadastrarUsuarios(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "usuario/cadastro";
	}

	
	@GetMapping("/editar")
	public String editarUsuario(@RequestParam Long id, Model model) {
		Usuario usuario = acessoUsuarioService.buscarPorId(id);
		model.addAttribute("usuario", usuario);
		return "usuario/cadastro";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/salvar")
	String salvarUsuarios(@ModelAttribute Usuario usuario) {
		acessoUsuarioService.salvar(usuario);
		return "redirect:/usuario/listar";
	}

}

