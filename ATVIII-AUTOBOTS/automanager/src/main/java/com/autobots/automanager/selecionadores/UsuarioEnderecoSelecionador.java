package com.autobots.automanager.selecionadores;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.entitades.Endereco;

@Component
public class UsuarioEnderecoSelecionador {
	public Usuario selecionar(List<Usuario> usuarios, Endereco endereco) {
		Usuario selecionado = null;
		for (Usuario usuario : usuarios) {
			Endereco end = usuario.getEndereco();
			if (end.getId() == endereco.getId()) {
				selecionado = usuario;
			}
		}
		return selecionado;
	}
}

