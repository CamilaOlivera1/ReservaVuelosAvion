/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reservavuelosavion;

/*import com.toedter.calendar.JDateChooser;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
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

    /*public void guardarReserva() {
        // Obtener el ID del usuario autenticado
        int usuarioId = obtenerIdUsuario();

        // Obtener el ID de la búsqueda de detalle seleccionada
        int busquedaDetalleId = obtenerBusquedaDetalleId();

        // Conexión a la base de datos
        Coneccion objetoConexion = new Coneccion();
        Connection conexion = null;
        PreparedStatement stmt = null;

        try {
            // Establecer la conexión
            conexion = objetoConexion.estableceConexion();

            // Sentencia SQL para insertar en la tabla reservas
            String sql = "INSERT INTO reservas (fkbusqueda_detalle, usuario_id) VALUES (?, ?)";

            // Preparar la sentencia
            stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, busquedaDetalleId);
            stmt.setInt(2, usuarioId);

            // Ejecutar la inserción
            int filasInsertadas = stmt.executeUpdate();
            if (filasInsertadas > 0) {
                JOptionPane.showMessageDialog(null, "Reserva guardada exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo guardar la reserva.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar la reserva: " + e.getMessage());
        } finally {
            // Cerrar la conexión y el statement
            try {
                if (stmt != null) stmt.close();
                objetoConexion.cerrarConexion();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }*/

    private int obtenerIdUsuario() {
        // Implementar la lógica para obtener el ID del usuario autenticado
        return 1; // Ejemplo de retorno, reemplazar con la lógica real
    }

   /* private int obtenerBusquedaDetalleId() {
        // Implementar la lógica para obtener el ID de la búsqueda de detalle seleccionada
        return 1; // Ejemplo de retorno, reemplazar con la lógica real
    }*/


    public void guardarReserva() {
        // Obtener el ID del usuario autenticado
        int usuarioId = SesionUtil.obtenerIdUsuarioAutenticado();

        // Obtener el ID de la búsqueda de detalle seleccionada
        int busquedaDetalleId = obtenerBusquedaDetalleId();

        // Conexión a la base de datos
        Coneccion objetoConexion = new Coneccion();
        Connection conexion = null;
        PreparedStatement stmt = null;

        try {
            // Establecer la conexión
            conexion = objetoConexion.estableceConexion();

            // Sentencia SQL para insertar en la tabla reservas
            String sql = "INSERT INTO reservas (fkbusqueda_detalle, usuario_id) VALUES (?, ?)";

            // Preparar la sentencia
            stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, busquedaDetalleId);
            stmt.setInt(2, usuarioId);

            // Ejecutar la inserción
            int filasInsertadas = stmt.executeUpdate();
            if (filasInsertadas > 0) {
                JOptionPane.showMessageDialog(null, "Reserva guardada exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo guardar la reserva.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar la reserva: " + e.getMessage());
        } finally {
            // Cerrar la conexión y el statement
            try {
                if (stmt != null) stmt.close();
                objetoConexion.cerrarConexion();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    private int obtenerBusquedaDetalleId() {
        // Implementar la lógica para obtener el ID de la búsqueda de detalle seleccionada
        return 1; // Ejemplo de retorno, reemplazar con la lógica real
    }
}
