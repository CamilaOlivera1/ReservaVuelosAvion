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
/*public void GuardarCredito(JTextField paramTitular, JTextField paramNumTarjetaCredito, JTextField paramMes, 
                           JTextField paramAño, JTextField paramCodSeguridad, JComboBox<String> comboCuotas) {
    String titular = paramTitular.getText();
    String numTarjCredito = paramNumTarjetaCredito.getText();
    int mes = Integer.parseInt(paramMes.getText());
    int año = Integer.parseInt(paramAño.getText());
    String codSeguridad = paramCodSeguridad.getText();
    String cuotas = comboCuotas.getSelectedItem().toString();

    Coneccion objetoConexion = new Coneccion();
    Connection conexion = null;
    PreparedStatement pstCuotas = null;
    PreparedStatement pstCredito = null;

    String sqlCuotas = "SELECT id FROM cuotas WHERE cantCuota = ?";
    String consultaCredito = "INSERT INTO credito (nomTitular, numTarjeta, codSeguridad, mesVenc, añoVenc, fkcuotas, fkreserva) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?);";

    try {
        conexion = objetoConexion.estableceConexion();

        // Obtener el ID del cuotas
        pstCuotas = conexion.prepareStatement(sqlCuotas);
        pstCuotas.setString(1, cuotas);
        ResultSet rsCuotas = pstCuotas.executeQuery();
        int fkcuotas = 0;
        if (rsCuotas.next()) {
            fkcuotas = rsCuotas.getInt("id");
        }
        rsCuotas.close();

        // Obtener el ID de reserva
        Pasajero pasajero = new Pasajero();
        int busquedaDetalleIdSeleccionada = 0;
        pasajero.setBusquedaDetalleIdSeleccionada(busquedaDetalleIdSeleccionada); // Usa el ID de búsqueda de vuelo seleccionado

        int reservaId = pasajero.guardarReserva();

        // Preparar la consulta SQL para la tabla `credito`
        pstCredito = conexion.prepareStatement(consultaCredito);
        pstCredito.setString(1, titular);
        pstCredito.setString(2, numTarjCredito);
        pstCredito.setString(3, codSeguridad);
        pstCredito.setInt(4, mes);
        pstCredito.setInt(5, año);
        pstCredito.setInt(6, fkcuotas);
        pstCredito.setInt(7, reservaId);

        // Ejecutar la consulta
        int rowsInserted = pstCredito.executeUpdate();
        if (rowsInserted > 0) {
            JOptionPane.showMessageDialog(null, "Pago exitoso");
        } else {
            JOptionPane.showMessageDialog(null, "Error al realizar Pago");
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al registrar Pago: " + e.getMessage());
    } finally {
        try {
            if (pstCuotas != null) pstCuotas.close();
            if (pstCredito != null) pstCredito.close();
            if (conexion != null) conexion.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
*/

    /*public void GuardarCredito(JTextField paramTitular, JTextField paramNumTarjetaCredito, JTextField paramMes, 
                           JTextField paramAño, JTextField paramCodSeguridad, JComboBox<String> comboCuotas, int reservaId) {
    String titular = paramTitular.getText();
    String numTarjCredito = paramNumTarjetaCredito.getText();
    int mes = Integer.parseInt(paramMes.getText());
    int año = Integer.parseInt(paramAño.getText());
    String codSeguridad = paramCodSeguridad.getText();
    String cuotas = comboCuotas.getSelectedItem().toString();

    // Obtener el precio de la reserva
    double precio = obtenerPrecioReserva(reservaId);

    // Mostrar el precio y confirmar el pago
    int confirmacion = JOptionPane.showConfirmDialog(null, 
        "El precio de la reserva es: $" + precio + ". ¿Desea proceder con el pago?", 
        "Confirmar Pago", 
        JOptionPane.YES_NO_OPTION);

    if (confirmacion == JOptionPane.YES_OPTION) {
        Coneccion objetoConexion = new Coneccion();

        String sqlCuotas = "SELECT id FROM cuotas WHERE cantCuota = ?";
        String consulta = "INSERT INTO credito (nomTitular, numTarjeta, codSeguridad, mesVenc, añoVenc, fkcuotas, fkreserva) " +
                          "VALUES (?, ?, ?, ?, ?, ?, ?);";

        try (Connection conexion = objetoConexion.estableceConexion();
             PreparedStatement pstCuotas = conexion.prepareStatement(sqlCuotas);
             PreparedStatement pst = conexion.prepareStatement(consulta)) {

            // Obtener el ID del cuotas
            pstCuotas.setString(1, cuotas);
            int fkcuotas = 0;
            try (ResultSet rsCuotas = pstCuotas.executeQuery()) {
                if (rsCuotas.next()) {
                    fkcuotas = rsCuotas.getInt("id");
                }
            }

            // Preparar la consulta SQL
            pst.setString(1, titular);
            pst.setString(2, numTarjCredito);
            pst.setString(3, codSeguridad);
            pst.setInt(4, mes);
            pst.setInt(5, año);
            pst.setInt(6, fkcuotas);
            pst.setInt(7, reservaId);

            // Ejecutar la consulta
            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "Pago exitoso");
            } else {
                JOptionPane.showMessageDialog(null, "Error al realizar el pago");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar el pago: " + e.getMessage());
        }
    } else {
        JOptionPane.showMessageDialog(null, "Pago cancelado");
    }
}*/


   /* public void processPayment(double amount) {
        // Aquí iría la lógica para procesar el pago con tarjeta de débito.
        System.out.println("Procesando pago con tarjeta de débito: $" + amount);*/
/*public void GuardarCredito(JTextField paramTitular, JTextField paramNumTarjetaCredito, JTextField paramMes, 
                           JTextField paramAño, JTextField paramCodSeguridad, JComboBox<String> comboCuotas, 
                           int busquedaDetalleId, int usuarioId) {
    String titular = paramTitular.getText();
    String numTarjCredito = paramNumTarjetaCredito.getText();
    int mes = Integer.parseInt(paramMes.getText());
    int año = Integer.parseInt(paramAño.getText());
    String codSeguridad = paramCodSeguridad.getText();
    String cuotas = comboCuotas.getSelectedItem().toString();

    Coneccion objetoConexion = new Coneccion();

    String sqlCuotas = "SELECT id FROM cuotas WHERE cantCuota = ?";
    String consultaReserva = "INSERT INTO reservas (fkbusqueda_detalle, usuario_id) VALUES (?, ?)";
    String consulta = "INSERT INTO credito (nomTitular, numTarjeta, codSeguridad, mesVenc, añoVenc, fkcuotas, fkreserva) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?);";

    try (Connection conexion = objetoConexion.estableceConexion();
         PreparedStatement pstCuotas = conexion.prepareStatement(sqlCuotas);
         PreparedStatement pstReserva = conexion.prepareStatement(consultaReserva, Statement.RETURN_GENERATED_KEYS);
         PreparedStatement pst = conexion.prepareStatement(consulta)) {

        // Obtener el ID de las cuotas
        pstCuotas.setString(1, cuotas);
        int fkcuotas = 0;
        try (ResultSet rsCuotas = pstCuotas.executeQuery()) {
            if (rsCuotas.next()) {
                fkcuotas = rsCuotas.getInt("id");
            } else {
                throw new SQLException("No se encontró el ID de las cuotas");
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

        // Insertar en la tabla credito
        pst.setString(1, titular);
        pst.setString(2, numTarjCredito);
        pst.setString(3, codSeguridad);
        pst.setInt(4, mes);
        pst.setInt(5, año);
        pst.setInt(6, fkcuotas);
        pst.setInt(7, reservaId); // Usa el reservaId obtenido

        int rowsInserted = pst.executeUpdate();
        if (rowsInserted > 0) {
            JOptionPane.showMessageDialog(null, "Pago exitoso");
        } else {
            JOptionPane.showMessageDialog(null, "Error al realizar el pago");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al registrar el pago: " + e.getMessage());
    }
}*/

 
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

public double obtenerPrecioReserva(int reservaId) {
    double precio = 0.0;
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        Coneccion objetoConexion = new Coneccion();
        con = objetoConexion.estableceConexion();
        
        // Consulta para obtener el precio de la reserva
        String sql = "SELECT bd.precio " +
                     "FROM reservas r " +
                     "JOIN busqueda_detalle bd ON r.fkbusqueda_detalle = bd.id " +
                     "WHERE r.id = ?";
        
        ps = con.prepareStatement(sql);
        ps.setInt(1, reservaId);
        rs = ps.executeQuery();
        
        if (rs.next()) {
            precio = rs.getDouble("precio");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    return precio;
}


    private int obtenerFkReserva() {
        // Implementa esta función para obtener el valor correspondiente de fkreserva
        // Este valor debería venir de la lógica de tu aplicación donde gestionas las reservas
        return 1; // Este es solo un valor de ejemplo
    }

}
