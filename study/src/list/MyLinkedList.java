package list;

public class MyLinkedList<T> {
    private static int len=0;
    private Node node;

    public static int getLen() {
        return len;
    }

    public static void setLen(int len) {
        MyLinkedList.len = len;
    }

    public int size(){
        return len;
    }

    public Node add(T data){
        Node newNode=new Node();
        newNode.data=data;
        if (len==0){
            this.node=newNode;
            len++;
        }else {
            Node tmp=this.node;
            newNode.next=tmp.next;
            tmp.next=newNode;
            len++;
        }
        return newNode;
    }

    public void showNode(){
        Node tmp=this.node;
        while (tmp!=null){
            System.out.println(tmp.data);
            tmp=tmp.next;
        }
    }


    class Node<T>{
        T data;
        Node next;

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    public static void main(String[] args) {
        MyLinkedList<Integer> myLinkedList = new MyLinkedList<>();
        myLinkedList.add(1);
        myLinkedList.add(2);
        myLinkedList.add(3);
        myLinkedList.add(4);
        myLinkedList.add(5);
        myLinkedList.showNode();
        System.out.println("myLinkedList.size()=="+myLinkedList.size());

        MyLinkedList<String> myLinkedList1 = new MyLinkedList<>();
        myLinkedList1.add("hello");
        myLinkedList1.add("world");
        myLinkedList1.add("mike");
        myLinkedList1.add("blue");
        myLinkedList1.add("red");
        myLinkedList.showNode();
        System.out.println("myLinkedList.size()=="+myLinkedList.size());
    }
}
