package com.mobilebanking.api;

import com.mobilebanking.entity.User;
import com.mobilebanking.util.ClientException;

public interface IPeerToPeerApi {

//	void peerToPeerTransfer(User fromUser, double amount, String toUser) throws ClientException;

	void peerToPeerTransfer(User fromUser, double amount, String toUser, String owner) throws ClientException;

}
