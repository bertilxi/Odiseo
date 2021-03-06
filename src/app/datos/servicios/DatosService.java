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
package app.datos.servicios;

import java.util.ArrayList;

import app.datos.entidades.Barrio;
import app.datos.entidades.Calle;
import app.datos.entidades.Estado;
import app.datos.entidades.EstadoInmueble;
import app.datos.entidades.Localidad;
import app.datos.entidades.Orientacion;
import app.datos.entidades.Pais;
import app.datos.entidades.Provincia;
import app.datos.entidades.TipoDocumento;
import app.datos.entidades.TipoInmueble;
import app.excepciones.PersistenciaException;

public interface DatosService {

	public ArrayList<Localidad> obtenerLocalidadesDe(Provincia provincia) throws PersistenciaException;

	public ArrayList<Provincia> obtenerProvinciasDe(Pais pais) throws PersistenciaException;

	public ArrayList<Pais> obtenerPaises() throws PersistenciaException;

	public ArrayList<TipoDocumento> obtenerTiposDeDocumento() throws PersistenciaException;

	public ArrayList<TipoInmueble> obtenerTiposDeInmueble() throws PersistenciaException;

	public ArrayList<Estado> obtenerEstados() throws PersistenciaException;

	public ArrayList<EstadoInmueble> obtenerEstadosInmueble() throws PersistenciaException;

	public ArrayList<Barrio> obtenerBarriosDe(Localidad localidad) throws PersistenciaException;

	public ArrayList<Calle> obtenerCallesDe(Localidad localidad) throws PersistenciaException;

	public ArrayList<Orientacion> obtenerOrientaciones() throws PersistenciaException;
}
