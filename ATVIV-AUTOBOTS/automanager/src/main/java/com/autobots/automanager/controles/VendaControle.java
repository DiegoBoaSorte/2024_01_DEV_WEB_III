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

import com.autobots.automanager.adicionadores.AdicionadorLinkVenda;
import com.autobots.automanager.atualizadores.VendaAtualizador;
import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.entitades.Venda;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.repositorios.VendaRepositorio;
import com.autobots.automanager.selecionadores.VendaSelecionador;

@RestController
@RequestMapping("/veiculo")
public class VendaControle {
	@Autowired
	private VendaRepositorio repositorioVenda;
	@Autowired
	private RepositorioEmpresa repositorioEmpresa;
	@Autowired
	private VendaSelecionador selecionadorVenda;
	@Autowired
	private AdicionadorLinkVenda adicionadorLinkVenda;

	@GetMapping("/venda/{id}")
	public ResponseEntity<Venda> obterVenda(@PathVariable long id) {
		List<Venda> vendas = repositorioVenda.findAll();
		Venda venda = selecionadorVenda.selecionar(vendas, id);
		if (venda == null) {
			ResponseEntity<Venda> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLinkVenda.adicionarLink(venda);
			ResponseEntity<Venda> resposta = new ResponseEntity<Venda>(venda, HttpStatus.FOUND);
			return resposta;
		} 
	}

	@GetMapping("/vendas")
	public ResponseEntity<List<Venda>> obterVendas() {
		List<Venda> vendas = repositorioVenda.findAll();
		if (vendas.isEmpty()) {
			ResponseEntity<List<Venda>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLinkVenda.adicionarLink(vendas);
			ResponseEntity<List<Venda>> resposta = new ResponseEntity<>(vendas, HttpStatus.FOUND);
			return resposta;
		}
	}

	@PostMapping("/cadastro/{id}") 
	public ResponseEntity<?> cadastrarVenda(@RequestBody Venda venda, @PathVariable long id) {
		HttpStatus status = HttpStatus.CONFLICT;
		if (venda.getId() == null) {
			Empresa empresa = repositorioEmpresa.getById(id);
			empresa.getVendas().add(venda);
		    repositorioEmpresa.save(empresa);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<>(status);

	}
	
	@PutMapping("/atualizar") 
	public ResponseEntity<?> atualizarVenda(@RequestBody Venda atualizacao) {
		HttpStatus status = HttpStatus.CONFLICT;
		Venda venda = repositorioVenda.getById(atualizacao.getId());
		if (venda != null) {
			VendaAtualizador atualizador = new VendaAtualizador();
			atualizador.atualizar(venda, atualizacao);
			repositorioVenda.save(venda);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
		
	}

	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<?> excluirVenda(@RequestBody Venda exclusao, @PathVariable long id) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Venda venda = repositorioVenda.getById(exclusao.getId());
		if (venda != null) {
			Empresa empresa = repositorioEmpresa.getById(id);
			Set<Venda> vendasEmpresa = empresa.getVendas();
			for (Venda vend : vendasEmpresa) {
		        if (vend.getId() == exclusao.getId()) {
		        	vendasEmpresa.remove(vend);
		            break;
		        }
		    }
		    empresa.setVendas(vendasEmpresa);
			repositorioEmpresa.save(empresa);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
		
	}
}
