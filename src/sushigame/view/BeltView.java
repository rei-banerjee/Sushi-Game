package sushigame.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import comp401.sushi.Plate;
import sushigame.model.Belt;
import sushigame.model.BeltEvent;
import sushigame.model.BeltObserver;

public class BeltView extends JPanel implements BeltObserver {

	private Belt belt;
	private JLabel[] belt_labels;
	//private boolean isMouseListenerActive = false;

	public BeltView(Belt b) {
		this.belt = b;
		belt.registerBeltObserver(this);
		setLayout(new GridLayout(belt.getSize(), 1));
		belt_labels = new JLabel[belt.getSize()];
		for (int i = 0; i < belt.getSize(); i++) {
			JLabel plabel = new JLabel("");
			plabel.setMinimumSize(new Dimension(300, 20));
			plabel.setPreferredSize(new Dimension(300, 20));
			plabel.setOpaque(true);
			plabel.setBackground(Color.GRAY);
			add(plabel);
			belt_labels[i] = plabel;
		}

		refresh();
	}

	@Override
	public void handleBeltEvent(BeltEvent e) {	
		refresh();
	}

	private void refresh() {
		for (int i=0; i<belt.getSize(); i++) {
			Plate p = belt.getPlateAtPosition(i);
			JLabel plabel = belt_labels[i];
			//int age = belt.getAgeOfPlateAtPosition(i);

			if (p == null) {
				plabel.setText("");
				plabel.setBackground(Color.GRAY);
			} else {
				int index = i;
				if (plabel.getMouseListeners() != null)  {
					for (int j = 0; j <= plabel.getMouseListeners().length-1; j++) {
						plabel.removeMouseListener(plabel.getMouseListeners()[j]);
					}
				}

				plabel.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						String info = "";
						if (p.getContents().getName().contains("sashimi") || p.getContents().getName().contains("nigiri")) {
							info += "This is a " + p.getContents().getName() + " it costs $" + p.getPrice() + ".\n"
									+ "It is made by " + p.getChef().getName() + ".\n"; 
							info += "It has been on the belt for " + belt.getAgeOfPlateAtPosition(index) + " rotations" + ".\n";
						} else {
							info += "This is a " + p.getContents().getName() + ".\n"
									+ "It is made by " + p.getChef().getName() + ".\n"; 
							info += "It has been on the belt for " + belt.getAgeOfPlateAtPosition(index) + " rotations" + ".\n";
							String ingredients = "";
							for (int j = 0; j < p.getContents().getIngredients().length;j++) {
								ingredients += p.getContents().getIngredients()[j].getName() + " " + (Math.round(p.getContents().getIngredients()[j].getAmount() * 100.0) / 100.0) + " ounces, " + "\n";
							}
							info += ingredients;
						}
						JOptionPane.showMessageDialog(null,info);
						//isMouseListenerActive = true;
					}
				});
				plabel.setText(p.getContents().getName() + " ($" + (Math.round(p.getPrice() * 100.0) / 100.0) + " )" + " (click for more info!)");
				switch (p.getColor()) {
				case RED:
					plabel.setBackground(Color.RED); break;
				case GREEN:
					plabel.setBackground(Color.GREEN); break;
				case BLUE:
					plabel.setBackground(Color.BLUE); break;
				case GOLD:
					plabel.setBackground(Color.YELLOW); break;
				}
			}
		}
	}
}
