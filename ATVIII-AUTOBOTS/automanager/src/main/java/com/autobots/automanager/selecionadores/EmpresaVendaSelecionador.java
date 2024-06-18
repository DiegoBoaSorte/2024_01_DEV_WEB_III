package com.autobots.automanager.selecionadores;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.entitades.Venda;

@Component
public class EmpresaVendaSelecionador {
	public Empresa selecionar(List<Empresa> empresas, Venda venda) {
		Empresa selecionado = null;
		for (Empresa empresa : empresas) {
			for (Venda ven : empresa.getVendas()) {
				if (ven.getId() ==  venda.getId()) {
					selecionado =  empresa;
				}
			}
		}
		return selecionado;
	}
}
