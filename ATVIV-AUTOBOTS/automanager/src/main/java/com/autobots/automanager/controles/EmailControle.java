package com.autobots.automanager.controles;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.adicionadores.AdicionadorLinkEmail;
import com.autobots.automanager.atualizadores.EmailAtualizador;
import com.autobots.automanager.entitades.Email;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.repositorios.EmailRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import com.autobots.automanager.selecionadores.EmailSelecionador;

@RestController
@RequestMapping("/email")
public class EmailControle {
	@Autowired
	private EmailRepositorio repositorioEmail;
	@Autowired
	private UsuarioRepositorio repositorioUsuario;
	@Autowired
	private EmailSelecionador selecionadorEmail;
	@Autowired
	private AdicionadorLinkEmail adicionadorLinkEmail;

	@GetMapping("/email/{id}")
	public ResponseEntity<Email> obterEmail(@PathVariable long id) {
		List<Email> emails = repositorioEmail.findAll();
		Email email = selecionadorEmail.selecionar(emails, id);
		if (email == null) {
			ResponseEntity<Email> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLinkEmail.adicionarLink(email);
			ResponseEntity<Email> resposta = new ResponseEntity<Email>(email, HttpStatus.FOUND);
			return resposta;
		} 
	}

	@GetMapping("/emails")
	public ResponseEntity<List<Email>> obterEmails() {
		List<Email> emails = repositorioEmail.findAll();
		if (emails.isEmpty()) {
			ResponseEntity<List<Email>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLinkEmail.adicionarLink(emails);
			ResponseEntity<List<Email>> resposta = new ResponseEntity<>(emails, HttpStatus.FOUND);
			return resposta;
		}
	}

	@PostMapping("/cadastro/{id}") 
	public ResponseEntity<?> cadastrarEmail(@RequestBody Email email, @PathVariable long id) {
		HttpStatus status = HttpStatus.CONFLICT;
		if (email.getId() == null) {
			Usuario usuario = repositorioUsuario.getById(id);
			usuario.getEmails().add(email);
		    repositorioUsuario.save(usuario);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<>(status);

	}
	
	@PutMapping("/atualizar") 
	public ResponseEntity<?> atualizarEmail(@RequestBody Email atualizacao) {
		HttpStatus status = HttpStatus.CONFLICT;
		Email email = repositorioEmail.getById(atualizacao.getId());
		if (email != null) {
			EmailAtualizador atualizador = new EmailAtualizador();
			atualizador.atualizar(email, atualizacao);
			repositorioEmail.save(email);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
		
	}

	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<?> excluirEmail(@RequestBody Email exclusao, @PathVariable long id) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Email email = repositorioEmail.getById(exclusao.getId());
		if (email != null) {
			Usuario usuario = repositorioUsuario.getById(id);
			Set<Email> emailsUsuario = usuario.getEmails();
			for (Email e : emailsUsuario) {
		        if (e.getId() == exclusao.getId()) {
		            emailsUsuario.remove(e);
		            break;
		        }
		    }
		    usuario.setEmails(emailsUsuario);
			repositorioUsuario.save(usuario);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
		
	}
}
