package com.autobots.automanager.autenticacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.recordes.Login;
import com.autobots.automanager.servicos.TokenServico;

@RestController
public class AutenticacaoControle{
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private TokenServico tokenServico;
	
	@PostMapping("/usuario/login")
	public String usuarioLogin(@RequestBody Login login) {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(login.nomeUsuario(), login.senha());
		
		Authentication authenticate = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
			
		var usuario = (Usuario) authenticate.getPrincipal();
		
		return tokenServico.gerarToken(usuario);
	}

	

}
