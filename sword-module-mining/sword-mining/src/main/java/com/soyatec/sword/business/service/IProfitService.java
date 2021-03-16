package com.soyatec.sword.business.service;

import java.util.Date;

import com.soyatec.sword.exceptions.TransactionException;

public interface IProfitService {

	int dispatchProfit(Date date) throws TransactionException;
}
