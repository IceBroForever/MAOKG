package main;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

import javax.swing.*;

public class Clock extends JPanel implements ActionListener {

    Timer timer;

    private static int maxWidth;
    private static int maxHeight;
    private static double r;

    private double scale = 1;
    private double delta = 0.01;

    public Clock() {
        timer = new Timer(10, this);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setBackground(new Color(86, 107, 48));
        g2d.clearRect(0, 0, maxWidth + 1, maxHeight + 1);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setColor(new Color(226, 214, 52));

        g2d.setStroke(new BasicStroke(8, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
        g2d.drawRect(20, 20, maxWidth - 40, maxHeight-40);

        g2d.translate(maxWidth/2, maxHeight/2);

        g2d.scale(scale, scale);

        g2d.fill(new Ellipse2D.Double(-r, -r, r * 2, r * 2));

        g2d.setPaint(new GradientPaint(0, 0, Color.white, 40, 40, Color.lightGray, true));
        g2d.fill(new Ellipse2D.Double(-r * 0.9, -r * 0.9, r * 0.9 * 2, r * 0.9 * 2));

        g2d.setColor(new Color(226, 214, 52));

        for(int i = 0; i < 4; i++) {
            g2d.rotate(Math.PI / 2);
            g2d.fill(new Ellipse2D.Double(-r * 0.1, -r * 0.8, r * 0.2, r * 0.2));
        }

        LocalDateTime now = LocalDateTime.now();
        final double secondsAngle = 2 * Math.PI * now.getSecond() / 60;
        final double minutesAngle = 2 * Math.PI * (now.getMinute() + now.getSecond() / 60f) / 60;
        final double hoursAngle = 2 * Math.PI * ((now.getHour() % 12) + now.getMinute() / 60f + now.getSecond() / 3600f) / 12;

        g2d.setColor(Color.BLACK);
        g2d.rotate(hoursAngle);
        GeneralPath hoursHand = new GeneralPath();
        hoursHand.moveTo(-r * 0.07, 0);
        hoursHand.lineTo(0, -r * 0.6 * 0.8);
        hoursHand.lineTo(r * 0.07, 0);
        hoursHand.lineTo(0, r * 0.6 * 0.2);
        hoursHand.closePath();
        g2d.fill(hoursHand);

        g2d.setColor(Color.WHITE);
        GeneralPath hoursHand2 = new GeneralPath();
        hoursHand2.moveTo(-r * 0.07 * 0.5, 0);
        hoursHand2.lineTo(0, -r * 0.6 * 0.8);
        hoursHand2.lineTo(r * 0.07 * 0.5, 0);
        hoursHand2.lineTo(0, r * 0.6 * 0.2);
        hoursHand2.closePath();
        g2d.fill(hoursHand2);
        g2d.rotate(-hoursAngle);

        g2d.setColor(Color.BLACK);
        g2d.rotate(minutesAngle);
        GeneralPath minutesHand = new GeneralPath();
        minutesHand.moveTo(-r * 0.035, 0);
        minutesHand.lineTo(0, -r * 0.8 * 0.8);
        minutesHand.lineTo(r * 0.035, 0);
        minutesHand.lineTo(0, r * 0.8 * 0.2);
        minutesHand.closePath();
        g2d.fill(minutesHand);
        g2d.rotate(-minutesAngle);

        g2d.setColor(new Color(226, 214, 52));
        g2d.rotate(secondsAngle);
        GeneralPath secondsHand = new GeneralPath();
        secondsHand.moveTo(-r * 0.025, 0);
        secondsHand.lineTo(0, -r * 0.9 * 0.8);
        secondsHand.lineTo(r * 0.025, 0);
        secondsHand.lineTo(0, r * 0.9 * 0.2);
        secondsHand.closePath();
        g2d.fill(secondsHand);
        g2d.rotate(-secondsAngle);
    }

    public void actionPerformed(ActionEvent e) {
        if ( scale < 0.5 ) {
            delta = -delta;
        } else if (scale > 2) {
            delta = -delta;
        }
        scale += delta;
        repaint();
    }

    public static void main(String[] argv) {
        JFrame frame = new JFrame("Lab 2");

        frame.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        frame.add(new Clock());

        Dimension size = frame.getSize();
        Insets insets = frame.getInsets();
        maxWidth = size.width - insets.left - insets.right - 1;
        maxHeight = size.height - insets.top - insets.bottom - 26;
        r = maxWidth / 8.0;

        frame.setVisible(true);
    }
}
