package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;

public class InfoTableDistances extends JPanel {

	String _title;
	TableModel _tableModel;
	InfoTableDistances(String title, TableModel tableModel) {
		_title = title;
		_tableModel = tableModel;
		initGUI();
	}
	private void initGUI() {
		// Cambia el layout del panel a BorderLayout()
		this.setLayout(new BorderLayout());
	
		// Añade un borde al panel con el titulo _title
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), _title, TitledBorder.LEFT, 
				TitledBorder.TOP));
		JTable tabla = new JTable(_tableModel);
		
		// Añade un boton de reset y un Jtable (con vertical scroll bar) que usa _tableModel
		JButton reset = new JButton("Reset Distances");
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetDistances();
			}
		});
		JPanel aux = new JPanel();
		aux.setLayout(new BoxLayout(aux, BoxLayout.Y_AXIS));
		aux.setAlignmentX(CENTER_ALIGNMENT);
		JPanel aux2 = new JPanel();
		aux2.add(reset);
		aux.add(aux2);
		aux.add(Box.createRigidArea(new Dimension(1, 10)));
		aux.add(new JScrollPane(tabla));
		add(aux);
	}
	
	private void resetDistances(){
		((DistanceTableModel) _tableModel).resetDistances();
	}
}
