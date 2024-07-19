/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reservavuelosavion;

/**
 *
 * @author Estudiante
 */
public class SesionUtil {
        private static int usuarioId;

    // Método para establecer el ID del usuario autenticado
    public static void establecerIdUsuarioAutenticado(int id) {
        usuarioId = id;
    }

    // Método para obtener el ID del usuario autenticado
    public static int obtenerIdUsuarioAutenticado() {
        return usuarioId;
    }
}
