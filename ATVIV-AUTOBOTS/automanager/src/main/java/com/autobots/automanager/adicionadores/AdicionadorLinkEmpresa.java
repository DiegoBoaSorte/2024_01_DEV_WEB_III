package com.autobots.automanager.adicionadores;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.EmpresaControle;
import com.autobots.automanager.entitades.Email;
import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.entitades.Usuario;

@Component
public class AdicionadorLinkEmpresa implements AdicionadorLink<Empresa> {

	@Override
	public void adicionarLink(List<Empresa> lista) {
		for (Empresa empresa : lista) {
			long id = empresa.getId();
			Link linkProprioObterEmpresa = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(EmpresaControle.class)
							.obterEmpresa(id))
					.withSelfRel();
			empresa.add(linkProprioObterEmpresa);
			
			Link linkProprioObterEmpresas = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(EmpresaControle.class)
							.obterEmpresas())
					.withSelfRel();
			empresa.add(linkProprioObterEmpresas);
					
			Link linkProprioAtualizarEmpresa = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(EmpresaControle.class)
							.atualizarEmpresa(empresa))
					.withSelfRel();
			empresa.add(linkProprioAtualizarEmpresa);
			
		}
	}

	@Override
	public void adicionarLink(Empresa objeto) {
		Link linkProprioObterEmpresa = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(EmpresaControle.class)
						.obterEmpresa(objeto.getId()))
				.withRel("empresa");
		objeto.add(linkProprioObterEmpresa);
		
		Link linkProprioObterEmpresas = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(EmpresaControle.class)
						.obterEmpresas())
				.withRel("empresas");
		objeto.add(linkProprioObterEmpresas);
		
		Link linkProprioAtualizarEmpresa = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(EmpresaControle.class)
						.atualizarEmpresa(objeto))
				.withRel("atualizar");
		objeto.add(linkProprioAtualizarEmpresa);
		
		Link linkProprioExcluirEmpresa = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(EmpresaControle.class)
						.excluirEmpresa(objeto))
				.withRel("excluir");
		objeto.add(linkProprioExcluirEmpresa);
		
	}

	@Override
	public void adicionarLink(Empresa objeto, Usuario usuario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void adicionarLink(Empresa objeto, Empresa empresa) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void adicionarLink(Empresa objeto, Mercadoria mercadoria) {
		// TODO Auto-generated method stub
		
	}



}