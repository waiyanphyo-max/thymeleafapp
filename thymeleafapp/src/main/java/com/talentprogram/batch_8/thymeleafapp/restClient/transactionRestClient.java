package com.talentprogram.batch_8.thymeleafapp.restClient;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;

import java.io.IOException;

public class transactionRestClient {

    public static void main(String[] args) throws IOException {

        getTransactionsByAccountId();
        getMonthlySummaries();
        addTransaction();
        updateTransaction();
        deleteTransaction();

    }

    private static void getTransactionsByAccountId() throws IOException {

        AsyncHttpClient client = new DefaultAsyncHttpClient();
        client.prepare("GET", "http://localhost:9090/transaction/09953514037")
                .execute()
                .toCompletableFuture()
                .thenAccept(System.out::println)
                .join();

        client.close();
    }

    private static void getMonthlySummaries() throws IOException {

        AsyncHttpClient client = new DefaultAsyncHttpClient();
        client.prepare("GET", "http://localhost:9090/transaction/09953514037?year=2025&month=8")
                .execute()
                .toCompletableFuture()
                .thenAccept(System.out::println)
                .join();

        client.close();
    }

    private static void addTransaction() throws IOException {

        AsyncHttpClient client = new DefaultAsyncHttpClient();
        client.prepare("POST", "http://localhost:9090/transaction/09953514037?type=income")
                .setHeader("content-type", "application/json")
                .setBody("{\n  \"transactionCategory\": \"TIP\",\n  \"amount\": 70000\n}")
                .execute()
                .toCompletableFuture()
                .thenAccept(System.out::println)
                .join();

        client.close();
    }

    private static void updateTransaction() throws IOException {

        AsyncHttpClient client = new DefaultAsyncHttpClient();
        client.prepare("PUT", "http://localhost:9090/transaction/13")
                .setHeader("content-type", "application/json")
                .setBody("{\n  \"transactionType\": \"expense\",\n  \"transactionCategory\": \"PRESENT\",\n  \"amount\": 58000\n}")
                .execute()
                .toCompletableFuture()
                .thenAccept(System.out::println)
                .join();

        client.close();
    }

    private static void deleteTransaction() throws IOException {

        AsyncHttpClient client = new DefaultAsyncHttpClient();
        client.prepare("DELETE", "http://localhost:9090/transaction/9/09782140544")
                .execute()
                .toCompletableFuture()
                .thenAccept(System.out::println)
                .join();

        client.close();
    }
}
