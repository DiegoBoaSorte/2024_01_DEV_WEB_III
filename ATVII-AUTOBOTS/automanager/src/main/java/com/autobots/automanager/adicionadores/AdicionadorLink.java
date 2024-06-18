package com.autobots.automanager.adicionadores;

import java.util.List;

import com.autobots.automanager.entidades.Cliente;


public interface AdicionadorLink<T> {
	public void adicionarLink(List<T> lista);
	public void adicionarLink(T objeto);
	public void adicionarLink(T objeto, Cliente cliente);
	
}