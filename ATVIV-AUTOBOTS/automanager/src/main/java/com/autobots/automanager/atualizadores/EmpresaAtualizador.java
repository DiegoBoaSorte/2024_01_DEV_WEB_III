package com.autobots.automanager.atualizadores;

import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.modelos.VerificadoresNulo;

public class EmpresaAtualizador {
	private VerificadoresNulo verificador = new VerificadoresNulo();
	private EnderecoAtualizador enderecoAtualizador = new EnderecoAtualizador();
	private TelefoneAtualizador telefoneAtualizador = new TelefoneAtualizador();
	private MercadoriaAtualizador mercadoriaAtualizador = new MercadoriaAtualizador();
	private VendaAtualizador vendaAtualizador = new VendaAtualizador();
	private UsuarioAtualizador usuarioAtualizador = new UsuarioAtualizador();
	private ServicoAtualizador servicoAtualizador = new ServicoAtualizador();
	
	public void atualizar(Empresa empresa, Empresa atualizacao) {
		if (atualizacao != null) {
			if (!verificador.verificar(atualizacao.getRazaoSocial())) {
				empresa.setRazaoSocial(atualizacao.getRazaoSocial());
			}
			if (!verificador.verificar(atualizacao.getNomeFantasia())) {
				empresa.setNomeFantasia(atualizacao.getNomeFantasia());
			}
			if (!verificador.verificar(atualizacao.getCadastro())) {
				empresa.setCadastro(atualizacao.getCadastro());
			}
		}
	}

	public void atualizarInformacoes(Empresa empresa, Empresa atualizacao) {
		atualizar(empresa, atualizacao);
		telefoneAtualizador.atualizar(empresa.getTelefones(), atualizacao.getTelefones());
		enderecoAtualizador.atualizar(empresa.getEndereco(), atualizacao.getEndereco());
		usuarioAtualizador.atualizar(empresa.getUsuarios(), atualizacao.getUsuarios());
		mercadoriaAtualizador.atualizar(empresa.getMercadorias(), atualizacao.getMercadorias());
		servicoAtualizador.atualizar(empresa.getServicos(), atualizacao.getServicos());
		vendaAtualizador.atualizar(empresa.getVendas(), atualizacao.getVendas());
	}
}