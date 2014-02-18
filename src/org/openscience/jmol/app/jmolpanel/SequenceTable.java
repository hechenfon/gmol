//added -hcf
//for genome sequence searching in Ensembl database(currently)

package org.openscience.jmol.app.jmolpanel;

import org.jmol.api.JmolViewer;
import org.jmol.i18n.GT;
import org.jmol.viewer.JmolConstants;
import org.openscience.jmol.app.jmolpanel.MeasurementTable.MeasurementListWindowListener;
import org.openscience.jmol.app.jmolpanel.MeasurementTable.MeasurementTableModel;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;

import java.awt.event.ActionEvent;
import javax.swing.JFrame;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.swing.JTextField;
import javax.swing.JLabel;

public class SequenceTable extends JDialog {
	JmolViewer viewer;
	private JTable sequenceTable;
	private SequenceTableModel sequenceTableModel;
	JPanel container = new JPanel();
	JButton okButton = new JButton(GT._("OK"));
	JButton cancelButton = new JButton(GT._("Cancel"));
	JTextField spText1 = null;
	JTextField chrText1 = null;
	JTextField fromText1 = null;
	JTextField endText1 = null;
	JTextField fromText2 = null;
	JTextField endText2 = null;
	private ResourceBundle generalEnsemblBundle;
	int speInitialId = 1;
	String spName1 = "";
	String spName = "Bos_taurus";//for initial displaying-ensembl
	int chrInitialId = 0;
	String chrName1 = "";
	String chrName2 = "1";
	BitSet selectedBS = new BitSet();
	int fromPos1 = 0;
	int endPos1 = 1;
	int fromPos2 = 0;
	int endPos2 = 1;
	String selectedChrFile = "";
	
	//for local genome database
	String localBaseDIR = "";

	//recording database type: 0-not specified; 1-Local Database; 2-Ensembl Database
	int dbType = 1;
	
	// initial the species hash - include all the ensembl species name/alias and
	// their relationship with sequencetable spChoice.
	private HashMap<String, Integer> initialSp() {
		HashMap<String, Integer> spMap = new HashMap<String, Integer>();
		spMap.put("Anolis carolinensis", 1);
		spMap.put("Anole lizard", 1);
		spMap.put("Anole_lizard", 1);
		spMap.put("Anolis_carolinensis", 1);
		spMap.put("Bos taurus", 2);
		spMap.put("Cow", 2);
		spMap.put("Bos_taurus", 2);
		spMap.put("Caenorhabditis elegans", 3);
		spMap.put("Caenorhabditis_elegans", 3);
		spMap.put("Callithrix jacchus", 4);
		spMap.put("Marmoset", 4);
		spMap.put("Callithrix_jacchus", 4);
		spMap.put("Ciona intestinalis", 6);
		spMap.put("Ciona_intestinalis", 6);
		spMap.put("Danio rerio", 7);
		spMap.put("Zebrafish", 7);
		spMap.put("Danio_rerio", 7);
		spMap.put("Drosophila melanogaster", 8);
		spMap.put("Fruitfly", 8);
		spMap.put("Drosophila_melanogaster", 8);
		spMap.put("Equus caballus", 9);
		spMap.put("Horse", 9);
		spMap.put("Equus_caballus", 9);
		spMap.put("Gallus gallus", 10);
		spMap.put("Chicken", 10);
		spMap.put("Gallus_gallus", 10);
		spMap.put("Gasterosteus aculeatus", 11);
		spMap.put("Stickleback", 11);
		spMap.put("Gasterosteus_aculeatus", 11);
		spMap.put("Homo sapiens", 13);
		spMap.put("Human", 13);
		spMap.put("Homo_sapiens", 13);
		spMap.put("Macaca mulatta", 14);
		spMap.put("Macaque", 14);
		spMap.put("Macaca_mulatta", 14);
		spMap.put("Meleagris gallopavo", 15);
		spMap.put("Turkey", 15);
		spMap.put("Meleagris_gallopavo", 15);
		spMap.put("Monodelphis domestica", 16);
		spMap.put("Opossum", 16);
		spMap.put("Monodelphis_domestica", 16);
		spMap.put("Mus musculus", 17);
		spMap.put("Mouse", 17);
		spMap.put("Mus_musculus", 17);
		spMap.put("Ornithorhynchus anatinus", 18);
		spMap.put("Platypus", 18);
		spMap.put("Ornithorhynchus_anatinus", 18);
		spMap.put("Oryctolagus cuniculus", 19);
		spMap.put("Rabbit", 19);
		spMap.put("Oryctolagus_cuniculus", 19);
		spMap.put("Oryzias latipes", 20);
		spMap.put("Medaka", 20);
		spMap.put("Oryzias_latipes", 20);
		spMap.put("Pan troglodytes", 21);
		spMap.put("Chimpanzee", 21);
		spMap.put("Pan_troglodytes", 21);
		spMap.put("Pongo abelii", 22);
		spMap.put("Orangutan", 22);
		spMap.put("Pongo_abelii", 22);
		spMap.put("Rattus norvegicus", 23);
		spMap.put("Rat", 23);
		spMap.put("Rattus_norvegicus", 23);
		spMap.put("Saccharomyces cerevisiae", 24);
		spMap.put("Saccharomyces_cerevisiae", 24);
		spMap.put("Sus scrofa", 25);
		spMap.put("Pig", 25);
		spMap.put("Sus_scrofa", 25);
		spMap.put("Taeniopygia guttata", 26);
		spMap.put("Zebra Finch", 26);
		spMap.put("Zebra_Finch", 26);
		spMap.put("Taeniopygia_guttata", 26);
		spMap.put("Tetraodon nigroviridis", 27);
		spMap.put("Tetraodon", 27);
		spMap.put("Tetraodon_nigroviridis", 27);
		return spMap;
	}

	public SequenceTable(JmolViewer viewer, JFrame parentFrame) {

		super(parentFrame, GT._("GenomeSequenceViewer"), false);
		this.viewer = viewer;

		container.setLayout(new BorderLayout());

		// add first line components
		container.add(constructUpPanel(), BorderLayout.NORTH);
		// add second line components
		JPanel sdPanel = new JPanel();
		sdPanel.setLayout(new BorderLayout());
		container.add(sdPanel, BorderLayout.CENTER);

		// add bottom line components
		container.add(constructBtPanel(), BorderLayout.SOUTH);

		// addWindowListener(new MeasurementListWindowListener());
		getContentPane().add(container);
		pack();
		centerDialog();
	}
	
	class SequenceTableModel extends AbstractTableModel {

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	JComponent constructUpPanel() {
		final JPanel upPanel = new JPanel();
		upPanel.setLayout(new BorderLayout());
		
		//for first panel - for local database sequence extracting
		final JPanel fstPanel = new JPanel();
		fstPanel.setLayout(new BorderLayout());
		JLabel spLabel1 = new JLabel(GT._("Species:"));
		spText1 = new JTextField(spName1,10);
		JLabel chrLabel1 = new JLabel(GT._("chrom:"));
		chrText1 = new JTextField(chrName1,5);
		JLabel fromLabel1 = new JLabel(GT._("From:"));
		fromText1 = new JTextField(String.valueOf(fromPos1),7);
		JLabel endLabel1 = new JLabel(GT._("End:"));
		endText1 = new JTextField(String.valueOf(endPos1),8);
		
		JPanel onePanel = new JPanel();
		onePanel.add(spLabel1);
		onePanel.add(spText1);
		spText1.setEditable(false);
		fstPanel.add(onePanel,BorderLayout.NORTH);
		
		JPanel twoPanel = new JPanel();
		twoPanel.add(chrLabel1);
		twoPanel.add(chrText1);
		chrText1.setEditable(false);
		twoPanel.add(fromLabel1);
		twoPanel.add(fromText1);
		fromText1.setEditable(false);
		twoPanel.add(endLabel1);
		twoPanel.add(endText1);
		endText1.setEditable(false);
		fstPanel.add(twoPanel,BorderLayout.SOUTH);
		
		//generate second panel - for Ensembl database sequence extracting
		final JPanel secPanel = new JPanel();
		secPanel.setLayout(new BorderLayout());
		JLabel spLabel2 = new JLabel(GT._("Species:"));
		final JComboBox spChoice2 = new JComboBox();
		JLabel chrLabel2 = new JLabel(GT._("chrom:"));
		final JComboBox chrChoice2 = new JComboBox();
		JLabel fromLabel2 = new JLabel(GT._("From:"));
		fromText2 = new JTextField(String.valueOf(fromPos2),7);
		JLabel endLabel2 = new JLabel(GT._("End:"));
		endText2 = new JTextField(String.valueOf(endPos2),8);
		
		// generate spcombobox - from properties file - xia la kuang
		// generate chrText - from properties file - xia la kuang
		try {
			String t = "/org/openscience/jmol/app/jmolpanel/Properties/ensembl-genomes.properties";
			generalEnsemblBundle = new PropertyResourceBundle(getClass()
					.getResourceAsStream(t));
			// initial construct
			String[] ensemblSps = tokenize(generalEnsemblBundle
					.getString("species"));

			for (String sp : ensemblSps) {
				spChoice2.addItem(sp);
			}
			spChoice2.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent ie) {
					if (ie.getStateChange() == 1) {
						chrChoice2.removeAllItems();
						String[] ensemblChr = tokenize(generalEnsemblBundle
								.getString(ie.getItem().toString()));
						for (String chr : ensemblChr) {
							chrChoice2.addItem(chr);
						}
						spName = (String)spChoice2.getSelectedItem();
					}
				}
			});
			spChoice2.setSelectedIndex(speInitialId);
			
			chrChoice2.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent ie) {
					if(ie.getStateChange() == 1) {
						chrName2 = (String)chrChoice2.getSelectedItem();
					}
				}
			});
			chrChoice2.setSelectedIndex(chrInitialId);
		} catch (IOException ex) {
			throw new RuntimeException(ex.toString());
		}
		
		JPanel threePanel = new JPanel();
		threePanel.add(spLabel2);
		threePanel.add(spChoice2);
		secPanel.add(threePanel,BorderLayout.NORTH);
		
		JPanel fourPanel = new JPanel();
		fourPanel.add(chrLabel2);
		fourPanel.add(chrChoice2);
		fourPanel.add(fromLabel2);
		fourPanel.add(fromText2);
		fourPanel.add(endLabel2);
		fourPanel.add(endText2);
		secPanel.add(fourPanel,BorderLayout.SOUTH);

		final JPanel resPanel = new JPanel();
		final JLabel resourceLabel = new JLabel(GT._("Choose Database:"));
		JComboBox resourceChoice = new JComboBox();
		resourceChoice.addItem(GT._("Local_Database"));
		resourceChoice.addItem(GT._("From_Ensembl"));
		resPanel.add(resourceLabel);
		resPanel.add(resourceChoice);
		upPanel.add(resPanel,BorderLayout.NORTH);

		upPanel.add(fstPanel);

		resourceChoice.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent ie) {
				if (ie.getStateChange() == 1) {
					if (ie.getItem().toString().equals("From_Ensembl")) {
						upPanel.remove(fstPanel);
						upPanel.add(secPanel);
						upPanel.repaint();
						upPanel.revalidate();
						dbType = 2;
					}
					else if (ie.getItem().toString().equals("Local_Database")) {
						localBaseDIR = viewer.getLocalSeqPath();
						upPanel.remove(secPanel);
						upPanel.add(fstPanel);
						upPanel.repaint();
						upPanel.revalidate();
						dbType = 1;
					}
				}
			}
		});
		
		return upPanel;
	}
	
	
	//construct the initial panel for local sequence - to use local database, user need to load and select one unit
	

	// refresh info based on unit selected
	void updateSequenceTableData() {
		okButton.setEnabled(true);
		selectedBS = viewer.getSelectionSet(false);
		// generate species,chrom,from,end
		if (selectedBS.cardinality() == 1) {
			int selectedUnitIndex = 0;
			for (int i = 0; i < selectedBS.length(); i++) {
				if (selectedBS.get(i)) {
					selectedUnitIndex = i;
				}
			}
			String selectedUnitInfo = viewer.getAtomInfo(selectedUnitIndex,true);//returned info: speceis name, local chrom number, ensembl chrom number, genome sequence from, genome sequence end, local sequence filename
			
			//initial sp name - local
			String lcSpName = findSpLc(selectedUnitInfo);
			spName1 = lcSpName;
			
			// initial sp name - Ensembl
			int selectedSpId = findSpEns(selectedUnitInfo);
			if (selectedSpId > 0) {
				setInitialSp(selectedSpId - 1);
			} else {
				setInitialSp(2);
			}
			
			//initial chr Num - local
			String selectedChrLc = findChrLc(selectedUnitInfo);
			chrName1 = selectedChrLc;
			
			//initial local database file name
			selectedChrFile = findLcSeqFile(selectedUnitInfo);
			
			
			// initial chr Num -Ensembl & local
			String selectedChr = findChrEns(selectedUnitInfo);
			if (isNumeric(selectedChr)) {
				try {
					setInitialChr(Integer.parseInt(selectedChr) - 1);
				} finally {
					// no action
				}
			} else {
				setInitialChr(1);
			}
			
			//initial frompos and endpos
			fromPos1 = findFR(selectedUnitInfo);
			endPos1 = findEN(selectedUnitInfo);
			fromPos2 = fromPos1;
			endPos2 = endPos1;
			
			//update panel-fstPanel && secPanel
			container.removeAll();
			container.setLayout(new BorderLayout());
			container.add(constructUpPanel(), BorderLayout.NORTH);
			JPanel sdPanel = new JPanel();
			sdPanel.setLayout(new BorderLayout());
			container.add(sdPanel, BorderLayout.CENTER);
			container.add(constructBtPanel(), BorderLayout.SOUTH);
			
			pack();
			centerDialog();
			//sequenceTable.fire
		}

	}
	
	//find species name- local
	private String findSpLc(String unitInfo) {
		String[] unitSeria = unitInfo.split(",");
		String spName = unitSeria[0];
		return spName;
	}
	
	// find default species - ensembl
	private int findSpEns(String unitInfo) {
		String[] unitSeria = unitInfo.split(",");
		String spName = unitSeria[0];
		HashMap<String, Integer> spMap = initialSp();
		Object spId = null;

		Iterator iter = spMap.keySet().iterator();
		while (iter.hasNext()) {
			Object key = iter.next();
			Object value = spMap.get(key);
			if (key.toString().toUpperCase().equals(spName.toUpperCase())) {
				spId = value;
				break;
			}
		}
		if (spId == null) {
			spId = 0;
		}
		return (Integer) spId;
	}
	
	//find default chr-local
	private String findChrLc(String unitInfo) {
		String[] unitSeria = unitInfo.split(",");
		String chrNum = unitSeria[1];
		return chrNum;
	}
	//find default sequence file name
	private String findLcSeqFile(String unitInfo) {
		String[] unitSeria = unitInfo.split(",");
		String chrNum = unitSeria[5];
		return chrNum;
	}
	
	// find default chr-ensembl
	private String findChrEns(String unitInfo) {
		String[] unitSeria = unitInfo.split(",");
		String chrNum = unitSeria[2];
		return chrNum;
	}

	//find default frompos - both
	private int findFR (String unitInfo) {
		String[] unitSeria = unitInfo.split(",");
		int fromPos = Integer.parseInt(unitSeria[3]);
		return fromPos;
	}
	
	//find default endpos - both
	private int findEN (String unitInfo) {
		String[] unitSeria = unitInfo.split(",");
		int endPos = Integer.parseInt(unitSeria[4]);
		return endPos;
	}
	
	private boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Take the given string and chop it up into a series of strings on
	 * whitespace boundries. This is useful for trying to get an array of
	 * strings out of the resource file.
	 * 
	 * @param input
	 *            String to chop
	 * @return Strings chopped on whitespace boundaries
	 */
	protected String[] tokenize(String input) {
		List<String> v = new ArrayList<String>();
		StringTokenizer t = new StringTokenizer(input);
		while (t.hasMoreTokens())
			v.add(t.nextToken());
		return v.toArray(new String[v.size()]);
	}

	JComponent constructBtPanel() {
		JPanel btPanel = new JPanel();
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//chrName1 = chromNum.getText();
				String fromPosString1 = fromText1.getText();
				String endPosString1 = endText1.getText();
				String fromPosString2 = fromText2.getText();
				String endPosString2 = endText2.getText();
				if (dbType == 2 && isNumeric(fromPosString2) && isNumeric(endPosString2)) {
					String getSeqScript = "getseqEnsembl" + " \"" + spName + "\"" + "," + chrName2 + "," + fromPosString2 + "," + endPosString2;
					viewer.script(getSeqScript);
					close();
				}
				else if (dbType == 1 && chrName1 != "" && isNumeric(fromPosString1) && isNumeric(endPosString1)) {
					String getSeqScript = "getseqLocal" + " \"" + spName + "\"" + "," + chrName1 + "," + fromPosString1 + "," + endPosString1 + "," + "\"" + selectedChrFile + "\"";
					viewer.script(getSeqScript);
					close();
				}
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		btPanel.setLayout(new FlowLayout());
		btPanel.add(okButton);
		btPanel.add(cancelButton);

		return btPanel;
	}
	
	
	private void cleanData() {
		spText1 = null;
		chrText1 = null;
		fromText1 = null;
		endText1 = null;
		fromText2 = null;
		endText2 = null;
		generalEnsemblBundle = null;
		speInitialId = 1;
		spName1 = "";
		spName = "Bos_taurus";
		chrInitialId = 0;
		chrName1 = "";
		chrName2 = "1";
		fromPos1 = 0;
		endPos1 = 1;
		fromPos2 = 0;
		endPos2 = 1;
		selectedChrFile = "";
	}
	
	public void close() {
		this.setVisible(false);
		cleanData();
	}

	public void activate() {
		updateSequenceTableData();
		setVisible(true);
	}

	protected void centerDialog() {
		Dimension screenSize = this.getToolkit().getScreenSize();
		Dimension size = this.getSize();
		screenSize.height = screenSize.height / 2;
		screenSize.width = screenSize.width / 2;
		size.height = size.height / 2;
		size.width = size.width / 2;
		int y = screenSize.height - size.height;
		int x = screenSize.width - size.width;
		this.setLocation(x, y);
	}

	private void setInitialSp(int i) {
		if (i < 0)
			return;
		this.speInitialId = i;
	}

	private void setInitialChr(int i) {
		if (i < 0)
			return;
		this.chrInitialId = i;
	}

	
}
