package Model;

import java.util.LinkedList;

/**
 * A queue data structure to be used for BFS.
 * Only needed methods included.
 * @param <q>
 */
public class Queue<q> {

    private LinkedList<q> queue = new LinkedList<>();

    public void enqueue(q state){
        queue.add(state);
    }

    public void poll(){
        queue.poll();
    }

    public q peek(){
        return queue.peek();
    }

    public boolean empty(){
        return queue.size() == 0;
    }
}
