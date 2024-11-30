
    public class Queue<T> {
        private Node<T> front;
        private Node<T> rear;
        private int size;

        private static class Node<T> {
            T data;
            Node<T> next;

            Node(T data) {
                this.data = data;
                this.next = null;
            }
        }

        public Queue() {
            front = rear = null;
            size = 0;
        }

        public void enqueue(T data) {
            Node<T> newNode = new Node<>(data);
            if (rear == null) {
                front = rear = newNode;
            } else {
                rear.next = newNode;
                rear = newNode;
            }
            size++;
        }

        public T dequeue() {
            if (front == null) return null;
            T data = front.data;
            front = front.next;
            if (front == null) rear = null;
            size--;
            return data;
        }

        public boolean isEmpty() {
            return front == null;
        }

        public int size() {
            return size;
        }

        public T peek() {
            return front != null ? front.data : null;
        }


    }


