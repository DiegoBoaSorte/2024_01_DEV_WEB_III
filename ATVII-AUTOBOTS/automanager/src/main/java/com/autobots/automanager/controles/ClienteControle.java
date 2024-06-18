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

import com.autobots.automanager.adicionadores.AdicionadorLinkCliente;
import com.autobots.automanager.adicionadores.AdicionadorLinkDocumento;
import com.autobots.automanager.adicionadores.AdicionadorLinkEndereco;
import com.autobots.automanager.adicionadores.AdicionadorLinkTelefone;
import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelo.ClienteAtualizador;
import com.autobots.automanager.modelo.ClienteDocumentoSelecionador;
import com.autobots.automanager.modelo.ClienteSelecionador;
import com.autobots.automanager.modelo.ClienteTelefoneSelecionador;
import com.autobots.automanager.modelo.DocumentoSelecionador;
import com.autobots.automanager.modelo.TelefoneSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@RestController
@RequestMapping("/cliente")
public class ClienteControle {
	@Autowired
	private ClienteRepositorio repositorio;
	@Autowired
	private ClienteSelecionador selecionador;
	@Autowired
	private ClienteDocumentoSelecionador selecionadorCliDocumento;
	@Autowired
	private DocumentoSelecionador selecionadorDocumento;
	@Autowired
	private AdicionadorLinkCliente adicionadorLink;
	@Autowired
	private AdicionadorLinkDocumento adicionadorLinkDocumento;
	@Autowired
	private AdicionadorLinkEndereco adicionadorLinkEndereco;
	@Autowired
	private TelefoneSelecionador selecionadorTelefone;
	@Autowired
	private ClienteTelefoneSelecionador selecionadorCliTelefone;
	@Autowired
	private AdicionadorLinkTelefone adicionadorLinkTelefone;

	@GetMapping("/cliente/{id}")
	public ResponseEntity<Cliente> obterCliente(@PathVariable long id) {
		List<Cliente> clientes = repositorio.findAll();
		Cliente cliente = selecionador.selecionar(clientes, id);
		if (cliente == null) {
			ResponseEntity<Cliente> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(cliente);
			for (Documento doc : cliente.getDocumentos()) {
				Documento documento = selecionadorDocumento.selecionar(cliente.getDocumentos(), doc.getId());
				adicionadorLinkDocumento.adicionarLink(documento);
				cliente = selecionadorCliDocumento.selecionar(clientes, doc);
				adicionadorLinkDocumento.adicionarLink(doc, cliente);
			}
			if (!(cliente.getEndereco() == null)) {
				adicionadorLinkEndereco.adicionarLink(cliente.getEndereco());
				adicionadorLinkEndereco.adicionarLink(cliente.getEndereco(), cliente);
			}
			for (Telefone tel : cliente.getTelefones()) {
				Telefone telefone = selecionadorTelefone.selecionar(cliente.getTelefones(), tel.getId());
				adicionadorLinkTelefone.adicionarLink(telefone);
				cliente = selecionadorCliTelefone.selecionar(clientes, telefone);
				adicionadorLinkTelefone.adicionarLink(tel, cliente);
			}
			ResponseEntity<Cliente> resposta = new ResponseEntity<Cliente>(cliente, HttpStatus.FOUND);
			return resposta;
		}
	}

	@GetMapping("/clientes")
	public ResponseEntity<List<Cliente>> obterClientes() {
		List<Cliente> clientes = repositorio.findAll();
		if (clientes.isEmpty()) {
			ResponseEntity<List<Cliente>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(clientes);
			for (Cliente cliente : clientes) {		
				for (Documento doc : cliente.getDocumentos()) {
					Documento documento = selecionadorDocumento.selecionar(cliente.getDocumentos(), doc.getId());
					adicionadorLinkDocumento.adicionarLink(documento);
					cliente = selecionadorCliDocumento.selecionar(clientes, doc);
					adicionadorLinkDocumento.adicionarLink(doc, cliente);
				}
				if (!(cliente.getEndereco() == null)) {
					adicionadorLinkEndereco.adicionarLink(cliente.getEndereco());
					adicionadorLinkEndereco.adicionarLink(cliente.getEndereco(), cliente);
				}
				for (Telefone tel : cliente.getTelefones()) {
					Telefone telefone = selecionadorTelefone.selecionar(cliente.getTelefones(), tel.getId());
					adicionadorLinkTelefone.adicionarLink(telefone);
					cliente = selecionadorCliTelefone.selecionar(clientes, telefone);
					adicionadorLinkTelefone.adicionarLink(tel, cliente);
				}
			}
		}
		ResponseEntity<List<Cliente>> resposta = new ResponseEntity<>(clientes, HttpStatus.FOUND);
		return resposta;
	}


	@PostMapping("/cadastro")
	public ResponseEntity<?> cadastrarCliente(@RequestBody Cliente cliente) {
		HttpStatus status = HttpStatus.CONFLICT;
		if (cliente.getId() == null) {
			repositorio.save(cliente);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<>(status);

	}

	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizarCliente(@RequestBody Cliente atualizacao) {
		HttpStatus status = HttpStatus.CONFLICT;
		Cliente cliente = repositorio.getById(atualizacao.getId());
		if (cliente != null) {
			ClienteAtualizador atualizador = new ClienteAtualizador();
			atualizador.atualizar(cliente, atualizacao);
			repositorio.save(cliente);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}

	@DeleteMapping("/excluir")
	public ResponseEntity<?> excluirCliente(@RequestBody Cliente exclusao) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Cliente cliente = repositorio.getById(exclusao.getId());
		if (cliente != null) {
			repositorio.delete(cliente);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
	}
}
