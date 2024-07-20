/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reservavuelosavion;

import com.toedter.calendar.JDateChooser;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Properties;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
/**
 *
 * @author Estudiante
 */
public class Pasajero {
        private int busquedaDetalleIdSeleccionada;
    private int usuarioId;
    
    public int getBusquedaDetalleIdSeleccionada() {
        return busquedaDetalleIdSeleccionada;
    }

    public void setBusquedaDetalleIdSeleccionada(int busquedaDetalleIdSeleccionada) {
        this.busquedaDetalleIdSeleccionada = busquedaDetalleIdSeleccionada;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

  /*  private int obtenerIdUsuario() {
        // Implementar la lógica para obtener el ID del usuario autenticado
        return 1; 
    }*/

public void guardarReserva() {
    Coneccion objetoConexion = new Coneccion();
    Connection conexion = null;
    PreparedStatement pstmt = null;

    try {
        // Establecer la conexión a la base de datos
        conexion = objetoConexion.estableceConexion();

        // Consulta SQL para insertar una nueva reserva
        String sql = "INSERT INTO reservas (fkbusqueda_detalle, usuario_id, fecha_reserva) VALUES (?, ?, CURRENT_TIMESTAMP)";

        pstmt = conexion.prepareStatement(sql);
        pstmt.setInt(1, busquedaDetalleIdSeleccionada);
        pstmt.setInt(2, usuarioId);

        // Ejecutar la consulta de inserción
        int rowsInserted = pstmt.executeUpdate();

        // Verificar si la inserción fue exitosa
        if (rowsInserted > 0) {
            JOptionPane.showMessageDialog(null, "Reserva guardada con éxito.");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo guardar la reserva.");
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al guardar la reserva: " + e.getMessage());
    } finally {
        try {
            // Cerrar el PreparedStatement y la conexión
            if (pstmt != null) pstmt.close();
            if (conexion != null) conexion.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + e.getMessage());
        }
    }
}

    private String nombre;
    private String apellido;
    private int fkgenero;
    private String dni;
    private String numeroPasaporte;
    private String nacionalidad;
    private String direccion;
    private java.sql.Date fechaNacimiento;
    private int reservaId;
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getFkgenero() {
        return fkgenero;
    }

    public void setFkgenero(int fkgenero) {
        this.fkgenero = fkgenero;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNumeroPasaporte() {
        return numeroPasaporte;
    }

    public void setNumeroPasaporte(String numeroPasaporte) {
        this.numeroPasaporte = numeroPasaporte;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
       public int getReservaId() {
        return reservaId;
    }

    public void setReservaId(int reservaId) {
        this.reservaId = reservaId;
    }

//ACA FUNCIONA INGRESAR INFO PARA UN SOLO PASAJERO
      public void GuardarPasajero(JTextField paramNombre, JTextField paramApellido, JComboBox<String> comboGenero, JTextField paramDNI,
                    JTextField paramPasaporte, JTextField paramNacionalidad, JTextField paramDireccion, JDateChooser paramFechaNac, int busquedaDetalleId, int usuarioId) {

    String nombre = paramNombre.getText();
    String apellido = paramApellido.getText();
    String genero = comboGenero.getSelectedItem().toString();
    String dni = paramDNI.getText();
    String pasaporte = paramPasaporte.getText();
    String nacionalidad = paramNacionalidad.getText();
    String direccion = paramDireccion.getText();
    java.util.Date fechaNac = paramFechaNac.getDate();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String fechaNacStr = sdf.format(fechaNac);

    Coneccion objetoConexion = new Coneccion();

    String sqlGenero = "SELECT id FROM genero WHERE Descripción = ?";
    String consultaReserva = "INSERT INTO reservas (fkbusqueda_detalle, usuario_id) VALUES (?, ?)";
    String consultaPasajero = "INSERT INTO pasajero (nombre, apellido, fkgenero, dni, numero_pasaporte, nacionalidad, direccion, fecha_nacimiento, fkreserva) " +
                              "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection conexion = objetoConexion.estableceConexion();
         PreparedStatement pstGenero = conexion.prepareStatement(sqlGenero);
         PreparedStatement pstReserva = conexion.prepareStatement(consultaReserva, Statement.RETURN_GENERATED_KEYS);
         PreparedStatement pstPasajero = conexion.prepareStatement(consultaPasajero)) {

        // Obtener el ID del género
        pstGenero.setString(1, genero);
        int fkgenero = 0;
        try (ResultSet rsGenero = pstGenero.executeQuery()) {
            if (rsGenero.next()) {
                fkgenero = rsGenero.getInt("id");
            } else {
                throw new SQLException("Género no encontrado en la base de datos");
            }
        }

        // Insertar en la tabla reservas
        pstReserva.setInt(1, busquedaDetalleId);
        pstReserva.setInt(2, usuarioId);
        int rowsInsertedReserva = pstReserva.executeUpdate();
        if (rowsInsertedReserva == 0) {
            throw new SQLException("Fallo la inserción en la tabla reservas");
        }

        // Obtener el id de la reserva insertada
        int reservaId;
        try (ResultSet generatedKeys = pstReserva.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                reservaId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Fallo al obtener el ID de la reserva insertada");
            }
        }

        // Preparar la consulta SQL para pasajero
        pstPasajero.setString(1, nombre);
        pstPasajero.setString(2, apellido);
        pstPasajero.setInt(3, fkgenero);
        pstPasajero.setString(4, dni);
        pstPasajero.setString(5, pasaporte);
        pstPasajero.setString(6, nacionalidad);
        pstPasajero.setString(7, direccion);
        pstPasajero.setString(8, fechaNacStr);
        pstPasajero.setInt(9, reservaId);

        // Ejecutar la consulta
        int rowsInsertedPasajero = pstPasajero.executeUpdate();
        if (rowsInsertedPasajero > 0) {
            JOptionPane.showMessageDialog(null, "Registro exitoso");
        } else {
            JOptionPane.showMessageDialog(null, "Error al registrar usuario");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al registrar usuario: " + e.getMessage());
    }
}
}
