package com.mobilebanking.api;

import com.mobilebanking.entity.SmsCountLog;
import com.mobilebanking.model.SmsCountDTO;
import com.mobilebanking.util.ClientException;
import org.json.JSONException;

import java.util.List;

public interface ISmsCountApi {

    List<SmsCountDTO> findSmsCountLog();
    SmsCountLog saveRequest(SmsCountDTO smsCountDTO) throws JSONException, ClientException;
}
