/**
 * Author: Ian Foertsch
 * Date: 12/18/14
 * Project: N-Puzzle Problem
 */


import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;


/**
 * Driver class to implement the GUI interface with the NPuzzleDriver
 * @author Ian
 *
 */
public class NPuzzleSolver extends JFrame {

	private JPanel contentPane;
	private JTextField dimensionField;
	private JTextField multiplierField;
	
	private final int DEFAULT_DIMENSION = 3;
	private final int MAX_DIMENSION = 10;
	private final int DEFAULT_MULTIPLIER = 1;


	/**
	 * Launch the application: Generated by the WindowBuilderTool.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NPuzzleSolver frame = new NPuzzleSolver();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public NPuzzleSolver() {
		
		setResizable(false);
		setTitle("NPuzzle Solver");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 299, 187);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		buildPanel();
		setVisible(true);
	}
	
	public void buildPanel()
	{
		dimensionField = new JTextField();
		dimensionField.setBounds(151, 11, 86, 20);
		contentPane.add(dimensionField);
		dimensionField.setColumns(2);
		
		multiplierField = new JTextField();
		multiplierField.setBounds(151, 55, 86, 20);
		contentPane.add(multiplierField);
		multiplierField.setColumns(2);
		
		JLabel puzzleDimensionLabel = new JLabel("Puzzle Dimension: ");
		puzzleDimensionLabel.setBounds(24, 14, 120, 14);
		contentPane.add(puzzleDimensionLabel);
		
		JLabel multiplierLabel = new JLabel("Heuristic Multiplier: ");
		multiplierLabel.setBounds(24, 58, 120, 14);
		contentPane.add(multiplierLabel);
		
		JButton solveButton = new JButton("Generate and Solve Puzzle");
		solveButton.setBounds(47, 104, 190, 23);
		solveButton.addActionListener(new buttonListener());
		contentPane.add(solveButton);
	}
	
	private class buttonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent action)
		{
			int dimension  = DEFAULT_DIMENSION;
			int multiplier = DEFAULT_MULTIPLIER;
			
			if(action.getActionCommand().equals("Generate and Solve Puzzle"))
			{
				//get and parse input from the dimension label and the heuristic multiplier label,
				//handling parse errors using try/catch blocks.
				try
				{
					dimension = Integer.parseInt(dimensionField.getText());
					if(dimension > MAX_DIMENSION)
					{
						dimension = MAX_DIMENSION;
					}
					else if(dimension < DEFAULT_DIMENSION)
					{
						dimension = DEFAULT_DIMENSION;
					}
				}
				catch(NumberFormatException e)
				{
					dimension = DEFAULT_DIMENSION;
				}
				//Repeat the operation for the heuristic multiplier Label.
				try
				{
					multiplier = Integer.parseInt(multiplierField.getText());
					if(multiplier < DEFAULT_MULTIPLIER)
					{
						multiplier = DEFAULT_MULTIPLIER;
					}
				}
				catch(NumberFormatException e)
				{
					multiplier = DEFAULT_MULTIPLIER;
				}
			//launch the puzzle driver class.
			new NPuzzleDriver(dimension, multiplier);
			}
		}
	}
}
