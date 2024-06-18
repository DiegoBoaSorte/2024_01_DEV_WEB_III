package com.autobots.automanager.atualizadores;

import java.util.Set;

import com.autobots.automanager.entitades.Credencial;
import com.autobots.automanager.modelos.VerificadoresNulo;

public class CredencialAtualizador {
	private VerificadoresNulo verificador = new VerificadoresNulo();

	public void atualizar(Credencial credencial, Credencial atualizacao) {
		if (atualizacao != null) {
			if (!verificador.verificar(atualizacao.getCriacao())) {
				credencial.setCriacao(atualizacao.getCriacao());
			}
			if (!verificador.verificar(atualizacao.getUltimoAcesso())) {
				credencial.setUltimoAcesso(atualizacao.getUltimoAcesso());
			}
			
		}
	}

	public void atualizar(Set<Credencial> credenciais, Set<Credencial> atualizacoes) {
		for (Credencial atualizacao : atualizacoes) {
			for (Credencial credencial : credenciais) {
				if (atualizacao.getId() != null) {
					if (atualizacao.getId() == credencial.getId()) {
						atualizar(credencial, atualizacao);
					}
				}
			}
		}
	}
}