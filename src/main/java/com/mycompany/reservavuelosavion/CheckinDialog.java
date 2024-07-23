/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reservavuelosavion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author Estudiante
 */
public class CheckinDialog extends JDialog {
    private JTextField txtDNI;
    private JButton btnConfirmar;
    private Reserva reserva;
    private int idReserva;

    public CheckinDialog(JFrame parent, boolean modal, Reserva reserva, int idReserva) {
        super(parent, modal);
        this.reserva = reserva;
        this.idReserva = idReserva;
        initComponents();
    }

    private void initComponents() {
        setTitle("Check-in en Línea");
        setSize(300, 150);
        setLocationRelativeTo(null);

        txtDNI = new JTextField(15);
        btnConfirmar = new JButton("Confirmar Check-in");

        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarCheckin();
            }
        });

        JPanel panel = new JPanel();
        panel.add(new JLabel("DNI del Pasajero:"));
        panel.add(txtDNI);
        panel.add(btnConfirmar);

        add(panel);
    }

    private void realizarCheckin() {
        String dni = txtDNI.getText().trim();

        if (dni.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa el DNI del pasajero.");
            return;
        }

        // Realizar el check-in
        reserva.realizarCheckin(idReserva);
        JOptionPane.showMessageDialog(this, "Check-in realizado con éxito.");

        // Cerrar el diálogo
        dispose();
    }
}
