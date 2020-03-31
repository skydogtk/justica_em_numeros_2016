package br.jus.trt4.justica_em_numeros_2016.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import br.jus.trt4.justica_em_numeros_2016.auxiliar.Auxiliar;

/**
 * DTO que armazena informações de um processo, a partir da consulta do arquivo "01_consulta_processo.sql".
 * 
 * @author felipegiotto@gmail.com
 */
public class ProcessoDto {

	private String numeroProcesso;
	private int idProcesso;
	private int numeroInstancia;
	private boolean segredoJustica;
	private LocalDateTime dataAutuacao;
	private Double valorCausa;
	
	private ClasseJudicialDto classeJudicial;
	private OrgaoJulgadorDto orgaoJulgador;
	private ProcessoDto processoReferencia;
	
	public ProcessoDto() {
	}
	
	public ProcessoDto(ResultSet rsProcesso) throws SQLException {
		this.numeroProcesso = rsProcesso.getString("numero_completo_processo");
		this.idProcesso = rsProcesso.getInt("id_processo_trf");
		this.numeroInstancia = rsProcesso.getInt("nr_instancia");
		this.segredoJustica = "S".equals(rsProcesso.getString("in_segredo_justica"));
		this.dataAutuacao = rsProcesso.getTimestamp("dt_autuacao").toLocalDateTime();
		this.valorCausa = Auxiliar.getCampoDoubleOrNull(rsProcesso, "vl_causa");
		
		this.classeJudicial = new ClasseJudicialDto();
		this.classeJudicial.setCodigo(rsProcesso.getInt("cd_classe_judicial"));
		this.classeJudicial.setDescricao(rsProcesso.getString("ds_classe_judicial"));
		
		this.orgaoJulgador = new OrgaoJulgadorDto();
		this.orgaoJulgador.setIdMunicipioIBGE(rsProcesso.getInt("id_municipio_ibge"), rsProcesso.getString("ds_orgao_julgador"));
		
		if (rsProcesso.getString("id_proc_referencia") != null) {
			this.processoReferencia = new ProcessoDto();
			this.processoReferencia.idProcesso = Auxiliar.getCampoIntOrNull(rsProcesso, "id_proc_referencia");
			this.processoReferencia.numeroProcesso = rsProcesso.getString("nr_processo_ref");
			this.processoReferencia.classeJudicial = new ClasseJudicialDto();
			this.processoReferencia.classeJudicial.setCodigo(rsProcesso.getInt("cd_classe_judicial_ref"));
			this.processoReferencia.classeJudicial.setDescricao(rsProcesso.getString("ds_classe_judicial_ref"));
		}
	}
	
	public String getNumeroProcesso() {
		return numeroProcesso;
	}
	
	public String getNumeroProcessoSemSinais() {
		return numeroProcesso.replaceAll("[^0-9]", "");
	}
	
	public int getIdProcesso() {
		return idProcesso;
	}
	
	public int getNumeroInstancia() {
		return numeroInstancia;
	}
	
	public boolean isSegredoJustica() {
		return segredoJustica;
	}
	
	public LocalDateTime getDataAutuacao() {
		return dataAutuacao;
	}
	
	public Double getValorCausa() {
		return valorCausa;
	}
	
	public ClasseJudicialDto getClasseJudicial() {
		return classeJudicial;
	}
	
	public OrgaoJulgadorDto getOrgaoJulgador() {
		return orgaoJulgador;
	}
	
	public ProcessoDto getProcessoReferencia() {
		return processoReferencia;
	}
}