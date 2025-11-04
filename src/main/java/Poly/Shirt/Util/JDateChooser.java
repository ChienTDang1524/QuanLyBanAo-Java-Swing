package Poly.Shirt.Util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JDateChooser extends JPanel {
    private JTextField txtDate;
    private JButton btnCalendar;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public JDateChooser() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(150, 25));
        
        txtDate = new JTextField();
        txtDate.setEditable(false);
        add(txtDate, BorderLayout.CENTER);
        
        btnCalendar = new JButton("...");
        btnCalendar.setPreferredSize(new Dimension(30, 25));
        btnCalendar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDatePicker();
            }
        });
        add(btnCalendar, BorderLayout.EAST);
    }

    private void showDatePicker() {
        DatePickerDialog dialog = new DatePickerDialog(null);
        dialog.setVisible(true);
        Date selectedDate = dialog.getSelectedDate();
        if (selectedDate != null) {
            setDate(selectedDate);
        }
    }

    public Date getDate() {
        try {
            return txtDate.getText().isEmpty() ? null : dateFormat.parse(txtDate.getText());
        } catch (Exception e) {
            return null;
        }
    }

    public void setDate(Date date) {
        txtDate.setText(date == null ? "" : dateFormat.format(date));
    }

    public void setDateFormat(String pattern) {
        this.dateFormat = new SimpleDateFormat(pattern);
    }

    public void setEditable(boolean editable) {
        txtDate.setEditable(editable);
    }
}