package com.autobots.automanager.adicionadores;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.VeiculoControle;
import com.autobots.automanager.controles.VendaControle;
import com.autobots.automanager.entitades.Email;
import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.entitades.Venda;

@Component
public class AdicionadorLinkVenda implements AdicionadorLink<Venda> {

	@Override
	public void adicionarLink(List<Venda> lista) {
		for (Venda venda : lista) {
			long id = venda.getId();
			Link linkProprioObterVenda = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(VendaControle.class)
							.obterVenda(id))
					.withSelfRel();
			venda.add(linkProprioObterVenda);
			
			Link linkProprioObterVendas = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(VendaControle.class)
							.obterVendas())
					.withSelfRel();
			venda.add(linkProprioObterVendas);

			Link linkProprioAtualizarVenda = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(VendaControle.class)
							.atualizarVenda(venda))
					.withSelfRel();
			venda.add(linkProprioAtualizarVenda);
			
		}
	}

	@Override
	public void adicionarLink(Venda objeto) {
		Link linkProprioObterVenda = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(VendaControle.class)
						.obterVenda(objeto.getId()))
				.withRel("venda");
		objeto.add(linkProprioObterVenda);
		
		Link linkProprioObterVendas = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(VendaControle.class)
						.obterVendas())
				.withRel("venda");
		objeto.add(linkProprioObterVendas);
		
		Link linkProprioAtualizarVenda = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(VendaControle.class)
						.atualizarVenda(objeto))
				.withRel("atualizar");
		objeto.add(linkProprioAtualizarVenda);
		
	
		
	}
	
	@Override
	public void adicionarLink(Venda objeto, Empresa empresa) {
		Link linkProprioExcluirVenda = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(VendaControle.class)
						.excluirVenda(objeto, empresa.getId()))
				.withRel("excluir");
		objeto.add(linkProprioExcluirVenda);
		
	}

	@Override
	public void adicionarLink(Venda objeto, Usuario usuario) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void adicionarLink(Venda objeto, Mercadoria mercadoria) {
		// TODO Auto-generated method stub
		
	}
}
