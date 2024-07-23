/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reservavuelosavion;

import java.sql.Connection;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Estudiante
 */
public class Reserva {

    public void mostrarReservas(JTable tablaReservas, int usuarioId) {
        DefaultTableModel modelo = new DefaultTableModel();
        tablaReservas.setModel(modelo);

        modelo.addColumn("ID Reserva");
        modelo.addColumn("Usuario ID");
        modelo.addColumn("Fecha Reserva");
        modelo.addColumn("ID Detalle");
        modelo.addColumn("Aerolinea");
        modelo.addColumn("Horario");
        modelo.addColumn("Duracion");
        modelo.addColumn("Precio");

        String sql = "SELECT r.id AS reserva_id, r.usuario_id, r.fecha_reserva, bd.id AS detalle_id, bd.aerolinea, bd.horario, bd.duracion, bd.precio " +
                     "FROM reservas r " +
                     "JOIN busqueda_detalle bd ON r.fkbusqueda_detalle = bd.id " +
                     "WHERE r.usuario_id = ?";

        try (Connection conexion = new Coneccion().estableceConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setInt(1, usuarioId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Object[] fila = new Object[8];
                fila[0] = rs.getInt("reserva_id");
                fila[1] = rs.getInt("usuario_id");
                fila[2] = rs.getTimestamp("fecha_reserva");
                fila[3] = rs.getInt("detalle_id");
                fila[4] = rs.getString("aerolinea");
                fila[5] = rs.getTime("horario");
                fila[6] = rs.getTime("duracion");
                fila[7] = rs.getDouble("precio");
                modelo.addRow(fila);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar reservas: " + e.getMessage());
        }
    }
    // Mostrar reservas en la tabla
    /*public void mostrarReservas(JTable tablaReservas, int usuarioId) {
        DefaultTableModel modelo = new DefaultTableModel();
        tablaReservas.setModel(modelo);

        modelo.addColumn("ID");
        modelo.addColumn("FK Busqueda Detalle");
        modelo.addColumn("Usuario ID");
        modelo.addColumn("Fecha Reserva");

        String sql = "SELECT * FROM reservas WHERE usuario_id = ?";

        try (Connection conexion = new Coneccion().estableceConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setInt(1, usuarioId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Object[] fila = new Object[4];
                fila[0] = rs.getInt("id");
                fila[1] = rs.getInt("fkbusqueda_detalle");
                fila[2] = rs.getInt("usuario_id");
                fila[3] = rs.getTimestamp("fecha_reserva");
                modelo.addRow(fila);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar reservas: " + e.getMessage());
        }
    }
*/
    // Obtener ID de la reserva seleccionada
    public int obtenerIdReservaSeleccionada(JTable tablaReservas) {
        int fila = tablaReservas.getSelectedRow();
        if (fila >= 0) {
            return (int) tablaReservas.getValueAt(fila, 0); // Suponiendo que la primera columna es el ID
        } else {
            JOptionPane.showMessageDialog(null, "Fila no seleccionada");
            return -1; // Indica que no se ha seleccionado ninguna fila
        }
    }

    // Eliminar reserva
    public void eliminarReserva(int idReserva) {
        String consulta = "DELETE FROM reservas WHERE id = ?";

        try (Connection conexion = new Coneccion().estableceConexion();
             PreparedStatement pstmt = conexion.prepareStatement(consulta)) {

            pstmt.setInt(1, idReserva);

            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Reserva eliminada con éxito.");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar la reserva.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar la reserva: " + e.getMessage());
        }
    }

    // Actualizar tabla de reservas
    public void actualizarTablaReservas(JTable tablaReservas, int usuarioId) {
        mostrarReservas(tablaReservas, usuarioId);
    }
    
    // Acción del botón para eliminar reserva
    public void btnEliminarReservaActionPerformed(JTable tablaReservas, int usuarioId) {
        int idReserva = obtenerIdReservaSeleccionada(tablaReservas);
        if (idReserva != -1) {
            eliminarReserva(idReserva);
            actualizarTablaReservas(tablaReservas, usuarioId);
        }
    }
    
}
