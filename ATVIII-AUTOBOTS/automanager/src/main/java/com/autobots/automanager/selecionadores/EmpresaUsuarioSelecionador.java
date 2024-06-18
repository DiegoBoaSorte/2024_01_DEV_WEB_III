package com.autobots.automanager.selecionadores;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.entitades.Usuario;

@Component
public class EmpresaUsuarioSelecionador {
	public Empresa selecionar(List<Empresa> empresas, Usuario usuario) {
		Empresa selecionado = null;
		for (Empresa empresa : empresas) {
			for (Usuario usu: empresa.getUsuarios()) {
				if (usu.getId() ==  usuario.getId()) {
					selecionado =  empresa;
				}
			}
		}
		return selecionado;
	}
}
