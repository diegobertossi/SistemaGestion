package presentacion.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class VentanaBusquedaEquipo extends JDialog {
    public VentanaBusquedaEquipo(JFrame parent) {
        super(parent, "Buscar Equipo", true);
        setSize(400, 200);
        setLayout(new GridLayout(4, 2, 10, 10));

        // Lista de campos disponibles para búsqueda
        String[] searchFields = {"Cliente", "Sucursal", "Equipo", "Marca", "Modelo", "N° de serie", "Cliente de cliente",
                "Remito de cliente", "Aviso de cliente", "Falla", "Diagnóstico", "Informe técnico"};

        // JComboBox para seleccionar el campo
        JComboBox<String> fieldComboBox = new JComboBox<>(searchFields);
        add(new JLabel("Campo a buscar:"));
        add(fieldComboBox);

        // JTextField para ingresar el texto de búsqueda
        JTextField searchField = new JTextField();
        add(new JLabel("Texto a buscar:"));
        add(searchField);

        // Botón Aceptar
        JButton acceptButton = new JButton("Aceptar");
        add(new JLabel()); // Espaciador
        add(acceptButton);

        // Resultado de búsqueda
        JTextArea resultArea = new JTextArea(5, 20);
        resultArea.setEditable(false);
        add(new JLabel("Resultados (N° de ELS):"));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane);

        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedField = (String) fieldComboBox.getSelectedItem();
                String searchText = searchField.getText();

                // Aquí se realiza la búsqueda en la base de datos
                // Simulación de resultados
                List<String> results = performSearch(selectedField, searchText);

                // Mostrar resultados
                resultArea.setText(String.join("\n", results));
            }
        });

        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private List<String> performSearch(String field, String text) {
        // Simula una búsqueda en la base de datos usando el campo y texto dados
        // En un caso real, aquí se ejecutaría una consulta SQL

        List<String> dummyResults = new ArrayList<>();
        dummyResults.add("ELS-12345");
        dummyResults.add("ELS-67890");

        // Filtrar resultados simulados usando el texto de búsqueda (comodín '*')
        if (text.contains("*")) {
            return dummyResults;
        } else {
            List<String> filteredResults = new ArrayList<>();
            for (String result : dummyResults) {
                if (result.contains(text)) {
                    filteredResults.add(result);
                }
            }
            return filteredResults;
        }
    }
}
