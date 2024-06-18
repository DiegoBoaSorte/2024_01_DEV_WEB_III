package com.autobots.automanager.modelo;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Documento;

@Component
public class ClienteDocumentoSelecionador {
	public Cliente selecionar(List<Cliente> clientes, Documento documento) {
		Cliente selecionado = null;
		for (Cliente cliente : clientes) {
			for (Documento doc : cliente.getDocumentos()) {
				if (doc.getId() == documento.getId()) {
					selecionado = cliente;
				}
			}
		}
		return selecionado;
	}
}
