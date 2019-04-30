package ru.cft.focusstart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));

        JMenu jMenu = new JMenu("File");
        jMenu.add(exitItem);

        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(jMenu);

        frame.setJMenuBar(jMenuBar);

        JPanel jPanel = new JPanel();
        frame.add(jPanel);

        jPanel.setLayout(new GridLayout(9, 9));
        Dimension buttonPreferredSize = new Dimension(40, 40);

        Controller controller = new Controller();
        for(int i = 0; i < 81; i++) {
            JButton button = new JButton();
            button.setPreferredSize(buttonPreferredSize);

            Icon icon = new ImageIcon(Main.class.getResource("/icons/closed.png"));
            button.setIcon(icon);

            int finalI = i;
            button.addMouseListener(new java.awt.event.MouseAdapter() {
//                public void mouseEntered(java.awt.event.MouseEvent evt) {
//                    button.setBackground(Color.GREEN);
//                }

//                public void mouseExited(java.awt.event.MouseEvent evt) {
//                    button.setBackground(UIManager.getColor("control"));
//                }

                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == 1) {
                        controller.getIcon(finalI);
                    } else if (e.getButton() == 3) {
                        controller.toggleMine(finalI);
                    }
                }
            });
            controller.setButtonItem(finalI, button);
//            button.addActionListener(e -> {controller.getIcon(finalI); System.out.println(e);});
            jPanel.add(button);
        }

//        Icon closedIcon = new ImageIcon(SwingApplication.class.getResource("/icons/closed.png"));
//        Icon openIcon = new ImageIcon(SwingApplication.class.getResource("/icons/zero.png"));
//        Icon minedIcon = new ImageIcon(SwingApplication.class.getResource("/icons/mined.png"));


//        JButton button1 = new JButton();
//        button1.setIcon(closedIcon);
//        button1.addActionListener(e -> button1.setIcon(openIcon));
//        button1.setPreferredSize(buttonPreferredSize);
//        jPanel.add(button1);
//
//        JButton button2 = new JButton();
//        button2.setIcon(closedIcon);
//        button2.addActionListener(e -> button2.setIcon(openIcon));
//        button2.setPreferredSize(buttonPreferredSize);
//        jPanel.add(button2);
//
//        JButton button3 = new JButton();
//        button3.setIcon(closedIcon);
//        button3.addActionListener(e -> button3.setIcon(minedIcon));
//        button3.setPreferredSize(buttonPreferredSize);
//        jPanel.add(button3);
//
//        JButton button4 = new JButton();
//        button4.setIcon(closedIcon);
//        button4.addActionListener(e -> button4.setIcon(openIcon));
//        button4.setPreferredSize(buttonPreferredSize);
//        jPanel.add(button4);
//
        frame.setLayout(new BorderLayout());
        frame.add(jPanel, BorderLayout.CENTER);

//        JLabel jLabel = new JLabel(new ImageIcon(SwingApplication.class.getResource("/icons/win.png")));
//        frame.add(jLabel, BorderLayout.WEST);

        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}
