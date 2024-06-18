package com.autobots.automanager.selecionadores;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.entitades.Documento;

@Component
public class UsuarioDocumentoSelecionador {
	public Usuario selecionar(List<Usuario> usuarios, Documento documento) {
		Usuario selecionado = null;
		for (Usuario usuario : usuarios) {
			for (Documento doc : usuario.getDocumentos()) {
				if (doc.getId() == documento.getId()) {
					selecionado = usuario;
				}
			}
		}
		return selecionado;
	}
}
