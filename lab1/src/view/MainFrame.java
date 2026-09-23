package view;

import controller.SinController;
import model.SinModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

public class MainFrame extends JFrame implements SinModel.ModelListener {

    private final SinModel model;
    private SinController controller;

    private final JLabel dateValue    = new JLabel("—");
    private final JLabel totalValue   = new JLabel("0");
    private final JLabel dailyValue   = new JLabel("0");
    private final JLabel deadlyValue  = new JLabel("0");
    private final JButton calcButton  = new JButton("Рассчитать грехи");

    private LocalDate lastBirthDate;

    public MainFrame(SinModel model) {
        this.model = model;
        model.addListener(this);

        setTitle("Счётчик совершённых грехов");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(520, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBackground(Theme.BG);
        root.setBorder(new EmptyBorder(28, 28, 28, 28));

        root.add(buildHeader());
        root.add(Box.createVerticalStrut(22));
        root.add(buildBirthCard());
        root.add(Box.createVerticalStrut(18));
        root.add(buildTotalCard());
        root.add(Box.createVerticalStrut(18));
        root.add(buildBreakdown());
        root.add(Box.createVerticalGlue());
        root.add(buildButton());

        setContentPane(root);

        calcButton.addActionListener(e -> {
            if (controller != null) controller.onCalculateClicked(this);
        });
    }

    public void setController(SinController controller) {
        this.controller = controller;
    }

    // ---------- БЛОКИ UI ----------

    private JComponent buildHeader() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        JLabel icon = new JLabel("☠");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        icon.setForeground(Theme.ACCENT);
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("Счётчик грехов");
        title.setFont(Theme.TITLE);
        title.setForeground(Theme.TEXT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = new JLabel("узнайте, сколько вы уже успели нагрешить");
        sub.setFont(Theme.SMALL);
        sub.setForeground(Theme.TEXT_DIM);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        p.add(icon);
        p.add(Box.createVerticalStrut(6));
        p.add(title);
        p.add(Box.createVerticalStrut(4));
        p.add(sub);
        return p;
    }

    private JComponent buildBirthCard() {
        JPanel card = card();
        card.setLayout(new BorderLayout(12, 0));

        JLabel caption = new JLabel("Дата рождения");
        caption.setFont(Theme.LABEL);
        caption.setForeground(Theme.TEXT_DIM);

        dateValue.setFont(Theme.LABEL.deriveFont(Font.BOLD, 16f));
        dateValue.setForeground(Theme.TEXT);

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.add(caption);
        left.add(Box.createVerticalStrut(4));
        left.add(dateValue);

        card.add(left, BorderLayout.CENTER);
        return card;
    }

    private JComponent buildTotalCard() {
        JPanel card = card();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel caption = new JLabel("Всего грехов");
        caption.setFont(Theme.LABEL);
        caption.setForeground(Theme.TEXT_DIM);
        caption.setAlignmentX(Component.CENTER_ALIGNMENT);

        totalValue.setFont(Theme.VALUE);
        totalValue.setForeground(Theme.ACCENT);
        totalValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(caption);
        card.add(Box.createVerticalStrut(6));
        card.add(totalValue);
        return card;
    }

    private JComponent buildBreakdown() {
        JPanel row = new JPanel(new GridLayout(1, 2, 14, 0));
        row.setOpaque(false);

        row.add(smallStat("Повседневные", dailyValue, Theme.TEXT));
        row.add(smallStat("Смертные",     deadlyValue, Theme.ACCENT));
        return row;
    }

    private JComponent smallStat(String caption, JLabel valueLabel, Color color) {
        JPanel card = card();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel cap = new JLabel(caption);
        cap.setFont(Theme.SMALL);
        cap.setForeground(Theme.TEXT_DIM);
        cap.setAlignmentX(Component.CENTER_ALIGNMENT);

        valueLabel.setFont(Theme.LABEL.deriveFont(Font.BOLD, 26f));
        valueLabel.setForeground(color);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(cap);
        card.add(Box.createVerticalStrut(6));
        card.add(valueLabel);
        return card;
    }

    private JComponent buildButton() {
        calcButton.setFont(Theme.BUTTON);
        calcButton.setForeground(Color.WHITE);
        calcButton.setBackground(Theme.ACCENT);
        calcButton.setFocusPainted(false);
        calcButton.setBorderPainted(false);
        calcButton.setOpaque(true);
        calcButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        calcButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        calcButton.setPreferredSize(new Dimension(200, 48));
        calcButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        calcButton.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                calcButton.setBackground(Theme.ACCENT_HOVER);
            }
            @Override public void mouseExited(MouseEvent e) {
                calcButton.setBackground(Theme.ACCENT);
            }
        });
        return calcButton;
    }

    private JPanel card() {
        JPanel p = new JPanel();
        p.setBackground(Theme.CARD);
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.BORDER, 1, true),
            new EmptyBorder(16, 20, 16, 20)));
        return p;
    }

    // ---------- API ДЛЯ КОНТРОЛЛЕРА ----------

    public LocalDate getLastBirthDate()       { return lastBirthDate; }
    public void setLastBirthDate(LocalDate d) { this.lastBirthDate = d; }

    public String askBirthDate() {
        return SinInputDialog.show(this, lastBirthDate);
    }

    public void showError(String message) {
        SinInputDialog.showError(this, message);
    }

    // ---------- РЕАКЦИЯ НА МОДЕЛЬ ----------

    @Override
    public void onModelChanged() {
        LocalDate d = model.getBirthDate();
        dateValue.setText(d == null ? "не задана" : d.toString());
        totalValue.setText(String.valueOf(model.getTotalSins()));
        dailyValue.setText(String.valueOf(model.getDailySins()));
        deadlyValue.setText(String.valueOf(model.getDeadlySins()));
    }
}