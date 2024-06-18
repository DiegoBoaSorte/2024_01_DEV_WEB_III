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

import com.autobots.automanager.atualizadores.CredencialAtualizador;
import com.autobots.automanager.entitades.Credencial;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.repositorios.CredencialRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import com.autobots.automanager.selecionadores.CredencialSelecionador;

@RestController
@RequestMapping("/credencial")
public class CredencialControle {
	@Autowired
	private CredencialRepositorio repositorioCredencial;
	@Autowired
	private UsuarioRepositorio repositorioUsuario;
	@Autowired
	private CredencialSelecionador selecionadorCredencial;
	@GetMapping("/credencial/{id}")
	public ResponseEntity<Credencial> obterCredencial(@PathVariable long id) {
		List<Credencial> credenciais = repositorioCredencial.findAll();
		Credencial credencial = selecionadorCredencial.selecionar(credenciais, id);
		if (credencial == null) {
			ResponseEntity<Credencial> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			ResponseEntity<Credencial> resposta = new ResponseEntity<Credencial>(credencial, HttpStatus.FOUND);
			return resposta;
		} 
	}

	@GetMapping("/credenciais")
	public ResponseEntity<List<Credencial>> obterCredenciais() {
		List<Credencial> credenciais = repositorioCredencial.findAll();
		if (credenciais.isEmpty()) {
			ResponseEntity<List<Credencial>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			ResponseEntity<List<Credencial>> resposta = new ResponseEntity<>(credenciais, HttpStatus.FOUND);
			return resposta;
		}
	}

	@PostMapping("/cadastro/{id}") 
	public ResponseEntity<?> cadastrarCredencial(@RequestBody Credencial credencial, @PathVariable long id) {
		HttpStatus status = HttpStatus.CONFLICT;
		if (credencial.getId() == null) {
			Usuario usuario = repositorioUsuario.getById(id);
			usuario.getCredenciais().add(credencial);
		    repositorioUsuario.save(usuario);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<>(status);

	}
	
	@PutMapping("/atualizar") 
	public ResponseEntity<?> atualizarCredencial(@RequestBody Credencial atualizacao) {
		HttpStatus status = HttpStatus.CONFLICT;
		Credencial credencial = repositorioCredencial.getById(atualizacao.getId());
		if (credencial != null) {
			CredencialAtualizador atualizador = new CredencialAtualizador();
			atualizador.atualizar(credencial, atualizacao);
			repositorioCredencial.save(credencial);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
		
	}

	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<?> excluirCredencial(@RequestBody Credencial exclusao, @PathVariable long id) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Credencial credencial = repositorioCredencial.getById(exclusao.getId());
		if (credencial != null) {
			Usuario usuario = repositorioUsuario.getById(id);
			Set<Credencial> credenciaisUsuario = usuario.getCredenciais();
			for (Credencial cre : credenciaisUsuario) {
		        if (cre.getId() == exclusao.getId()) {
		            credenciaisUsuario.remove(cre);
		            break;
		        }
		    }
		    usuario.setCredenciais(credenciaisUsuario);
			repositorioUsuario.save(usuario);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
		
	}
}
