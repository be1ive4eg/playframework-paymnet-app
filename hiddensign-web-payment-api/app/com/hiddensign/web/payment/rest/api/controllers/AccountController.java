package com.hiddensign.web.payment.rest.api.controllers;

import com.hiddensign.core.payment.db.entity.account.Account;
import com.hiddensign.core.payment.db.service.account.AccountService;
import com.hiddensign.web.common.utils.JsonUtils;
import com.hiddensign.web.payment.rest.api.models.requests.AccountCreateRequest;
import com.hiddensign.web.payment.rest.api.models.requests.AccountTransferRequest;
import com.hiddensign.web.payment.rest.api.models.response.AccountResponse;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Nikolay Denisenko
 * @version 2016 /11/12
 */
@Singleton
public class AccountController extends Controller {

    @Inject
    private FormFactory formFactory;

    @Inject
    private AccountService accountService;

    public Result create() {
        Form<AccountCreateRequest> createRequest = formFactory.
                form(AccountCreateRequest.class).bindFromRequest();
        AccountCreateRequest acr = createRequest.get();
        Account account = accountService.createOne(acr.balance, acr.ccy);
        return Results.ok(JsonUtils.response(new AccountResponse
                (account.getId(), account.getBalance(), account.getCcy())));
    }

    public Result find(Long accId) {
        Account account = accountService.findOne(accId);
        return Results.ok(JsonUtils.response(new AccountResponse
                (account.getId(), account.getBalance(), account.getCcy())));
    }

    public Result transfer() {
        Form<AccountTransferRequest> transferRequest = formFactory.
                form(AccountTransferRequest.class).bindFromRequest();
        AccountTransferRequest atr = transferRequest.get();
        accountService.transfer(atr.fromAcc, atr.toAcc, atr.amount);
        return Results.ok(JsonUtils.emptyResponse());
    }

}
