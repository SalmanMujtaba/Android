package com.example.salman.inclasssphotoretrieval;


public class DoublyLinkedList {
    Node head;  // head of list

    /* Linked list Node.  This inner class is made static so that
       main() can access it */
    static class Node {
        int data;
        Node next;
//        Node prev;
        Node(int d)  { data = d;  next=null; } // Constructor
    }

    public void push(Node current)
    {

        Node n = head;
        n.next = current;

        current.next = null;

    }




}
