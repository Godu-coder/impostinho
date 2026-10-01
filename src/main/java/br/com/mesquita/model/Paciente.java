package br.com.mesquita.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;

@Entity
public class Paciente extends Usuario {

	private String cpf;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate dataNascimento;

	private String sexo;
	private String endereco;
	private String telefone;
	private String alergia;
    private String historico;
	private String medicamento;

	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}

	public String getMedicamento() {
		return medicamento;
	}

	public void setMedicamento(String medicamento) {
		this.medicamento = medicamento;
	}

	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	public String getSexo() {
		return sexo;
	}
	
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	public String getEndereco() {
		return endereco;
	}
	
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
	public void setTelefone(String telefone) {
		if (telefone != null) {
			this.telefone = telefone.replaceAll("\\D", "");
		} else {
			this.telefone = null;
		}
	}
	
	public String getAlergia() {
		return alergia;
	}
	
	public void setAlergia(String alergia) {
		this.alergia = alergia;
	}
	
	public String getCpfFormatada() {
		return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
	}
	
	public String getDataNasc() {
		if (dataNascimento == null) {
			return "N/A";
		}
		return dataNascimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
	
	public String getDataNascInput() {
		if (dataNascimento == null) {
			return "00/00/0000";
		}
		return dataNascimento.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
	}
	
	public String getFoneFormatado() {
		if (this.telefone == null || this.telefone.length() != 13) {
			return this.telefone;
		}
		return this.telefone.replaceAll("^(\\d{2})(\\d{2})(\\d{5})(\\d{4})$", "+$1 ($2) $3-$4");
	}
}