import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class sqlite {
    Connection connection;

    public sqlite(){

    }

    /**
     * Takes in a name and returns the typeID
     * @param name
     * @return
     */
    public int fetchID(String name){

        String input = "SELECT typeID FROM invTypes WHERE LOWER(typeName) = LOWER('"+name+"')";

        try(Connection conn = sqlconn.connect()){
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(input);

            return rs.getInt("typeID");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return 0;
    }

    /**
     * Takes in a typeID and returns a typename
     * @param typeID
     * @return
     */
    public String fetchName(int typeID){

        String input = "SELECT typeName FROM invTypes WHERE typeID ="+typeID;

        try(Connection conn = sqlconn.connect()){
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(input);

            return rs.getString("typeName");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return null;
    }

    /**
     * takes in the typeID of the item you want to build and returns its blueprint typeID, the number of items out, the actvity id and its name
     * @param itemBP
     * @return
     */
    public Item fetchBPID(int itemBP){

        String input = "SELECT typeID,quantity,activityID FROM industryActivityProducts WHERE productTypeID ="+itemBP;

        try(Connection conn = sqlconn.connect()){
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(input);

            while(rs.next()) {
                int ID = rs.getInt("typeID");
                int qty = rs.getInt("quantity");
                String name = fetchName(ID);
                int actID = rs.getInt("activityID");
                return new Item(ID,0,name,actID);
            }



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return null;
    }

    /**
     * This takes in a blueprint ID and returns an array of items that contain the material ID, the number of items required, the name of the items, and the actvity ID
     * @param BPID
     * @return
     */
    public Item[] fetchBPrequirements(int BPID){

        String input = "SELECT materialTypeID,quantity,activityID FROM industryActivityMaterials WHERE (typeID ="+BPID+" AND activityID = 11) OR (activityID=1 AND typeID ="+BPID+")";

        try(Connection conn = sqlconn.connect()){
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(input);

            Item[] re = new Item[20];
            int z = 0;

            while(rs.next()) {
                int ID = rs.getInt("materialTypeID");
                int qty = rs.getInt("quantity");
                String name = fetchName(ID);
                int actID = rs.getInt("activityID");
                Item r =  new Item(ID,qty,name,actID);
                re[z] = r;
                z++;
            }

            return trimArray(re);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return null;
    }

    /**
     * Takes in the ID of an item and returns the number of that item that one run of its blueprints will produce
     * @param productTypeID
     * @return
     */
    public int fetchOutAmount(int productTypeID){
        String input = "SELECT quantity FROM industryActivityProducts WHERE productTypeID ="+productTypeID ;
        int qty = 0;

        try(Connection conn = sqlconn.connect()){
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(input);

            Item[] re = new Item[20];
            int z = 0;

            while(rs.next()) {
                qty = rs.getInt("quantity");
            }

            return qty;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return 0;
    }

    /**
     * takes in an array of Items and reduces it down to required size
     * @param inarry
     * @return
     */
    public Item[] trimArray(Item[] inarry){
        int i =0;
        while(inarry[i] != null){
            i++;
        }
        Item[] r =new Item[i];

        for(int n =0; n< i;n++){
            r[n] = inarry[n];
        }
        return itemSort(r);

    }

    /**
     * Takes in an array and sorts the items from the most items needed to the least
     * @param arr
     * @return
     */
    public static Item[] itemSort(Item[] arr){

        for (int i = 0; i < arr.length-1; i++)
            for (int j = 0; j < arr.length-i-1; j++)
                if (arr[j].getAmount() < arr[j+1].getAmount())
                {
                    Item temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }

        return arr;
    }






}
