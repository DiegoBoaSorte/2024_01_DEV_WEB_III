package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Telefone;
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

	@GetMapping("/documento/{id}")
	public Documento obterDocumento(@PathVariable long id) {
		List<Documento> documentos = repositorioDocumento.findAll();
		return selecionadorDocumento.selecionar(documentos, id);
	}

	@GetMapping("/documentos")
	public List<Documento> obterDocumentos() {
		List<Documento> documentos = repositorioDocumento.findAll();
		return documentos;
	}

	@PostMapping("/cadastro/{id}") 
	public void cadastrarDocumento(@RequestBody Documento documento, @PathVariable long id) {
		Cliente cliente = repositorioCliente.getById(id);
		cliente.getDocumentos().add(documento);
	    repositorioCliente.save(cliente);
		
	}

	@PutMapping("/atualizar") 
	public void atualizarDocumento(@RequestBody Documento atualizacao) {
		Documento documento = repositorioDocumento.getById(atualizacao.getId());
		DocumentoAtualizador atualizador = new DocumentoAtualizador();
		atualizador.atualizar(documento, atualizacao);
		repositorioDocumento.save(documento);
	}

	@DeleteMapping("/excluir/{id}") 
	public void excluirDocumento(@RequestBody Documento exclusao, @PathVariable long id) {
		Cliente cliente = repositorioCliente.getById(id);
		List<Documento> documentosCliente = cliente.getDocumentos();
		for (Documento documento : documentosCliente) {
	        if (documento.getId() == exclusao.getId()) {
	            documentosCliente.remove(documento);
	            break;
	        }
	    }
	    cliente.setDocumentos(documentosCliente);
		repositorioCliente.save(cliente);
	}
}
