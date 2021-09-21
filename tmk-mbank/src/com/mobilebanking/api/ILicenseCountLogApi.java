package com.mobilebanking.api;

import com.mobilebanking.entity.LicenseCountLog;
import com.mobilebanking.model.LicenseCountLogDTO;
import com.mobilebanking.util.ClientException;
import org.json.JSONException;

import java.util.List;

public interface ILicenseCountLogApi {

    List<LicenseCountLogDTO> findLicenseCountLog();
    LicenseCountLog saveRequest(LicenseCountLogDTO licenseCountLogDTO) throws JSONException, ClientException;

}
