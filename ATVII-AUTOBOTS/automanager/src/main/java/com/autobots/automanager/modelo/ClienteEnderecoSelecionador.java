package com.autobots.automanager.modelo;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Endereco;

@Component
public class ClienteEnderecoSelecionador {
	public Cliente selecionar(List<Cliente> clientes, Endereco endereco) {
		Cliente selecionado = null;
		for (Cliente cliente : clientes) {
			Endereco end = cliente.getEndereco();
			if (end.getId() == endereco.getId()) {
				selecionado = cliente;
			}
		}
		return selecionado;
	}
}
