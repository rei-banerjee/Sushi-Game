package sushigame.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import sushigame.model.Belt;
import sushigame.model.BeltEvent;
import sushigame.model.BeltObserver;
import sushigame.model.Chef;
import sushigame.model.SushiGameModel;

public class ScoreboardWidget extends JPanel implements BeltObserver {

	private SushiGameModel game_model;
	private JLabel display;
	private boolean isBalance = true;
	private boolean isConsumed = false;
	private boolean isSpoiled = false;

	public ScoreboardWidget(SushiGameModel gm) {
		game_model = gm;
		game_model.getBelt().registerBeltObserver(this);

		display = new JLabel();
		display.setVerticalAlignment(SwingConstants.TOP);
		//setLayout(new BorderLayout());
		add(display, BorderLayout.CENTER);
		//		display.setText(makeScoreboardHTML());
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setAlignmentX(Component.CENTER_ALIGNMENT);


		String[] scoreOption = {"Balance", "Total Spoiled Food", "Total Food Consumed"};
		JComboBox<String> options = new JComboBox<String>(scoreOption);
		options.setMaximumSize(options.getPreferredSize());
		options.setAlignmentX(Component.LEFT_ALIGNMENT);
		options.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(options.getSelectedItem().toString().equals("Balance")) {
					setIsBalance(true);
					setIsSpoiled(false);
					setIsConsumed(false);
					display.setText(makeScoreboardHTML());
				} else if (options.getSelectedItem().toString().equals("Total Food Consumed")) {
					setIsBalance(false);
					setIsSpoiled(false);
					setIsConsumed(true);
					display.setText(makeScoreboardHTML());
				} else {
					setIsBalance(false);
					setIsSpoiled(true);
					setIsConsumed(false);
					display.setText(makeScoreboardHTML());
				}
			}

		});
		add(options);
	}

	private String makeScoreboardHTML() {
		String sb_html = "<html>";
		sb_html += "<h1>Scoreboard</h1>";

		// Create an array of all chefs and sort by balance.
		Chef[] opponent_chefs= game_model.getOpponentChefs();
		Chef[] chefs = new Chef[opponent_chefs.length+1];
		chefs[0] = game_model.getPlayerChef();
		for (int i=1; i<chefs.length; i++) {
			chefs[i] = opponent_chefs[i-1];
		}
		if (getIsBalance()) {
			Arrays.sort(chefs, new HighToLowBalanceComparator());
			for (Chef c : chefs) {
				sb_html += c.getName() + " ($" + Math.round(c.getBalance()*100.0)/100.0 + ") <br>";
			}
		} else if (getIsConsumed()) {
			Arrays.sort(chefs, new ConsumptionComparator());
			for (Chef c : chefs) {
				sb_html += c.getName() + " (" + Math.round(c.getTotalFood()*100.0)/100.0 + " ounces) <br>";
			}
		} else if (getIsSpoiled()){
			Arrays.sort(chefs, new SpoiledComparator());
			for (Chef c : chefs) {
				sb_html += c.getName() + " (" + Math.round(c.getTotalSpoiled()*100.0)/100.0 + " ounces) <br>";
			}
		}
			return sb_html;
		}

		public void refresh() {
			display.setText(makeScoreboardHTML());	

		}

		@Override
		public void handleBeltEvent(BeltEvent e) {
			if (e.getType() == BeltEvent.EventType.ROTATE) {
				refresh();
			}		
		}

		public void setIsBalance(boolean a) {
			this.isBalance = a;
		}

		public void setIsSpoiled(boolean a) {
			this.isSpoiled = a;
		}

		public void setIsConsumed(boolean a) {
			this.isConsumed = a;
		}

		public boolean getIsBalance() {
			return isBalance;
		}

		public boolean getIsSpoiled() {
			return isSpoiled;
		}

		public boolean getIsConsumed() {
			return isConsumed;
		}
	}
