package list;

import java.util.LinkedList;

public class MyLinkedList<T> {
    private  int len;
    private Node node;


    private MyLinkedList() {
        this.len = 0;
    }

    public int size(){
        return len;
    }


    public Node addLast(T data){
        Node newNode=new Node();
        newNode.setData(data);
        if (this.node==null || len==0){
            this.node=newNode;
            this.node.last=newNode;
            this.node.next=newNode;
            ++this.len;
        }else {
            Node tmp=this.node.last;
            newNode.prefix=tmp;
            tmp.next=newNode;
            this.node.last=newNode;
            ++this.len;
        }
        return newNode;
    }


    public Node addFirst(T data){
        Node newNode=new Node();
        newNode.setData(data);
        if (this.node==null||this.len==0){
            this.node=newNode;
            this.node.first=newNode;
            this.node.prefix=newNode;
            ++this.len;
        }else {
            Node tmp=this.node.first;
            newNode.next=tmp;
            tmp.prefix=newNode;
            this.node.first=newNode;
            ++this.len;
        }
        return newNode;
    }

    public void showSequence(){
        Node tmp=this.node;
        while (tmp!=null){
            System.out.println(tmp.data);
            tmp=tmp.prefix;
        }
    }

    public void  showFirst(){
        Node tmp=this.node.first;
        while (tmp!=null){
            System.out.println(tmp.data);
            tmp=tmp.next;
        }
    }

    public void removeFist(){
        Node tmp=this.node.first;
        tmp.data=null;
        Node next=tmp.next;
        if (null==next){
            //log
        }else {
            tmp.next=null;
            next.prefix=null;
            this.node.first=next;
        }
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


    class Node{
        T data;
        Node next;
        Node prefix;
        Node last;
        Node first;


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

        public Node getPrefix() {
            return prefix;
        }

        public void setPrefix(Node prefix) {
            this.prefix = prefix;
        }

        public Node getLast() {
            return last;
        }

        public void setLast(Node last) {
            this.last = last;
        }

        public Node getFirst() {
            return first;
        }

        public void setFirst(Node first) {
            this.first = first;
        }
    }

    public static void main(String[] args) {


        /**
         * 头插法
         */
        MyLinkedList<Integer> addFirst=new MyLinkedList<>();
        addFirst.addFirst(1);
        addFirst.addFirst(2);
        addFirst.addFirst(3);
        addFirst.addFirst(4);
        addFirst.addFirst(5);
        addFirst.showFirst();

        addFirst.removeFist();
        System.out.println("------------------");

        addFirst.showFirst();
        System.out.println("------------------");
        addFirst.removeFist();
        addFirst.showFirst();

        /**
         * 尾插法
         */
       /* MyLinkedList<Integer> objectMyLinkedList = new MyLinkedList<>();
        objectMyLinkedList.addLast(1);
        objectMyLinkedList.addLast(2);
        objectMyLinkedList.addLast(3);
        objectMyLinkedList.addLast(4);
        objectMyLinkedList.addLast(5);
        objectMyLinkedList.showNode();*/


        /**
         * 在头部后面插入
         */
        /*MyLinkedList<Integer> myLinkedList = new MyLinkedList<>();
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
        myLinkedList1.showNode();
        System.out.println("myLinkedList.size()=="+myLinkedList1.size());

        LinkedList<String> objects = new LinkedList<>();
        objects.add("7");
        objects.add("8");
        objects.add("9");
        for (String s:objects
             ) {
            System.out.println(s);
        }*/
    }
}
