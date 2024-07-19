/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reservavuelosavion;

/*import com.toedter.calendar.JDateChooser;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JTextField;*/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
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

}
