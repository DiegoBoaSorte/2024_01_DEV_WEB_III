package com.autobots.automanager.selecionadores;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.entitades.Mercadoria;

@Component
public class EmpresaMercadoriaSelecionador {
	public Empresa selecionar(List<Empresa> empresas, Mercadoria mercadoria) {
		Empresa selecionado = null;
		for (Empresa empresa : empresas) {
			for (Mercadoria mer : empresa.getMercadorias()) {
				if (mer.getId() ==  mercadoria.getId()) {
					selecionado =  empresa;
				}
			}
		}
		return selecionado;
	}
}
