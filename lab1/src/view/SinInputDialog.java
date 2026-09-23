package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

public final class SinInputDialog {
    private SinInputDialog() {}

    /** Возвращает введённую строку или null, если пользователь отменил. */
    public static String show(Component parent, LocalDate last) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(parent),
            "Введите дату", true);
        dialog.setUndecorated(true);

        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBackground(Theme.CARD);
        root.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.ACCENT, 1, true),
            new EmptyBorder(22, 26, 22, 26)));

        JLabel title = new JLabel("Дата рождения");
        title.setFont(Theme.TITLE.deriveFont(Font.BOLD, 18f));
        title.setForeground(Theme.TEXT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel hint = new JLabel("Формат: ГГГГ-ММ-ДД, например 2000-01-01");
        hint.setFont(Theme.SMALL);
        hint.setForeground(Theme.TEXT_DIM);
        hint.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField field = new JTextField(last == null ? "" : last.toString());
        field.setFont(Theme.LABEL.deriveFont(Font.PLAIN, 16f));
        field.setForeground(Theme.TEXT);
        field.setBackground(Theme.BG);
        field.setCaretColor(Theme.ACCENT);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.BORDER, 1, true),
            new EmptyBorder(10, 12, 10, 12)));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        field.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton ok = flatButton("OK", Theme.ACCENT, Theme.ACCENT_HOVER);
        JButton cancel = flatButton("Отмена", Theme.CARD_HOVER, Theme.BORDER);

        final String[] result = { null };

        ok.addActionListener(e -> { result[0] = field.getText(); dialog.dispose(); });
        cancel.addActionListener(e -> dialog.dispose());
        field.addActionListener(e -> { result[0] = field.getText(); dialog.dispose(); });

        JPanel buttons = new JPanel(new GridLayout(1, 2, 10, 0));
        buttons.setOpaque(false);
        buttons.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        buttons.add(cancel);
        buttons.add(ok);

        root.add(title);
        root.add(Box.createVerticalStrut(6));
        root.add(hint);
        root.add(Box.createVerticalStrut(16));
        root.add(field);
        root.add(Box.createVerticalStrut(18));
        root.add(buttons);

        dialog.setContentPane(root);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        SwingUtilities.invokeLater(field::requestFocusInWindow);
        dialog.setVisible(true);

        return result[0];
    }

    public static void showError(Component parent, String message) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(parent),
            "Ошибка", true);
        dialog.setUndecorated(true);

        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBackground(Theme.CARD);
        root.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.ERROR, 1, true),
            new EmptyBorder(22, 26, 22, 26)));

        JLabel icon = new JLabel("⚠");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 34));
        icon.setForeground(Theme.ERROR);
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel msg = new JLabel("<html><div style='width:260px;text-align:center;'>"
            + message + "</div></html>");
        msg.setFont(Theme.LABEL);
        msg.setForeground(Theme.TEXT);
        msg.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton ok = flatButton("Понятно", Theme.ACCENT, Theme.ACCENT_HOVER);
        ok.setAlignmentX(Component.CENTER_ALIGNMENT);
        ok.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        ok.addActionListener(e -> dialog.dispose());

        root.add(icon);
        root.add(Box.createVerticalStrut(8));
        root.add(msg);
        root.add(Box.createVerticalStrut(18));
        root.add(ok);

        dialog.setContentPane(root);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

    private static JButton flatButton(String text, Color bg, Color hover) {
        JButton b = new JButton(text);
        b.setFont(Theme.BUTTON);
        b.setForeground(Color.WHITE);
        b.setBackground(bg);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { b.setBackground(hover); }
            @Override public void mouseExited(MouseEvent e)  { b.setBackground(bg); }
        });
        return b;
    }
}