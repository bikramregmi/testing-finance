package com.mobilebanking.api.impl;

import java.util.List;

import com.mobilebanking.model.NoticeDTO;

public interface INoticeApi {

	boolean saveNotice(String title,String notice, long l);
	
	List<NoticeDTO>listNotice(String clientId);
	
	boolean editNotice(Long id,String title,String notice);

	NoticeDTO getNoticeById(Long id);

	List<NoticeDTO> listNoticeByDate(String date,String clientId);

	List<NoticeDTO> listNotice(long associatedId);
	
	Boolean updateNotice(List<Long>id);
}
