package com.autobots.automanager.atualizadores;

import com.autobots.automanager.entitades.CredencialUsuarioSenha;
import com.autobots.automanager.modelos.VerificadoresNulo;

public class CredencialUsuarioSenhaAtualizador {
	private VerificadoresNulo verificador = new VerificadoresNulo();

	public void atualizar(CredencialUsuarioSenha credencialUsuarioSenha, CredencialUsuarioSenha atualizacao) {
		if (atualizacao != null) {
			if (!verificador.verificar(atualizacao.getNomeUsuario())) {
				credencialUsuarioSenha.setNomeUsuario(atualizacao.getNomeUsuario());
			}
			if (!verificador.verificar(atualizacao.getSenha())) {
				credencialUsuarioSenha.setSenha(atualizacao.getSenha());
			}
		}
	}
}	
