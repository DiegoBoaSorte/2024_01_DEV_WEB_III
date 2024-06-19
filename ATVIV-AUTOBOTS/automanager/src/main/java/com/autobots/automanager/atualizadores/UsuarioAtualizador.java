package com.autobots.automanager.atualizadores;

import java.util.Set;

import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.modelos.VerificadoresNulo;

public class UsuarioAtualizador {
	private VerificadoresNulo verificador = new VerificadoresNulo();
	private EnderecoAtualizador enderecoAtualizador = new EnderecoAtualizador();
	private DocumentoAtualizador documentoAtualizador = new DocumentoAtualizador();
	private TelefoneAtualizador telefoneAtualizador = new TelefoneAtualizador();
	private EmailAtualizador emailAtualizador = new EmailAtualizador();
	private CredencialAtualizador credencialAtualizador = new CredencialAtualizador();
	private MercadoriaAtualizador mercadoriaAtualizador = new MercadoriaAtualizador();
	private VendaAtualizador vendaAtualizador = new VendaAtualizador();

	private void atualizarDados(Usuario usuario, Usuario atualizacao) {
		if (!verificador.verificar(atualizacao.getNome())) {
			usuario.setNome(atualizacao.getNome());
		}
		if (!verificador.verificar(atualizacao.getNomeSocial())) {
			usuario.setNomeSocial(atualizacao.getNomeSocial());
		}
		if (!(verificador.verificarPerfil(atualizacao.getPerfis()))) {
			usuario.setPerfis(atualizacao.getPerfis());
		}
		
	}

	public void atualizar(Usuario usuario, Usuario atualizacao) {
		atualizarDados(usuario, atualizacao);
		telefoneAtualizador.atualizar(usuario.getTelefones(), atualizacao.getTelefones());
		enderecoAtualizador.atualizar(usuario.getEndereco(), atualizacao.getEndereco());
		documentoAtualizador.atualizar(usuario.getDocumentos(), atualizacao.getDocumentos());
		emailAtualizador.atualizar(usuario.getEmails(), atualizacao.getEmails());
		credencialAtualizador.atualizar(usuario.getCredenciais(), atualizacao.getCredenciais());
		mercadoriaAtualizador.atualizar(usuario.getMercadorias(), atualizacao.getMercadorias());
		vendaAtualizador.atualizar(usuario.getVendas(), atualizacao.getVendas());
	}
	
	public void atualizar(Set<Usuario> usuarios, Set<Usuario> atualizacoes) {
		for (Usuario atualizacao : atualizacoes) {
			for (Usuario usuario : usuarios) {
				if (atualizacao.getId() != null) {
					if (atualizacao.getId() == usuario.getId()) {
						atualizar(usuario, atualizacao);
					}
				}
			}
		}
	}
}
