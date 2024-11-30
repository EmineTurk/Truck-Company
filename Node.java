public class Node {
    public ParkingLot parkingLot;
    public int key; // capacityConstraint as the key
    public int height;
    public Node left, right;

    public Node(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
        this.key = parkingLot.getCapacityConstraint();
        this.height = 1;
    }



}
