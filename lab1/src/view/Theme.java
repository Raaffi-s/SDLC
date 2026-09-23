package view;

import java.awt.*;

public final class Theme {
    private Theme() {}

    public static final Color BG          = new Color(0x1E1E2E);
    public static final Color CARD        = new Color(0x2A2A3C);
    public static final Color CARD_HOVER  = new Color(0x33334A);
    public static final Color ACCENT      = new Color(0xE94560);
    public static final Color ACCENT_HOVER= new Color(0xFF5C7A);
    public static final Color TEXT        = new Color(0xEAEAEA);
    public static final Color TEXT_DIM    = new Color(0xA0A0B0);
    public static final Color BORDER      = new Color(0x3A3A4E);
    public static final Color ERROR       = new Color(0xFF6B6B);

    public static final Font TITLE  = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font LABEL  = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font VALUE  = new Font("Segoe UI", Font.BOLD, 42);
    public static final Font SMALL  = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font BUTTON = new Font("Segoe UI", Font.BOLD, 15);
}
