package ru.cft.focusstart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class View {
    private NewModel model;
    private NewController controller;

    private JButton[][] buttons = new JButton[10][10];

    private static final Icon one = new ImageIcon(Main.class.getResource("/icons/one.png"));
    private static final Icon two = new ImageIcon(Main.class.getResource("/icons/two.png"));
    private static final Icon three = new ImageIcon(Main.class.getResource("/icons/three.png"));
    private static final Icon four = new ImageIcon(Main.class.getResource("/icons/four.png"));
    private static final Icon five = new ImageIcon(Main.class.getResource("/icons/five.png"));
    private static final Icon six = new ImageIcon(Main.class.getResource("/icons/six.png"));
    private static final Icon seven = new ImageIcon(Main.class.getResource("/icons/seven.png"));
    private static final Icon eight = new ImageIcon(Main.class.getResource("/icons/eight.png"));

    private static final Icon zero = new ImageIcon(Main.class.getResource("/icons/zero.png"));
    private static final Icon mine = new ImageIcon(Main.class.getResource("/icons/mine.png"));
    private static final Icon flag = new ImageIcon(Main.class.getResource("/icons/flag.png"));
    private static final Icon closed = new ImageIcon(Main.class.getResource("/icons/closed.png"));

    private Icon getNumber(int number) {
        switch (number) {
            case 1:
                return one;
            case 2:
                return two;
            case 3:
                return three;
            case 4:
                return four;
            case 5:
                return five;
            case 6:
                return six;
            case 7:
                return seven;
            case 8:
                return eight;
            default:
                return zero;
        }
    }

    public void renderBoard() {
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

        jPanel.setLayout(new GridLayout(10, 10));
        Dimension buttonPreferredSize = new Dimension(40, 40);
        for (int row = 0; row < 10; row++) {
            for (int column = 0; column < 10; column++) {
                JButton button = new JButton();
                button.setPreferredSize(buttonPreferredSize);

                button.setIcon(closed);

                int rowIndex = row;
                int columnIndex = column;
                button.addMouseListener(new java.awt.event.MouseAdapter() {
//                public void mouseEntered(java.awt.event.MouseEvent evt) {
//                    button.setBackground(Color.GREEN);
//                }

//                public void mouseExited(java.awt.event.MouseEvent evt) {
//                    button.setBackground(UIManager.getColor("control"));
//                }

                    public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == 1) {
                        controller.onLeftClick(rowIndex, columnIndex);
                    } else if (e.getButton() == 3) {
                        controller.onRightClick(rowIndex, columnIndex);
                    }
                    }
                });
                buttons[row][column] = button;
                jPanel.add(button);
            }
        }

        frame.setLayout(new BorderLayout());

        frame.add(jPanel, BorderLayout.CENTER);

        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    public void setController(NewController controller) {
        this.controller = controller;
    }

    public void setModel(NewModel model) {
        this.model = model;
    }

    public void syncWithModel(int row, int column) {
        ButtonCell cell = model.getButtonCell(row, column);
        boolean isMined = cell.getIsMined();
        String status = cell.getStatus();
        System.out.println(status);
        switch (status) {
            case "opened":
                if (isMined) {
                    buttons[row][column].setIcon(mine);
                    break;
                }
                int number = cell.getNumber();
                System.out.println(number);
                Icon icon = getNumber(number);
                buttons[row][column].setIcon(icon);
                break;
            case "closed":
                buttons[row][column].setIcon(closed);
                break;
            case "flagged":
                buttons[row][column].setIcon(flag);
                break;
        }
    }
}
