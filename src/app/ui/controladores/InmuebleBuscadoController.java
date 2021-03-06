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
package app.ui.controladores;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import app.datos.entidades.Barrio;
import app.datos.entidades.Cliente;
import app.datos.entidades.InmuebleBuscado;
import app.datos.entidades.Localidad;
import app.datos.entidades.Pais;
import app.datos.entidades.Provincia;
import app.datos.entidades.TipoInmueble;
import app.excepciones.PersistenciaException;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * Controlador de la vista inmueble buscado.
 * Se utiliza para crear un nuevo inmueble buscado o modificar uno existente
 */
public class InmuebleBuscadoController extends OlimpoController {

	public static final String URLVista = "/app/ui/vistas/inmuebleBuscado.fxml";

	@FXML
	private ComboBox<Pais> comboBoxPais;
	@FXML
	private ComboBox<Provincia> comboBoxProvincia;
	@FXML
	private ComboBox<Localidad> comboBoxLocalidad;
	@FXML
	private ComboBox<Barrio> comboBoxBarrio;

	@FXML
	private TableView<Localidad> tablaLocalidades;
	@FXML
	private TableColumn<Localidad, String> columnaNombreLocalidad;

	@FXML
	private Button botonAgregarLocalidad;
	@FXML
	private Button botonQuitarLocalidad;

	@FXML
	private TableView<Barrio> tablaBarrios;
	@FXML
	private TableColumn<Barrio, String> columnaNombreBarrio;
	@FXML
	private TableColumn<Barrio, String> columnaNombreLocalidadDelBarrio;

	@FXML
	private Button botonAgregarBarrio;
	@FXML
	private Button botonQuitarBarrio;

	@FXML
	protected CheckBox checkBoxLocal;
	@FXML
	protected CheckBox checkBoxCasa;
	@FXML
	protected CheckBox checkBoxDepartamento;
	@FXML
	protected CheckBox checkBoxTerreno;
	@FXML
	protected CheckBox checkBoxGalpon;
	@FXML
	protected CheckBox checkBoxQuinta;

	@FXML
	protected TextField textFieldSuperficie;
	@FXML
	protected TextField textFieldAntiguedad;
	@FXML
	protected TextField textFieldDormitorios;
	@FXML
	protected TextField textFieldBaños;
	@FXML
	protected TextField textFieldPrecio;

	@FXML
	protected CheckBox checkBoxPropiedadHorizontal;
	@FXML
	protected CheckBox checkBoxGarage;
	@FXML
	protected CheckBox checkBoxPatio;
	@FXML
	protected CheckBox checkBoxPiscina;
	@FXML
	protected CheckBox checkBoxAguaCorriente;
	@FXML
	protected CheckBox checkBoxCloaca;
	@FXML
	protected CheckBox checkBoxGasNatural;
	@FXML
	protected CheckBox checkBoxAguaCaliente;
	@FXML
	protected CheckBox checkBoxTelefono;
	@FXML
	protected CheckBox checkBoxLavadero;
	@FXML
	protected CheckBox checkBoxPavimento;

	protected Boolean alta;

	protected Cliente cliente;

	protected ArrayList<Localidad> listaLocalidadesSeleccionadas;

	protected ArrayList<Barrio> listaBarriosSeleccionados;

	/**
	 * Setea el cliente pasado por parámetro y, si posee un inmueble buscado,
	 * se cargan los campos con los datos.
	 *
	 * @param cliente
	 *            cliente del que se obtienen los datos
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
		InmuebleBuscado inmueble = cliente.getInmuebleBuscado();
		Platform.runLater(() -> {
			if(inmueble != null){
				alta = false; //si el cliente tiene inmueble se está modificando, no dando de alta
				//se setean los datos actuales
				listaBarriosSeleccionados.addAll(inmueble.getBarrios());
				listaLocalidadesSeleccionadas.addAll(inmueble.getLocalidades());
				checkBoxAguaCaliente.setSelected(inmueble.getAguaCaliente());
				checkBoxAguaCorriente.setSelected(inmueble.getAguaCorriente());
				checkBoxCloaca.setSelected(inmueble.getCloacas());
				checkBoxGarage.setSelected(inmueble.getGaraje());
				checkBoxGasNatural.setSelected(inmueble.getGasNatural());
				checkBoxLavadero.setSelected(inmueble.getLavadero());
				checkBoxPatio.setSelected(inmueble.getPatio());
				checkBoxPavimento.setSelected(inmueble.getPavimento());
				checkBoxPiscina.setSelected(inmueble.getPiscina());
				checkBoxPropiedadHorizontal.setSelected(inmueble.getPropiedadHorizontal());
				checkBoxTelefono.setSelected(inmueble.getTelefono());
				textFieldAntiguedad.setText(inmueble.getAntiguedadMax().toString());
				textFieldBaños.setText(inmueble.getBañosMin().toString());
				textFieldDormitorios.setText(inmueble.getDormitoriosMin().toString());
				textFieldSuperficie.setText(inmueble.getSuperficieMin().toString());
				textFieldPrecio.setText(inmueble.getPrecioMax().toString());
				for(TipoInmueble tipo: inmueble.getTiposInmueblesBuscados()){
					switch(tipo.getTipo()) {
					case LOCAL:
						checkBoxLocal.setSelected(true);
						break;
					case CASA:
						checkBoxCasa.setSelected(true);
						break;
					case DEPARTAMENTO:
						checkBoxDepartamento.setSelected(true);
						break;
					case GALPON:
						checkBoxGalpon.setSelected(true);
						break;
					case QUINTA:
						checkBoxQuinta.setSelected(true);
						break;
					case TERRENO:
						checkBoxTerreno.setSelected(true);
						break;
					}
				}
			}
			else{ //si el cliente no tiene inmueble, se lo está dando de alta
				alta = true;
			}
			//cargo las tablas
			tablaBarrios.getItems().clear();
			tablaBarrios.getItems().addAll(listaBarriosSeleccionados);
			tablaLocalidades.getItems().clear();
			tablaLocalidades.getItems().addAll(listaLocalidadesSeleccionadas);
		});
	}

	/**
	 * Acción que se ejecuta al apretar el botón aceptar.
	 *
	 * Valida que se hayan insertado datos y los carga al inmueble buscado.
	 * Si el cliente no posee inmueble buscado se crea uno, sino, se modifica el existente.
	 *
	 * Al finalizar regresa a la pantalla correspondiente, ya sea alta cliente o modificar cliente.
	 */
	@FXML
	protected void acceptAction() {
		Double supeficieMinima = null;
		Integer antiguedadMaxima = null;
		Integer dormitoriosMinimos = null;
		Integer bañosMinimos = null;
		Double precioMaximo = null;

		StringBuffer errores = new StringBuffer("");

		//obtengo datos introducidos por el usuario
		try{
			supeficieMinima = Double.valueOf(textFieldSuperficie.getText().trim());
		} catch(Exception e){
			errores.append("Superficie incorrecta. Introduzca solo números y un punto para decimales.\n");
		}

		try{
			precioMaximo = Double.valueOf(textFieldPrecio.getText().trim());
		} catch(Exception e){
			errores.append("Precio incorrecto. Introduzca solo números y un punto para decimales.\n");
		}

		try{
			antiguedadMaxima = Integer.valueOf(textFieldAntiguedad.getText().trim());
		} catch(Exception e){
			errores.append("Antigüedad incorrecta. Introduzca solo números\n");
		}

		try{
			dormitoriosMinimos = Integer.valueOf(textFieldDormitorios.getText().trim());
		} catch(Exception e){
			errores.append("Dormitorios incorrecto. Introduzca solo números\n");
		}

		try{
			bañosMinimos = Integer.valueOf(textFieldBaños.getText().trim());
		} catch(Exception e){
			errores.append("Baños incorrecto. Introduzca solo números\n");
		}

		if(!errores.toString().isEmpty()){ //si hay algún error lo muestro al usuario
			presentador.presentarError("Revise sus campos", errores.toString(), stage);
		}
		else{
			//Si no hay errores se terminan de obtener los datos que no se verifican
			Boolean propiedadHorizontal = checkBoxPropiedadHorizontal.isSelected();
			Boolean garage = checkBoxGarage.isSelected();
			Boolean patio = checkBoxPatio.isSelected();
			Boolean piscina = checkBoxPiscina.isSelected();
			Boolean aguaCorriente = checkBoxAguaCorriente.isSelected();
			Boolean cloaca = checkBoxCloaca.isSelected();
			Boolean gasNatural = checkBoxGasNatural.isSelected();
			Boolean aguaCaliente = checkBoxAguaCaliente.isSelected();
			Boolean telefono = checkBoxTelefono.isSelected();
			Boolean lavadero = checkBoxLavadero.isSelected();
			Boolean pavimento = checkBoxPavimento.isSelected();

			Boolean casa = checkBoxCasa.isSelected();
			Boolean departamento = checkBoxDepartamento.isSelected();
			Boolean local = checkBoxLocal.isSelected();
			Boolean galpon = checkBoxGalpon.isSelected();
			Boolean terreno = checkBoxTerreno.isSelected();
			Boolean quinta = checkBoxQuinta.isSelected();

			InmuebleBuscado inmuebleBuscado = cliente.getInmuebleBuscado();
			if(alta){ //si se está dando de alta el cliente, se crea un nuevo inmueble
				inmuebleBuscado = new InmuebleBuscado();
				cliente.setInmuebleBuscado(inmuebleBuscado);
				inmuebleBuscado.setCliente(cliente);
			}

			//cargo los datos al inmueble
			inmuebleBuscado.setSuperficieMin(supeficieMinima)
					.setAntiguedadMax(antiguedadMaxima)
					.setDormitoriosMin(dormitoriosMinimos)
					.setBañosMin(bañosMinimos)
					.setPrecioMax(precioMaximo)
					.setPropiedadHorizontal(propiedadHorizontal)
					.setGaraje(garage)
					.setPatio(patio)
					.setPiscina(piscina)
					.setAguaCaliente(aguaCaliente)
					.setAguaCorriente(aguaCorriente)
					.setCloacas(cloaca)
					.setGasNatural(gasNatural)
					.setTelefono(telefono)
					.setLavadero(lavadero)
					.setPavimento(pavimento);

			inmuebleBuscado.getLocalidades().clear();
			inmuebleBuscado.getLocalidades().addAll(listaLocalidadesSeleccionadas);
			inmuebleBuscado.getBarrios().clear();
			inmuebleBuscado.getBarrios().addAll(listaBarriosSeleccionados);

			try{
				for(TipoInmueble tipo: coordinador.obtenerTiposInmueble()){
					switch(tipo.getTipo()) {
					case CASA:
						if(casa){
							inmuebleBuscado.getTiposInmueblesBuscados().add(tipo);
						}
						break;
					case DEPARTAMENTO:
						if(departamento){
							inmuebleBuscado.getTiposInmueblesBuscados().add(tipo);
						}
						break;
					case GALPON:
						if(galpon){
							inmuebleBuscado.getTiposInmueblesBuscados().add(tipo);
						}
						break;
					case LOCAL:
						if(local){
							inmuebleBuscado.getTiposInmueblesBuscados().add(tipo);
						}
						break;
					case QUINTA:
						if(quinta){
							inmuebleBuscado.getTiposInmueblesBuscados().add(tipo);
						}
						break;
					case TERRENO:
						if(terreno){
							inmuebleBuscado.getTiposInmueblesBuscados().add(tipo);
						}
						break;
					}
				}
			} catch(PersistenciaException e){ //excepción originada en la capa de persistencia
				presentador.presentarExcepcion(e, stage);
			}

			if(alta){ //si se está dando de alta vuelvo a la vista de alta cliente
				AltaClienteController controlador = (AltaClienteController) cambiarmeAScene(AltaClienteController.URLVista);
				controlador.setCliente(cliente);
			}
			else{ //si se está modificando vuelvo a la vista de modificar cliente
				ModificarClienteController controlador = (ModificarClienteController) cambiarmeAScene(ModificarClienteController.URLVista);
				controlador.setClienteEnModificacion(cliente);
			}
		}
	}

	/**
	 * Acción que se ejecuta al presionar el botón cancelar.
	 * Se vuelve a la pantalla correspondiente, ya sea alta cliente o modificar cliente.
	 */
	@FXML
	private void cancelAction() {
		if(alta){
			cambiarmeAScene(AltaClienteController.URLVista);
		}
		else{
			cambiarmeAScene(ModificarClienteController.URLVista);
		}
	}

	@Override
	public void inicializar(URL location, ResourceBundle resources) {
		this.setTitulo("Cargar inmueble buscado");

		listaLocalidadesSeleccionadas = new ArrayList<>();
		listaBarriosSeleccionados = new ArrayList<>();

		try{
			comboBoxPais.getItems().addAll(coordinador.obtenerPaises());
		} catch(PersistenciaException e){
			presentador.presentarExcepcion(e, stage);
		}

		botonAgregarBarrio.setDisable(true);
		botonAgregarLocalidad.setDisable(true);
		botonQuitarBarrio.setDisable(true);
		botonQuitarLocalidad.setDisable(true);

		//setea qué datos se muestran en cada columna
		columnaNombreLocalidad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().toString()));
		columnaNombreBarrio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().toString()));
		columnaNombreLocalidadDelBarrio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocalidad().toString()));

		//cada vez que cambia el item seleccionado
		comboBoxPais.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> actualizarProvincias(newValue));
		comboBoxProvincia.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> actualizarLocalidades(newValue));
		comboBoxLocalidad.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> localidadSeleccionadaCombo(newValue));

		tablaLocalidades.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> localidadSeleccionadaTabla(newValue));

		comboBoxBarrio.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> BarrioSeleccionadoCombo(newValue));
		tablaBarrios.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> BarrioSeleccionadoTabla(newValue));

	}

	/**
	 * Agrega una localidad a la lista de localidades seleccionadas, es decir, las localidades
	 * en las que se desea buscar un inmueble
	 */
	@FXML
	private void handleAgregarLocalidad() {
		if(comboBoxLocalidad.getSelectionModel().getSelectedItem() != null && !listaLocalidadesSeleccionadas.contains(comboBoxLocalidad.getSelectionModel().getSelectedItem())){
			listaLocalidadesSeleccionadas.add(comboBoxLocalidad.getSelectionModel().getSelectedItem());
			tablaLocalidades.getItems().clear();
			tablaLocalidades.getItems().addAll(listaLocalidadesSeleccionadas);
		}
	}

	/**
	 * Quita una localidad de la lista de localidades seleccionadas, es decir, las localidades
	 * en las que se desea buscar un inmueble.
	 *
	 * Si había barrios pertenecientes a esa localidad en la lista de barrios deseados,
	 * se quitan de dicha lista.
	 */
	@FXML
	private void handleQuitarLocalidad() {
		if(tablaLocalidades.getSelectionModel().getSelectedItem() != null){
			for(Barrio bar: tablaBarrios.getItems()){
				if(bar.getLocalidad().equals(tablaLocalidades.getSelectionModel().getSelectedItem())){
					listaBarriosSeleccionados.remove(bar);
				}
			}
			tablaBarrios.getItems().clear();
			tablaBarrios.getItems().addAll(listaBarriosSeleccionados);
			listaLocalidadesSeleccionadas.remove(tablaLocalidades.getSelectionModel().getSelectedItem());
			tablaLocalidades.getItems().clear();
			tablaLocalidades.getItems().addAll(listaLocalidadesSeleccionadas);
		}
	}

	/**
	 * Agrega un barrio a la lista de barrios seleccionados, es decir, los barrios
	 * en los que se desea buscar un inmueble
	 */
	@FXML
	private void handleAgregarBarrio() {
		if(comboBoxBarrio.getSelectionModel().getSelectedItem() != null && !listaBarriosSeleccionados.contains(comboBoxBarrio.getSelectionModel().getSelectedItem())){
			listaBarriosSeleccionados.add(comboBoxBarrio.getSelectionModel().getSelectedItem());
			tablaBarrios.getItems().clear();
			tablaBarrios.getItems().addAll(listaBarriosSeleccionados);
		}
	}

	/**
	 * Quita un barrio de la lista de barrios seleccionados, es decir, los barrios
	 * en loss que se desea buscar un inmueble.
	 */
	@FXML
	private void handleQuitarBarrio() {
		if(tablaBarrios.getSelectionModel().getSelectedItem() != null){
			listaBarriosSeleccionados.remove(tablaBarrios.getSelectionModel().getSelectedItem());
			tablaBarrios.getItems().clear();
			tablaBarrios.getItems().addAll(listaBarriosSeleccionados);
		}
	}

	/**
	 * Habilita/deshabilita la opción de quitar un barrio de la lista de barrios seleccionados
	 * según si hay uno seleccionado en la tabla o no.
	 *
	 * @param bar
	 *            barrio seleccionado en la tabla. Si no hay ninguno seleccionado, es <code>null</code>
	 */
	private void BarrioSeleccionadoTabla(Barrio bar) {
		if(bar == null){
			botonQuitarBarrio.setDisable(true);
		}
		else{
			botonQuitarBarrio.setDisable(false);
		}
	}

	/**
	 * Habilita/deshabilita la opción de quitar una localidad de la lista de localidades seleccionadas
	 * según si hay una seleccionado en la tabla o no.
	 * También actualiza el comboBox de barrios según la localidad seleccionada de la tabla
	 *
	 * @param loc
	 *            localidad seleccionada en la tabla. Si no hay ninguna seleccionada, es <code>null</code>
	 */
	private void localidadSeleccionadaTabla(Localidad loc) {
		if(loc == null){
			comboBoxBarrio.getItems().clear();
			botonQuitarLocalidad.setDisable(true);
		}
		else{
			botonQuitarLocalidad.setDisable(false);
			comboBoxBarrio.getItems().clear();
			try{
				comboBoxBarrio.getItems().addAll(coordinador.obtenerBarriosDe(loc));
			} catch(PersistenciaException e){
				presentador.presentarExcepcion(e, stage);
			}
		}
	}

	/**
	 * Habilita/deshabilita la opción de agregar un barrio a la lista de barrios seleccionados
	 * según si hay uno seleccionado en el comboBox o no.
	 *
	 * @param bar
	 *            barrio seleccionado en el comboBox. Si no hay ninguno seleccionado, es <code>null</code>
	 */
	private void BarrioSeleccionadoCombo(Barrio bar) {
		if(bar == null){
			botonAgregarBarrio.setDisable(true);
		}
		else{
			botonAgregarBarrio.setDisable(false);
		}
	}

	/**
	 * Habilita/deshabilita la opción de agregar una localidad a la lista de localidades seleccionadas
	 * según si hay una seleccionada en el comboBox o no.
	 *
	 * @param loc
	 *            localidad seleccionada en el comboBox. Si no hay ninguna seleccionada, es <code>null</code>
	 */
	private void localidadSeleccionadaCombo(Localidad loc) {
		if(loc == null){
			botonAgregarLocalidad.setDisable(true);
		}
		else{
			botonAgregarLocalidad.setDisable(false);
		}
	}

	/**
	 * Cuando varía la seleccion del comboBox de provincias, se actualiza el comboBox de localidades.
	 *
	 * @param provincia
	 *            provincia que fué seleccionada en el comboBox. Si no hay nada seleccionado, es <code>null</code>
	 */
	private void actualizarLocalidades(Provincia provincia) {
		comboBoxLocalidad.getItems().clear();
		if(provincia != null){
			try{
				comboBoxLocalidad.getItems().addAll(coordinador.obtenerLocalidadesDe(provincia));
			} catch(PersistenciaException e){
				presentador.presentarExcepcion(e, stage);
			}
		}
	}

	/**
	 * Cuando varía la seleccion del comboBox de países, se actualiza el comboBox de provincias.
	 * También se delega la tarea de vaciar el comboBox de localidades
	 *
	 * @param pais
	 *            país que fué seleccionado en el comboBox. Si no hay nada seleccionado, es <code>null</code>
	 */
	private void actualizarProvincias(Pais pais) {
		comboBoxProvincia.getItems().clear();
		if(pais != null){
			try{
				comboBoxProvincia.getItems().addAll(coordinador.obtenerProvinciasDe(pais));
			} catch(PersistenciaException e){
				presentador.presentarExcepcion(e, stage);
			}
		}
		actualizarLocalidades(null);
	}
}
