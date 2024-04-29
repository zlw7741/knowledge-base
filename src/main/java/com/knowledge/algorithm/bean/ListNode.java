package com.knowledge.algorithm.bean;

/**
 * @author zhoulinwen
 * @title: ListNode
 * @description: 链表结构
 * @date 2023/9/8 4:52 PM
 */
public class ListNode {
    
    public int val;
    
    public ListNode next;

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode setNext(int i) {
        next = new ListNode(i);
        return next;
    }

    public static void main(String[] args) {
        ListNode listNode = new ListNode(1);
        listNode.setNext(3).setNext(2).setNext(5);
        System.out.println(listNode);

    }
    
    

    @Override
    public String toString() {
        return "ListNode{" +
                "val=" + val +
                ", next=" + next +
                '}';
    }
}
