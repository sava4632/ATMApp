package atmapp.utils;

import javax.swing.*;
import javax.swing.text.*;

public class UppercaseJTextField extends DocumentFilter {

    private static final int MAX_CHARS = 29;

    @Override
    public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
        if ((fb.getDocument().getLength() + text.length()) <= MAX_CHARS) {
            if ((offset % 5) == 4) {
                text = " " + text; // Add a space separator after every 5 characters
            }
            fb.insertString(offset, text.toUpperCase(), attr); // Convert text to uppercase and insert it
        } else {
            JOptionPane.showMessageDialog(null, "You cannot input more than " + MAX_CHARS + " characters");
        }
    }

    @Override
    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if ((fb.getDocument().getLength() + text.length() - length) <= MAX_CHARS) {
            if ((offset % 5) == 4) {
                text = " " + text; // Add a space separator after every 5 characters
            }
            fb.replace(offset, length, text.toUpperCase(), attrs); // Convert text to uppercase and replace it
        } else {
            JOptionPane.showMessageDialog(null, "You cannot input more than " + MAX_CHARS + " characters");
        }
    }
}



