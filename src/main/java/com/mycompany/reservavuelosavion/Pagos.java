/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reservavuelosavion;

import com.toedter.calendar.JDateChooser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

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
    
        public void MostrarComboCuotas(JComboBox comboCuotas) {
    Coneccion objetoConexion = new Coneccion();
    String sql = "SELECT * FROM cuotas;";

    try (Connection conexion = objetoConexion.estableceConexion();
         Statement st = conexion.createStatement();
         ResultSet rs = st.executeQuery(sql)) {

        comboCuotas.removeAllItems();
        
        while (rs.next()) {
            String nombreNuevo = rs.getString("cantCuota");
            comboCuotas.addItem(nombreNuevo);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "No se pudo mostrar combo metodo de Pago, error: " + e.toString());
    }
}
    private String titular;
    private String numTarjetaCredito;
    private String numTarjetaDebito;
    private int fechaMes;
    private int fechaAño;
    private String codSeguridad;
    private int fkcuotas;
    
    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getNumTarjetaCredito() {
        return numTarjetaCredito;
    }

    public void setNumTarjetaCredito(String numTarjetaCredito) {
        this.numTarjetaCredito = numTarjetaCredito;
    }

    public int getFechaMes() {
        return fechaMes;
    }

    public void setFechaMes(int fechaMes) {
        this.fechaMes = fechaMes;
    }

    public int getFechaAño() {
        return fechaAño;
    }

    public void setFechaAño(int fechaAño) {
        this.fechaAño = fechaAño;
    }

    public String getCodSeguridad() {
        return codSeguridad;
    }

    public void setCodSeguridad(String codSeguridad) {
        this.codSeguridad = codSeguridad;
    }

    public int getFkcuotas() {
        return fkcuotas;
    }

    public void setFkcuotas(int fkcuotas) {
        this.fkcuotas = fkcuotas;
    }
        public String getNumTarjetaDebito() {
        return numTarjetaDebito;
    }

    public void setNumTarjetaDebito(String numTarjetaDebito) {
        this.numTarjetaDebito = numTarjetaDebito;
    }

   /* public void processPayment(double amount) {
        // Aquí iría la lógica para procesar el pago con tarjeta de débito.
        System.out.println("Procesando pago con tarjeta de débito: $" + amount);*/
 public void GuardarCredito(JTextField paramTitular, JTextField paramNumTarjetaCredito, JTextField paramMes, 
                               JTextField paramAño, JTextField paramCodSeguridad, JComboBox<String> comboCuotas) {
        String titular = paramTitular.getText();
        String numTarjCredito = paramNumTarjetaCredito.getText();
        int mes = Integer.parseInt(paramMes.getText());
        int año = Integer.parseInt(paramAño.getText());
        String codSeguridad = paramCodSeguridad.getText();
        String cuotas = comboCuotas.getSelectedItem().toString();

        Coneccion objetoConexion = new Coneccion();

        String sqlCuotas = "SELECT id FROM cuotas WHERE cantCuota = ?";
        String consulta = "INSERT INTO credito (nomTitular, numTarjeta, codSeguridad, mesVenc, añoVenc, fkcuotas, fkreserva) " +
                          "VALUES (?, ?, ?, ?, ?, ?, ?);";

        try (Connection conexion = objetoConexion.estableceConexion();
             PreparedStatement pstCuotas = conexion.prepareStatement(sqlCuotas);
             PreparedStatement pst = conexion.prepareStatement(consulta)) {

            // Obtener el ID del cuotas
            pstCuotas.setString(1, cuotas);
            try (ResultSet rsCuotas = pstCuotas.executeQuery()) {
                int fkcuotas = 0;
                if (rsCuotas.next()) {
                    fkcuotas = rsCuotas.getInt("id");
                }

                // Preparar la consulta SQL
                pst.setString(1, titular);
                pst.setString(2, numTarjCredito);
                pst.setString(3, codSeguridad);
                pst.setInt(4, mes);
                pst.setInt(5, año);
                pst.setInt(6, fkcuotas);
                pst.setInt(7, obtenerFkReserva()); // Aquí debes obtener el fkreserva correspondiente

                // Ejecutar la consulta
                int rowsInserted = pst.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(null, "Pago exitoso");
                    
                    
                } else {
                    JOptionPane.showMessageDialog(null, "Error al realizar Pago");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar Pago: " + e.getMessage());
        }
    }
 
public void GuardarDebito(JTextField paramTitular, JTextField paramNumTarjetaCredito, JTextField paramMes, 
                          JTextField paramAño, JTextField paramCodSeguridad, int reservaId) {
    String titular = paramTitular.getText();
    String numTarjCredito = paramNumTarjetaCredito.getText();
    int mes = Integer.parseInt(paramMes.getText());
    int año = Integer.parseInt(paramAño.getText());
    String codSeguridad = paramCodSeguridad.getText();

    Coneccion objetoConexion = new Coneccion();

    String consulta = "INSERT INTO debito (nomTitular, numTarjeta, codSeguridad, mesVenc, añoVenc, fkreserva) " +
                      "VALUES (?, ?, ?, ?, ?, ?);";

    try (Connection conexion = objetoConexion.estableceConexion();
         PreparedStatement pst = conexion.prepareStatement(consulta)) {

        // Preparar la consulta SQL
        pst.setString(1, titular);
        pst.setString(2, numTarjCredito);
        pst.setString(3, codSeguridad);
        pst.setInt(4, mes);
        pst.setInt(5, año);
        pst.setInt(6, reservaId); // Aquí debes obtener el fkreserva correspondiente

        // Ejecutar la consulta
        int rowsInserted = pst.executeUpdate();
        if (rowsInserted > 0) {
            JOptionPane.showMessageDialog(null, "Pago exitoso");
        } else {
            JOptionPane.showMessageDialog(null, "Error al realizar Pago");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al registrar Pago: " + e.getMessage());
    }
}


    private int obtenerFkReserva() {
        // Implementa esta función para obtener el valor correspondiente de fkreserva
        // Este valor debería venir de la lógica de tu aplicación donde gestionas las reservas
        return 1; // Este es solo un valor de ejemplo
    }

}
