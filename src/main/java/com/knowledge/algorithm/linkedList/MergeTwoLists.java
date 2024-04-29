package com.knowledge.algorithm.linkedList;

import com.knowledge.algorithm.bean.ListNode;

/**
 * @author zhoulinwen
 * @title: MergeTwoLists
 * @description: 合并两个有序链表
 * 输入：l1 = [1,2,4], l2 = [1,3,4]
 * 输出：[1,1,2,3,4,4]
 * @date 2023/9/8 4:49 PM
 */
public class MergeTwoLists {
    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        l1.setNext(2).setNext(4);
        ListNode l2 = new ListNode(1);
        l2.setNext(3).setNext(4);
        ListNode listNode = mergeTwoLists(l1, l2);
        System.out.println(listNode);

    }
    
    public static ListNode mergeTwoLists(ListNode l1,ListNode l2){
        // 设置一个带头结点的新链表
        ListNode dummy = new ListNode(-1),p = dummy;
        ListNode p1 = l1, p2 = l2;
        
        // 不为空则遍历
        while (p1 != null && p2 != null){
            if(p1.val > p2.val){
                p.next = p2;//直接把p2接到新链表中
                p2 = p2.next;//删除p2拼接的元素
            }else {
                p.next = p1;
                p1 = p1.next;
            }
            // 前进一个指针
            p = p.next;
        }
        
        if(p1 != null){
            p.next = p1;
        }
        if(p2 != null){
            p.next = p2;
        }
        // 返回组装好的新链表
        return dummy.next;
    }
    
}
