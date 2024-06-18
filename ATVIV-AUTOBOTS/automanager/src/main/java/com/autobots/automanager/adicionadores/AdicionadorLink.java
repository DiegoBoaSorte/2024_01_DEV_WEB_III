package com.autobots.automanager.adicionadores;

import java.util.List;

import com.autobots.automanager.entitades.Email;
import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.entitades.Usuario;


public interface AdicionadorLink<T> {
	public void adicionarLink(List<T> lista);
	public void adicionarLink(T objeto);
	public void adicionarLink(T objeto, Usuario usuario);
	public void adicionarLink(T objeto, Empresa empresa);
	public void adicionarLink(T objeto, Mercadoria mercadoria);
	
}