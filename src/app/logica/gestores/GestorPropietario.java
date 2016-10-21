package app.logica.gestores;

import java.util.ArrayList;

import app.datos.entidades.Propietario;
import app.datos.servicios.PropietarioService;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoCrearPropietario;
import app.logica.resultados.ResultadoEliminarPropietario;
import app.logica.resultados.ResultadoCrearPropietario.ErrorResultadoCrearPropietario;
import app.logica.resultados.ResultadoEliminarPropietario.ErrorResultadoEliminarPropietario;
import app.logica.resultados.ResultadoModificarPropietario;
import app.logica.resultados.ResultadoModificarPropietario.ErrorResultadoModificarPropietario;

public class GestorPropietario {

	private PropietarioService persistidorPropietario;
	private ValidadorFormato validador;

	public GestorPropietario(PropietarioService persistidorPropietario, ValidadorFormato validador) {
		super();
		this.persistidorPropietario = persistidorPropietario;
		this.validador = validador;
	}

	public ResultadoCrearPropietario crearPropietario(Propietario propietario) throws PersistenciaException {
		ArrayList<ErrorResultadoCrearPropietario> errores = new ArrayList<>();
		Propietario propietarioAuxiliar;

		if(!validador.validarNombre(propietario.getNombre())){
			errores.add(ErrorResultadoCrearPropietario.Formato_Nombre_Incorrecto);
		}

		if(!validador.validarApellido(propietario.getApellido())){
			errores.add(ErrorResultadoCrearPropietario.Formato_Apellido_Incorrecto);
		}

		if(!validador.validarDocumento(propietario.getTipoDocumento(), propietario.getNumeroDocumento())){
			errores.add(ErrorResultadoCrearPropietario.Formato_Documento_Incorrecto);
		}

		if(!validador.validarTelefono(propietario.getTelefono())){
			errores.add(ErrorResultadoCrearPropietario.Formato_Telefono_Incorrecto);
		}

		if(!validador.validarEmail(propietario.getEmail())){
			errores.add(ErrorResultadoCrearPropietario.Formato_Email_Incorrecto);
		}

		if(!validador.validarDireccion(propietario.getDireccion())){
			errores.add(ErrorResultadoCrearPropietario.Formato_Direccion_Incorrecto);
		}

		try{
			propietarioAuxiliar = persistidorPropietario.obtenerPropietario(propietario.getTipoDocumento(), propietario.getNumeroDocumento());
		} catch(PersistenciaException e){
			throw e;
		}
		if(propietarioAuxiliar != null){
			errores.add(ErrorResultadoCrearPropietario.Ya_Existe_Propietario);
		}

		if(errores.isEmpty()){
			try{
				persistidorPropietario.guardarPropietario(propietario);
			} catch(PersistenciaException e){
				throw e;
			}
		}

		return new ResultadoCrearPropietario(errores.toArray(new ErrorResultadoCrearPropietario[0]));
	}

	public ResultadoModificarPropietario modificarPropietario(Propietario propietario) throws PersistenciaException {
		ArrayList<ErrorResultadoModificarPropietario> errores = new ArrayList<>();
		Propietario propietarioAuxiliar;

		if(!validador.validarNombre(propietario.getNombre())){
			errores.add(ErrorResultadoModificarPropietario.Formato_Nombre_Incorrecto);
		}

		if(!validador.validarApellido(propietario.getApellido())){
			errores.add(ErrorResultadoModificarPropietario.Formato_Apellido_Incorrecto);
		}

		if(!validador.validarDocumento(propietario.getTipoDocumento(), propietario.getNumeroDocumento())){
			errores.add(ErrorResultadoModificarPropietario.Formato_Documento_Incorrecto);
		}

		if(!validador.validarTelefono(propietario.getTelefono())){
			errores.add(ErrorResultadoModificarPropietario.Formato_Telefono_Incorrecto);
		}

		if(!validador.validarEmail(propietario.getEmail())){
			errores.add(ErrorResultadoModificarPropietario.Formato_Email_Incorrecto);
		}

		if(!validador.validarDireccion(propietario.getDireccion())){
			errores.add(ErrorResultadoModificarPropietario.Formato_Direccion_Incorrecto);
		}

		try{
			propietarioAuxiliar = persistidorPropietario.obtenerPropietario(propietario.getTipoDocumento(), propietario.getNumeroDocumento());
		} catch(PersistenciaException e){
			throw e;
		}

		if(null != propietarioAuxiliar && !propietario.getId().equals(propietarioAuxiliar.getId())){
			errores.add(ErrorResultadoModificarPropietario.Ya_Existe_Propietario_Con_Mismo_Documento_y_Tipo);
		}

		if(errores.isEmpty()){
			try{
				persistidorPropietario.modificarPropietario(propietario);
			} catch(PersistenciaException e){
				throw e;
			}
		}

		return new ResultadoModificarPropietario(errores.toArray(new ErrorResultadoModificarPropietario[0]));
	}

	public ResultadoEliminarPropietario eliminarPropietario(Propietario propietario) throws PersistenciaException {
		ArrayList<ErrorResultadoEliminarPropietario> errores = new ArrayList<>();
		Propietario propietarioAuxiliar;

		try{
			propietarioAuxiliar = persistidorPropietario.obtenerPropietario(propietario.getTipoDocumento(), propietario.getNumeroDocumento());
		} catch(PersistenciaException e){
			throw e;
		}
		if(null == propietarioAuxiliar){
			errores.add(ErrorResultadoEliminarPropietario.No_Existe_Propietario_En_BD);
		}

		if(errores.isEmpty()){
			try{
				persistidorPropietario.eliminarPropietario(propietario);
			} catch(PersistenciaException e){
				throw e;
			}
		}

		return new ResultadoEliminarPropietario(errores.toArray(new ErrorResultadoEliminarPropietario[0]));
	}

}
