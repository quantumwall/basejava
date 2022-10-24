package ru.javawebinar.basejava;

import java.util.ArrayList;
import java.util.List;

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

    public static void main(String[] args) throws InterruptedException {
        var account1 = new Account(13456.23);
        var account2 = new Account(34543.3);
        Runnable transferA1toA2 = () -> {
            synchronized (account1) {
                synchronized (account2) {
                    double toTransfer = account1.withDraw(345);
                    account2.deposit(toTransfer);
                }
            }
        };
        Runnable transferA2toA1 = () -> {
            synchronized (account2) {
                synchronized (account1) {
                    double toTransfer = account2.withDraw(345);
                    account1.deposit(toTransfer);
                }
            }
        };
        int threadsCount = 1000;
        List<Thread> threads = new ArrayList<>(threadsCount);
        for (int i = 0; i < threadsCount / 2; i++) {
            threads.add(new Thread(transferA1toA2));
            threads.add(new Thread(transferA2toA1));
        }
        threads.forEach(t -> t.start());
    }
}

