package sushigame.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import comp401.sushi.AvocadoPortion;
import comp401.sushi.CrabPortion;
import comp401.sushi.EelPortion;
import comp401.sushi.IngredientPortion;
import comp401.sushi.Nigiri;
import comp401.sushi.Plate;
import comp401.sushi.RedPlate;
import comp401.sushi.RicePortion;
import comp401.sushi.Roll;
import comp401.sushi.SalmonPortion;
import comp401.sushi.Sashimi;
import comp401.sushi.SeaweedPortion;
import comp401.sushi.ShrimpPortion;
import comp401.sushi.Sushi;
import comp401.sushi.TunaPortion;

public class PlayerChefView extends JPanel implements ActionListener {

	private List<ChefViewListener> listeners;
	private Sushi kmp_roll;
	private Sushi crab_sashimi;
	private Sushi eel_sashimi;
	private Sushi salmon_sashimi;
	private Sushi shrimp_sashimi;
	private Sushi tuna_sashimi;
	private Sushi eel_nigiri;
	private Sushi crab_nigiri;
	private Sushi  salmon_nigiri;
	private Sushi shrimp_nigiri;
	private Sushi tuna_nigiri;
	private int belt_size;
	private String type;
	private String[] positionArray;

	public PlayerChefView(int belt_size) {
		this.belt_size = belt_size;
		listeners = new ArrayList<ChefViewListener>();
		positionArray = new String[belt_size];

		for (int i = 0; i < belt_size; i++) {
			positionArray[i] = "" + i;
		}

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		//		JButton sashimi_button = new JButton("Make red plate of crab sashimi at position 3");
		//		sashimi_button.setActionCommand("red_crab_sashimi_at_3");
		//		sashimi_button.addActionListener(this);
		//		add(sashimi_button);

		JButton sashimi_option = new JButton("Make Sashimi");
		sashimi_option.setActionCommand("make_sashimi");
		sashimi_option.addActionListener(this);
		add(sashimi_option);

		//		JButton nigiri_button = new JButton("Make blue plate of eel nigiri at position 8");
		//		nigiri_button.setActionCommand("blue_eel_nigiri_at_8");
		//		nigiri_button.addActionListener(this);
		//		add(nigiri_button);

		JButton nigiri_option = new JButton("Make Nigiri");
		nigiri_option.setActionCommand("make_nigiri");
		nigiri_option.addActionListener(this);
		add(nigiri_option);

		//		JButton roll_button = new JButton("Make gold plate of KMP roll at position 5");
		//		roll_button.setActionCommand("gold_kmp_roll_at_5");
		//		roll_button.addActionListener(this);
		//		add(roll_button);

		JButton roll_option = new JButton("Make Roll");
		roll_option.setActionCommand("make_roll");
		roll_option.addActionListener(this);
		add(roll_option);

		kmp_roll = new Roll("KMP Roll", new IngredientPortion[] {new EelPortion(1.0), new AvocadoPortion(0.5), new SeaweedPortion(0.2)});
		crab_sashimi = new Sashimi(Sashimi.SashimiType.CRAB);
		eel_sashimi = new Sashimi(Sashimi.SashimiType.EEL);
		salmon_sashimi = new Sashimi(Sashimi.SashimiType.SALMON);
		shrimp_sashimi = new Sashimi(Sashimi.SashimiType.SHRIMP);
		tuna_sashimi = new Sashimi(Sashimi.SashimiType.TUNA);

		crab_nigiri = new Nigiri(Nigiri.NigiriType.CRAB);
		eel_nigiri = new Nigiri(Nigiri.NigiriType.EEL);
		salmon_nigiri = new Nigiri(Nigiri.NigiriType.SALMON);
		shrimp_nigiri = new Nigiri(Nigiri.NigiriType.SHRIMP);
		tuna_nigiri = new Nigiri(Nigiri.NigiriType.TUNA);

	}

	public void registerChefListener(ChefViewListener cl) {
		listeners.add(cl);
	}

	private void makeRedPlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleRedPlateRequest(plate_sushi, plate_position);
		}
	}

	private void makeGreenPlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleGreenPlateRequest(plate_sushi, plate_position);
		}
	}

	private void makeBluePlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleBluePlateRequest(plate_sushi, plate_position);
		}
	}

	private void makeGoldPlateRequest(Sushi plate_sushi, int plate_position, double price) {
		for (ChefViewListener l : listeners) {
			l.handleGoldPlateRequest(plate_sushi, plate_position, price);
		}
	}

	public void sashimiMaker() {
		//		String type;
		JFrame newFrame = new JFrame("Make Sashimi");
		newFrame.setBackground(Color.CYAN);
		newFrame.setSize(700, 300);
		newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel newPanel = new JPanel();
		newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
		newFrame.add(newPanel);

		JLabel label = new JLabel("Select your type of sashimi, color of plate, and position. If plate is gold, select a price");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		newPanel.add(label);

		JLabel typeLabel = new JLabel("Select type of sashimi");
		typeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		newPanel.add(typeLabel);

		String[] typeOptions = {  "Crab" , "Eel", "Salmon", "Shrimp", "Tuna" };
		final JComboBox<String> typeOptionPane = new JComboBox<String>(typeOptions);
		typeOptionPane.setMaximumSize(typeOptionPane.getPreferredSize());
		typeOptionPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		newPanel.add(typeOptionPane);

		Hashtable labelTable = new Hashtable();
		labelTable.put(Integer.valueOf(50), new JLabel("$5.0"));
		labelTable.put(Integer.valueOf(75), new JLabel("$7.5"));
		labelTable.put(Integer.valueOf(100), new JLabel("$10.0"));

		JLabel priceLabel = new JLabel("Price");
		priceLabel.setVisible(false);
		priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		newPanel.add(priceLabel);

		JSlider price = new JSlider(50,100);
		price.setLabelTable(labelTable);
		price.setPaintLabels(true);
		//		price.addChangeListener(this);
		price.setName("price");
		price.setVisible(false);
		price.setAlignmentX(Component.CENTER_ALIGNMENT);
		price.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent event) {
				int current = ((JSlider)event.getSource()).getValue();
				double currentX = current / 10.0;
				priceLabel.setText("Price $" + currentX);
			}

		});
		newPanel.add(price);
		price.setMajorTickSpacing(10);
		price.setMinorTickSpacing(1);
		price.setPaintTicks(true);

		JLabel plateLabel = new JLabel("Select type of plate");
		plateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		newPanel.add(plateLabel);		

		String[] plates = { "Red", "Blue", "Green", "Gold" };
		final JComboBox<String> plateOptions = new JComboBox<String> (plates);
		plateOptions.setMaximumSize(plateOptions.getPreferredSize());
		plateOptions.setAlignmentX(Component.CENTER_ALIGNMENT);
		newPanel.add(plateOptions);
		plateOptions.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (plateOptions.getSelectedItem().toString().equals("Gold")) {
					price.setVisible(true);
					priceLabel.setVisible(true);
				} else {
					price.setVisible(false);
					priceLabel.setVisible(false);
				}
			}

		});

		JLabel positionLabel = new JLabel("Select the position");
		positionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		newPanel.add(positionLabel);

		final JComboBox<String> numbers = new JComboBox<String> (positionArray);
		numbers.setMaximumSize(numbers.getPreferredSize());
		numbers.setAlignmentX(Component.CENTER_ALIGNMENT);
		newPanel.add(numbers);

		JButton make = new JButton("Make Sashimi!");
		make.setActionCommand("make");
		make.setAlignmentX(Component.CENTER_ALIGNMENT);
		newPanel.add(make);
		make.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object numberSelected = numbers.getSelectedItem();
				int newNumber = Integer.parseInt(numberSelected.toString());
				//				Object typeSelected = typeOptionPane.getSelectedItem();
				//				Object plateSelected = plateOptions.getSelectedItem();
				int priceFinal = price.getValue();
				double priceX = priceFinal / 10.0;

				switch(typeOptionPane.getSelectedItem().toString()) {
				case "Crab": 
					switch(plateOptions.getSelectedItem().toString()) {
					case "Red":
						makeRedPlateRequest(crab_sashimi, newNumber);
						break;
					case "Blue":
						makeBluePlateRequest(crab_sashimi, newNumber);
						break;
					case "Green":
						makeGreenPlateRequest(crab_sashimi, newNumber);
					case "Gold":
						makeGoldPlateRequest(crab_sashimi, newNumber,  priceX);
					}
					break;
				case "Eel":
					switch(plateOptions.getSelectedItem().toString()) {
					case "RED":
						makeRedPlateRequest(eel_sashimi, newNumber);
						break;
					case "BLUE":
						makeBluePlateRequest(eel_sashimi, newNumber);
						break;
					case "GREEN":
						makeGreenPlateRequest(eel_sashimi, newNumber);
					case "GOLD":
						makeGoldPlateRequest(eel_sashimi, newNumber,  priceX);
					}
					break;
				case "Salmon":
					switch(plateOptions.getSelectedItem().toString()) {
					case "RED":
						makeRedPlateRequest(salmon_sashimi, newNumber);
						break;
					case "BLUE":
						makeBluePlateRequest(salmon_sashimi, newNumber);
						break;
					case "GREEN":
						makeGreenPlateRequest(salmon_sashimi, newNumber);
					case "GOLD":
						makeGoldPlateRequest(salmon_sashimi, newNumber,  priceX);
					}
					break;
				case "Shrimp":
					switch(plateOptions.getSelectedItem().toString()) {
					case "RED":
						makeRedPlateRequest(shrimp_sashimi, newNumber);
						break;
					case "BLUE":
						makeBluePlateRequest(shrimp_sashimi, newNumber);
						break;
					case "GREEN":
						makeGreenPlateRequest(shrimp_sashimi, newNumber);
					case "GOLD":
						makeGoldPlateRequest(shrimp_sashimi, newNumber,  priceX);
					}
					break;
				case "Tuna":
					switch(plateOptions.getSelectedItem().toString()) {
					case "RED":
						makeRedPlateRequest(tuna_sashimi, newNumber);
						break;
					case "BLUE":
						makeBluePlateRequest(tuna_sashimi, newNumber);
						break;
					case "GREEN":
						makeGreenPlateRequest(tuna_sashimi, newNumber);
					case "GOLD":
						makeGoldPlateRequest(tuna_sashimi, newNumber,  priceX);
					}
				}
				newFrame.setVisible(false);
			}
		});

		newFrame.setVisible(true);

	}

	public void nigiriMaker() {
		//		String type;
		JFrame newFrame = new JFrame("Make Nigiri");
		newFrame.setBackground(Color.CYAN);
		newFrame.setSize(700, 300);
		newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel newPanel = new JPanel();
		newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
		newFrame.add(newPanel);

		JLabel label = new JLabel("Select your type of nigiri, color of plate, and position. If plate is gold, select a price");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		newPanel.add(label);

		JLabel typeLabel = new JLabel("Select type of nigiri");
		typeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		newPanel.add(typeLabel);

		String[] typeOptions = {  "Crab" , "Eel", "Salmon", "Shrimp", "Tuna" };
		final JComboBox<String> typeOptionPane = new JComboBox<String>(typeOptions);
		typeOptionPane.setMaximumSize(typeOptionPane.getPreferredSize());
		typeOptionPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		newPanel.add(typeOptionPane);

		Hashtable labelTable = new Hashtable();
		labelTable.put(Integer.valueOf(50), new JLabel("$5.0"));
		labelTable.put(Integer.valueOf(75), new JLabel("$7.5"));
		labelTable.put(Integer.valueOf(100), new JLabel("$10.0"));

		JLabel priceLabel = new JLabel("Price");
		priceLabel.setVisible(false);
		priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		newPanel.add(priceLabel);

		JSlider price = new JSlider(50,100);
		price.setLabelTable(labelTable);
		price.setPaintLabels(true);
		//		price.addChangeListener(this);
		price.setName("price");
		price.setVisible(false);
		price.setAlignmentX(Component.CENTER_ALIGNMENT);
		price.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent event) {
				int current = ((JSlider)event.getSource()).getValue();
				double currentX = current / 10.0;
				priceLabel.setText("Price $" + currentX);
			}

		});
		newPanel.add(price);
		price.setMajorTickSpacing(10);
		price.setMinorTickSpacing(1);
		price.setPaintTicks(true);

		JLabel plateLabel = new JLabel("Select type of plate");
		plateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		newPanel.add(plateLabel);

		String[] plates = { "Red", "Blue", "Green", "Gold" };
		final JComboBox<String> plateOptions = new JComboBox<String> (plates);
		plateOptions.setMaximumSize(plateOptions.getPreferredSize());
		plateOptions.setAlignmentX(Component.CENTER_ALIGNMENT);
		newPanel.add(plateOptions);
		plateOptions.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (plateOptions.getSelectedItem().toString().equals("Gold")) {
					price.setVisible(true);
					priceLabel.setVisible(true);
				} else {
					price.setVisible(false);
					priceLabel.setVisible(false);
				}
			}

		});

		JLabel positionLabel = new JLabel("Select position");
		positionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		newPanel.add(positionLabel);

		final JComboBox<String> numbers = new JComboBox<String> (positionArray);
		numbers.setMaximumSize(numbers.getPreferredSize());
		numbers.setAlignmentX(Component.CENTER_ALIGNMENT);
		newPanel.add(numbers);

		JButton make = new JButton("Make Nigiri!");
		make.setActionCommand("make");
		make.setAlignmentX(Component.CENTER_ALIGNMENT);
		newPanel.add(make);
		make.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object numberSelected = numbers.getSelectedItem();
				int newNumber = Integer.parseInt(numberSelected.toString());
				//				Object typeSelected = typeOptionPane.getSelectedItem();
				//				Object plateSelected = plateOptions.getSelectedItem();
				int priceFinal = price.getValue();
				double priceX = priceFinal / 10.0;

				switch(typeOptionPane.getSelectedItem().toString()) {
				case "Crab": 
					switch(plateOptions.getSelectedItem().toString()) {
					case "Red":
						makeRedPlateRequest(crab_nigiri, newNumber);
						break;
					case "Blue":
						makeBluePlateRequest(crab_nigiri, newNumber);
						break;
					case "Green":
						makeGreenPlateRequest(crab_nigiri, newNumber);
					case "Gold":
						makeGoldPlateRequest(crab_nigiri, newNumber,  priceX);
					}
					break;
				case "Eel":
					switch(plateOptions.getSelectedItem().toString()) {
					case "RED":
						makeRedPlateRequest(eel_nigiri, newNumber);
						break;
					case "BLUE":
						makeBluePlateRequest(eel_nigiri, newNumber);
						break;
					case "GREEN":
						makeGreenPlateRequest(eel_nigiri, newNumber);
					case "GOLD":
						makeGoldPlateRequest(eel_nigiri, newNumber,  priceX);
					}
					break;
				case "Salmon":
					switch(plateOptions.getSelectedItem().toString()) {
					case "RED":
						makeRedPlateRequest(salmon_nigiri, newNumber);
						break;
					case "BLUE":
						makeBluePlateRequest(salmon_nigiri, newNumber);
						break;
					case "GREEN":
						makeGreenPlateRequest(salmon_nigiri, newNumber);
					case "GOLD":
						makeGoldPlateRequest(salmon_nigiri, newNumber,  priceX);
					}
					break;
				case "Shrimp":
					switch(plateOptions.getSelectedItem().toString()) {
					case "RED":
						makeRedPlateRequest(shrimp_nigiri, newNumber);
						break;
					case "BLUE":
						makeBluePlateRequest(shrimp_nigiri, newNumber);
						break;
					case "GREEN":
						makeGreenPlateRequest(shrimp_nigiri, newNumber);
					case "GOLD":
						makeGoldPlateRequest(shrimp_nigiri, newNumber,  priceX);
					}
					break;
				case "Tuna":
					switch(plateOptions.getSelectedItem().toString()) {
					case "RED":
						makeRedPlateRequest(tuna_nigiri, newNumber);
						break;
					case "BLUE":
						makeBluePlateRequest(tuna_nigiri, newNumber);
						break;
					case "GREEN":
						makeGreenPlateRequest(tuna_nigiri, newNumber);
					case "GOLD":
						makeGoldPlateRequest(tuna_nigiri, newNumber,  priceX);
					}
				}
				newFrame.setVisible(false);
			}
		});

		newFrame.setVisible(true);
	}

	public void rollMaker() {
		JFrame newFrame = new JFrame("Make Roll");
		newFrame.setBackground(Color.CYAN);
		newFrame.setSize(700, 700);
		newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel newPanel = new JPanel();
		newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
		newFrame.add(newPanel);

		JLabel label = new JLabel("Write the name of the roll in the whitespace below!");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		newPanel.add(label);

		JTextField name = new JTextField("Default", 2);
		name.setAlignmentX(Component.CENTER_ALIGNMENT);
		newPanel.add(name);

		//		JButton addIngredients = new JButton("Add Ingredients");
		//		addIngredients.setAlignmentX(Component.CENTER_ALIGNMENT);
		//		newPanel.add(addIngredients);
		//		addIngredients.addActionListener(new ActionListener () {
		//			@Override
		//			public void actionPerformed(ActionEvent e) {
		//				makeIngredients();
		//			}
		//			
		//		});

		Hashtable labelTable = new Hashtable();
		labelTable.put(Integer.valueOf(0), new JLabel("0.0 Ounces"));
		labelTable.put(Integer.valueOf(75), new JLabel());
		labelTable.put(Integer.valueOf(150), new JLabel("1.5 Ounces"));

		JLabel riceLabel = new JLabel("Rice");
		riceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		newPanel.add(riceLabel);

		JSlider riceSlider = new JSlider(0, 150);
		riceSlider.setLabelTable(labelTable);
		riceSlider.setPaintLabels(true);
		riceSlider.setName("Rice Slider");
		riceSlider.setValue(0);
		riceSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent event) {
				int current = ((JSlider)event.getSource()).getValue();
				double currentX = current / 100.0;
				riceLabel.setText("Rice " + currentX + " Ounces");
			}

		});
		newPanel.add(riceSlider);

		JLabel seaweedLabel = new JLabel("Seaweed");
		seaweedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		newPanel.add(seaweedLabel);

		JSlider seaweedSlider = new JSlider(0, 150);
		seaweedSlider.setLabelTable(labelTable);
		seaweedSlider.setPaintLabels(true);
		seaweedSlider.setName("Seaweed Slider");
		seaweedSlider.setValue(0);
		seaweedSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent event) {
				int current = ((JSlider)event.getSource()).getValue();
				double currentX = current / 100.0;
				seaweedLabel.setText("Seaweed " + currentX + " Ounces");
			}

		});
		newPanel.add(seaweedSlider);

		JLabel avocadoLabel = new JLabel("Avocado");
		avocadoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		newPanel.add(avocadoLabel);

		JSlider avocadoSlider = new JSlider(0, 150);
		avocadoSlider.setLabelTable(labelTable);
		avocadoSlider.setPaintLabels(true);
		avocadoSlider.setName("Avocado Slider");
		avocadoSlider.setValue(0);
		avocadoSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent event) {
				int current = ((JSlider)event.getSource()).getValue();
				double currentX = current / 100.0;
				avocadoLabel.setText("Avocado " + currentX + " Ounces");
			}

		});
		newPanel.add(avocadoSlider);

		JLabel crabLabel = new JLabel("Crab");
		crabLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		newPanel.add(crabLabel);

		JSlider crabSlider = new JSlider(0, 150);
		crabSlider.setLabelTable(labelTable);
		crabSlider.setPaintLabels(true);
		crabSlider.setName("Crab Slider");
		crabSlider.setValue(0);
		crabSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent event) {
				int current = ((JSlider)event.getSource()).getValue();
				double currentX = current / 100.0;
				crabLabel.setText("Crab " + currentX + " Ounces");
			}

		});
		newPanel.add(crabSlider);

		JLabel eelLabel = new JLabel("Eel");
		eelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		newPanel.add(eelLabel);

		JSlider eelSlider = new JSlider(0, 150);
		eelSlider.setLabelTable(labelTable);
		eelSlider.setPaintLabels(true);
		eelSlider.setName("Eel Slider");
		eelSlider.setValue(0);
		eelSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent event) {
				int current = ((JSlider)event.getSource()).getValue();
				double currentX = current / 100.0;
				eelLabel.setText("Eel " + currentX + " Ounces");
			}

		});
		newPanel.add(eelSlider);

		JLabel salmonLabel = new JLabel("Salmon");
		salmonLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		newPanel.add(salmonLabel);

		JSlider salmonSlider = new JSlider(0, 150);
		salmonSlider.setLabelTable(labelTable);
		salmonSlider.setPaintLabels(true);
		salmonSlider.setName("Salmon Slider");
		salmonSlider.setValue(0);
		salmonSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent event) {
				int current = ((JSlider)event.getSource()).getValue();
				double currentX = current / 100.0;
				salmonLabel.setText("Salmon " + currentX + " Ounces");
			}

		});
		newPanel.add(salmonSlider);

		JLabel shrimpLabel = new JLabel("Shrimp");
		shrimpLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		newPanel.add(shrimpLabel);

		JSlider shrimpSlider = new JSlider(0, 150);
		shrimpSlider.setLabelTable(labelTable);
		shrimpSlider.setPaintLabels(true);
		shrimpSlider.setName("Shrimp Slider");
		shrimpSlider.setValue(0);
		shrimpSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent event) {
				int current = ((JSlider)event.getSource()).getValue();
				double currentX = current / 100.0;
				shrimpLabel.setText("Shrimp " + currentX + " Ounces");
			}

		});
		newPanel.add(shrimpSlider);

		JLabel tunaLabel = new JLabel("Tuna");
		tunaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		newPanel.add(tunaLabel);

		JSlider tunaSlider = new JSlider(0, 150);
		tunaSlider.setLabelTable(labelTable);
		tunaSlider.setPaintLabels(true);
		tunaSlider.setValue(0);
		tunaSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent event) {
				int current = ((JSlider)event.getSource()).getValue();
				double currentX = current / 100.0;
				tunaLabel.setText("Tuna " + currentX + " Ounces");
			}

		});
		tunaSlider.setName("Tuna Slider");

		newPanel.add(tunaSlider);

		Hashtable priceTable = new Hashtable();
		priceTable.put(Integer.valueOf(50), new JLabel("$5.0"));
		priceTable.put(Integer.valueOf(75), new JLabel("$7.5"));
		priceTable.put(Integer.valueOf(100), new JLabel("$10.0"));

		JLabel priceLabel = new JLabel("Price");
		priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		newPanel.add(priceLabel);

		JSlider price = new JSlider(50,100);
		price.setLabelTable(priceTable);
		price.setPaintLabels(true);
		//		price.addChangeListener(this);
		price.setName("price");
		price.setVisible(true);
		price.setAlignmentX(Component.CENTER_ALIGNMENT);
		price.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent event) {
				int current = ((JSlider)event.getSource()).getValue();
				double currentX = current / 10.0;
				priceLabel.setText("Price $" + currentX);
			}

		});
		newPanel.add(price);
		price.setMajorTickSpacing(10);
		price.setMinorTickSpacing(1);
		price.setPaintTicks(true);

		final JComboBox<String> numbers = new JComboBox<String> (positionArray);
		numbers.setMaximumSize(numbers.getPreferredSize());
		numbers.setAlignmentX(Component.CENTER_ALIGNMENT);
		newPanel.add(numbers);

		JButton make = new JButton("Make roll");
		make.setAlignmentX(Component.CENTER_ALIGNMENT);
		newPanel.add(make);
		make.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String rollName = name.getText();

				Object numberSelected = numbers.getSelectedItem();
				int newNumber = Integer.parseInt(numberSelected.toString());

				int priceFinal = price.getValue();
				double priceX = priceFinal / 10.0;

				int riceFinal = riceSlider.getValue();
				double riceX = riceFinal / 100.0;

				int seaweedFinal = seaweedSlider.getValue();
				double seaweedX = seaweedFinal / 100.0;

				int avocadoFinal = avocadoSlider.getValue();
				double avocadoX = avocadoFinal / 100.0;

				int crabFinal = crabSlider.getValue();
				double crabX = crabFinal / 100.0;

				int eelFinal = eelSlider.getValue();
				double eelX = eelFinal / 100.0;

				int salmonFinal = salmonSlider.getValue();
				double salmonX = salmonFinal / 100.0;

				int shrimpFinal = shrimpSlider.getValue();
				double shrimpX = shrimpFinal / 100.0;

				int tunaFinal = tunaSlider.getValue();
				double tunaX = tunaFinal / 100.0;

				int ingredIndex =0;

				int[] binary = new int[8];
				List<IngredientPortion> ingreds = new ArrayList<IngredientPortion>();


				if (riceX > 0) {
					ingredIndex++;
					RicePortion rice = new RicePortion(riceX);
					binary[0] = 1;
					ingreds.add(rice);
				}
				if (seaweedX > 0) {
					ingredIndex++;
					SeaweedPortion seaweed = new SeaweedPortion(seaweedX);
					binary[1] = 1;
					ingreds.add(seaweed);

				}
				if (avocadoX > 0) {
					ingredIndex++;
					AvocadoPortion avocado = new AvocadoPortion(avocadoX);
					binary[2] = 1;
					ingreds.add(avocado);
				}
				if (crabX > 0) {
					ingredIndex++;
					CrabPortion crab = new CrabPortion(crabX);
					binary[3] = 1;
					ingreds.add(crab);
				}
				if (eelX > 0) {
					ingredIndex++;
					EelPortion eel = new EelPortion(eelX);
					binary[4] = 1;	
					ingreds.add(eel);
				}
				if (salmonX > 0) {
					ingredIndex++;
					SalmonPortion salmon = new SalmonPortion(salmonX);
					binary[5] = 1;
					ingreds.add(salmon);
				}
				if (shrimpX > 0) {
					ingredIndex++;
					ShrimpPortion shrimp = new ShrimpPortion(shrimpX);
					binary[6] = 1;
					ingreds.add(shrimp);
				}
				if (tunaX > 0) {
					ingredIndex++;
					TunaPortion tuna = new TunaPortion(tunaX);
					binary[7] = 1;
					ingreds.add(tuna);
				}

				//				for (int i = 0; i < binary.length; i++) {
				//					if (binary[i] == 1) {
				//						arrayList.add();
				//					}
				//				}

				if (binary[0] == 1) {

				}

				//	IngredientPortion[] ingredients = new IngredientPortion[ingredIndex];
				//				for (int i = 0; i < ingredients.length; i++) {
				//					
				//				}
				IngredientPortion[] ingredients = new IngredientPortion[ingreds.size()];
				ingreds.toArray(ingredients);
				Roll playerRoll = new Roll(rollName, ingredients);
				makeGoldPlateRequest(playerRoll, newNumber, priceX);

				newFrame.setVisible(false);

			}

		});


		newFrame.setVisible(true);




	}

	public void createRoll() {
		List<IngredientPortion> ingreds = new ArrayList<IngredientPortion>();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "make_sashimi":
			//			makeRedPlateRequest(crab_sashimi, 3);
			sashimiMaker();
			break;
		case "make_nigiri":
			//			makeBluePlateRequest(eel_nigiri, 8);
			nigiriMaker();
			break;
		case "make_roll":
			//			makeGoldPlateRequest(kmp_roll, 5, 5.00);
			rollMaker();

		}
	}
}
