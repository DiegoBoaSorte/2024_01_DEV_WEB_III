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

import com.autobots.automanager.adicionadores.AdicionadorLinkEndereco;
import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelo.ClienteEnderecoSelecionador;
import com.autobots.automanager.modelo.EnderecoAtualizador;
import com.autobots.automanager.modelo.EnderecoSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.EnderecoRepositorio;

@RestController
@RequestMapping("/endereco")
public class EnderecoControle {
	@Autowired
	private EnderecoRepositorio repositorioEndereco;
	@Autowired
	private ClienteRepositorio repositorioCliente;
	@Autowired
	private EnderecoSelecionador selecionadorEndereco;
	@Autowired
	private ClienteEnderecoSelecionador selecionadorCliEndereco;
	@Autowired
	private AdicionadorLinkEndereco adicionadorLinkEndereco;
	
	@GetMapping("/endereco/{id}")
	public ResponseEntity<Endereco> obterEndereco(@PathVariable long id) {
		List<Endereco> enderecos = repositorioEndereco.findAll();
		Endereco endereco = selecionadorEndereco.selecionar(enderecos, id);
		if (endereco == null) {
			ResponseEntity<Endereco> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLinkEndereco.adicionarLink(endereco);
			List<Cliente> clientes = repositorioCliente.findAll();
			Cliente	cliente = selecionadorCliEndereco.selecionar(clientes, endereco);
			adicionadorLinkEndereco.adicionarLink(endereco, cliente);
			ResponseEntity<Endereco> resposta = new ResponseEntity<Endereco>(endereco, HttpStatus.FOUND);
			return resposta;
		}
		
	}

	@GetMapping("/enderecos")
	public ResponseEntity<List<Endereco>> obterEnderecos() {
		List<Endereco> enderecos = repositorioEndereco.findAll();
		if (enderecos.isEmpty()) {
			ResponseEntity<List<Endereco>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLinkEndereco.adicionarLink(enderecos);
			List<Cliente> clientes = repositorioCliente.findAll();
			for (Endereco endereco : enderecos) {
				Cliente	cliente = selecionadorCliEndereco.selecionar(clientes, endereco);
				adicionadorLinkEndereco.adicionarLink(endereco, cliente);
			}
			ResponseEntity<List<Endereco>> resposta = new ResponseEntity<>(enderecos, HttpStatus.FOUND);
			return resposta;
		}
		
	}

	@PostMapping("/cadastro/{id}") 
	public ResponseEntity<?> cadastrarEndereco(@RequestBody Endereco endereco, @PathVariable long id) {
		HttpStatus status = HttpStatus.CONFLICT;
		if (endereco.getId() == null) {
			Cliente cliente = repositorioCliente.getById(id);
			cliente.setEndereco(endereco);
			repositorioCliente.save(cliente);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<>(status);
		 
	}

	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizarEndereco(@RequestBody Endereco atualizacao) {
		HttpStatus status = HttpStatus.CONFLICT;
		Endereco endereco = repositorioEndereco.getById(atualizacao.getId());
		if (endereco != null) {
			EnderecoAtualizador atualizador = new EnderecoAtualizador();
			atualizador.atualizar(endereco, atualizacao);
			repositorioEndereco.save(endereco);
			adicionadorLinkEndereco.adicionarLink(endereco);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status); 
	}

	@DeleteMapping("/excluir/{id}") 
	public ResponseEntity<?> excluirEndereco(@PathVariable long id) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Cliente cliente = repositorioCliente.getById(id);
		if (cliente.getEndereco()!= null) {
		    cliente.setEndereco(null);
		    repositorioCliente.save(cliente);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);

	}
}
