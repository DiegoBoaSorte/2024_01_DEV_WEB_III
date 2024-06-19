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

import com.autobots.automanager.adicionadores.AdicionadorLinkUsuario;
import com.autobots.automanager.atualizadores.UsuarioAtualizador;
import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import com.autobots.automanager.selecionadores.UsuarioSelecionador;

@RestController
@RequestMapping("/usuario")
public class UsuarioControle {
	@Autowired
	private UsuarioRepositorio repositorioUsuario;
	@Autowired
	private UsuarioSelecionador selecionadorUsuario;
	@Autowired
	private RepositorioEmpresa repositorioEmpresa;
	@Autowired
	private AdicionadorLinkUsuario adicionadorLink;

	@GetMapping("/usuario/{id}")
	public ResponseEntity<Usuario> obterUsuario(@PathVariable long id) {
		List<Usuario> usuarios = repositorioUsuario.findAll();
		Usuario usuario = selecionadorUsuario.selecionar(usuarios, id);
		if (usuario == null) {
			ResponseEntity<Usuario> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(usuario);
			ResponseEntity<Usuario> resposta = new ResponseEntity<Usuario>(usuario, HttpStatus.FOUND);
			return resposta;
		}
	}

	@GetMapping("/usuarios")
	public ResponseEntity<List<Usuario>> obterUsuarios() {
		List<Usuario> usuarios = repositorioUsuario.findAll();
		if (usuarios.isEmpty()) {
			ResponseEntity<List<Usuario>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(usuarios);
			ResponseEntity<List<Usuario>> resposta = new ResponseEntity<>(usuarios, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	@SuppressWarnings("deprecation")
	@PostMapping("/cadastro/{id}")
	public ResponseEntity<?> cadastrarUsuario(@RequestBody Usuario usuario, @PathVariable long id) {
		HttpStatus status = HttpStatus.CONFLICT;
		if (usuario.getId() == null) {
			try {
				Empresa empresa = repositorioEmpresa.getById(id);
				Set<Usuario> usuariosEmpresa = empresa.getUsuarios();
				usuariosEmpresa.add(usuario);
				empresa.setUsuarios(usuariosEmpresa);
				repositorioEmpresa.save(empresa);
				status = HttpStatus.CREATED;
			} catch (Exception e) {
				status = HttpStatus.BAD_REQUEST;
			}
		}
		return new ResponseEntity<>(status);

	}

	@SuppressWarnings("deprecation")
	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizarUsuario(@RequestBody Usuario atualizacao) {
		HttpStatus status = HttpStatus.CONFLICT;
		Usuario usuario = repositorioUsuario.getById(atualizacao.getId());
		if (usuario != null) {
			UsuarioAtualizador atualizador = new UsuarioAtualizador();
			atualizador.atualizar(usuario, atualizacao);
			repositorioUsuario.save(usuario);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}

	@SuppressWarnings("deprecation")
	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<?> excluirUsuario(@RequestBody Usuario exclusao, @PathVariable long id) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Usuario usuario = repositorioUsuario.getById(exclusao.getId());
		if (usuario != null) {
			Empresa empresa = repositorioEmpresa.getById(id);
			Set<Usuario> usuarios = empresa.getUsuarios();
			for (Usuario usu: usuarios) {
				if (usuario.getId() == exclusao.getId()) {
					usuarios.remove(usu);
					break;
				}
			}
			empresa.setUsuarios(usuarios);
			repositorioEmpresa.save(empresa);
			status = HttpStatus.OK;
		}
		
		return new ResponseEntity<>(status);
	}
}
