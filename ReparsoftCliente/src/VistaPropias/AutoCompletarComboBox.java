package VistaPropias;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.*;

public class AutoCompletarComboBox extends PlainDocument {
    private final JComboBox comboBox;
    private ComboBoxModel model;
    private JTextComponent editor;
    private boolean selecting = false;
    private boolean hidePopupOnFocusLoss;
    private boolean hitBackspace = false;
    private boolean hitBackspaceOnSelection;
    private final boolean mostrarMensaje;
    private final boolean editable;
    private boolean mensajeMostrado = false;

    public AutoCompletarComboBox(final JComboBox comboBox, boolean editable, boolean mostrarMensaje) {
        this.comboBox = comboBox;
        this.model = comboBox.getModel();
        this.editable = editable;
        this.mostrarMensaje = mostrarMensaje;

        comboBox.setEditable(true);
        comboBox.addActionListener(e -> {
            if (!selecting) {
                highlightCompletedText(0);
            }
        });

        comboBox.addPropertyChangeListener(e -> {
            if (e.getPropertyName().equals("editor")) {
                configureEditor((ComboBoxEditor) e.getNewValue());
            }
            if (e.getPropertyName().equals("model")) {
                model = (ComboBoxModel) e.getNewValue();
            }
        });

        hidePopupOnFocusLoss = false;
        configureEditor(comboBox.getEditor());

        Object selected = comboBox.getSelectedItem();
        if (selected != null) {
            setText(selected.toString());
        }

        highlightCompletedText(0);
    }

    public static void enable(JComboBox comboBox, boolean editable, boolean mostrarMensaje) {
        new AutoCompletarComboBox(comboBox, editable, mostrarMensaje);
    }

    private void configureEditor(ComboBoxEditor newEditor) {
        if (editor != null) {
            editor.removeKeyListener(editorKeyListener);
            editor.removeFocusListener(editorFocusListener);
        }

        if (newEditor != null) {
            editor = (JTextComponent) newEditor.getEditorComponent();
            editor.addKeyListener(editorKeyListener);
            editor.addFocusListener(editorFocusListener);
            editor.setDocument(this);
        }
    }

    private final KeyListener editorKeyListener = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            hitBackspace = e.getKeyCode() == KeyEvent.VK_BACK_SPACE;
            hitBackspaceOnSelection = editor.getSelectionStart() != editor.getSelectionEnd();

            switch (e.getKeyCode()) {
                case KeyEvent.VK_ENTER:
                	comboBox.setPopupVisible(false);
                    verificarItemNoEncontrado(comboBox.getName());
                    // Forzar validación inmediata para Enter
                    if (!findExactMatch(editor.getText())) {
                    	
                        e.consume(); // Evitar comportamiento por defecto
                    }
                    break;
                case KeyEvent.VK_TAB:
                	comboBox.setPopupVisible(false);
                    verificarItemNoEncontrado(comboBox.getName());
                    // Forzar validación y mantener el foco si no es válido
                    if (!findExactMatch(editor.getText())) {
                        e.consume(); // Evitar que pierda el foco
                        editor.requestFocusInWindow();
                    }
                    break;
            }
        }
  

        @Override
        public void keyReleased(KeyEvent e) {
            String text = editor.getText();
            Object item = lookupItem(text);

            if (item == null) {
                comboBox.setPopupVisible(false);
            } else {
                if (e.getKeyCode() != KeyEvent.VK_ENTER && 
                        e.getKeyCode() != KeyEvent.VK_TAB && 
                        !text.isEmpty()) {
                        comboBox.setPopupVisible(true);
                    }
            }
        }
    };

    private final FocusListener editorFocusListener = new FocusAdapter() {
        @Override
        public void focusGained(FocusEvent e) {
            editor.selectAll();
            mensajeMostrado = false; // reset para permitir mostrar mensaje de nuevo
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (hidePopupOnFocusLoss) {
                comboBox.setPopupVisible(false);
            }
            verificarItemNoEncontrado(comboBox.getName());
        }
    };

    @Override
    public void remove(int offs, int len) throws BadLocationException {
        if (selecting) return;
        super.remove(offs, len);
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (selecting || str == null) return;

        super.insertString(offs, str, a);
        String content = getText(0, getLength());
        Object item = lookupItem(content);

        if (item != null) {
            setSelectedItem(item);
            setText(item.toString());
            highlightCompletedText(offs + str.length());
        }
    }

    private void setText(String text) {
        try {
            super.remove(0, getLength());
            super.insertString(0, text, null);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    private void highlightCompletedText(int start) {
        editor.setCaretPosition(getLength());
        editor.moveCaretPosition(start);
    }

    private void setSelectedItem(Object item) {
        selecting = true;
        model.setSelectedItem(item);
        selecting = false;
    }

    private Object lookupItem(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            return null;
        }

        String patternLower = pattern.toLowerCase();
        Object exactMatch = null;
        Object prefixMatch = null;

        // Primera pasada: buscar coincidencia exacta
        for (int i = 0; i < model.getSize(); i++) {
            Object item = model.getElementAt(i);
            if (item != null) {
                String itemLower = item.toString().toLowerCase();
                if (itemLower.equals(patternLower)) {
                    exactMatch = item;
                    break;
                }
            }
        }

        // Si encontramos coincidencia exacta, devolverla
        if (exactMatch != null) {
            return exactMatch;
        }

        // Segunda pasada: buscar el prefijo más corto que coincida
        int minLength = Integer.MAX_VALUE;
        for (int i = 0; i < model.getSize(); i++) {
            Object item = model.getElementAt(i);
            if (item != null) {
                String itemLower = item.toString().toLowerCase();
                if (itemLower.startsWith(patternLower)) {
                    int itemLength = item.toString().length();
                    if (itemLength < minLength) {
                        minLength = itemLength;
                        prefixMatch = item;
                    }
                }
            }
        }

        return prefixMatch;
    }

    private boolean findExactMatch(String text) {
        if (text == null) {
            return false;
        }

        String textLower = text.toLowerCase();
        for (int i = 0; i < model.getSize(); i++) {
            Object item = model.getElementAt(i);
            if (item != null) {
                String itemLower = item.toString().toLowerCase();
                // Reconocer items vacíos cuando el texto también está vacío
                if (text.isEmpty() && itemLower.isEmpty()) {
                    return true;
                }
                // Comparar normalmente para el resto de casos
                if (itemLower.equals(textLower)) {
                    return true;
                }
            }
        }
        return false;
    }

    
    
    
    private void verificarItemNoEncontrado(String mensaje) {
        String text = editor.getText();
       
        boolean exacto = findExactMatch(text);
        
       

        if (!exacto && !mensajeMostrado) {
            mensajeMostrado = true;

            if (mostrarMensaje) {
            	
            	 switch (mensaje) {
         		
                 case "comboCliente":
                 mensaje = "Cliente no encontrado. Deberá darlo de alta";
         		break;
         		
                 case "comboSucursal":
                 mensaje = "Sucursal no encontrada. Deberá darla de alta";
             	break;

         		default:
         			break;
         		}
            	
                JOptionPane.showMessageDialog(null, mensaje);
            }

            if (!editable) {
                if (comboBox.getItemCount() > 0) {
                    Object primerItem = comboBox.getItemAt(0);
                    setSelectedItem(primerItem);
                    setText(primerItem.toString());
                    highlightCompletedText(0);
                }
            }
        }
    }

    

    public static boolean esItemValido(JComboBox comboBox) {
        ComboBoxModel model = comboBox.getModel();
        Object editorObj = comboBox.getEditor().getEditorComponent();
        if (editorObj instanceof JTextComponent) {
            String text = ((JTextComponent) editorObj).getText();
            String textLower = text.toLowerCase();
            for (int i = 0; i < model.getSize(); i++) {
                Object item = model.getElementAt(i);
                if (item != null) {
                    String itemLower = item.toString().toLowerCase();
                    // Reconocer items vacíos cuando el texto también está vacío
                    if (text.isEmpty() && itemLower.isEmpty()) {
                        return true;
                    }
                    // Comparar normalmente para el resto de casos
                    if (itemLower.equals(textLower)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}