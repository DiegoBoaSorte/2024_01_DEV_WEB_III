package com.autobots.automanager.atualizadores;

import java.util.Set;

import com.autobots.automanager.entitades.Documento;
import com.autobots.automanager.modelos.VerificadoresNulo;

public class DocumentoAtualizador {
	private VerificadoresNulo verificador = new VerificadoresNulo();

	public void atualizar(Documento documento, Documento atualizacao) {
		if (atualizacao != null) {
			if (!verificador.verificarDocumento(atualizacao.getTipo())) {
				documento.setTipo(atualizacao.getTipo());
			}
			if (!verificador.verificar(atualizacao.getNumero())) {
				documento.setNumero(atualizacao.getNumero());
			}
		}
	}

	public void atualizar(Set<Documento> documentos, Set<Documento> atualizacoes) {
		for (Documento atualizacao : atualizacoes) {
			for (Documento documento : documentos) {
				if (atualizacao.getId() != null) {
					if (atualizacao.getId() == documento.getId()) {
						atualizar(documento, atualizacao);
					}
				}
			}
		}
	}
}
