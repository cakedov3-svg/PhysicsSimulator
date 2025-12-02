package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class StatusBar extends JPanel implements SimulatorObserver{
	int groupNumber;
	JLabel time;
	JLabel timeValue;
	JLabel groups;
	JLabel groupsCount;
	
	StatusBar(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}
	
	private void initGUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createBevelBorder(1));

		time = new JLabel("Time: ");
		timeValue = new JLabel("0.00");
		add(time);
		add(timeValue);
		
		JSeparator s1 = new JSeparator(JSeparator.VERTICAL);
		s1.setPreferredSize(new Dimension(10, 20));
		add(s1);

		groups = new JLabel ("Groups: ");
		groupsCount = new JLabel(((Integer)groupNumber).toString());
		add(groups);
		add(groupsCount);
		
		JSeparator s2 = new JSeparator(JSeparator.VERTICAL);
		s2.setPreferredSize(new Dimension(10, 20));
		add(s2);
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		timeValue.setText(((Double) time).toString());
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		groupNumber = 0;
		groupsCount.setText(((Integer)groupNumber).toString());
		timeValue.setText("0.00");
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		groupNumber = groups.values().size();
		groupsCount.setText(((Integer)groupNumber).toString());
		timeValue.setText(((Double) time).toString());
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		groupNumber++;
		groupsCount.setText(((Integer)groupNumber).toString());
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
		
	}
	
	// TODO el resto de m�todos van aqu�
}
