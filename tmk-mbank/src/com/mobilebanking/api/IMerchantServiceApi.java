/**
 * 
 */
package com.mobilebanking.api;

import java.util.List;

import org.json.JSONException;

import com.mobilebanking.model.MerchantServiceDTO;
import com.mobilebanking.model.Status;
import com.mobilebanking.util.ClientException;

/**
 * @author bibek
 *
 */
public interface IMerchantServiceApi {
	
	MerchantServiceDTO saveMerchantService(MerchantServiceDTO merchantServiceDto) throws JSONException, ClientException;
	
	MerchantServiceDTO editMerchantService(MerchantServiceDTO merchantServiceDto);
	
	MerchantServiceDTO findMerchantServiceById(long merchantServiceId);
	
	List<MerchantServiceDTO> findAllMerchantService();
	
	List<MerchantServiceDTO> findMerchantServiceByStatus(Status status);
	
	MerchantServiceDTO findServiceByUniqueIdentifier(String uniqueId);

	List<MerchantServiceDTO> findNotIncludedMerchantServiceByMerchantIdAndStatus(long merchantId, Status status);

	List<MerchantServiceDTO> findMerchantServiceByMerchantAndStatus(long merchantId, Status active);

	List<MerchantServiceDTO> getAllMerchantServiceExceptIncluded(long merchantId);

	List<MerchantServiceDTO> getAllMerchantServicesByMerchant(long merchantId);

	List<MerchantServiceDTO> getMerchantServicesWithoutCommissionByMerchant(long merchantId);

}
