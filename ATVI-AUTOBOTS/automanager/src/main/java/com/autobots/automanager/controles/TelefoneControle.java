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
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelo.TelefoneAtualizador;
import com.autobots.automanager.modelo.TelefoneSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.TelefoneRepositorio;

@RestController
@RequestMapping("/telefone")
public class TelefoneControle {
	@Autowired
	private TelefoneRepositorio repositorioTelefone;
	@Autowired
	private ClienteRepositorio repositorioCliente;
	@Autowired
	private TelefoneSelecionador selecionadorTelefone;

	@GetMapping("/telefone/{id}")
	public Telefone obterTelefone(@PathVariable long id) {
		List<Telefone> telefones = repositorioTelefone.findAll();
		return selecionadorTelefone.selecionar(telefones, id);
	}

	@GetMapping("/telefones")
	public List<Telefone> obterTelefones() {
		List<Telefone> telefones = repositorioTelefone.findAll();
		return telefones;
	}

	@PostMapping("/cadastro/{id}") 
	public void cadastrarTelefone(@RequestBody Telefone telefone, @PathVariable long id) {
		Cliente cliente = repositorioCliente.getById(id);
		cliente.getTelefones().add(telefone);
	    repositorioCliente.save(cliente);
	}
	
	@PutMapping("/atualizar") 
	public void atualizarTelefone(@RequestBody Telefone atualizacao) {
		Telefone telefone = repositorioTelefone.getById(atualizacao.getId());
		TelefoneAtualizador atualizador = new TelefoneAtualizador();
		atualizador.atualizar(telefone, atualizacao);
		repositorioTelefone.save(telefone);
	}

	@DeleteMapping("/excluir/{id}")
	public void excluirTelefone(@RequestBody Telefone exclusao, @PathVariable long id) {
		Cliente cliente = repositorioCliente.getById(id);
		List<Telefone> telefonesCliente = cliente.getTelefones();
		for (Telefone telefone : telefonesCliente) {
	        if (telefone.getId() == exclusao.getId()) {
	            telefonesCliente.remove(telefone);
	            break;
	        }
	    }
	    cliente.setTelefones(telefonesCliente);
		repositorioCliente.save(cliente);
	}
}
