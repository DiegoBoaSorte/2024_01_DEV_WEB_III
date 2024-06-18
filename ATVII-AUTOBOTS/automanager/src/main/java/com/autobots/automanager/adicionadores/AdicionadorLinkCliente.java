package com.autobots.automanager.adicionadores;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.ClienteControle;
import com.autobots.automanager.entidades.Cliente;

@Component
public class AdicionadorLinkCliente implements AdicionadorLink<Cliente> {

	@Override
	public void adicionarLink(List<Cliente> lista) {
		for (Cliente cliente : lista) {
			long id = cliente.getId();
			Link linkProprioObterCliente = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(ClienteControle.class)
							.obterCliente(id))
					.withSelfRel();
			cliente.add(linkProprioObterCliente);
			
			Link linkProprioObterClientes = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(ClienteControle.class)
							.obterClientes())
					.withSelfRel();
			cliente.add(linkProprioObterClientes);
					
			Link linkProprioAtualizarCliente = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(ClienteControle.class)
							.atualizarCliente(cliente))
					.withSelfRel();
			cliente.add(linkProprioAtualizarCliente);
			
			Link linkProprioExcluirCliente = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(ClienteControle.class)
							.excluirCliente(cliente))
					.withSelfRel();
			cliente.add(linkProprioExcluirCliente);	
			
		}
	}

	@Override
	public void adicionarLink(Cliente objeto) {
		Link linkProprioObterCliente = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(ClienteControle.class)
						.obterCliente(objeto.getId()))
				.withRel("cliente");
		objeto.add(linkProprioObterCliente);
		
		Link linkProprioObterClientes = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(ClienteControle.class)
						.obterClientes())
				.withRel("clientes");
		objeto.add(linkProprioObterClientes);
		
		Link linkProprioAtualizarCliente = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(ClienteControle.class)
						.atualizarCliente(objeto))
				.withRel("atualizar");
		objeto.add(linkProprioAtualizarCliente);
		
		Link linkProprioExcluirCliente = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(ClienteControle.class)
						.excluirCliente(objeto))
				.withRel("excluir");
		objeto.add(linkProprioExcluirCliente);
		
	}

	@Override
	public void adicionarLink(Cliente objeto, Cliente cliente) {
		// TODO Auto-generated method stub
		
	}


}
