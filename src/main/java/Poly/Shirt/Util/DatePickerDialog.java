package Poly.Shirt.Util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

public class DatePickerDialog extends JDialog {
    private JSpinner spnDay;
    private JSpinner spnMonth;
    private JSpinner spnYear;
    private Date selectedDate;
    private boolean cancelled = true;

    public DatePickerDialog(java.awt.Frame parent) {
        super(parent, "Chọn ngày", true);
        initComponents();
    }

    private void initComponents() {
        setSize(300, 150);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel pnlDate = new JPanel(new FlowLayout());
        pnlDate.setBackground(Color.WHITE);

        // Day spinner
        spnDay = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
        spnDay.setEditor(new JSpinner.NumberEditor(spnDay, "00"));
        pnlDate.add(spnDay);

        // Month spinner
        spnMonth = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1));
        spnMonth.setEditor(new JSpinner.NumberEditor(spnMonth, "00"));
        pnlDate.add(spnMonth);

        // Year spinner
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        spnYear = new JSpinner(new SpinnerNumberModel(currentYear, 1900, 2100, 1));
        spnYear.setEditor(new JSpinner.NumberEditor(spnYear, "0000"));
        pnlDate.add(spnYear);

        add(pnlDate, BorderLayout.CENTER);

        // Button panel
        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnOK = new JButton("OK");
        JButton btnCancel = new JButton("Cancel");

        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSelectedDate();
                cancelled = false;
                dispose();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelled = true;
                dispose();
            }
        });

        pnlButton.add(btnOK);
        pnlButton.add(btnCancel);
        add(pnlButton, BorderLayout.SOUTH);

        // Set current date
        Calendar cal = Calendar.getInstance();
        spnDay.setValue(cal.get(Calendar.DAY_OF_MONTH));
        spnMonth.setValue(cal.get(Calendar.MONTH) + 1);
        spnYear.setValue(cal.get(Calendar.YEAR));
    }

    private void setSelectedDate() {
        int day = (Integer) spnDay.getValue();
        int month = (Integer) spnMonth.getValue() - 1;
        int year = (Integer) spnYear.getValue();

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        selectedDate = cal.getTime();
    }

    public Date getSelectedDate() {
        return cancelled ? null : selectedDate;
    }
}