package com.autobots.automanager.adicionadores;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.MercadoriaControle;
import com.autobots.automanager.controles.TelefoneControle;
import com.autobots.automanager.entitades.Email;
import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.entitades.Telefone;
import com.autobots.automanager.entitades.Usuario;

@Component
public class AdicionadorLinkMercadoria implements AdicionadorLink<Mercadoria> {

	@Override
	public void adicionarLink(List<Mercadoria> lista) {
		for (Mercadoria mercadoria : lista) {
			long id = mercadoria.getId();
			Link linkProprioObterMercadoria = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(MercadoriaControle.class)
							.obterMercadoria(id))
					.withSelfRel();
			mercadoria.add(linkProprioObterMercadoria);
			
			Link linkProprioObterMercadorias = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(MercadoriaControle.class)
							.obterMercadorias())
					.withSelfRel();
			mercadoria.add(linkProprioObterMercadorias);
					
			Link linkProprioAtualizarMercadoria = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(MercadoriaControle.class)
							.atualizarMercadoria(mercadoria))
					.withSelfRel();
			mercadoria.add(linkProprioAtualizarMercadoria);
			
		}
	}
	
	@Override
	public void adicionarLink(Mercadoria objeto) {
		Link linkProprioObterMercadoria = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(MercadoriaControle.class)
						.obterMercadoria(objeto.getId()))
				.withRel("mercadoria");
		objeto.add(linkProprioObterMercadoria);
		
		Link linkProprioObterMercadorias = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(MercadoriaControle.class)
						.obterMercadorias())
				.withRel("mercadorias");
		objeto.add(linkProprioObterMercadorias);
		
		Link linkProprioAtualizarMercadoria = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(MercadoriaControle.class)
						.atualizarMercadoria(objeto))
				.withRel("atualizarMercadoria");
		objeto.add(linkProprioAtualizarMercadoria);
		
	}
	
	
	@Override
	public void adicionarLink(Mercadoria objeto, Empresa empresa) {
		Link linkProprioExcluirMercadoria = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(MercadoriaControle.class)
						.excluirMercadoria(objeto, empresa.getId()))
				.withRel("excluirMercadoria");
		objeto.add(linkProprioExcluirMercadoria);
		
	}

	@Override
	public void adicionarLink(Mercadoria objeto, Usuario usuario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void adicionarLink(Mercadoria objeto, Mercadoria mercadoria) {
		// TODO Auto-generated method stub
		
	}

}