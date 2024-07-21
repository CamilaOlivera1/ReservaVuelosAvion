/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reservavuelosavion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Estudiante
 */
public class Pagos {
    public void MostrarComboMetPagos(JComboBox comboMetPago) {
    Coneccion objetoConexion = new Coneccion();
    String sql = "SELECT * FROM MetododePago;";

    try (Connection conexion = objetoConexion.estableceConexion();
         Statement st = conexion.createStatement();
         ResultSet rs = st.executeQuery(sql)) {

        comboMetPago.removeAllItems();
        
        while (rs.next()) {
            String nombreNuevo = rs.getString("nombre");
            comboMetPago.addItem(nombreNuevo);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "No se pudo mostrar combo metodo de Pago, error: " + e.toString());
    }
}
    
}
