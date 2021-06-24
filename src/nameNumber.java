public class nameNumber {

    String name;
    int ID;
    int Amount;

    public nameNumber(String name, int amount ){
        this.name = name;
        this.Amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) { this.ID = ID; }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }
}
