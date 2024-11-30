public class Truck {
    public  int ID ;
    public int maximumcapacity;

    public int load;
    public ParkingLot currentparkinglot;


    public Truck(int ID,int maximumcapacity){
        this.ID=ID;
        this.maximumcapacity=maximumcapacity;
        load=0;
        this.currentparkinglot = null;
    }
    public int getID() {
        return ID;
    }

    public int getMaxCapacity() {
        return maximumcapacity;
    }

    public int getRemainingCapacity() {
        return maximumcapacity - load;
    }

    public void load(int amount) {
      load = Math.min(load + amount, maximumcapacity);
    }

    public boolean isFull() {
        return load == maximumcapacity;
    }

    public void unload() {
        load = 0;
    }

    public ParkingLot getCurrentparkinglot(){
        return currentparkinglot;
    }

}



