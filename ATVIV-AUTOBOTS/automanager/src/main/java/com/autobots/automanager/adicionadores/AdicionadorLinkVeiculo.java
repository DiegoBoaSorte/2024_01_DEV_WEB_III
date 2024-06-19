package com.autobots.automanager.adicionadores;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.VeiculoControle;
import com.autobots.automanager.entitades.Email;
import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.entitades.Veiculo;

@Component
public class AdicionadorLinkVeiculo implements AdicionadorLink<Veiculo> {

	@Override
	public void adicionarLink(List<Veiculo> lista) {
		for (Veiculo veiculo : lista) {
			long id = veiculo.getId();
			Link linkProprioObterVeiculo = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(VeiculoControle.class)
							.obterVeiculo(id))
					.withSelfRel();
			veiculo.add(linkProprioObterVeiculo);
			
			Link linkProprioObterVeiculos = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(VeiculoControle.class)
							.obterVeiculos())
					.withSelfRel();
			veiculo.add(linkProprioObterVeiculos);
					
			Link linkProprioAtualizarVeiculo = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(VeiculoControle.class)
							.atualizarVeiculo(veiculo))
					.withSelfRel();
			veiculo.add(linkProprioAtualizarVeiculo);			
		}
	}

	@Override
	public void adicionarLink(Veiculo objeto) {
		Link linkProprioObterVeiculo = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(VeiculoControle.class)
						.obterVeiculo(objeto.getId()))
				.withRel("veiculo");
		objeto.add(linkProprioObterVeiculo);
		
		Link linkProprioObterVeiculos = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(VeiculoControle.class)
						.obterVeiculos())
				.withRel("veiculos");
		objeto.add(linkProprioObterVeiculos);
				
		Link linkProprioAtualizarVeiculo = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(VeiculoControle.class)
						.atualizarVeiculo(objeto))
				.withRel("atualizarVeiculo");
		objeto.add(linkProprioAtualizarVeiculo);
		
	}
	
	@Override
	public void adicionarLink(Veiculo objeto, Usuario usuario) {
		Link linkProprioExcluirVeiculo = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(VeiculoControle.class)
						.excluirVeiculo(objeto, usuario.getId()))
				.withRel("excluirVeiculo");
		objeto.add(linkProprioExcluirVeiculo);
		
	}

	@Override
	public void adicionarLink(Veiculo objeto, Empresa empresa) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void adicionarLink(Veiculo objeto, Mercadoria mercadoria) {
		// TODO Auto-generated method stub
		
	}



}
