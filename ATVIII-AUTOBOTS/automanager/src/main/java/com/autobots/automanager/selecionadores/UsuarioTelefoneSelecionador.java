package com.autobots.automanager.selecionadores;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.entitades.Telefone;

@Component
public class UsuarioTelefoneSelecionador {
	public Usuario selecionar(List<Usuario> usuarios, Telefone telefone) {
		Usuario selecionado = null;
		for (Usuario usuario : usuarios) {
			for (Telefone tel : usuario.getTelefones()) {
				if (tel.getId() == telefone.getId()) {
					selecionado = usuario;
				}
			}
		}
		return selecionado;
	}

}

