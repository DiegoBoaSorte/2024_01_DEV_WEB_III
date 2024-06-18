package com.autobots.automanager.selecionadores;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entitades.Email;
import com.autobots.automanager.entitades.Usuario;

@Component
public class UsuarioEmailSelecionador {
	public Usuario selecionar(List<Usuario> usuarios, Email email) {
		Usuario selecionado = null;
		for (Usuario usuario : usuarios) {
			for (Email e : usuario.getEmails()) {
				if (e.getId() == email.getId()) {
					selecionado = usuario;
				}
			}
		}
		return selecionado;
	}

}

