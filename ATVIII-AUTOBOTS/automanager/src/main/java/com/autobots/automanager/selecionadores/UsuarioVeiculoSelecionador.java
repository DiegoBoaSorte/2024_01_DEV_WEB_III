package com.autobots.automanager.selecionadores;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.entitades.Veiculo;

@Component
public class UsuarioVeiculoSelecionador {
	public Usuario selecionar(List<Usuario> usuarios, Veiculo veiculo) {
		Usuario selecionado = null;
		for (Usuario usuario : usuarios) {
			for (Veiculo vei : usuario.getVeiculos()) {
				if (vei.getId() == veiculo.getId()) {
					selecionado = usuario;
				}
			}
		}
		return selecionado;
	}
}
