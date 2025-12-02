package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;

public class InfoTable extends JPanel {
	String _title;
	TableModel _tableModel;
	InfoTable(String title, TableModel tableModel) {
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
		
		// Añade un Jtable (con vertical scroll bar) que usa _tableModel
		add(new JScrollPane(tabla));
	}
}