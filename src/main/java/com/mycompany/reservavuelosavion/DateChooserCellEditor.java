/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reservavuelosavion;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.Date;
/**
 *
 * @author Estudiante
 */
public class DateChooserCellEditor extends AbstractCellEditor implements TableCellEditor {

    private JDateChooser dateChooser;

    public DateChooserCellEditor() {
        dateChooser = new JDateChooser();
    }

    @Override
    public Object getCellEditorValue() {
        return dateChooser.getDate();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        dateChooser.setDate((Date) value);
        return dateChooser;
    }
}
