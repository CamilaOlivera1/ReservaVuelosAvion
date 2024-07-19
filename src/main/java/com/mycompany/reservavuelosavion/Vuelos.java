/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reservavuelosavion;

import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Estudiante
 */
public class Vuelos {
    private int id;
    private int fkorigen;
    private int fkdestino;
    private java.util.Date fechaViaje;
    private int cantAdultos;
    private int cantNiños;
    private int cantBebes;
    private String aerolinea;
    private String horario;
    private String duracion;
    private double precio;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFkorigen() {
        return fkorigen;
    }

    public void setFkorigen(int fkorigen) {
        this.fkorigen = fkorigen;
    }

    public int getFkdestino() {
        return fkdestino;
    }

    public void setFkdestino(int fkdestino) {
        this.fkdestino = fkdestino;
    }
    
    public Date getFechaViaje() {
        return fechaViaje;
    }

    public void setFechaViaje(Date fechaViaje) {
        this.fechaViaje = fechaViaje;
    }
        public int getCantAdultos() {
        return cantAdultos;
    }

    public void setCantAdultos(int cantAdultos) {
        this.cantAdultos = cantAdultos;
    }

    public int getCantNiños() {
        return cantNiños;
    }

    public void setCantNiños(int cantNiños) {
        this.cantNiños = cantNiños;
    }

    public int getCantBebes() {
        return cantBebes;
    }

    public void setCantBebes(int cantBebes) {
        this.cantBebes = cantBebes;
    }

    public String getAerolinea() {
        return aerolinea;
    }

    public void setAerolinea(String aerolinea) {
        this.aerolinea = aerolinea;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
public int BusquedaVuelos(JComboBox<String> comboOrigen, JComboBox<String> comboDestino, JDateChooser fechaDeViaje, JSpinner jSpinnerMayores, JSpinner jSpinnerNiños, JSpinner jSpinnerBebes, JComboBox<String> comboClase) {
    Coneccion coneccion = new Coneccion();
    String sqlInsertBusqueda = "INSERT INTO busqueda (fkorigen, fkdestino, fecha_viaje, fkclase, cant_adultos, cant_niños, cant_bebes) VALUES (?, ?, ?, ?, ?, ?, ?)";

    // Obtener valores de los componentes
    String origen = comboOrigen.getSelectedItem() != null ? comboOrigen.getSelectedItem().toString() : "";
    String destino = comboDestino.getSelectedItem() != null ? comboDestino.getSelectedItem().toString() : "";
    Date fechaViajeDate = fechaDeViaje.getDate();
    String clase = comboClase.getSelectedItem() != null ? comboClase.getSelectedItem().toString() : "";
    int adultos = (int) jSpinnerMayores.getValue();
    int niños = (int) jSpinnerNiños.getValue();
    int bebes = (int) jSpinnerBebes.getValue();

    // Validar entradas
    if (adultos <= 0) {
        JOptionPane.showMessageDialog(null, "Debe haber al menos un adulto.");
        return -1;
    }

    if (adultos < 0 || niños < 0 || bebes < 0) {
        JOptionPane.showMessageDialog(null, "La cantidad de pasajeros no puede ser negativa.");
        return -1;
    }

    if (origen.isEmpty() || destino.isEmpty() || fechaViajeDate == null || clase.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos correctamente.");
        return -1;
    }
        // Validar que la fecha no haya pasado
    Calendar fechaActual = GregorianCalendar.getInstance();
    fechaActual.set(Calendar.HOUR_OF_DAY, 0);
    fechaActual.set(Calendar.MINUTE, 0);
    fechaActual.set(Calendar.SECOND, 0);
    fechaActual.set(Calendar.MILLISECOND, 0);

    Calendar fechaViaje = GregorianCalendar.getInstance();
    fechaViaje.setTime(fechaViajeDate);
    fechaViaje.set(Calendar.HOUR_OF_DAY, 0);
    fechaViaje.set(Calendar.MINUTE, 0);
    fechaViaje.set(Calendar.SECOND, 0);
    fechaViaje.set(Calendar.MILLISECOND, 0);

    if (fechaViaje.before(fechaActual)) {
        JOptionPane.showMessageDialog(null, "La fecha del viaje no puede ser anterior a la fecha actual.");
        return -1;
    }

    int origenId = ObtenerIdPorNombre("origen", origen);
    int destinoId = ObtenerIdPorNombre("destino", destino);
    java.sql.Date fechaViajeSql = new java.sql.Date(fechaViajeDate.getTime());
    int claseId = ObtenerIdPorNombre("clase", clase);

    try (Connection conexionBD = coneccion.estableceConexion();
         PreparedStatement pst = conexionBD.prepareStatement(sqlInsertBusqueda, Statement.RETURN_GENERATED_KEYS)) {

        pst.setInt(1, origenId);
        pst.setInt(2, destinoId);
        pst.setDate(3, fechaViajeSql);
        pst.setInt(4, claseId);
        pst.setInt(5, adultos);
        pst.setInt(6, niños);
        pst.setInt(7, bebes);

        int affectedRows = pst.executeUpdate();

        if (affectedRows > 0) {
            ResultSet generatedKeys = pst.getGeneratedKeys();
            if (generatedKeys.next()) {
                int busquedaId = generatedKeys.getInt(1);
                GuardarDetallesBusqueda(busquedaId, origenId, destinoId, fechaViajeDate, adultos, niños, bebes, claseId);
                return busquedaId;
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al realizar búsqueda: " + e.getMessage());
    }

    return -1;
}

public void MostrarOrigenCombo(JComboBox<String> comboOrigen) {
    Coneccion coneccion = new Coneccion();
    String sql = "SELECT detalle FROM origen";

    try (Connection conexionBD = coneccion.estableceConexion();
         PreparedStatement pst = conexionBD.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {

        comboOrigen.removeAllItems();

        while (rs.next()) {
            String nombreNuevo = rs.getString("detalle");
            comboOrigen.addItem(nombreNuevo);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "No se pudo mostrar comboOrigen, error: " + e.toString());
    }
}

public void MostrarDestinoCombo(JComboBox<String> comboDestino) {
    Coneccion coneccion = new Coneccion();
    String sql = "SELECT detalle FROM destino";

    try (Connection conexionBD = coneccion.estableceConexion();
         PreparedStatement pst = conexionBD.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {

        comboDestino.removeAllItems();

        while (rs.next()) {
            String nombreNuevo = rs.getString("detalle");
            comboDestino.addItem(nombreNuevo);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "No se pudo mostrar comboDestino, error: " + e.toString());
    }
}

public void MostrarClaseCombo(JComboBox<String> comboClase) {
    Coneccion coneccion = new Coneccion();
    String sql = "SELECT detalle FROM clase";

    try (Connection conexionBD = coneccion.estableceConexion();
         PreparedStatement pst = conexionBD.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {

        comboClase.removeAllItems();

        while (rs.next()) {
            String nombreNuevo = rs.getString("detalle");
            comboClase.addItem(nombreNuevo);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "No se pudo mostrar comboClase, error: " + e.toString());
    }
}


public int GuardarDetallesBusqueda(int busquedaId, int origenId, int destinoId, Date fecha, int adultos, int niños, int bebés, int claseId) {
    Coneccion coneccion = new Coneccion();
    String sqlDetalle = "INSERT INTO busqueda_detalle (fkbusqueda, aerolinea, horario, duracion, precio) VALUES (?, ?, ?, ?, ?)";
    
    Random random = new Random();
    String[] aerolineas = {"Aerolinea1", "Aerolinea2", "Aerolinea3"};
    String[] horarios = {"06:00:00", "08:00:00", "10:00:00", "12:00:00", "14:00:00", "16:00:00", "18:00:00"};
    
    // Determinar precio base según destino y clase
    int precioBase;
    String destinoNombre = ObtenerNombrePorId("destino", destinoId);
    if (destinoNombre.equals("Córdoba")) {
        precioBase = 60000;
    } else if (destinoNombre.equals("Buenos Aires")) {
        precioBase = 80000;
    } else if (destinoNombre.equals("Bariloche")) {
        precioBase = 200000;
    } else {
        JOptionPane.showMessageDialog(null, "Destino no válido");
        return -1;
    }
    
    String claseNombre = ObtenerNombrePorId("clase", claseId);
    if (claseNombre.equals("Premium")) {
        precioBase *= 1.4;
    }
    
    int precioTotal = precioBase * adultos + (int) (precioBase * 0.6 * niños); // Bebés viajan gratis
    
    // Lista para mantener las aerolíneas ya seleccionadas
    List<String> aerolineasSeleccionadas = new ArrayList<>();
    
    try (Connection conexionBD = coneccion.estableceConexion();
         PreparedStatement pstDetalle = conexionBD.prepareStatement(sqlDetalle)) {
        
        // Insertar en tabla 'busqueda_detalle'
        for (int i = 0; i < 2; i++) {
            String aerolinea;
            do {
                aerolinea = aerolineas[random.nextInt(aerolineas.length)];
            } while (aerolineasSeleccionadas.contains(aerolinea));
            
            aerolineasSeleccionadas.add(aerolinea);
            
            String horario = horarios[random.nextInt(horarios.length)];
            String duracion = destinoNombre.equals("Córdoba") ? "01:00:00" :
                              destinoNombre.equals("Buenos Aires") ? "01:30:00" : "02:00:00";
            
            pstDetalle.setInt(1, busquedaId);
            pstDetalle.setString(2, aerolinea);
            pstDetalle.setString(3, horario);
            pstDetalle.setString(4, duracion);
            pstDetalle.setInt(5, precioTotal);
            pstDetalle.executeUpdate();
        }
        
        return busquedaId;
        
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al guardar detalles de búsqueda: " + e.getMessage());
        return -1;
    }
}

public void MostrarResultadosBusquedaConSeleccion(JTable tabla, int busquedaId) {
    Coneccion objetoConexion = new Coneccion();
    Connection conexion = objetoConexion.estableceConexion();
    DefaultTableModel model = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 0; // Hacer que solo la primera columna sea editable (para el botón)
        }
    };

    String sql = "SELECT b.fkclase, b.cant_adultos, b.cant_niños, b.cant_bebes, o.detalle AS origen, d.detalle AS destino, b.fecha_viaje, bd.aerolinea, bd.horario, bd.duracion, bd.precio FROM busqueda b JOIN origen o ON b.fkorigen = o.id JOIN destino d ON b.fkdestino = d.id JOIN busqueda_detalle bd ON b.id = bd.fkbusqueda WHERE b.id = ?";

    model.addColumn("Seleccionar");
    model.addColumn("Clase");
    model.addColumn("Cant. Adultos");
    model.addColumn("Cant. Niños");
    model.addColumn("Cant. Bebés");
    model.addColumn("Origen");
    model.addColumn("Destino");
    model.addColumn("Fecha Viaje");
    model.addColumn("Aerolínea");
    model.addColumn("Horario");
    model.addColumn("Duración");
    model.addColumn("Precio");

    tabla.setModel(model);

    try (PreparedStatement pst = conexion.prepareStatement(sql)) {
        pst.setInt(1, busquedaId);

        try (ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                model.addRow(new Object[]{
                    "Seleccionar",
                    ObtenerNombrePorId("clase", rs.getInt("fkclase")),
                    rs.getInt("cant_adultos"),
                    rs.getInt("cant_niños"),
                    rs.getInt("cant_bebes"),
                    rs.getString("origen"),
                    rs.getString("destino"),
                    rs.getDate("fecha_viaje"),
                    rs.getString("aerolinea"),
                    rs.getString("horario"),
                    rs.getString("duracion"),
                    rs.getInt("precio")
                });
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al mostrar los resultados de la búsqueda: " + e.toString());
    }
}

public class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText((value == null) ? "" : value.toString());
        return this;
    }
}

public class ButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String label;
    private boolean isPushed;
    private JTable table;

    public ButtonEditor(JCheckBox checkBox, JTable table) {
        super(checkBox);
        this.table = table;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            // Accion del botón
            int row = table.getSelectedRow();
            ConfirmarSeleccion(table, row);
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}

public void ConfirmarSeleccion(JTable table, int row) {
    String clase = table.getValueAt(row, 1).toString();
    int adultos = Integer.parseInt(table.getValueAt(row, 2).toString());
    int niños = Integer.parseInt(table.getValueAt(row, 3).toString());
    int bebes = Integer.parseInt(table.getValueAt(row, 4).toString());
    String origen = table.getValueAt(row, 5).toString();
    String destino = table.getValueAt(row, 6).toString();
    String fechaViaje = table.getValueAt(row, 7).toString();
    String aerolinea = table.getValueAt(row, 8).toString();
    String horario = table.getValueAt(row, 9).toString();
    String duracion = table.getValueAt(row, 10).toString();
    String precio = table.getValueAt(row, 11).toString();

    int confirmar = JOptionPane.showConfirmDialog(null, "¿Desea confirmar la siguiente selección?\n\n" +
            "Clase: " + clase + "\n" +
            "Adultos: " + adultos + "\n" +
            "Niños: " + niños + "\n" +
            "Bebés: " + bebes + "\n" +
            "Origen: " + origen + "\n" +
            "Destino: " + destino + "\n" +
            "Fecha de Viaje: " + fechaViaje + "\n" +
            "Aerolínea: " + aerolinea + "\n" +
            "Horario: " + horario + "\n" +
            "Duración: " + duracion + "\n" +
            "Precio: " + precio, "Confirmar Selección", JOptionPane.YES_NO_OPTION);

    if (confirmar == JOptionPane.YES_OPTION) {
        // Aquí puedes manejar lo que sucede después de confirmar la selección
        JOptionPane.showMessageDialog(null, "Selección confirmada.");
        // Navegar a la siguiente pantalla o realizar la acción correspondiente
    }
}


public void guardarSeleccionVuelo(int busquedaId, String aerolinea, String horario) {
    // Lógica para guardar la selección del vuelo en la base de datos
    // Esto puede implicar una actualización en la tabla 'busqueda' o una inserción en otra tabla relacionada
    Connection con = null;
    PreparedStatement ps = null;

    try {
        Coneccion objetoConexion = new Coneccion();
        con = objetoConexion.estableceConexion();
        
        // Supongamos que estás actualizando una tabla llamada 'busqueda_detalle'
        String sql = "UPDATE busqueda_detalle SET aerolinea = ?, horario = ? WHERE id = ?";
        ps = con.prepareStatement(sql);
        ps.setString(1, aerolinea);
        ps.setString(2, horario);
        ps.setInt(3, busquedaId);
        ps.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

   /* public void GuardarSeleccionVuelo(int idBusqueda, String aerolinea, String horario) {
        Coneccion objetoConexion = new Coneccion();
        try (Connection conexion = objetoConexion.estableceConexion();
             PreparedStatement pstmt = conexion.prepareStatement("UPDATE busqueda_detalle SET aerolinea = ?, horario = ? WHERE fkbusqueda = ?")) {
            pstmt.setString(1, aerolinea);
            pstmt.setString(2, horario);
            pstmt.setInt(3, idBusqueda);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar la selección del vuelo: " + e.getMessage());
        }
    }*/




public String ObtenerNombrePorId(String tabla, int id) {
    Coneccion objetoConexion = new Coneccion();
    String nombre = null;
    String consulta = "SELECT detalle FROM " + tabla + " WHERE id = ?";

    try (Connection conexion = objetoConexion.estableceConexion();
         PreparedStatement pst = conexion.prepareStatement(consulta)) {

        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            nombre = rs.getString("detalle");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el detalle para ID: " + id + " en la tabla: " + tabla);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al obtener detalle por ID: " + e.getMessage());
    }

    return nombre;
}

public int ObtenerIdPorNombre(String tabla, String detalle) {
    Coneccion objetoConexion = new Coneccion();
    int id = -1;
    String consulta = "SELECT id FROM " + tabla + " WHERE detalle = ?";

    try (Connection conexion = objetoConexion.estableceConexion();
         PreparedStatement pst = conexion.prepareStatement(consulta)) {

        pst.setString(1, detalle);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            id = rs.getInt("id");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el ID para el detalle: " + detalle + " en la tabla: " + tabla);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al obtener ID por nombre: " + e.getMessage());
    }

    return id;
}


}