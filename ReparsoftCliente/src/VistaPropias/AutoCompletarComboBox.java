package VistaPropias;

import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.text.*;

/**
 * Clase para habilitar autocompletado en JComboBox, permitiendo entrada libre
 * si es editable.
 */
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
            if (comboBox.isDisplayable()) comboBox.setPopupVisible(true);
            hitBackspace = false;

            switch (e.getKeyCode()) {
                case KeyEvent.VK_BACK_SPACE:
                    hitBackspace = true;
                    hitBackspaceOnSelection = editor.getSelectionStart() != editor.getSelectionEnd();
                    break;
                case KeyEvent.VK_ENTER:
                case KeyEvent.VK_TAB:
                    verificarItemNoEncontrado();
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
                comboBox.setPopupVisible(true);
            }
        }
    };

    private final FocusListener editorFocusListener = new FocusAdapter() {
        @Override
        public void focusGained(FocusEvent e) {
            highlightCompletedText(0);
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (hidePopupOnFocusLoss) {
                comboBox.setPopupVisible(false);
            }
            verificarItemNoEncontrado();
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
        Object selectedItem = model.getSelectedItem();
        if (selectedItem != null && startsWithIgnoreCase(selectedItem.toString(), pattern)) {
            return selectedItem;
        } else {
            for (int i = 0, n = model.getSize(); i < n; i++) {
                Object currentItem = model.getElementAt(i);
                if (currentItem != null && startsWithIgnoreCase(currentItem.toString(), pattern)) {
                    return currentItem;
                }
            }
        }
        return null;
    }

    private boolean startsWithIgnoreCase(String str1, String str2) {
        return str1.toLowerCase().startsWith(str2.toLowerCase());
    }

    private void verificarItemNoEncontrado() {
        String text = editor.getText();
        Object item = findExactMatch(text);

        if (item == null && !mensajeMostrado) {
            mensajeMostrado = true;

            if (!editable) {
                if (mostrarMensaje) {
                    JOptionPane.showMessageDialog(null, "Item no encontrado");
                }
                comboBox.setSelectedIndex(-1); // NO selecciona nada
            }
        }
    }

    private Object findExactMatch(String text) {
        for (int i = 0; i < model.getSize(); i++) {
            Object item = model.getElementAt(i);
            if (item != null && item.toString().equalsIgnoreCase(text)) {
                return item;
            }
        }
        return null;
    }

    public static boolean esItemValido(JComboBox comboBox) {
        ComboBoxModel model = comboBox.getModel();
        Object editorObj = comboBox.getEditor().getEditorComponent();
        if (editorObj instanceof JTextComponent) {
            String text = ((JTextComponent) editorObj).getText();
            for (int i = 0; i < model.getSize(); i++) {
                Object item = model.getElementAt(i);
                if (item != null && item.toString().equalsIgnoreCase(text)) {
                    return true;
                }
            }
        }
        return false;
    }
}
