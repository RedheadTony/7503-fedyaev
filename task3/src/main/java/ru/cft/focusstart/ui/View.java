package ru.cft.focusstart.ui;

import ru.cft.focusstart.*;
import ru.cft.focusstart.model.Cell;
import ru.cft.focusstart.model.CellState;
import ru.cft.focusstart.model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

import static javax.swing.JOptionPane.showMessageDialog;

public class View implements ChangeListener {
    private Model model;
    private Controller controller;
    private final Settings settings = Settings.getInstance();

    private JButton[][] buttons = new JButton[settings.getSize()][settings.getSize()];

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
        int size = settings.getSize();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JMenuItem exitItem = new JMenuItem("New game");
        exitItem.addActionListener(e -> controller.startNewGame());

        JMenu jMenu = new JMenu("Game");
        jMenu.add(exitItem);

        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(jMenu);

        frame.setJMenuBar(jMenuBar);

        JPanel jPanel = new JPanel();
        frame.add(jPanel);

        jPanel.setLayout(new GridLayout(size, size));
        Dimension buttonPreferredSize = new Dimension(40, 40);
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                JButton button = new JButton();
                button.setPreferredSize(buttonPreferredSize);

                button.setIcon(closed);

                int rowIndex = row;
                int columnIndex = column;
                button.addMouseListener(new java.awt.event.MouseAdapter() {

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

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(Model model) {
        this.model = model;
        this.model.setOnChangedListener(this);
    }


    public void syncWithModel(int row, int column) {
        Cell cell = model.getButtonCell(row, column);
        CellState status = cell.getStatus();
        switch (status) {
            case EXPLODED:
                buttons[row][column].setIcon(mine);
                break;
            case OPENED:
                int number = cell.getMinesAroundCount();
                Icon icon = getNumber(number);
                buttons[row][column].setIcon(icon);
                break;
            case CLOSED:
                buttons[row][column].setIcon(closed);
                break;
            case FLAGGED:
                buttons[row][column].setIcon(flag);
                break;
        }
    }

    @Override
    public void onChanged(int row, int column) {
        syncWithModel(row, column);
    }

    @Override
    public void onWin() {
        showMessageDialog(null, "You won!!!");
    }

    @Override
    public void onDefeat() {
        showMessageDialog(null, "You lose!!!");
    }
}
