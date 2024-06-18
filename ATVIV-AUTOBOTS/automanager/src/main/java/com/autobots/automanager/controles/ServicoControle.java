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

import com.autobots.automanager.adicionadores.AdicionadorLinkServico;
import com.autobots.automanager.atualizadores.ServicoAtualizador;
import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.entitades.Servico;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.repositorios.ServicoRepositorio;
import com.autobots.automanager.selecionadores.ServicoSelecionador;

@RestController
@RequestMapping("/servico")
public class ServicoControle {
	@Autowired
	private ServicoRepositorio repositorioServico;
	@Autowired
	private RepositorioEmpresa repositorioEmpresa;
	@Autowired
	private ServicoSelecionador selecionadorServico;
	@Autowired
	private AdicionadorLinkServico adicionadorLinkServico;

	@GetMapping("/mercadoria/{id}")
	public ResponseEntity<Servico> obterServico(@PathVariable long id) {
		List<Servico> servicos = repositorioServico.findAll();
		Servico servico = selecionadorServico.selecionar(servicos, id);
		if (servico == null) {
			ResponseEntity<Servico> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLinkServico.adicionarLink(servico);
			ResponseEntity<Servico> resposta = new ResponseEntity<Servico>(servico, HttpStatus.FOUND);
			return resposta;
		} 
	}

	@GetMapping("/servicos")
	public ResponseEntity<List<Servico>> obterServicos() {
		List<Servico> servicos = repositorioServico.findAll();
		if (servicos.isEmpty()) {
			ResponseEntity<List<Servico>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLinkServico.adicionarLink(servicos);
			ResponseEntity<List<Servico>> resposta = new ResponseEntity<>(servicos, HttpStatus.FOUND);
			return resposta;
		}
	}

	@PostMapping("/cadastro/{id}") 
	public ResponseEntity<?> cadastrarServico(@RequestBody Servico servico, @PathVariable long id) {
		HttpStatus status = HttpStatus.CONFLICT;
		if (servico.getId() == null) {
			Empresa empresa = repositorioEmpresa.getById(id);
			empresa.getServicos().add(servico);
		    repositorioEmpresa.save(empresa);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<>(status);

	}
	
	@PutMapping("/atualizar") 
	public ResponseEntity<?> atualizarServico(@RequestBody Servico atualizacao) {
		HttpStatus status = HttpStatus.CONFLICT;
		Servico servico = repositorioServico.getById(atualizacao.getId());
		if (servico != null) {
			ServicoAtualizador atualizador = new ServicoAtualizador();
			atualizador.atualizar(servico, atualizacao);
			repositorioServico.save(servico);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
		
	}

	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<?> excluirServico(@RequestBody Servico exclusao, @PathVariable long id) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Servico servico = repositorioServico.getById(exclusao.getId());
		if (servico != null) {
			Empresa empresa = repositorioEmpresa.getById(id);
			Set<Servico> servicosEmpresa = empresa.getServicos();
			for (Servico serv : servicosEmpresa) {
		        if (serv.getId() == exclusao.getId()) {
		        	servicosEmpresa.remove(serv);
		            break;
		        }
		    }
		    empresa.setServicos(servicosEmpresa);
			repositorioEmpresa.save(empresa);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
		
	}
}
