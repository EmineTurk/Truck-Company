import java.io.*;


/**
 * @author EmineTurk student ıd:2022400228
 */
public class Main {
    public static void main(String[] args) throws IOException {

        avltree AvlTree =new avltree();
        try (BufferedReader br = new BufferedReader(new FileReader(args[0]));
             BufferedWriter bw = new BufferedWriter(new FileWriter(args[1]))) {



            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts[0].equals("create_parking_lot")) {
                    int capacity = Integer.parseInt(parts[1]);
                    int truckLimit = Integer.parseInt(parts[2]);
                    AvlTree.create_parking_lot(capacity, truckLimit);
                } else if (parts[0].equals("add_truck")) {
                    int truckId = Integer.parseInt(parts[1]);
                    int truckCapacity = Integer.parseInt(parts[2]);
                    int result = AvlTree.add_truck(truckId, truckCapacity);
                    bw.write(result + "\n");
                    bw.flush();
                } else if (parts[0].equals("delete_parking_lot")) {
                    AvlTree.removeParkingLot(Integer.parseInt(parts[1]));

                }else if(parts[0].equals("ready")) {
                    String result1 = AvlTree.readyCommand(Integer.parseInt(parts[1]));
                    bw.write(result1 + "\n");
                    bw.flush();
                }else if(parts[0].equals("load")) {
                    String result2 = AvlTree.load(Integer.parseInt(parts[1]),Integer.parseInt(parts[2]));
                    bw.write(result2 + "\n");
                    bw.flush();

                }




            }
        } catch (IOException e) {
            System.err.println("Dosya okuma/yazma hatası: " + e.getMessage());
        }






    }





























































}