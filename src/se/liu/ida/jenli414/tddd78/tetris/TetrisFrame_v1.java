/*package se.liu.ida.jenli414.tddd78.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TetrisFrame_v1 extends JFrame
{

    public TetrisFrame_v1(Board board) throws HeadlessException {
	super("Awesome Tetris");

	JMenuBar menuBar = new JMenuBar();

	JMenuItem exitOption = new JMenuItem("Exit");
	exitOption.addActionListener(exitAction);
	menuBar.add(exitOption);

	setJMenuBar(menuBar);

	setLayout(new BorderLayout());

	JTextArea jTextArea = new JTextArea(board.getHeight(), board.getWidth());
	jTextArea.setFont(new Font("Monospaced",Font.PLAIN,20));
	jTextArea.setText(BoardToTextConverter.convertToText(board));

	add(jTextArea, BorderLayout.CENTER);

	pack();
	setVisible(true);

    }

    final Action exitAction = new AbstractAction() {
	public void actionPerformed(ActionEvent e) {
	    if (JOptionPane.showConfirmDialog(null, "Exit game?",
		 "Exit", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
		System.exit(0);
	    }
	}
    };
}*/
