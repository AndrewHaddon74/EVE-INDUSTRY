import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.*;
import java.beans.PropertyChangeListener;

public class GUI {
    private JFormattedTextField runs;
    private JButton updateButton;
    private JPanel main;
    private JComboBox matRig;
    private JPanel Ripable;
    private JEditorPane editorPane1;
    private JLabel totalCost;
    private JLabel profit;
    private JLabel itemNumOut;
    private JComboBox inputItem;
    private JButton buyAllButton;
    private JLabel buyAllListLabel;
    JComboBox inLineRigSelector = new JComboBox();

    private JCheckBox inLine = new JCheckBox();

    private JTable table2 = new JTable();


    Item[] levelOneBuildRequirments;
    Item[] levelTwoBuildRequirments;

    int lvltworuns;


    public GUI() {
        inLineRigSelector.setVisible(true);
        inLineRigSelector.addItem("buy");
        inLineRigSelector.addItem("Build");
//        inLineRigSelector.addItem("no Rig");
//        inLineRigSelector.addItem("lowsec Rig T1");
//        inLineRigSelector.addItem("lowsec Rig T2");
//        inLineRigSelector.addItem("nullsec Rig T1");
//        inLineRigSelector.addItem("nullsec Rig T2");
        inLineRigSelector.setSelectedIndex(1);

        //Sets up the rig selection combobox
        matRig.addItem("no Rig");
        matRig.addItem("lowsec Rig T1");
        matRig.addItem("lowsec Rig T2");
        matRig.addItem("nullsec Rig T1");
        matRig.addItem("nullsec Rig T2");

        matRig.setSelectedIndex(0);

        //Sets up the reaction auto-complete/secletionbox
        AutoCompleteDecorator.decorate(inputItem);
        JTable table = new JTable();


        //Sets up the main table table


        JFrame frame = new JFrame("Reactions");
        frame.setContentPane(main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        //Global Varaible


        //Update Button Pushed All calculations and sheet updating done inside
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Internail Variable
                Double materialReductionMultiplier = reactionrigReductionSwitch(matRig);
                int numRuns = Integer.parseInt(runs.getText());

                //initialize methods
                sqlite sqlite = new sqlite();

                //Get the reduction amount from selected Rig


                //Get the typeID of the item from selection window
                int inputID = sqlite.fetchID(inputItem.getSelectedItem().toString());
                Item inputBPID = sqlite.fetchBPID(inputID);

                levelOneBuildRequirments = sqlite.fetchBPrequirements(inputBPID.ID);

                //Table this sets up the table and holds the action listener for the secondary table
                DefaultTableModel model = new DefaultTableModel(levelOneBuildRequirments.length, 5);
                model.addTableModelListener(new TableModelListener() {
                    @Override
                    public void tableChanged(TableModelEvent e) {
                        //clears the level two build list
                        if(levelTwoBuildRequirments != null){
                            levelTwoBuildRequirments = new Item[0];
                        }

                        //sets up the model well also clearing any data that would of been on a old table
                        DefaultTableModel model2 = new DefaultTableModel(10,5);
                        model2.setRowCount(0);
                        model2.setRowCount(10);
                        table2.setModel(model2);

                        //go though the main table once to identify all rows that have been flagged for building
                        for(int i =0; i<levelOneBuildRequirments.length; i++){
                            if(table.getValueAt(i,1) != null && table.getValueAt(i,1) !="buy"){
                                //takes care of manufacturing
                                if(levelOneBuildRequirments[i].getActvityTYPE() == 1){

                                }
                                //takes care of reactions
                                else if(levelOneBuildRequirments[i].getActvityTYPE() == 11){
                                    //gets the number of runs for the level two reactions
                                    lvltworuns = (twohundredRounder(Integer.parseInt(table.getValueAt(i,2).toString())) / sqlite.fetchOutAmount(levelOneBuildRequirments[i].ID));
                                    //gets the build requirments
                                    Item[] temp = sqlite.fetchBPrequirements(sqlite.fetchBPID(levelOneBuildRequirments[i].ID).getID());
                                    //creates the holding array and copys leveltwobuildrequirments into it
                                    if(levelTwoBuildRequirments != null) {
                                        Item[] holding = new Item[levelTwoBuildRequirments.length];
                                        for (int z = 0; z < levelTwoBuildRequirments.length; z++) {
                                            holding[z] = levelTwoBuildRequirments[z];
                                        }
                                        //gets the new combined size
                                        int size = temp.length + levelTwoBuildRequirments.length;
                                        levelTwoBuildRequirments = new Item[size];

                                        //combines all the level two build requirements
                                        System.arraycopy(holding, 0, levelTwoBuildRequirments, 0, holding.length);
                                        System.arraycopy(temp, 0, levelTwoBuildRequirments, holding.length, temp.length);

                                    //if this is the first time though the loop it just remakes level two build requirements
                                    }else{
                                        levelTwoBuildRequirments = sqlite.fetchBPrequirements(sqlite.fetchBPID(levelOneBuildRequirments[i].ID).getID());
                                    }
                                }
                            }

                        }
                        if(levelTwoBuildRequirments != null){
                            model2.setRowCount(levelTwoBuildRequirments.length);
                            table2.setModel(model2);
                            //fills out the level two table
                            for (int j = 0; j < levelTwoBuildRequirments.length; j++) {
                                table2.setValueAt(levelTwoBuildRequirments[j].name, j, 0);
                                table2.setValueAt(trueAmount(levelTwoBuildRequirments[j], materialReductionMultiplier, 1.0, lvltworuns), j, 2);
                                table2.setValueAt("PPR", j, 3);
                                table2.setValueAt("Total Price", j, 4);
                            }
                        }


                    }
                });
                //sets up the first table
                table.setModel(model);
                table.clearSelection();
                table.setRowHeight(20);
                TableColumn selection = table.getColumnModel().getColumn(1);
                selection.setCellEditor(new DefaultCellEditor(inLineRigSelector));

                //Fills out the first level table
                for (int i = 0; i < levelOneBuildRequirments.length; i++) {
                    table.setValueAt(levelOneBuildRequirments[i].name, i, 0);
                    table.setValueAt(trueAmount(levelOneBuildRequirments[i], materialReductionMultiplier, 1.0, numRuns), i, 2);
                    table.setValueAt("PPR", i, 3);
                    table.setValueAt("Total Price", i, 4);
                }


                //adds both the table to the ripable pannel
                Ripable.add(table);
                Ripable.add(table2);
                Ripable.updateUI();

            }
        });


        //Button To create the buy all list
        buyAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                //uses name number to just pass in needed info
                nameNumber[] f = new nameNumber[30];


                int offSet =0;
                //Goes though level one tree to find none builds
                for (int i = 0; i < levelOneBuildRequirments.length; i++) {
                    if(table.getValueAt(i,1) == null || table.getValueAt(i,1) =="buy"){
                        nameNumber holding = new nameNumber(levelOneBuildRequirments[i].name,Integer.parseInt(table.getValueAt(i,2).toString()));
                        f[offSet] = holding;
                        offSet++;
                    }
                }

                //Goes though secend level loop and gets all materials there

                for (int i = 0; i < levelTwoBuildRequirments.length; i++) {
                        nameNumber holding = new nameNumber(levelTwoBuildRequirments[i].name,Integer.parseInt(table2.getValueAt(i,2).toString()));
                        f[offSet] = holding;
                        offSet++;
                    }

                buyAllList bal = new buyAllList(f);

            }
        });

    }

    public int trueAmount(Item item, Double rigReduction, double MEreduction, int numRuns) {

        int out = (int) Math.round((item.getAmount() * numRuns) * rigReduction * MEreduction);
        if (item.name.contains("Fuel Block")) {
            return out;
        }


        return out;
    }

    public static int twohundredRounder(int intake) {
        if (intake % 200 == 0) {
            return intake;
        }
        intake = ((intake + 99) / 100) * 100;
        if (intake % 200 == 0) {
            return intake;
        }
        if (intake % 200 == 100) {
            return intake + 100;
        }
        return -1;
    }

    public static int rounderFuel(int intake) {
        Double preCast = 5 * (Math.ceil(Math.abs(intake / 5)));
        return (int) Math.round(preCast);
    }

    public static double reactionrigReductionSwitch(JComboBox matRig) {
        Double materialReductionMultiplier;
        switch (matRig.getSelectedIndex()) {
            //No Rig
            case 0:
                materialReductionMultiplier = 1.0;
                break;
            //LowSec T1
            case 1:
                materialReductionMultiplier = 0.98;
                break;
            //LowSec T2
            case 2:
                materialReductionMultiplier = .976;
                break;
            //NullSec T1
            case 3:
                materialReductionMultiplier = 0.978;
                break;
            //NullSec T2
            case 4:
                materialReductionMultiplier = 0.9736;
                break;
            //Defualt is set to no rig
            default:
                materialReductionMultiplier = 1.0;
        }
        return materialReductionMultiplier;
    }


    public static nameNumber[] fAdder(nameNumber[] nameNumber,Item item,int truns){
        int i =0;
        while(nameNumber[i] != null){
            if(nameNumber[i].getID() == item.ID){
                int t =nameNumber[i].getAmount();
                t += item.Amount * truns;
                nameNumber[i].setAmount(t);
            }
        }
        return nameNumber;



    }

}

