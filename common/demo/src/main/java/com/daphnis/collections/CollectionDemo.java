package com.daphnis.collections;

import java.util.Queue;
import java.util.Stack;

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

    public static void main(String... args) {
        queueDemo();
        stackDemo();
    }
}