package com.autobots.automanager.atualizadores;

import java.util.Set;

import com.autobots.automanager.entitades.Venda;
import com.autobots.automanager.modelos.VerificadoresNulo;

public class VendaAtualizador {
	private VerificadoresNulo verificador = new VerificadoresNulo();
	private static MercadoriaAtualizador mercadoriaAtualizador = new MercadoriaAtualizador();
	private static ServicoAtualizador servicoAtualizador = new ServicoAtualizador();
	
	public void atualizar(Venda venda, Venda atualizacao) {
		if (atualizacao != null) {
			if (!verificador.verificar(atualizacao.getCadastro())) {
				venda.setCadastro(atualizacao.getCadastro());
			}
			if (!verificador.verificar(atualizacao.getIdentificacao())) {
				venda.setIdentificacao(atualizacao.getIdentificacao());
			}
			if (!verificador.verificar(atualizacao.getCliente())) {
				venda.setCliente(atualizacao.getCliente());
			}
			if (!verificador.verificar(atualizacao.getFuncionario())) {
				venda.setFuncionario(atualizacao.getFuncionario());
			}
			if (!verificador.verificar(atualizacao.getVeiculo())) {
				venda.setVeiculo(atualizacao.getVeiculo());
			}
			
			
			
		}
	}

	public void atualizar(Set<Venda> vendas, Set<Venda> atualizacoes) {
		for (Venda atualizacao : atualizacoes) {
			for (Venda venda : vendas) {
				if (atualizacao.getId() != null) {
					if (atualizacao.getId() == venda.getId()) {
						atualizar(venda, atualizacao);
						mercadoriaAtualizador.atualizar(venda.getMercadorias(), atualizacao.getMercadorias());
						servicoAtualizador.atualizar(venda.getServicos(), atualizacao.getServicos());
						
					}
				}
			}
		}
	}
}