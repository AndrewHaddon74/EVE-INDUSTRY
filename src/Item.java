public class Item {

    int ID;
    int Amount;
    String name;
    int actvityTYPE;
    Double materialReductionAmount = 1.0;


    public Item(int ID, int Amount, String name, int actvityTYPE){
        this.ID = ID;
        this.Amount = Amount;
        this.name = name;
        this.actvityTYPE = actvityTYPE;

        //this.BPID = BPID;
    }

    public int getID(){
        return ID;
    }

    public int getAmount(){
        return Amount;
    }

    public String getName(){
        return name;
    }

    public int getActvityTYPE() { return actvityTYPE;}

    public void setMaterialReductionAmount(Double reductionAmount){this.materialReductionAmount = reductionAmount;}

    public double getMaterialReductionAmount(){ return materialReductionAmount;}

    @Override
    public String toString(){
        return name+" "+Amount;
    }

}
