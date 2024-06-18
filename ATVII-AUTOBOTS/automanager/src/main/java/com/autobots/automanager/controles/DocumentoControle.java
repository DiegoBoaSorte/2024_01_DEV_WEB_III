package com.autobots.automanager.controles;

import java.util.List;

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

import com.autobots.automanager.adicionadores.AdicionadorLinkDocumento;
import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelo.ClienteDocumentoSelecionador;
import com.autobots.automanager.modelo.DocumentoAtualizador;
import com.autobots.automanager.modelo.DocumentoSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.DocumentoRepositorio;

@RestController
@RequestMapping("/documento")
public class DocumentoControle {
	@Autowired
	private DocumentoRepositorio repositorioDocumento;
	@Autowired
	private ClienteRepositorio repositorioCliente;
	@Autowired
	private DocumentoSelecionador selecionadorDocumento;
	@Autowired
	private ClienteDocumentoSelecionador selecionadorCliDocumento;
	@Autowired
	private AdicionadorLinkDocumento adicionadorLinkDocumento;

	@GetMapping("/documento/{id}")
	public ResponseEntity<Documento> obterDocumento(@PathVariable long id) {
		List<Documento> documentos = repositorioDocumento.findAll();
		Documento documento = selecionadorDocumento.selecionar(documentos, id);
		if (documento == null) {
			ResponseEntity<Documento> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLinkDocumento.adicionarLink(documento);
			List<Cliente> clientes = repositorioCliente.findAll();
			for (Documento doc : documentos) {
				Cliente	cliente = selecionadorCliDocumento.selecionar(clientes, documento);
				adicionadorLinkDocumento.adicionarLink(doc, cliente);
			}
			ResponseEntity<Documento> resposta = new ResponseEntity<Documento>(documento, HttpStatus.FOUND);
			return resposta;
		}
	}

	@GetMapping("/documentos")
	public ResponseEntity<List<Documento>> obterDocumentos() {
		List<Documento> documentos = repositorioDocumento.findAll();
		if (documentos.isEmpty()) {
			ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLinkDocumento.adicionarLink(documentos);
			List<Cliente> clientes = repositorioCliente.findAll();
			for (Documento documento : documentos) {
				Cliente	cliente = selecionadorCliDocumento.selecionar(clientes, documento);
				adicionadorLinkDocumento.adicionarLink(documento, cliente);
			}
			ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(documentos, HttpStatus.FOUND);
			return resposta;
		}
	}

	@PostMapping("/cadastro/{id}") 
	public ResponseEntity<?> cadastrarDocumento(@RequestBody Documento documento, @PathVariable long id) {
		HttpStatus status = HttpStatus.CONFLICT;
		if (documento.getId() == null) {
			Cliente cliente = repositorioCliente.getById(id);
			cliente.getDocumentos().add(documento);
			repositorioCliente.save(cliente);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<>(status);
	}

	@PutMapping("/atualizar") 
	public ResponseEntity<?> atualizarDocumento(@RequestBody Documento atualizacao) {
		HttpStatus status = HttpStatus.CONFLICT;
		Documento documento = repositorioDocumento.getById(atualizacao.getId());
		if (documento != null) {
			DocumentoAtualizador atualizador = new DocumentoAtualizador();
			atualizador.atualizar(documento, atualizacao);
			repositorioDocumento.save(documento);
			adicionadorLinkDocumento.adicionarLink(documento);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}
	
	@DeleteMapping("/excluir/{id}") 
	public ResponseEntity<?> excluirDocumento(@RequestBody Documento exclusao, @PathVariable long id) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Documento documento = repositorioDocumento.getById(exclusao.getId());
		if (documento != null) {
			Cliente cliente = repositorioCliente.getById(id);
			cliente.getDocumentos().remove(documento);
			repositorioCliente.save(cliente);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
		
	}
}
	