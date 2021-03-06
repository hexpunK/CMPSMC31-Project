package uk.ac.uea.mathsthing.plugins.extensions;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

import uk.ac.uea.mathsthing.IPlugin.IExtensionPlugin;
import uk.ac.uea.mathsthing.util.FormulaException;

public class ShowFormulaPlugin extends IExtensionPlugin {

	private String text;
	JMenuItem button;
	
	@Override
	public String getName() { return "showFormulaView"; }

	@Override
	public JMenuItem getMenuEntry()
	{		
		button = new JMenuItem("Show Formula");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				createGUI();
			}
		});
		button.setEnabled(false);
		return button;
	}

	@Override
	public void processFormula() throws FormulaException
	{
		text = formula.toString();
		StringBuilder sb = new StringBuilder();
		boolean superScript = false;
		int bracketsDeep = 0;
		sb.append("<html>");
		for (int i = 0; i < text.length(); i++) {
			char token = text.charAt(i);
						
			if (token == '^') {
				superScript = true;
				continue;
			}
			
			if (superScript && token == '(')
				bracketsDeep++;
			
			if (superScript && bracketsDeep > 0 && token == ')')
				bracketsDeep--;
			
			if (superScript && bracketsDeep <= 0
					&& (token == '*' || token == '+' 
					|| token == '-' || token == '/'))
				superScript = false;
			
			if (superScript)
				sb.append("<sup>");
			sb.append(token);
			if (superScript)
				sb.append("</sup>");
			
			if (superScript && bracketsDeep <= 0
					&& token == ')')
				superScript = false;
		}
		sb.append("</html>");
		text = sb.toString();
		
		button.setEnabled(formula.toString().length() > 0);
	}
	
	private void createGUI()
	{
		JFrame window = new JFrame();
		window.setBounds(50, 50, 50, 200);
		window.setTitle("Show Formula");
		window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		FlowLayout layout = new FlowLayout();
		layout.setHgap(5);
		layout.setVgap(5);
		layout.setAlignment(FlowLayout.CENTER);
		
		JLabel label = new JLabel();
		label.setFont(new Font("Arial", Font.CENTER_BASELINE, 16));
		label.setText(text);
		
		window.setLayout(layout);
		window.add(label);
		window.pack();
		window.setResizable(false);
		window.setVisible(true);
	}

	@Override
	public void onReset() {
		this.formula = null;
		this.button.setEnabled(false);
	}
}