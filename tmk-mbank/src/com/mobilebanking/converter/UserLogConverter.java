/**
 * 
 */
package com.mobilebanking.converter;

import java.util.ArrayList;
import java.util.List;

import com.mobilebanking.entity.UserLog;
import com.mobilebanking.model.UserLogDto;

/**
 * @author bibek
 *
 */
public class UserLogConverter implements IConverter<UserLog, UserLogDto>, IListConverter<UserLog, UserLogDto> {

	@Override
	public List<UserLogDto> convertToDtoList(List<UserLog> entityList) {
		List<UserLogDto> userLogList = new ArrayList<UserLogDto>();
		for (UserLog uLog : entityList) {
			userLogList.add(convertToDto(uLog));
		}
		return userLogList;
	}

	@Override
	public UserLog convertToEntity(UserLogDto dto) {
		
		return null;
	}

	@Override
	public UserLogDto convertToDto(UserLog entity) {
		UserLogDto userLog = new UserLogDto();
		userLog.setId(entity.getId());
		userLog.setLastLoggedIn(entity.getLastLoggedIn().toString());
		userLog.setRemarks(entity.getRemarks());
		userLog.setUser(entity.getUser().getUserName());
		return userLog;
	}

}
