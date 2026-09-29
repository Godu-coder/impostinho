package br.com.mesquita.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import org.thymeleaf.util.StringUtils;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;

@Entity
public class Medico extends Usuario {

	@ElementCollection
	@CollectionTable(
			name = "especialidade",
			joinColumns = @JoinColumn(name = "medico_id")
			)
	@Column(name = "nome_especialidade")
	private Set<String> especialidade;

	private String cpf;
	private LocalDate dataAdmissao;
	private LocalDate dataDemissao;

	public Set<String> getEspecialidade() {
		return especialidade;
	}
	
	public void setEspecialidade(Set<String> especialidade) {
		this.especialidade = especialidade;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public LocalDate getDataAdmissao() {
		return dataAdmissao;
	}
	
	public String getDataAdmissaoFormatada() {
		if (dataAdmissao == null) {
			return "N/A";
		}
		return dataAdmissao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
	
	public void setDataAdmissao(LocalDate dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}
	
	public LocalDate getDataDemissao() {
		return dataDemissao;
	}
	
	public String getDataDemissaoFormatada() {
		if (dataDemissao == null) {
			return "N/A";
		}
		return dataDemissao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
	
	public void setDataDemissao(LocalDate dataDemissao) {
		this.dataDemissao = dataDemissao;
	}
	
	public String getCpfFormatada() {
		return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
	}

	public boolean equals(Medico medico) {
		return StringUtils.equals(this.getCpf(), medico.getCpf());
	}
}
