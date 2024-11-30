
    public class ParkingLot {
        public int capacityConstraint;
        public int truckLimit;
        public Queue<Truck> waitingSection;
        public Queue<Truck> readySection;

        public ParkingLot(int capacityConstraint, int truckLimit) {
            this.capacityConstraint = capacityConstraint;
            this.truckLimit = truckLimit;
            this.waitingSection = new Queue<>();
            this.readySection = new Queue<>();
        }

        public int getCapacityConstraint() {
            return capacityConstraint;
        }

        public boolean isFull() {
            return (waitingSection.size() + readySection.size()) >= truckLimit;
        }

        public void addTruckToWaiting(Truck truck) {
            if (!isFull()) {
                waitingSection.enqueue(truck);
            }
        }

        public Truck moveTruckToReady() {
            Truck truck = waitingSection.dequeue();
            if (truck != null) {
                readySection.enqueue(truck);
            }
            return truck;
        }

        public Queue<Truck> getReadyTrucks() {
            return readySection;
        }





    }









