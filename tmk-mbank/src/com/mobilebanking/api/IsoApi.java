package com.mobilebanking.api;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.impl.IIsoApi;
import com.mobilebanking.entity.Bank;
import com.mobilebanking.model.Status;
import com.mobilebanking.repositories.BankRepository;
import com.mobilebanking.util.DateUtil;
import com.mobilebanking.util.STANGenerator;
import com.mobilebanking.util.StringConstants;
import com.wallet.iso8583.core.NavaJeevanIsoCore;
import com.wallet.iso8583.network.NetworkTransportNavaJeevan;

@Service
public class IsoApi implements IIsoApi {

	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private NavaJeevanIsoCore navaJeevanIsoCore;

	@Autowired
	private NetworkTransportNavaJeevan networkTransporstNavaJeevan;

	@Override
	public boolean echoBank(Long bankId) {
		Bank bank = bankRepository.findOne(bankId);
		HashMap<String, String> isoData = new HashMap<String, String>();
		isoData.put("3", "400000");
		isoData.put("7", DateUtil.convertDateToStringForIso(new Date()));
		isoData.put("11", STANGenerator.StanForIso("41"));
		isoData.put("32", bank.getAcquiringInstitutionIdentificationCode());
		String packedData = "";
		try {
			packedData = navaJeevanIsoCore.packMessage("0800", isoData);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String response = "";
		try {
			response = networkTransporstNavaJeevan.transportEchoIsoMessage(bank.getIsoUrl(),
					Integer.parseInt(bank.getPortNumber()), packedData);
			if (response.equalsIgnoreCase("failure"))
				return false;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Scheduled(fixedDelay = 30 * 60 * 1000)
	public void checkBankStatus() {
		if (StringConstants.IS_PRODUCTION) {
			List<Bank> bankList = bankRepository.findAll();
			for (Bank bank : bankList) {
				try {
					if (echoBank(bank.getId())) {
						bank = bankRepository.findOne(bank.getId());
						bank.setCbsStatus(Status.Active);
					} else {
						bank = bankRepository.findOne(bank.getId());
						bank.setCbsStatus(Status.Inactive);
					}
					bankRepository.save(bank);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
