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

   /* public void mostrarReservas(JTable tablaReservas, int usuarioId) {
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
    */
    
    public void mostrarReservas(JTable jTable1, int usuarioId) {
    DefaultTableModel modelo = new DefaultTableModel();
    jTable1.setModel(modelo);

    modelo.addColumn("ID Reserva");
    modelo.addColumn("Usuario ID");
    modelo.addColumn("Fecha Reserva");
    modelo.addColumn("Fecha Viaje");
    modelo.addColumn("Cantidad Adultos");
    modelo.addColumn("Cantidad Niños");
    modelo.addColumn("Cantidad Bebes");
    modelo.addColumn("Clase");
    modelo.addColumn("Aerolinea");
    modelo.addColumn("Horario");
    modelo.addColumn("Duración");
    modelo.addColumn("Precio");

    String sql = "SELECT r.id AS reserva_id, r.usuario_id, r.fecha_reserva, b.fecha_viaje, b.cant_adultos, b.cant_niños, b.cant_bebes, c.detalle AS clase, " +
                 "bd.aerolinea, bd.horario, bd.duracion, bd.precio " +
                 "FROM reservas r " +
                 "JOIN busqueda_detalle bd ON r.fkbusqueda_detalle = bd.id " +
                 "JOIN busqueda b ON bd.fkbusqueda = b.id " +
                 "JOIN clase c ON b.fkclase = c.id " +
                 "WHERE r.usuario_id = ?";

    try (Connection conexion = new Coneccion().estableceConexion();
         PreparedStatement pstmt = conexion.prepareStatement(sql)) {

        pstmt.setInt(1, usuarioId);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Object[] fila = new Object[12];
            fila[0] = rs.getInt("reserva_id");
            fila[1] = rs.getInt("usuario_id");
            fila[2] = rs.getTimestamp("fecha_reserva");
            fila[3] = rs.getDate("fecha_viaje");
            fila[4] = rs.getInt("cant_adultos");
            fila[5] = rs.getInt("cant_niños");
            fila[6] = rs.getInt("cant_bebes");
            fila[7] = rs.getString("clase");
            fila[8] = rs.getString("aerolinea");
            fila[9] = rs.getTime("horario");
            fila[10] = rs.getTime("duracion");
            fila[11] = rs.getDouble("precio");
            modelo.addRow(fila);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al mostrar reservas: " + e.getMessage());
    }
}

    
    
    
    public void mostrarReservasFUNCIONA(JTable jTable1/*tablaReservas*/, int usuarioId) {
    DefaultTableModel modelo = new DefaultTableModel();
    jTable1.setModel(modelo);
    //tablaReservas.setModel(modelo);

    modelo.addColumn("ID Reserva");
    modelo.addColumn("Usuario ID");
    modelo.addColumn("Fecha Reserva");
    modelo.addColumn("Fecha Viaje");
    modelo.addColumn("Cantidad Adultos");
    modelo.addColumn("Cantidad Niños");
    modelo.addColumn("Cantidad Bebes");
    modelo.addColumn("Clase");
    modelo.addColumn("Aerolinea");
    modelo.addColumn("Horario");
    modelo.addColumn("Duración");
    modelo.addColumn("Precio");

    String sql = "SELECT r.id AS reserva_id, r.usuario_id, r.fecha_reserva, b.fecha_viaje, b.cant_adultos, b.cant_niños, b.cant_bebes, c.detalle AS clase, " +
                 "bd.aerolinea, bd.horario, bd.duracion, bd.precio " +
                 "FROM reservas r " +
                 "JOIN busqueda_detalle bd ON r.fkbusqueda_detalle = bd.id " +
                 "JOIN busqueda b ON bd.fkbusqueda = b.id " +
                 "JOIN clase c ON b.fkclase = c.id " +
                 "WHERE r.usuario_id = ?";

    try (Connection conexion = new Coneccion().estableceConexion();
         PreparedStatement pstmt = conexion.prepareStatement(sql)) {

        pstmt.setInt(1, usuarioId);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Object[] fila = new Object[12];
            fila[0] = rs.getInt("reserva_id");
            fila[1] = rs.getInt("usuario_id");
            fila[2] = rs.getTimestamp("fecha_reserva");
            fila[3] = rs.getDate("fecha_viaje");
            fila[4] = rs.getInt("cant_adultos");
            fila[5] = rs.getInt("cant_niños");
            fila[6] = rs.getInt("cant_bebes");
            fila[7] = rs.getString("clase");
            fila[8] = rs.getString("aerolinea");
            fila[9] = rs.getTime("horario");
            fila[10] = rs.getTime("duracion");
            fila[11] = rs.getDouble("precio");
            modelo.addRow(fila);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al mostrar reservas: " + e.getMessage());
    }
}
    
    
public void actualizarFechaViaje(int idBusqueda, java.sql.Date nuevaFechaViaje) {
    String consulta = "UPDATE busqueda SET fecha_viaje = ? WHERE id = ?";

    try (Connection conexion = new Coneccion().estableceConexion();
         PreparedStatement pstmt = conexion.prepareStatement(consulta)) {

        pstmt.setDate(1, nuevaFechaViaje);
        pstmt.setInt(2, idBusqueda);

        System.out.println("Ejecutando consulta: " + pstmt.toString()); // Mensaje de depuración

        int rowsUpdated = pstmt.executeUpdate();
        if (rowsUpdated > 0) {
            JOptionPane.showMessageDialog(null, "Fecha de viaje actualizada con éxito.");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo actualizar la fecha de viaje.");
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al actualizar la fecha de viaje: " + e.getMessage());
    }
}





 //ESTO DE ABAJO PUEDE LLEGAR A FUNCIONAR, PERO NO MUESTRA LA FECHA MODIFICADA 
/*public void mostrarReservas(JTable tablaReservas, int usuarioId) {
        DefaultTableModel modelo = new DefaultTableModel();
        tablaReservas.setModel(modelo);

        modelo.addColumn("ID Reserva");
        modelo.addColumn("Usuario ID");
        modelo.addColumn("Fecha Reserva");
        modelo.addColumn("Fecha Viaje");
        modelo.addColumn("Cantidad Adultos");
        modelo.addColumn("Cantidad Niños");
        modelo.addColumn("Cantidad Bebes");
        modelo.addColumn("Clase");
        modelo.addColumn("Aerolinea");
        modelo.addColumn("Horario");
        modelo.addColumn("Duración");
        modelo.addColumn("Precio");

        String sql = "SELECT r.id AS reserva_id, r.usuario_id, r.fecha_reserva, b.fecha_viaje, b.cant_adultos, b.cant_niños, b.cant_bebes, c.detalle AS clase, " +
                     "bd.aerolinea, bd.horario, bd.duracion, bd.precio " +
                     "FROM reservas r " +
                     "JOIN busqueda_detalle bd ON r.fkbusqueda_detalle = bd.id " +
                     "JOIN busqueda b ON bd.fkbusqueda = b.id " +
                     "JOIN clase c ON b.fkclase = c.id " +
                     "WHERE r.usuario_id = ?";

        try (Connection conexion = new Coneccion().estableceConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setInt(1, usuarioId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Object[] fila = new Object[12];
                fila[0] = rs.getInt("reserva_id");
                fila[1] = rs.getInt("usuario_id");
                fila[2] = rs.getTimestamp("fecha_reserva");
                fila[3] = rs.getDate("fecha_viaje");
                fila[4] = rs.getInt("cant_adultos");
                fila[5] = rs.getInt("cant_niños");
                fila[6] = rs.getInt("cant_bebes");
                fila[7] = rs.getString("clase");
                fila[8] = rs.getString("aerolinea");
                fila[9] = rs.getTime("horario");
                fila[10] = rs.getTime("duracion");
                fila[11] = rs.getDouble("precio");
                modelo.addRow(fila);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar reservas: " + e.getMessage());
        }
    }

    public void actualizarFechaViaje(int idBusqueda, java.sql.Date nuevaFechaViaje) {
        String consulta = "UPDATE busqueda SET fecha_viaje = ? WHERE id = ?";

        try (Connection conexion = new Coneccion().estableceConexion();
             PreparedStatement pstmt = conexion.prepareStatement(consulta)) {

            pstmt.setDate(1, nuevaFechaViaje);
            pstmt.setInt(2, idBusqueda);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Fecha de viaje actualizada con éxito.");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo actualizar la fecha de viaje.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar la fecha de viaje: " + e.getMessage());
        }
    }*/
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
    public int obtenerIdReservaSeleccionada(JTable jTable1/*tablaReservas*/) {
        int fila = /*tablaReservas*/jTable1.getSelectedRow();
        if (fila >= 0) {
            return (int) /*tablaReservas*/jTable1.getValueAt(fila, 0); // Suponiendo que la primera columna es el ID
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
public void actualizarTablaReservas(JTable jTable1, int usuarioId) {
    System.out.println("Actualizando tabla de reservas para el usuario ID: " + usuarioId);
    mostrarReservas(jTable1, usuarioId);
    jTable1.repaint();
    jTable1.revalidate();
}

    // Actualizar tabla de reservas
    
    /*public void actualizarTablaReservas(JTable jTable1/*tablaReservas, int usuarioId) {
        mostrarReservas(/*tablaReservas jTable1, usuarioId);
    }*/
    
    // Acción del botón para eliminar reserva
    public void btnEliminarReservaActionPerformed(JTable /*tablaReservas*/jTable1, int usuarioId) {
        int idReserva = obtenerIdReservaSeleccionada(/*tablaReservas*/jTable1);
        if (idReserva != -1) {
            eliminarReserva(idReserva);
            actualizarTablaReservas(/*tablaReservas*/jTable1, usuarioId);
        }
    }
    
   /* public void actualizarFechaViaje(int idBusqueda, Date nuevaFechaViaje) {
        String consulta = "UPDATE busqueda SET fecha_viaje = ? WHERE id = ?";

        try (Connection conexion = new Coneccion().estableceConexion();
             PreparedStatement pstmt = conexion.prepareStatement(consulta)) {

            pstmt.setDate(1, nuevaFechaViaje);
            pstmt.setInt(2, idBusqueda);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Fecha de viaje actualizada con éxito.");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo actualizar la fecha de viaje.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar la fecha de viaje: " + e.getMessage());
        }
    }*/

    
     public void mostrarInforrmacionVuelos(JTable tablaReservas, int usuarioId) {
    DefaultTableModel modelo = new DefaultTableModel();
    tablaReservas.setModel(modelo);

   // modelo.addColumn("ID Reserva");
    //modelo.addColumn("Usuario ID");
    modelo.addColumn("Fecha Reserva");
    modelo.addColumn("Fecha Viaje");
    modelo.addColumn("Cantidad Adultos");
    modelo.addColumn("Cantidad Niños");
    modelo.addColumn("Cantidad Bebes");
    modelo.addColumn("Clase");
    modelo.addColumn("Aerolinea");
    modelo.addColumn("Horario");
    modelo.addColumn("Duración");
    modelo.addColumn("Precio");

    String sql = "SELECT r.id AS reserva_id, r.usuario_id, r.fecha_reserva, b.fecha_viaje, b.cant_adultos, b.cant_niños, b.cant_bebes, c.detalle AS clase, " +
                 "bd.aerolinea, bd.horario, bd.duracion, bd.precio " +
                 "FROM reservas r " +
                 "JOIN busqueda_detalle bd ON r.fkbusqueda_detalle = bd.id " +
                 "JOIN busqueda b ON bd.fkbusqueda = b.id " +
                 "JOIN clase c ON b.fkclase = c.id " +
                 "WHERE r.usuario_id = ?";

    try (Connection conexion = new Coneccion().estableceConexion();
         PreparedStatement pstmt = conexion.prepareStatement(sql)) {

        pstmt.setInt(1, usuarioId);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Object[] fila = new Object[12];
            fila[0] = rs.getInt("reserva_id");
            fila[1] = rs.getInt("usuario_id");
            fila[2] = rs.getTimestamp("fecha_reserva");
            fila[3] = rs.getDate("fecha_viaje");
            fila[4] = rs.getInt("cant_adultos");
            fila[5] = rs.getInt("cant_niños");
            fila[6] = rs.getInt("cant_bebes");
            fila[7] = rs.getString("clase");
            fila[8] = rs.getString("aerolinea");
            fila[9] = rs.getTime("horario");
            fila[10] = rs.getTime("duracion");
            fila[11] = rs.getDouble("precio");
            modelo.addRow(fila);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al mostrar reservas: " + e.getMessage());
    }
}
    
}
