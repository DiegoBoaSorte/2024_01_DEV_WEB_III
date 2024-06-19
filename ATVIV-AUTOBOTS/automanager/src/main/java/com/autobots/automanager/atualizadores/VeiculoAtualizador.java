package com.autobots.automanager.atualizadores;

import java.util.List;

import com.autobots.automanager.entitades.Veiculo;
import com.autobots.automanager.modelos.VerificadoresNulo;

public class VeiculoAtualizador {
	private VerificadoresNulo verificador = new VerificadoresNulo();
	private static VendaAtualizador vendaAtualizador = new VendaAtualizador();
	
	public void atualizar(Veiculo veiculo, Veiculo atualizacao) {
		if (atualizacao != null) {
			if (!verificador.verificar(atualizacao.getTipo())) {
				veiculo.setTipo(atualizacao.getTipo());
			}
			if (!verificador.verificar(atualizacao.getModelo())) {
				veiculo.setModelo(atualizacao.getModelo());
			}
			if (!verificador.verificar(atualizacao.getPlaca())) {
				veiculo.setPlaca(atualizacao.getPlaca());
			}
			if (!verificador.verificar(atualizacao.getProprietario())) {
				veiculo.setProprietario(atualizacao.getProprietario());
			}
			
			
			
		}
	}

	public void atualizar(List<Veiculo> veiculos, List<Veiculo> atualizacoes) {
		for (Veiculo atualizacao : atualizacoes) {
			for (Veiculo veiculo : veiculos) {
				if (atualizacao.getId() != null) {
					if (atualizacao.getId() == veiculo.getId()) {
						atualizar(veiculo, atualizacao);
						vendaAtualizador.atualizar(veiculo.getVendas(), atualizacao.getVendas());
						
					}
				}
			}
		}
	}
}