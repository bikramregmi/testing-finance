/**
 * 
 */
package com.mobilebanking.api;

import com.mobilebanking.entity.GeneralSettings;

/**
 * @author bibek
 *
 */
public interface IGeneralSettingsApi {
	
	public void save(String key, String value);
	
	public void addCredit(String value) throws Exception;
	
	public GeneralSettings get(String key);
	
	public void smsCredit();

}
