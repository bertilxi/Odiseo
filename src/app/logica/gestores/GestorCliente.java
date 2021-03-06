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
package app.logica.gestores;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import app.comun.ValidadorFormato;
import app.datos.clases.EstadoStr;
import app.datos.clases.FiltroCliente;
import app.datos.entidades.Cliente;
import app.datos.entidades.Estado;
import app.datos.servicios.ClienteService;
import app.excepciones.EntidadExistenteConEstadoBajaException;
import app.excepciones.GestionException;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoCrearCliente;
import app.logica.resultados.ResultadoCrearCliente.ErrorCrearCliente;
import app.logica.resultados.ResultadoEliminarCliente;
import app.logica.resultados.ResultadoModificarCliente;
import app.logica.resultados.ResultadoModificarCliente.ErrorModificarCliente;

/**
 * Se encarga de la lógica de negocios respectiva a la entidad cliente
 *
 * Task card 18 de la iteración 1, historia de usuario 2
 *
 */
@Service
public class GestorCliente {

	@Resource
	protected ClienteService persistidorCliente;

	@Resource
	protected GestorDatos gestorDatos;

	@Resource
	protected ValidadorFormato validador;

	/**
	 * Se encarga de validar los datos de un cliente a crear y, en caso de que no haya errores,
	 * delegar el guardado del objeto a la capa de acceso a datos.
	 *
	 * Modificada en TaskCard 27 de la iteración 2
	 *
	 * @param cliente
	 *            cliente a crear
	 * @return un resultado informando errores correspondientes en caso de que los haya
	 *
	 * @throws PersistenciaException
	 *             se lanza esta excepción al ocurrir un error interactuando con la capa de acceso a datos
	 * @throws GestionException
	 *             se lanza una excepción EntidadExistenteConEstadoBaja cuando se encuentra que ya existe un vendedor con la misma identificación pero tiene estado BAJA
	 */
	public ResultadoCrearCliente crearCliente(Cliente cliente) throws PersistenciaException, GestionException {
		ArrayList<ErrorCrearCliente> errores = new ArrayList<>();

		// valida formato de datos
		if(!validador.validarNombre(cliente.getNombre())){
			errores.add(ErrorCrearCliente.Formato_Nombre_Incorrecto);
		}

		if(!validador.validarApellido(cliente.getApellido())){
			errores.add(ErrorCrearCliente.Formato_Apellido_Incorrecto);
		}

		if(!validador.validarTelefono(cliente.getTelefono())){
			errores.add(ErrorCrearCliente.Formato_Telefono_Incorrecto);
		}

		if(!validador.validarDocumento(cliente.getTipoDocumento(), cliente.getNumeroDocumento())){
			errores.add(ErrorCrearCliente.Formato_Documento_Incorrecto);
		}

		if(!validador.validarEmail(cliente.getCorreo())){
			errores.add(ErrorCrearCliente.Formato_Correo_Incorrecto);
		}

		//valida si existe un cliente con ese tipo y número de documento
		Cliente clienteAuxiliar = persistidorCliente.obtenerCliente(new FiltroCliente(cliente.getTipoDocumento().getTipo(),
				cliente.getNumeroDocumento()));

		if(null != clienteAuxiliar){
			if(clienteAuxiliar.getEstado().getEstado().equals(EstadoStr.ALTA)){
				errores.add(ErrorCrearCliente.Ya_Existe_Cliente);// si existe y tiene estado alta
			}
			else{// si existe y tiene estado baja
				throw new EntidadExistenteConEstadoBajaException();
			}
		}

		if(errores.isEmpty()){//si no hay errores
			ArrayList<Estado> estados = gestorDatos.obtenerEstados();
			for(Estado e: estados){
				if(e.getEstado().equals(EstadoStr.ALTA)){
					cliente.setEstado(e);//seteo el estado en alta
				}
			}
			persistidorCliente.guardarCliente(cliente);
		}

		return new ResultadoCrearCliente(errores.toArray(new ErrorCrearCliente[0]));
	}

	/**
	 * Se encarga de validar los datos de un cliente a modificar y, en caso de que no haya errores,
	 * delegar el guardado del objeto a la capa de acceso a datos.
	 *
	 * Modificada en TaskCard 27 de la iteración 2
	 *
	 * @param cliente
	 *            cliente a modificar
	 * @return un resultado informando errores correspondientes en caso de que los haya
	 *
	 * @throws PersistenciaException
	 *             se lanza esta excepción al ocurrir un error interactuando con la capa de acceso a datos
	 */
	public ResultadoModificarCliente modificarCliente(Cliente cliente) throws PersistenciaException {
		ArrayList<ErrorModificarCliente> errores = new ArrayList<>();

		// valida formato de datos
		if(!validador.validarNombre(cliente.getNombre())){
			errores.add(ErrorModificarCliente.Formato_Nombre_Incorrecto);
		}

		if(!validador.validarApellido(cliente.getApellido())){
			errores.add(ErrorModificarCliente.Formato_Apellido_Incorrecto);
		}

		if(!validador.validarTelefono(cliente.getTelefono())){
			errores.add(ErrorModificarCliente.Formato_Telefono_Incorrecto);
		}

		if(!validador.validarDocumento(cliente.getTipoDocumento(), cliente.getNumeroDocumento())){
			errores.add(ErrorModificarCliente.Formato_Documento_Incorrecto);
		}

		if(!validador.validarEmail(cliente.getCorreo())){
			errores.add(ErrorModificarCliente.Formato_Correo_Incorrecto);
		}

		//verifica si existe otro cliente con los nuevos tipo y número de documento
		Cliente clienteAuxiliar = persistidorCliente.obtenerCliente(new FiltroCliente(cliente.getTipoDocumento().getTipo(),
				cliente.getNumeroDocumento()));

		if(clienteAuxiliar != null && !cliente.equals(clienteAuxiliar)){
			errores.add(ErrorModificarCliente.Otro_Cliente_Posee_Mismo_Documento_Y_Tipo);
		}

		if(errores.isEmpty()){//si no hay errores
			if(cliente.getEstado().getEstado().equals(EstadoStr.BAJA)){
				ArrayList<Estado> estados = gestorDatos.obtenerEstados();
				for(Estado e: estados){
					if(e.getEstado().equals(EstadoStr.ALTA)){
						cliente.setEstado(e); //si el estado es baja, se setea en alta
					}
				}
			}
			persistidorCliente.modificarCliente(cliente);
		}

		return new ResultadoModificarCliente(errores.toArray(new ErrorModificarCliente[0]));
	}

	/**
	 * Se encarga de validar que exista el cliente a eliminar, se setea el estado en BAJA y,
	 * en caso de que no haya errores, delegar el guardado del objeto a la capa de acceso a datos.
	 *
	 * @param cliente
	 *            cliente a eliminar
	 * @return un resultado informando errores correspondientes en caso de que los haya
	 *
	 * @throws PersistenciaException
	 *             se lanza esta excepción al ocurrir un error interactuando con la capa de acceso a datos
	 */
	public ResultadoEliminarCliente eliminarCliente(Cliente cliente) throws PersistenciaException {
		//se setea el estado en baja y se manda a guardar
		ArrayList<Estado> estados = gestorDatos.obtenerEstados();
		for(Estado e: estados){
			if(e.getEstado().equals(EstadoStr.BAJA)){
				cliente.setEstado(e);
			}
		}
		persistidorCliente.modificarCliente(cliente);

		return new ResultadoEliminarCliente();
	}

	/**
	 * Obtiene el listado de clientes solicitándola a la capa de acceso a datos
	 *
	 * @return el listado de clientes solicitados
	 *
	 * @throws PersistenciaException
	 *             se lanza esta excepción al ocurrir un error interactuando con la capa de acceso a datos
	 */
	public ArrayList<Cliente> obtenerClientes() throws PersistenciaException {
		return persistidorCliente.listarClientes();
	}
}
