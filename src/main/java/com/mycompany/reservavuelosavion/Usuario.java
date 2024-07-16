/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reservavuelosavion;

import com.toedter.calendar.JDateChooser;
import java.sql.CallableStatement;
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
public class Usuario {
    private int id;
    private String nombre;
    private String apellido;
    private String dni;
    private String nacionalidad;
    private String direccion;
    private String numeroPasaporte;
    private java.util.Date fechaNacimiento;
    private int fkgenero;
    private String correo;
    private String contrasena;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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

    public String getNumeroPasaporte() {
        return numeroPasaporte;
    }

    public void setNumeroPasaporte(String numeroPasaporte) {
        this.numeroPasaporte = numeroPasaporte;
    }

    public int getFkgenero() {
        return fkgenero;
    }

    public void setFkgenero(int fkgenero) {
        this.fkgenero = fkgenero;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    //getter y setter para fechaNacimiento
    public java.util.Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(java.util.Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }


    
public void RegistrarUsuario(JTextField paramNombre, JTextField paramApellido, JComboBox comboGenero,JTextField paramDNI, 
        JTextField paramPasaporte, JTextField paramNacionalidad, JTextField paramDireccion, JDateChooser paramFechaNac,
        JTextField paramTelefono, JTextField paramCorreo, JPasswordField paramContraseña) {
    
    String nombre = paramNombre.getText();
    String apellido = paramApellido.getText();
    String genero = comboGenero.getSelectedItem().toString();
    String dni = paramDNI.getText();
    String pasaporte = paramPasaporte.getText();
    String nacionalidad = paramNacionalidad.getText();
    String direccion = paramDireccion.getText();
    java.util.Date fechaNac = paramFechaNac.getDate();
    String telefono = paramTelefono.getText();
    String correo = paramCorreo.getText();
    String contraseña = new String(paramContraseña.getPassword());
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String fechaNacStr = sdf.format(fechaNac);
    
    Coneccion objetoConexion = new Coneccion();
    
    String sqlGenero = "SELECT id FROM genero WHERE descripción = ?";
    String consulta = "INSERT INTO registro (nombre, apellido, fkgenero, dni, numero_pasaporte, nacionalidad, direccion, fecha_nacimiento, telefono, correo, contraseña) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    
    try (Connection conexion = objetoConexion.estableceConexion();
         PreparedStatement pstGenero = conexion.prepareStatement(sqlGenero);
         PreparedStatement pst = conexion.prepareStatement(consulta)) {
        
        // Obtener el ID del género
        pstGenero.setString(1, genero);
        try (ResultSet rsGenero = pstGenero.executeQuery()) {
            int fkgenero = 0;
            if (rsGenero.next()) {
                fkgenero = rsGenero.getInt("id");
            }

            // Preparar la consulta SQL
            pst.setString(1, nombre);
            pst.setString(2, apellido);
            pst.setInt(3, fkgenero);
            pst.setString(4, dni);
            pst.setString(5, pasaporte);
            pst.setString(6, nacionalidad);
            pst.setString(7, direccion);
            pst.setString(8, fechaNacStr);
            pst.setString(9, telefono);
            pst.setString(10, correo);
            pst.setString(11, contraseña);
            
            // Ejecutar la consulta
            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "Registro exitoso");
            } else {
                JOptionPane.showMessageDialog(null, "Error al registrar usuario");
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al registrar usuario: " + e.getMessage());
    }
}

   
public void MostrarNuevoCombo(JComboBox comboGenero) {
    Coneccion objetoConexion = new Coneccion();
    String sql = "SELECT * FROM genero;";

    try (Connection conexion = objetoConexion.estableceConexion();
         Statement st = conexion.createStatement();
         ResultSet rs = st.executeQuery(sql)) {

        comboGenero.removeAllItems();
        
        while (rs.next()) {
            String nombreNuevo = rs.getString("Descripción");
            comboGenero.addItem(nombreNuevo);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "No se pudo mostrar combo, error: " + e.toString());
    }
}


public boolean InicioSesion(JTextField paramCorreo, JPasswordField paramContraseña) {
    String correo = paramCorreo.getText();
    String contrasena = new String(paramContraseña.getPassword());

    Coneccion objetoConexion = new Coneccion();
    Connection conexion = objetoConexion.estableceConexion();
    try {
        String consulta = "SELECT * FROM registro WHERE correo = ? AND contraseña = ?";
        PreparedStatement pst = conexion.prepareStatement(consulta);
        pst.setString(1, correo);
        pst.setString(2, contrasena);
        
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso");
            // Credenciales correctas: el usuario ha sido encontrado en la base de datos
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Correo o contraseña incorrectos");
            // Credenciales incorrectas: no se encontró ningún usuario con esas credenciales
            return false;
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al iniciar sesión: " + e.getMessage());
        return false;
    } finally {
        objetoConexion.cerrarConexion();
    }
}

public boolean OlvideMiContraseña(JTextField paramCorreo, JPasswordField paramNuevaContraseña, JPasswordField paramRepetirContraseña) {
    String correo = paramCorreo.getText();
    String nuevaContrasena = new String(paramNuevaContraseña.getPassword());
    String repetirContrasena = new String(paramRepetirContraseña.getPassword());

    // Validar que las contraseñas coincidan
    if (!nuevaContrasena.equals(repetirContrasena)) {
        JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden");
        return false;
    }

    Coneccion objetoConexion = new Coneccion();
    Connection conexion = objetoConexion.estableceConexion();
    try {
        // Verificar si el correo existe en la base de datos
        String consultaVerificacion = "SELECT * FROM registro WHERE correo = ?";
        PreparedStatement pstVerificacion = conexion.prepareStatement(consultaVerificacion);
        pstVerificacion.setString(1, correo);
        
        ResultSet rs = pstVerificacion.executeQuery();
        if (rs.next()) {
            // El correo existe, actualizar la contraseña
            String consultaActualizacion = "UPDATE registro SET contraseña = ? WHERE correo = ?";
            PreparedStatement pstActualizacion = conexion.prepareStatement(consultaActualizacion);
            pstActualizacion.setString(1, nuevaContrasena);
            pstActualizacion.setString(2, correo);
            
            int rowsUpdated = pstActualizacion.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Contraseña actualizada exitosamente");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar la contraseña");
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "El correo no está registrado");
            return false;
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al recuperar contraseña: " + e.getMessage());
        return false;
    } finally {
        objetoConexion.cerrarConexion();
    }
}


    // Método para recuperar contraseña
    public static void recuperarContrasena(String correo, String nuevaContrasena) {
        Coneccion conexion = new Coneccion();
        Connection con = conexion.estableceConexion();
        try {
            String query = "UPDATE registro SET contrasena = ? WHERE correo = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, nuevaContrasena);
            ps.setString(2, correo);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Contraseña actualizada exitosamente");
            } else {
                JOptionPane.showMessageDialog(null, "Correo no encontrado");
            }
            ps.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar contraseña: " + e.getMessage());
        }
    }
}
