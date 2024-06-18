package com.autobots.automanager.servicos;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.autobots.automanager.entitades.Usuario;

public class TokenServico {

	   public String gerarToken(Usuario usuario) {
	        return JWT.create()
	                .withIssuer("")
	                .withSubject(usuario.getUsername())
	                .withClaim("id", usuario.getId())
	                .withExpiresAt(LocalDateTime.now()
	                        .plusMinutes(60)
	                        .toInstant(ZoneOffset.of("-03:00"))).sign(Algorithm.HMAC256("chaveToken"));
	    }


	    public String getSubject(String token) {
	        return JWT.require(Algorithm.HMAC256("chaveToken"))
	                .withIssuer("")
	                .build().verify(token).getSubject();

	    }
}
