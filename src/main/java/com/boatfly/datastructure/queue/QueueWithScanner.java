package com.boatfly.datastructure.queue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class QueueWithScanner {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ArrayQueue queue = new ArrayQueue(10);
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        String line = "";
        while (loop) {
            line = scanner.nextLine();
            String[] inputs = line.split(" ");
            //for (String item : inpus) {
            System.out.println(inputs[0] + "\t" + inputs[1]);
            Method method = queue.getClass().getMethod(inputs[0], Integer.class);
            method.invoke(queue, Integer.valueOf(inputs[1]));
            //System.out.println("head data is="+queue.peek());
            //}
            method = queue.getClass().getMethod("peek");
            System.out.println(method.invoke(queue));
        }
    }
}
