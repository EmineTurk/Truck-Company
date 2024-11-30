import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;



public class avltree {
    class Node {
        ParkingLot parkingLot;
        int height;
        Node left, right, parent;

        Node(ParkingLot parkingLot) {
            this.parkingLot = parkingLot;
            this.height = 1;
            this.parent = null;
        }
    }


    private Node root;


    private int height(Node node) {
        return node == null ? 0 : node.height;
    }//returns height

    private int getBalance(Node node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }//it looks at height difference between left and right

    private Node rotateRight(Node y) {//makes right rotation
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    private Node rotateLeft(Node x) {//makes left rotation
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }


    public Node findNodeByCapacity(int capacityConstraint) {//It finds node with given capacity.
        Node current = root;

        while (current != null) {
            if (current.parkingLot.capacityConstraint == capacityConstraint) {//Starting from root it compares capacity constraints and decides whether to go left or right.
                return current;
            } else if (capacityConstraint < current.parkingLot.capacityConstraint) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return null;
    }


    public void insert(ParkingLot parkingLot) {

        if (root == null) {
            root = new Node(parkingLot);// If the tree is empty, creates the root node
            return;
        }
        Node current = root;
        // It places the parking lot in the appropriate position
        while (true) {

            if (parkingLot.capacityConstraint < current.parkingLot.capacityConstraint) {// If the new parking lot has a smaller capacity than the current node, goes left
                if (current.left == null) {// If the left child is null, inserts the new node
                    current.left = new Node(parkingLot);
                    current.left.parent = current;// Updates the parent of the new node
                    break;
                }
                current = current.left;
            } else if (parkingLot.capacityConstraint > current.parkingLot.capacityConstraint) {
                if (current.right == null) {// If the right child is null, inserts the new node
                    current.right = new Node(parkingLot);
                    current.right.parent = current;// Updates the parent of the new node
                    break;
                }
                current = current.right;
            } else {

                return;
            }
        }//After insertion it goes to root and update heights and balance the tree.
        current = current.parent;
        while (current != null) {

            current.height = 1 + Math.max(height(current.left), height(current.right)); // Updates the height of the current node
            int balance = getBalance(current);


            // If the tree is unbalanced makes a right rotation
            if (balance > 1 && parkingLot.capacityConstraint < current.left.parkingLot.capacityConstraint) {
                Node oldParent = current.parent;
                Node newTop = rotateRight(current);
                updateafterrotationtobalance(oldParent, current, newTop);
                current = newTop;
            }
            // If the tree is unbalanced and needs a left rotation
            else if (balance < -1 && parkingLot.capacityConstraint > current.right.parkingLot.capacityConstraint) {
                Node oldParent = current.parent;
                Node newTop = rotateLeft(current);
                updateafterrotationtobalance(oldParent, current, newTop);
                current = newTop;
            }
            // If the tree is unbalanced makes a left right rotation
            else if (balance > 1 && parkingLot.capacityConstraint > current.left.parkingLot.capacityConstraint) {
                current.left = rotateLeft(current.left);
                Node oldParent = current.parent;
                Node newTop = rotateRight(current);
                updateafterrotationtobalance(oldParent, current, newTop);
                current = newTop;
            }
            // If the tree is unbalanced makes a right left rotation
            else if (balance < -1 && parkingLot.capacityConstraint < current.right.parkingLot.capacityConstraint) {
                current.right = rotateRight(current.right);
                Node oldParent = current.parent;
                Node newTop = rotateLeft(current);
                updateafterrotationtobalance(oldParent, current, newTop);
                current = newTop;
            }

            current = current.parent;
        }
    }

    private void updateafterrotationtobalance(Node parent, Node oldTop, Node newTop) {// This function updates the parent-child relationships after a rotation  to maintain the correct structure in the AVL tree.

        if (oldTop == root) {// If the old top node was the root, updates the root to the new top node
            root = newTop;
        } else {// If the old top node was not the root,It updates the parent’s child reference. If the old top was the left child, sets the new top as the left child
            if (parent.left == oldTop) {
                parent.left = newTop;
            } else { // Otherwise, It sets the new top as the right child
                parent.right = newTop;
            }
        }
        newTop.parent = parent;  // It sets the parent of the new top node to the current parent node
    }


    private Node findclosestsmallerCapacity(int capacity) {// It finds the node with the closest smaller capacity constraint than the given capacity
        Node current = root;
        Node closest = null;

        while (current != null) {
            if (current.parkingLot.capacityConstraint < capacity) {
                closest = current;// It Updates closest node if a smaller capacity is found
                current = current.right;//To close capacity we want if current capacity is smaller go right.
            } else {
                current = current.left;//To close capacity we want if current capacity is greater go left.
            }
        }
        return closest;
    }


    public Node findlargestsmaller(int capacity) {
        Node suitableNode = findclosestsmallerCapacity(capacity);//It finds node which has smaller but biggest capacity.
        while (suitableNode != null && suitableNode.parkingLot.isFull()) {//If this node's parkinglot is full finds a new one.
            suitableNode = findclosestsmallerCapacity(suitableNode.parkingLot.capacityConstraint);
        }
        return suitableNode;
    }
    private Node findsmalleratunderright(Node node) {

        if (node.right != null) { // It checks if the node has a right child and finds the smallest node in the right subtree.
            Node current = node.right;
            while (current.left != null) {//To find larger but smallest node than given node. It goes left until find a node which is null.
                current = current.left;
            }
            return current;
        }

        Node rightsmaller = null;
        Node current = root;
        while (current != null) {  // If current node has a larger capacity than the given node, It updates rightsmaller
            // and moves to the left subtree to find a closer match
            if (node.parkingLot.capacityConstraint < current.parkingLot.capacityConstraint) {
                rightsmaller = current;
                current = current.left;
            } else if (node.parkingLot.capacityConstraint > current.parkingLot.capacityConstraint) { // If the current node's capacity is smaller, It moves to the right subtree.
                current = current.right;
            } else {
                break;
            }
        }
        return rightsmaller;
    }

    private Node findFirstNodeHasGreaterCapacity(int capacity) {
        Node current = root;
        Node closest = null;

        while (current != null) {
            // If the current node's capacity is greater than the given capacity,
            // It updates the closest node and move to the left to find a smaller valid node.
            if (current.parkingLot.capacityConstraint > capacity) {
                closest = current;
                current = current.left;
            } else {// Otherwise,It  moves to the right subtree to find a larger capacity.
                current = current.right;
            }
        }
        return closest;
    }

    public Node findNextLargerNodeWithNonEmptyWaiting(int capacity) {

        Node current = findFirstNodeHasGreaterCapacity(capacity);// It starts with the first node that has a greater capacity than the given value.
        while (current != null) { // It traverses to find the closest node with a non-empty waiting section.
            if (current.parkingLot.waitingSection.size() > 0) {
                return current;// It returns the node if it has trucks in the waiting section.
            }
            current = findsmalleratunderright(current);// Otherwise, It moves to the next closest node in the right subtree.
        }

        return null;
    }
    public Node findNextLargerNodeWithNonEmptyReady(int capacity) {

        Node current = findFirstNodeHasGreaterCapacity(capacity); //It  Starts with the first node that has a greater capacity than the given value.
        while (current != null) {//It  traverses to find the closest node with a non-empty ready section.
            if (current.parkingLot.readySection.size() > 0) {
                return current; // It returns the node if it has trucks in the ready section.
            }
            current = findsmalleratunderright(current);// Otherwise,It  moves to the next closest node in the right subtree.
        }

        return null;
    }


    public int add_truck(int ID, int maximumCapacity) {
        Truck truck = new Truck(ID, maximumCapacity);
        Node exactNode ;
        Node realNode = findNodeByCapacity(maximumCapacity);// It checks if a parking lot with an exact match for the truck's maximum capacity exists.
        if (realNode != null && !realNode.parkingLot.isFull()) {
            exactNode = realNode;//It uses the exact matching parking lot if it is not full.
        } else {
            exactNode = findlargestsmaller(maximumCapacity); // Otherwise,It finds the largest parking lot with a smaller capacity than the truck's capacity.
        }
        if (exactNode == null) {
            return -1;
        }

        exactNode.parkingLot.addTruckToWaiting(truck);//adds truck
        truck.currentparkinglot=exactNode.parkingLot;

        return exactNode.parkingLot.getCapacityConstraint();
    }
    public void create_parking_lot(int capacityConstraint, int truckLimit) {

        Node existingNode = findNodeByCapacity(capacityConstraint);// It checks if a parking lot with the given capacity already exists.
        if (existingNode != null) {//If it exist does nothing.
            return;
        }
        ParkingLot newParkingLot = new ParkingLot(capacityConstraint, truckLimit);//Else constructs a new parkinglot.
        insert(newParkingLot);
    }
    public void removeParkingLot(int capacity) { // Removes a parking lot with the specified capacity from the tree.
        root = remove(root, capacity);
    }
    public Node remove(Node node, int capacity) {

        if (node == null) return node;
        if (capacity < node.parkingLot.capacityConstraint) {// Traverses the tree to find the node with the given capacity.
            node.left = remove(node.left, capacity);// Searchs in the left subtree if the target capacity is smaller.
        } else if (capacity > node.parkingLot.capacityConstraint) {
            node.right = remove(node.right, capacity);// Searchs in the right subtree if the target capacity is larger.
        } else {
            if (node.left == null || node.right == null) {//if there is one child or no child
                Node temp = (node.left != null) ? node.left : node.right;// It Sets temp to the nonnull child, or null.
                if (temp == null) {
                    node = null;
                } else {
                    node = temp;
                }
            } else {//If there is two children

                Node temp = findMin(node.right);// make equal the smallest node’s parkinglot to the current node.
                node.parkingLot = temp.parkingLot;
                node.right = remove(node.right, temp.parkingLot.capacityConstraint); // Deletes the smallest node from the right subtree.
            }
        }

        if (node == null) return node;// Returns if tree is now empty.

        node.height = 1 + Math.max(height(node.left), height(node.right));// It Updates the height of the current node.
        int balance = getBalance(node);// Balances the tree if needed after deletion.
        if (balance > 1 && getBalance(node.left) >= 0) {
            return rotateRight(node);
        }
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (balance < -1 && getBalance(node.right) <= 0) {
            return rotateLeft(node);
        }
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        return node;// Returns the balanced node.
    }


    private Node findMin(Node node) {// finds the node with the minimum capacity in a given subtree.
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    public String readyCommand(int capacity) {
        Node targetNode = findNodeByCapacity(capacity);// Finds the node with the given capacity.
        if (targetNode != null && !targetNode.parkingLot.waitingSection.isEmpty()) {//If this node is not null and there is trucks at waiting section
            Truck truck = targetNode.parkingLot.moveTruckToReady();//moves earliest truck to ready.
            return (truck.getID() + " " + targetNode.parkingLot.getCapacityConstraint());

        }
        Node largerNode = findNextLargerNodeWithNonEmptyWaiting(capacity);//If target node is null, looks for a node has capacity larger and smallest also it's waiting section is not empty.

        if (largerNode != null) {
            Truck truck = largerNode.parkingLot.moveTruckToReady();//moves earliest truck to ready.
            return (truck.getID() + " " + largerNode.parkingLot.getCapacityConstraint());

        }

        return "-1";
    }
    public String load(int capacityConstraint, int loadAmount) {
        String output = "";
        int remainingLoad = loadAmount;
        Node currentNode = findNodeByCapacity(capacityConstraint);

        if (currentNode == null || currentNode.parkingLot.getReadyTrucks().isEmpty()) {
            currentNode = findNextLargerNodeWithNonEmptyReady(capacityConstraint);
            if (currentNode == null) {
                return "-1";
            }
        }

        while (remainingLoad > 0 && currentNode != null) { // Distributes the load among the ready trucks.

            ParkingLot parkingLot = currentNode.parkingLot;
            Queue<Truck> readyTrucks = parkingLot.getReadyTrucks();// Gets the ready trucks in the parking lot.


            while (remainingLoad > 0 && !readyTrucks.isEmpty()) {
                Truck truck = readyTrucks.peek();// Gets the next truck in the ready section.
                int remainingCapacity = truck.getRemainingCapacity();
                int loadedAmount = Math.min(remainingCapacity, remainingLoad);
                int loadedAmount2 = Math.min(loadedAmount, parkingLot.capacityConstraint);//Sets load amount to not exceed parkinglot's capacity

                truck.load(loadedAmount2);
                remainingLoad -= loadedAmount2;//decreases load amount.

                if (truck.isFull()) {
                    readyTrucks.dequeue();
                    truck.unload();

                    Node targetNode = findNodeByCapacity(truck.maximumcapacity);
                    if (targetNode != null && !targetNode.parkingLot.isFull()) {//If there is a parkinglot with given capacity places truck to it.
                        targetNode.parkingLot.addTruckToWaiting(truck);
                        truck.currentparkinglot = targetNode.parkingLot;
                        output += truck.getID() + " " + truck.getCurrentparkinglot().capacityConstraint + " - ";
                    } else {
                        output += findAndMoveToSuitableLot(truck);//Otherwise finds a new parkinglot with smaller but greatest capacity and places on it.
                    }
                } else if (!truck.isFull()) {//İf truck still have remaining load it places truck to parkinglot which has capacity matches remaining load .
                    readyTrucks.dequeue();
                    int targetCapacity = truck.getMaxCapacity() - truck.load;
                    Node targetNode = findNodeByCapacity(targetCapacity);

                    if (targetNode != null && !targetNode.parkingLot.isFull()) {//If there exist such a load adds truck to it.
                        targetNode.parkingLot.addTruckToWaiting(truck);
                        truck.currentparkinglot = targetNode.parkingLot;
                        output += truck.getID() + " " + truck.currentparkinglot.capacityConstraint + " - ";
                    } else {
                        Node suitableNode = findlargestsmaller(targetCapacity);//else finds smallerlargest and adds truck to it.
                        if (suitableNode != null) {
                            suitableNode.parkingLot.addTruckToWaiting(truck);
                            truck.currentparkinglot = suitableNode.parkingLot;
                            output += truck.getID() + " " + truck.currentparkinglot.capacityConstraint + " - ";
                        } else {
                            output += truck.getID() + " -1 - ";
                        }
                    }
                }
            }

            if (remainingLoad > 0) {//If still there is remainnig load passes to next larger node.
                currentNode = findNextLargerNodeWithNonEmptyReady(currentNode.parkingLot.capacityConstraint);
            }
        }

        return output.endsWith(" - ") ? output.substring(0, output.length() - 3) : output;
    }

    private String findAndMoveToSuitableLot(Truck truck) {
        String output = "";
        Node suitableNode = findlargestsmaller(truck.getMaxCapacity());//It finds smaller but greatest node than trucks maximum capaxity.
        if (suitableNode != null) {
            suitableNode.parkingLot.addTruckToWaiting(truck);//If this node is not null add truck to it's waiting section.
            truck.currentparkinglot = suitableNode.parkingLot;//updates current parkinglot of truck.
            output += truck.getID() + " " + truck.getCurrentparkinglot().capacityConstraint + " - ";
        } else {
            output += truck.getID() + " -1 - ";
        }
        return output;
    }





}












