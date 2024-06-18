package com.autobots.automanager.controles;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.adicionadores.AdicionadorLinkMercadoria;
import com.autobots.automanager.atualizadores.MercadoriaAtualizador;
import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.repositorios.MercadoriaRepositorio;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.selecionadores.MercadoriaSelecionador;

@RestController
@RequestMapping("/mercadoria")
public class MercadoriaControle {
	@Autowired
	private MercadoriaRepositorio repositorioMercadoria;
	@Autowired
	private RepositorioEmpresa repositorioEmpresa;
	@Autowired
	private MercadoriaSelecionador selecionadorMercadoria;
	@Autowired
	private AdicionadorLinkMercadoria adicionadorLinkMercadoria;
	
	

	@GetMapping("/mercadoria/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
	public ResponseEntity<Mercadoria> obterMercadoria(@PathVariable long id) {
		List<Mercadoria> mercadorias = repositorioMercadoria.findAll();
		Mercadoria mercadoria = selecionadorMercadoria.selecionar(mercadorias, id);
		if (mercadoria == null) {
			ResponseEntity<Mercadoria> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLinkMercadoria.adicionarLink(mercadoria);
			ResponseEntity<Mercadoria> resposta = new ResponseEntity<Mercadoria>(mercadoria, HttpStatus.FOUND);
			return resposta;
		} 
	}

	@GetMapping("/mercadorias")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
	public ResponseEntity<List<Mercadoria>> obterMercadorias() {
		List<Mercadoria> mercadorias = repositorioMercadoria.findAll();
		if (mercadorias.isEmpty()) {
			ResponseEntity<List<Mercadoria>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLinkMercadoria.adicionarLink(mercadorias);
			ResponseEntity<List<Mercadoria>> resposta = new ResponseEntity<>(mercadorias, HttpStatus.FOUND);
			return resposta;
		}
	}

	@PostMapping("/cadastro/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE')")
	public ResponseEntity<?> cadastrarMercadoria(@RequestBody Mercadoria mercadoria, @PathVariable long id) {
		HttpStatus status = HttpStatus.CONFLICT;
		if (mercadoria.getId() == null) {
			Empresa empresa = repositorioEmpresa.getById(id);
			empresa.getMercadorias().add(mercadoria);
		    repositorioEmpresa.save(empresa);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<>(status);

	}
	
	@PutMapping("/atualizar")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE')")
	public ResponseEntity<?> atualizarMercadoria(@RequestBody Mercadoria atualizacao) {
		HttpStatus status = HttpStatus.CONFLICT;
		Mercadoria mercadoria = repositorioMercadoria.getById(atualizacao.getId());
		if (mercadoria != null) {
			MercadoriaAtualizador atualizador = new MercadoriaAtualizador();
			atualizador.atualizar(mercadoria, atualizacao);
			repositorioMercadoria.save(mercadoria);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
		
	}

	@DeleteMapping("/excluir/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE')")
	public ResponseEntity<?> excluirMercadoria(@RequestBody Mercadoria exclusao, @PathVariable long id) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Mercadoria mercadoria = repositorioMercadoria.getById(exclusao.getId());
		if (mercadoria != null) {
			Empresa empresa = repositorioEmpresa.getById(id);
			Set<Mercadoria> mercadoriasEmpresa = empresa.getMercadorias();
			for (Mercadoria mercad : mercadoriasEmpresa) {
		        if (mercad.getId() == exclusao.getId()) {
		        	mercadoriasEmpresa.remove(mercad);
		            break;
		        }
		    }
		    empresa.setMercadorias(mercadoriasEmpresa);
			repositorioEmpresa.save(empresa);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
		
	}
}
