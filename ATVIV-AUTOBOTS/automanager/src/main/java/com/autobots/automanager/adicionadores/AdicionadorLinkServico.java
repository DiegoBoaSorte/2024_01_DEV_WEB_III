package com.autobots.automanager.adicionadores;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.MercadoriaControle;
import com.autobots.automanager.controles.ServicoControle;
import com.autobots.automanager.entitades.Email;
import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.entitades.Servico;
import com.autobots.automanager.entitades.Usuario;

@Component
public class AdicionadorLinkServico implements AdicionadorLink<Servico> {

	@Override
	public void adicionarLink(List<Servico> lista) {
		for (Servico servico : lista) {
			long id = servico.getId();
			Link linkProprioObterServico = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(ServicoControle.class)
							.obterServico(id))
					.withSelfRel();
			servico.add(linkProprioObterServico);
			
			Link linkProprioObterServicos = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(ServicoControle.class)
							.obterServicos())
					.withSelfRel();
			servico.add(linkProprioObterServicos);

			Link linkProprioAtualizarServico = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(ServicoControle.class)
							.atualizarServico(servico))
					.withSelfRel();
			servico.add(linkProprioAtualizarServico);
			
		}
	}

	@Override
	public void adicionarLink(Servico objeto) {
		Link linkProprioObterServico = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(ServicoControle.class)
						.obterServico(objeto.getId()))
				.withRel("servico");
		objeto.add(linkProprioObterServico);
		
		Link linkProprioObterServicos = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(ServicoControle.class)
						.obterServicos())
				.withRel("servicos");
		objeto.add(linkProprioObterServicos);
			
		Link linkProprioAtualizarServico = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(ServicoControle.class)
						.atualizarServico(objeto))
				.withRel("atualizar");
		objeto.add(linkProprioAtualizarServico);
		
	}
	
	@Override
	public void adicionarLink(Servico objeto, Empresa empresa) {
		Link linkProprioExcluirServico = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(ServicoControle.class)
						.excluirServico(objeto, empresa.getId()))
				.withRel("excluirServico");
		objeto.add(linkProprioExcluirServico);
		
	}

	@Override
	public void adicionarLink(Servico objeto, Usuario usuario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void adicionarLink(Servico objeto, Mercadoria mercadoria) {
		// TODO Auto-generated method stub
		
	}
}
