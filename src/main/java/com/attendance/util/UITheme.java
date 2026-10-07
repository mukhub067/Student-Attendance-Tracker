package com.attendance.util;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public final class UITheme {
    public static final Color NAVY=new Color(30,48,71), BLUE=new Color(48,98,150), LIGHT=new Color(244,247,250), TEXT=new Color(35,45,55), DANGER=new Color(179,57,57);
    public static final Font TITLE=new Font(Font.SANS_SERIF,Font.BOLD,22), HEADING=new Font(Font.SANS_SERIF,Font.BOLD,16), BODY=new Font(Font.SANS_SERIF,Font.PLAIN,13);
    private UITheme() { }
    public static Border padding(int n) { return new EmptyBorder(n,n,n,n); }
    public static JPanel panel(LayoutManager layout) { JPanel p=new JPanel(layout); p.setBackground(Color.WHITE); return p; }
    public static JButton button(String text) { JButton b=new JButton(text); b.setFocusPainted(false); b.setFont(BODY); b.setBackground(BLUE); b.setForeground(Color.WHITE); b.setBorder(new CompoundBorder(new LineBorder(BLUE),new EmptyBorder(8,14,8,14))); return b; }
    public static JButton dangerButton(String text) { JButton b=button(text); b.setBackground(DANGER); b.setBorder(new CompoundBorder(new LineBorder(DANGER),new EmptyBorder(8,14,8,14))); return b; }
    public static void styleTable(JTable table) { table.setRowHeight(28); table.setFont(BODY); table.setSelectionBackground(new Color(210,225,240)); table.setSelectionForeground(TEXT); table.getTableHeader().setFont(HEADING); table.getTableHeader().setBackground(NAVY); table.getTableHeader().setForeground(Color.WHITE); }
    public static void error(Component parent, Exception ex) { JOptionPane.showMessageDialog(parent,ex.getMessage(),"Unable to complete action",JOptionPane.ERROR_MESSAGE); }
    public static JLabel title(String text) { JLabel label=new JLabel(text); label.setFont(TITLE); label.setForeground(NAVY); return label; }
}
