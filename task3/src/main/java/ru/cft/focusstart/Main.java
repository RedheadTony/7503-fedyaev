package ru.cft.focusstart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class Main {
    public static void main(String[] args) {
        NewModel model = new NewModel();
        View view = new View();
        NewController controller = new NewController();

        view.setController(controller);
        view.setModel(model);

        controller.setModel(model);
        controller.setView(view);

        view.renderBoard();
        model.startGame();
//        JFrame frame = new JFrame();
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//
//        JMenuItem exitItem = new JMenuItem("Exit");
//        exitItem.addActionListener(e -> System.exit(0));
//
//        JMenu jMenu = new JMenu("File");
//        jMenu.add(exitItem);
//
//        JMenuBar jMenuBar = new JMenuBar();
//        jMenuBar.add(jMenu);
//
//        frame.setJMenuBar(jMenuBar);
//
//        JPanel jPanel = new JPanel();
//        frame.add(jPanel);
//
//        jPanel.setLayout(new GridLayout(9, 9));
//        Dimension buttonPreferredSize = new Dimension(40, 40);
//
//        Controller controller = new Controller();
//        for(int i = 0; i < 81; i++) {
//            JButton button = new JButton();
//            button.setPreferredSize(buttonPreferredSize);
//
//            Icon icon = new ImageIcon(Main.class.getResource("/icons/closed.png"));
//            button.setIcon(icon);
//
//            int finalI = i;
//            button.addMouseListener(new java.awt.event.MouseAdapter() {
////                public void mouseEntered(java.awt.event.MouseEvent evt) {
////                    button.setBackground(Color.GREEN);
////                }
//
////                public void mouseExited(java.awt.event.MouseEvent evt) {
////                    button.setBackground(UIManager.getColor("control"));
////                }
//
//                public void mouseClicked(MouseEvent e) {
//                    if (e.getButton() == 1) {
//                        controller.getIcon(finalI);
//                    } else if (e.getButton() == 3) {
//                        controller.toggleMine(finalI);
//                    }
//                }
//            });
//            controller.setButtonItem(finalI, button);
////            button.addActionListener(e -> {controller.getIcon(finalI); System.out.println(e);});
//            jPanel.add(button);
//        }
//
//        frame.setLayout(new BorderLayout());
//
////        JPanel topPanel = new JPanel();
////        topPanel.setLayout(new GridLayout(1, 3));
////        JLabel mineCounter = new JLabel("10");
////        JLabel title = new JLabel("Сапер");
////        JLabel timer = new JLabel("1:20");
////        topPanel.add(mineCounter);
////        topPanel.add(title);
////        topPanel.add(timer);
////        frame.add(topPanel, BorderLayout.CENTER);
//
//        frame.add(jPanel, BorderLayout.CENTER);
//
////        JLabel jLabel = new JLabel(new ImageIcon(SwingApplication.class.getResource("/icons/win.png")));
////        frame.add(jLabel, BorderLayout.WEST);
//
//        frame.setResizable(false);
//        frame.pack();
//        frame.setVisible(true);
//        controller.setAllIcons();
    }
}
