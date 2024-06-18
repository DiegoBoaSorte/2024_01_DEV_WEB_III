package com.autobots.automanager.selecionadores;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.entitades.Servico;

@Component
public class EmpresaServicoSelecionador {
	public Empresa selecionar(List<Empresa> empresas, Servico servico) {
		Empresa selecionado = null;
		for (Empresa empresa : empresas) {
			for (Servico ser : empresa.getServicos()) {
				if (ser.getId() ==  servico.getId()) {
					selecionado =  empresa;
				}
			}
		}
		return selecionado;
	}
}
