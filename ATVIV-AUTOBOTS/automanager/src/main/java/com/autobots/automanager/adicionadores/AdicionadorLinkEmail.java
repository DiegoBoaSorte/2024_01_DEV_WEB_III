package com.autobots.automanager.adicionadores;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.EmailControle;
import com.autobots.automanager.entitades.Email;
import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.entitades.Usuario;

@Component
public class AdicionadorLinkEmail implements AdicionadorLink<Email> {

	@Override
	public void adicionarLink(List<Email> lista) {
		for (Email email : lista) {
			long id = email.getId();
			Link linkProprioObterEmail = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(EmailControle.class)
							.obterEmail(id))
					.withSelfRel();
			email.add(linkProprioObterEmail);
			
			Link linkProprioObterEmails = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(EmailControle.class)
							.obterEmails())
					.withSelfRel();
			email.add(linkProprioObterEmails);
					
			Link linkProprioAtualizarEmail = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(EmailControle.class)
							.atualizarEmail(email))
					.withSelfRel();
			email.add(linkProprioAtualizarEmail);
			
		}
	}
	
	@Override
	public void adicionarLink(Email objeto) {
		Link linkProprioObterEmail = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(EmailControle.class)
						.obterEmail(objeto.getId()))
				.withRel("email");
		objeto.add(linkProprioObterEmail);
		
		Link linkProprioObterEmails = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(EmailControle.class)
						.obterEmails())
				.withRel("emails");
		objeto.add(linkProprioObterEmails);
		
		Link linkProprioAtualizarEmail = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(EmailControle.class)
						.atualizarEmail(objeto))
				.withRel("atualizarTelefone");
		objeto.add(linkProprioAtualizarEmail);
		
	}
	
	
	@Override
	public void adicionarLink(Email objeto, Usuario usuario) {
		Link linkProprioExcluirEmail = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(EmailControle.class)
						.excluirEmail(objeto, usuario.getId()))
				.withRel("excluirTelefone");
		objeto.add(linkProprioExcluirEmail);
		
	}

	@Override
	public void adicionarLink(Email objeto, Empresa empresa) {
	}

	

	@Override
	public void adicionarLink(Email objeto, Mercadoria mercadoria) {		
	}

}
