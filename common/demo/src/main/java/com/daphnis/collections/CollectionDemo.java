package com.daphnis.collections;

import java.util.*;
import java.util.function.UnaryOperator;

import com.google.common.collect.Lists;

public class CollectionDemo{

    static void show(String msg){
        System.out.println(msg);
    }

    public static void queueDemo() {
        show("");
        show("-------------------------- queue usage --------------------------");

        Queue<String> queue=Lists.newLinkedList();

        show("往队列里面插入元素[ queue.offer() ] ..");
        queue.offer("1");
        queue.offer("2");
        queue.offer("3");

        show("取出队列头部元素[ queue.poll() ]: "+queue.poll());
        show("查看队列头部元素[ queue.peek() ]: "+queue.peek());

        show("仅遍历队列[ for (String str : queue) ]:");
        for (String str : queue) {
            show(str);
        }

        show("依次取出队列里的元素[ while(queue.peek()!=null) ]");
        while(queue.peek()!=null){
            show(queue.poll());
        }

        show("队列元素个数： "+queue.size());
    }

    public static void dequeDemo(){
        show("");
        show("-------------------------- deque usage --------------------------");

        Deque<String> deque=new LinkedList<>();

        show("往双向队列里面放入元素[ deque.offer() ] ..");
        deque.offer("1");
        deque.offer("2");
        deque.offer("3");
        deque.offer("4");

        show("取出队首元素[ deque.pollFirst() ]: "+deque.pollFirst());
        show("取出队尾元素[ deque.pollLast() ]: "+deque.pollLast());

        show("依次取出双向队列里面的元素[ while(!deque.isEmpty()) ]");
        while(!deque.isEmpty()){
            show(deque.pollFirst());
        }
    }

    public static void stackDemo(){
        show("");
        show("-------------------------- stack usage --------------------------");

        Stack<String> stack=new Stack<>();

        show("往栈里面放入元素[ stack.push() ] ..");
        stack.push("1");
        stack.push("2");
        stack.push("3");

        show("取出栈顶元素[ stack.pop() ]: "+stack.pop());
        show("查看栈顶元素[ stack.peek() ]: "+stack.peek());

        show("依次取出栈里面的元素[ while(!stack.isEmpty()) ]");
        while(!stack.isEmpty()){
            show(stack.pop());
        }
    }

    public static void listDemo(){
        List<String> strs=Lists.newArrayList("hello","java","list");
        show("create list: "+String.join(",",strs));

        strs.replaceAll(String::toUpperCase);
        show("change list elements: "+String.join(",",strs));

        // 自定义排序：根据字符串长度逆序排列
        List<String> sortList=new ArrayList<>();
        Collections.addAll(sortList,"R","Java","C++","Python");
        System.out.println("origin list is: "+sortList.toString());
        sortList.sort((a,b)->b.length()-a.length());
        System.out.println("sort list is: "+sortList.toString());

        // 把整数集合中的所有元素都加倍
        List<Integer> intList=new ArrayList<>();
        Collections.addAll(intList,2,5,8);
        System.out.println("origin list is: "+intList.toString());
        intList.replaceAll(num -> num*2);
        System.out.println("all integer is double: "+intList.toString());

        // 求集合A与集合B的交集
        List<Integer> A=new ArrayList<>();
        Collections.addAll(A,2,1,5,8);
        List<Integer> B=new ArrayList<>();
        Collections.addAll(B,2,5,6,9);
        System.out.println("A: "+A.toString()+",B: "+B.toString());
        A.retainAll(B);
        System.out.println("A和B的交集: "+A.toString());

        // 求 A-A交B
//        A.removeAll(B);
//        System.out.println("A-A交B: "+A.toString());
    }

    public static void main(String... args) {
//        queueDemo();
//        stackDemo();
//        dequeDemo();

        listDemo();
    }
}