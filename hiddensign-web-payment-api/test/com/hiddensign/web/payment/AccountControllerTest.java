package com.hiddensign.web.payment;

import com.fasterxml.jackson.databind.JsonNode;
import com.hiddensign.core.payment.db.entity.account.CurrencyType;
import com.hiddensign.web.payment.rest.api.models.requests.AccountCreateRequest;
import com.hiddensign.web.payment.rest.api.models.requests.AccountTransferRequest;
import com.hiddensign.web.payment.rest.api.models.response.AccountResponse;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import play.test.WithApplication;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static play.test.Helpers.*;

/**
 * @author Nikolay Denisenko
 * @version 2016/11/12
 */
public class AccountControllerTest extends WithApplication {


    public AccountResponse createAccount(CurrencyType ccy, BigDecimal balance) {
        Http.RequestBuilder request = new Http.RequestBuilder().method(POST)
                .bodyJson(Json.toJson(new AccountCreateRequest(ccy, balance)))
                .uri("/account");
        Result result = route(request);

        assertEquals(Results.ok().status(), result.status());

        return Json.fromJson(Json.parse(contentAsString(result)),
                AccountResponse.class);
    }

    public AccountResponse findAccount(long accId) {
        Http.RequestBuilder request = new Http.RequestBuilder().method(GET)
                .uri("/account/" + accId);
        Result result = route(request);

        assertEquals(Results.ok().status(), result.status());

        return Json.fromJson(Json.parse(contentAsString(result)),
                AccountResponse.class);
    }

    @Test
    //Test account creation
    public void createAccountTest() {
        //create account
        AccountResponse ac1 = createAccount(CurrencyType.RUB, BigDecimal.ONE);
        assertEquals(0, ac1.balance.compareTo(BigDecimal.ONE));
        assertEquals(CurrencyType.RUB, ac1.ccy);
        assertNotNull(ac1.id);
        //find this account
        ac1 = findAccount(ac1.id);
        assertEquals(0, ac1.balance.compareTo(BigDecimal.ONE));
        assertEquals(CurrencyType.RUB, ac1.ccy);
        assertNotNull(ac1.id);

        //create account
        AccountResponse ac2 = createAccount(CurrencyType.USD, BigDecimal.valueOf(53.53));
        assertEquals(0, ac2.balance.compareTo(BigDecimal.valueOf(53.53)));
        assertEquals(CurrencyType.USD, ac2.ccy);
        assertNotNull(ac2.id);
        //find this account
        ac2 = findAccount(ac2.id);
        assertEquals(0, ac2.balance.compareTo(BigDecimal.valueOf(53.53)));
        assertEquals(CurrencyType.USD, ac2.ccy);
        assertNotNull(ac2.id);
    }

    @Test
    //Test single transfer money between accounts
    public void transferAccountTest0() {
        AccountResponse ac1 = createAccount(CurrencyType.RUB, BigDecimal.valueOf(1500.47));
        AccountResponse ac2 = createAccount(CurrencyType.RUB, BigDecimal.valueOf(500.53));

        Http.RequestBuilder request = new Http.RequestBuilder().method(POST)
                .bodyJson(Json.toJson(new AccountTransferRequest(ac1.id, ac2.id, BigDecimal.valueOf(500.48))))
                .uri("/account/transfer");
        Result result = route(request);

        assertEquals(Results.ok().status(), result.status());

        //find this account
        ac1 = findAccount(ac1.id);
        assertEquals(0, ac1.balance.compareTo(BigDecimal.valueOf(999.99)));

        //find this account
        ac2 = findAccount(ac2.id);
        assertEquals(0, ac2.balance.compareTo(BigDecimal.valueOf(1001.01)));
    }

    @Test
    //Test single transfer money between accounts when there not enough money on balance
    public void transferAccountTest1() {
        AccountResponse ac1 = createAccount(CurrencyType.RUB, BigDecimal.valueOf(500.47));
        AccountResponse ac2 = createAccount(CurrencyType.RUB, BigDecimal.valueOf(500.53));

        Http.RequestBuilder request = new Http.RequestBuilder().method(POST)
                .bodyJson(Json.toJson(new AccountTransferRequest(ac1.id, ac2.id, BigDecimal.valueOf(500.48))))
                .uri("/account/transfer");
        Result result = route(request);

        assertEquals(Results.badRequest().status(), result.status());

        //find this account
        ac1 = findAccount(ac1.id);
        assertEquals(0, ac1.balance.compareTo(BigDecimal.valueOf(500.47)));

        //find this account
        ac2 = findAccount(ac2.id);
        assertEquals(0, ac2.balance.compareTo(BigDecimal.valueOf(500.53)));
    }

    @Test
    //Test multiple transfer money between accounts in a sequence
    public void transferAccountTest2() {
        AccountResponse ac1 = createAccount(CurrencyType.RUB, BigDecimal.valueOf(1500.47));
        AccountResponse ac2 = createAccount(CurrencyType.RUB, BigDecimal.valueOf(500.53));

        for (int i = 0; i < 10; i++) {
            Http.RequestBuilder request = new Http.RequestBuilder().method(POST)
                    .bodyJson(Json.toJson(new AccountTransferRequest(ac1.id, ac2.id, BigDecimal.valueOf(50.04))))
                    .uri("/account/transfer");
            Result result = route(request);

            assertEquals(Results.ok().status(), result.status());
        }

        //find this account
        ac1 = findAccount(ac1.id);
        assertEquals(0, ac1.balance.compareTo(BigDecimal.valueOf(1000.07)));

        //find this account
        ac2 = findAccount(ac2.id);
        assertEquals(0, ac2.balance.compareTo(BigDecimal.valueOf(1000.93)));
    }


    @Test
    //Test multiple transfer money between accounts in random order simultaneously
    //try to achieve race condition
    public void transferAccountTest3() {
        AccountResponse ac1 = createAccount(CurrencyType.RUB, BigDecimal.valueOf(1500.47));
        AccountResponse ac2 = createAccount(CurrencyType.RUB, BigDecimal.valueOf(500.53));

        CompletableFuture[] rss = new CompletableFuture[20];
        for (int i = 0; i < rss.length / 2; i++) {
            final AccountResponse acc1 = ac1;
            final AccountResponse acc2 = ac2;
            rss[i] = CompletableFuture.supplyAsync(() -> {
                Http.RequestBuilder request = new Http.RequestBuilder().method(POST)
                        .bodyJson(Json.toJson(new AccountTransferRequest(acc1.id, acc2.id, BigDecimal.valueOf(50.04))))
                        .uri("/account/transfer");
                return route(request);
            });
        }

        for (int i = rss.length / 2; i < rss.length; i++) {
            final AccountResponse acc1 = ac1;
            final AccountResponse acc2 = ac2;
            rss[i] = CompletableFuture.supplyAsync(() -> {
                Http.RequestBuilder request = new Http.RequestBuilder().method(POST)
                        .bodyJson(Json.toJson(new AccountTransferRequest(acc2.id, acc1.id, BigDecimal.valueOf(50.04))))
                        .uri("/account/transfer");
                return route(request);
            });
        }

        CompletableFuture[] rsss = shuffleArray(rss);
        CompletableFuture<List<Result>> results = CompletableFuture.
                allOf(rsss).thenApply(v -> Arrays.stream(rsss).
                    map(future -> ((Result) future.join())).
                        collect(Collectors.toList()));

        results.join().forEach(result ->
                assertTrue(Results.ok().status() == result.status() ||
                        Results.badRequest().status() == result.status()));

        //find these accounts
        ac1 = findAccount(ac1.id);
        ac2 = findAccount(ac2.id);
        assertEquals(0, ac1.balance.add(ac2.balance).compareTo(BigDecimal.valueOf(2001)));
    }


    @SuppressWarnings(value = "unchecked")
    public static CompletableFuture[] shuffleArray(CompletableFuture[] futures) {
        List<CompletableFuture> list = Arrays.stream(futures).collect(Collectors.toList());
        Collections.shuffle(list);
        return list.stream().toArray(CompletableFuture[]::new);
    }
}
