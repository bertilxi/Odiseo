/**
 * Copyright (C) 2016 Fernando Berti - Daniel Campodonico - Emiliano Gioria - Lucas Moretti - Esteban Rebechi - Andres Leonel Rico
 * This file is part of Olimpo.
 *
 * Olimpo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Olimpo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Olimpo. If not, see <http://www.gnu.org/licenses/>.
 */
package app.logica.resultados;

import app.datos.entidades.Vendedor;
import app.logica.resultados.ResultadoAutenticacion.ErrorAutenticacion;

public class ResultadoAutenticacion extends Resultado<ErrorAutenticacion> {

	public enum ErrorAutenticacion {
		Datos_Incorrectos
	}

	Vendedor vendedorLogueado;

	public ResultadoAutenticacion(Vendedor vendedorLogueado, ErrorAutenticacion... errores) {
		super(errores);
		this.vendedorLogueado = vendedorLogueado;
	}

	public Vendedor getVendedorLogueado() {
		return vendedorLogueado;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj){
			return true;
		}
		if(!super.equals(obj)){
			return false;
		}
		if(getClass() != obj.getClass()){
			return false;
		}
		ResultadoAutenticacion other = (ResultadoAutenticacion) obj;
		if(vendedorLogueado == null){
			if(other.vendedorLogueado != null){
				return false;
			}
		}
		else if(!vendedorLogueado.equals(other.vendedorLogueado)){
			return false;
		}
		return true;
	}
}
