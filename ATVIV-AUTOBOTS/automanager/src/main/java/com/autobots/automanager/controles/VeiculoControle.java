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

import com.autobots.automanager.adicionadores.AdicionadorLinkVeiculo;
import com.autobots.automanager.atualizadores.VeiculoAtualizador;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.entitades.Veiculo;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import com.autobots.automanager.repositorios.VeiculoRepositorio;
import com.autobots.automanager.selecionadores.VeiculoSelecionador;

@RestController
@RequestMapping("/veiculo")
public class VeiculoControle {
	@Autowired
	private VeiculoRepositorio repositorioVeiculo;
	@Autowired
	private UsuarioRepositorio repositorioUsuario;
	@Autowired
	private VeiculoSelecionador selecionadorVeiculo;
	@Autowired
	private AdicionadorLinkVeiculo adicionadorLinkVeiculo;

	@GetMapping("/veiculo/{id}")
	public ResponseEntity<Veiculo> obterVeiculo(@PathVariable long id) {
		List<Veiculo> veiculos = repositorioVeiculo.findAll();
		Veiculo veiculo = selecionadorVeiculo.selecionar(veiculos, id);
		if (veiculo == null) {
			ResponseEntity<Veiculo> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLinkVeiculo.adicionarLink(veiculo);
			ResponseEntity<Veiculo> resposta = new ResponseEntity<Veiculo>(veiculo, HttpStatus.FOUND);
			return resposta;
		} 
	}

	@GetMapping("/veiculos")
	public ResponseEntity<List<Veiculo>> obterVeiculos() {
		List<Veiculo> veiculos = repositorioVeiculo.findAll();
		if (veiculos.isEmpty()) {
			ResponseEntity<List<Veiculo>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLinkVeiculo.adicionarLink(veiculos);
			ResponseEntity<List<Veiculo>> resposta = new ResponseEntity<>(veiculos, HttpStatus.FOUND);
			return resposta;
		}
	}

	@PostMapping("/cadastro/{id}") 
	public ResponseEntity<?> cadastrarVeiculo(@RequestBody Veiculo veiculo, @PathVariable long id) {
		HttpStatus status = HttpStatus.CONFLICT;
		if (veiculo.getId() == null) {
			Usuario usuario = repositorioUsuario.getById(id);
			usuario.getVeiculos().add(veiculo);
		    repositorioUsuario.save(usuario);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<>(status);

	}
	
	@PutMapping("/atualizar") 
	public ResponseEntity<?> atualizarVeiculo(@RequestBody Veiculo atualizacao) {
		HttpStatus status = HttpStatus.CONFLICT;
		Veiculo veiculo = repositorioVeiculo.getById(atualizacao.getId());
		if (veiculo != null) {
			VeiculoAtualizador atualizador = new VeiculoAtualizador();
			atualizador.atualizar(veiculo, atualizacao);
			repositorioVeiculo.save(veiculo);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
		
	}

	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<?> excluirVeiculo(@RequestBody Veiculo exclusao, @PathVariable long id) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Veiculo veiculo = repositorioVeiculo.getById(exclusao.getId());
		if (veiculo != null) {
			Usuario usuario = repositorioUsuario.getById(id);
			Set<Veiculo> veiculosUsuario = usuario.getVeiculos();
			for (Veiculo ve : veiculosUsuario) {
		        if (ve.getId() == exclusao.getId()) {
		        	veiculosUsuario.remove(ve);
		            break;
		        }
		    }
		    usuario.setVeiculos(veiculosUsuario);
			repositorioUsuario.save(usuario);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
		
	}
}
