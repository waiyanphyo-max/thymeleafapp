package com.talentprogram.batch_8.thymeleafapp.restClient;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;

import java.io.IOException;

public class accountRestClient {

    public static void main(String[] args) throws IOException {

        getAllAccounts();
        addNewAccount();
        updateAccount();
        deleteAccount();
        findById();
        findByName();
    }

    private static void getAllAccounts() throws IOException {

        AsyncHttpClient client = new DefaultAsyncHttpClient();
        client.prepare("GET", "http://localhost:9090/account")
                .execute()
                .toCompletableFuture()
                .thenAccept(System.out::println)
                .join();

        client.close();
    }

    private static void addNewAccount() throws IOException {

        AsyncHttpClient client = new DefaultAsyncHttpClient();
        client.prepare("POST", "http://localhost:9090/account")
                .setHeader("content-type", "application/json")
                .setBody("{\n  \"accountId\": \"0996662900\",\n  \"userName\": \"eieimon\",\n  \"email\": \"eieihtway@gmail.com\",\n  \"password\": \"silvermoon\",\n  \"nrcNumber\": \"8/CHAUK(N)210293\",\n  \"address\": \"Chauk\",\n  \"dateOfBirth\": \"2001-06-13\"\n}")
                .execute()
                .toCompletableFuture()
                .thenAccept(System.out::println)
                .join();

        client.close();
    }

    private static void updateAccount() throws IOException {

        AsyncHttpClient client = new DefaultAsyncHttpClient();
        client.prepare("PUT", "http://localhost:9090/account/0996662900")
                .setHeader("content-type", "application/json")
                .setBody("{\n  \"accountId\": \"0996662900\",\n  \"userName\": \"eieimon\",\n  \"email\": \"eieimon@gmail.com\",\n  \"password\": \"silvermoon\",\n  \"nrcNumber\": \"8/CHAUK(N)210293\",\n  \"address\": \"Chauk\",\n  \"dateOfBirth\": \"2001-06-13\"\n}")
                .execute()
                .toCompletableFuture()
                .thenAccept(System.out::println)
                .join();

        client.close();
    }

    private static void deleteAccount() throws IOException {

        AsyncHttpClient client = new DefaultAsyncHttpClient();
        client.prepare("DELETE", "http://localhost:9090/account/09782140544")
                .execute()
                .toCompletableFuture()
                .thenAccept(System.out::println)
                .join();

        client.close();
    }

    private static void findByName() throws IOException {

        AsyncHttpClient client = new DefaultAsyncHttpClient();
        client.prepare("GET", "http://localhost:9090/account/name/aunglinhtun")
                .execute()
                .toCompletableFuture()
                .thenAccept(System.out::println)
                .join();

        client.close();
    }

    private static void findById() throws IOException {

        AsyncHttpClient client = new DefaultAsyncHttpClient();
        client.prepare("GET", "http://localhost:9090/account/id/09782140544")
                .execute()
                .toCompletableFuture()
                .thenAccept(System.out::println)
                .join();

        client.close();
    }

}
