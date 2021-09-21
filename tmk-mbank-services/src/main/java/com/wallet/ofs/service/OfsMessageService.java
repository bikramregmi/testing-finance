package com.wallet.ofs.service;

import com.wallet.ofs.OfsMessage;
import com.wallet.ofs.OfsResponse;

public interface OfsMessageService {

	 OfsResponse composeOfsMessage(OfsMessage ofsMessage,String host,String port);

	OfsResponse reverseTransaction(OfsMessage ofsMessage, String transactionId,String host, String port);

	
}
