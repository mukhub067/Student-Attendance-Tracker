package com.attendance.util;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public final class UITheme {
    /** Lapis Velvet Evening palette: #213885, #ECDFD2, #5F3475, #081849, #CCCACC, #893172. */
    public static final Color NAVY=new Color(8,24,73), BLUE=new Color(33,56,133), LIGHT=new Color(236,223,210), TEXT=new Color(8,24,73), DANGER=new Color(137,49,114);
    public static final Color SUCCESS=new Color(95,52,117), WARNING=new Color(137,49,114), SECONDARY=new Color(204,202,204), SIDEBAR_HOVER=new Color(33,56,133);
    public static final Font TITLE=new Font(Font.SANS_SERIF,Font.BOLD,22), HEADING=new Font(Font.SANS_SERIF,Font.BOLD,16), BODY=new Font(Font.SANS_SERIF,Font.PLAIN,13);
    private UITheme() { }
    public static Border padding(int n) { return new EmptyBorder(n,n,n,n); }
    public static JPanel panel(LayoutManager layout) { JPanel p=new JPanel(layout); p.setBackground(Color.WHITE); return p; }
    public static JButton button(String text) { return coloredButton(text, BLUE, Color.WHITE); }
    public static JButton successButton(String text) { return coloredButton(text, SUCCESS, Color.WHITE); }
    public static JButton warningButton(String text) { return coloredButton(text, WARNING, Color.WHITE); }
    public static JButton secondaryButton(String text) { return coloredButton(text, SECONDARY, TEXT); }
    public static JButton dangerButton(String text) { return coloredButton(text, DANGER, Color.WHITE); }
    private static JButton coloredButton(String text, Color color, Color foreground) {
        JButton b=new JButton(text);
        b.setUI(new BasicButtonUI());
        b.setFocusPainted(false); b.setFont(BODY); b.setOpaque(true); b.setContentAreaFilled(true); b.setBorderPainted(true);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); b.setBackground(color); b.setForeground(foreground);
        b.setBorder(new CompoundBorder(new LineBorder(color.darker(), 1),new EmptyBorder(8,14,8,14)));
        return b;
    }
    public static void styleSidebarButton(JButton button) {
        button.setUI(new BasicButtonUI());
        button.setFont(BODY); button.setOpaque(true); button.setContentAreaFilled(true); button.setBorderPainted(true);
        button.setBackground(SUCCESS); button.setForeground(Color.WHITE);
        button.setBorder(new CompoundBorder(new LineBorder(SECONDARY, 1), new EmptyBorder(9, 12, 9, 12)));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
    public static void styleTable(JTable table) {
        table.setRowHeight(30); table.setFont(BODY); table.setBackground(Color.WHITE); table.setForeground(TEXT);
        table.setGridColor(SECONDARY); table.setSelectionBackground(LIGHT); table.setSelectionForeground(TEXT);
        JTableHeader header = table.getTableHeader();
        header.setUI(new BasicTableHeaderUI());
        header.setOpaque(true); header.setBackground(BLUE); header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 36)); header.setReorderingAllowed(false);
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(JTable source, Object value,
                    boolean selected, boolean focused, int row, int column) {
                JLabel label=(JLabel)super.getTableCellRendererComponent(source,value,selected,focused,row,column);
                label.setOpaque(true); label.setBackground(BLUE); label.setForeground(Color.WHITE);
                label.setFont(HEADING); label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setBorder(new MatteBorder(0,0,1,1,NAVY));
                return label;
            }
        });
    }
    public static void error(Component parent, Exception ex) { JOptionPane.showMessageDialog(parent,ex.getMessage(),"Unable to complete action",JOptionPane.ERROR_MESSAGE); }
    public static JLabel title(String text) { JLabel label=new JLabel(text); label.setFont(TITLE); label.setForeground(NAVY); return label; }
}
