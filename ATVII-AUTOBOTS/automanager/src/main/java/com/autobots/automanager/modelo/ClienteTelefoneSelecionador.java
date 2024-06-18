package com.autobots.automanager.modelo;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Telefone;

@Component
public class ClienteTelefoneSelecionador {
	public Cliente selecionar(List<Cliente> clientes, Telefone telefone) {
		Cliente selecionado = null;
		for (Cliente cliente : clientes) {
			for (Telefone tel : cliente.getTelefones()) {
				if (tel.getId() == telefone.getId()) {
					selecionado = cliente;
				}
			}
		}
		return selecionado;
	}

}

	
