/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baobab.htc.midlets;

/**
 *
 * @author yamiko
 */
import java.util.*;
// Simple Operations Queue
// It runs in an independent thread and executes Operations serially
class OperationsQueue implements Runnable {

    private volatile boolean running = true;
    private final Vector operations = new Vector();

    OperationsQueue() {
// Notice that all operations will be done in another
// thread to avoid deadlocks with GUI thread
        new Thread(this).start();
    }

    void enqueueOperation(Operation nextOperation) {
        operations.addElement(nextOperation);
        synchronized (this) {
            notify();
        }
    }
// stop the thread
    void abort() {
        running = false;
        synchronized (this) {
            notify();
        }
    }

    public void run() {
        while (running) {
            while (operations.size() > 0) {
                try {
// execute the first operation on the queue
                    ((Operation) operations.firstElement()).execute();
                } catch (Exception e) {
// Nothing to do. It is expected that each operation handles
// its own exception locally but this block is to ensure
// that the queue continues to operate
                }
                operations.removeElementAt(0);
            }
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
// it doesn't matter
                }
            }
        }
    }
}
