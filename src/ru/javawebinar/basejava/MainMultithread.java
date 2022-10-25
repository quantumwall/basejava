package ru.javawebinar.basejava;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MainMultithread {

    public static class Account {

        private volatile double balance;

        public Account(double balance) {
            this.balance = balance;
        }

        public double withDraw(double amount) {
            return balance -= amount;
        }

        public void deposit(double amount) {
            balance += amount;
        }

        public double getBalance() {
            return balance;
        }
    }

    public static class TransferThread extends Thread {

        private final Account from;
        private final Account to;

        public TransferThread(Account from, Account to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public void run() {
            synchronized (from) {
                try {
                    sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainMultithread.class.getName()).log(Level.SEVERE, null, ex);
                }
                synchronized (to) {
                    double toTransfer = from.withDraw(345);
                    to.deposit(toTransfer);
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        var account1 = new Account(13456.23);
        var account2 = new Account(34543.3);
        new TransferThread(account1, account2).start();
        new TransferThread(account2, account1).start();
    }
}
