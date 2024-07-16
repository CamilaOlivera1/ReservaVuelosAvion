/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reservavuelosavion;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Estudiante
 */
public class Coneccion {
    Connection conectar = null;
    
    String usuario = "root";
    String contraseña = "";
    String bd = "BoletosAvion";
    String ip = "localhost";
    String puerto = "3306";
    
    String cadena = "jdbc:mysql://"+ip+":"+puerto+"/"+bd;
    
    public Connection estableceConexion(){
        
        try {
        
            Class.forName("com.mysql.cj.jdbc.Driver");
            conectar = DriverManager.getConnection(cadena,usuario,contraseña);
            //JOptionPane.showMessageDialog(null,"La conección se ha realizado con Éxito");
            
            
    }catch (Exception e){
        JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos, error: "+e.toString());
    }   
        return conectar;
    }

   /* Object establecerConexion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
    
    Object cerrarConexion(){
        try {
            if (conectar!= null && conectar.isClosed()) {
                conectar.close();
                JOptionPane.showMessageDialog(null, "Conexion cerrada");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo cerrar la Conexion");
        }
        return null;
    }
}
