package com.mobilebanking.api.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.BankOAuthClient;
import com.mobilebanking.entity.Notice;
import com.mobilebanking.model.NoticeDTO;
import com.mobilebanking.repositories.BankOAuthClientRepository;
import com.mobilebanking.repositories.BankRepository;
import com.mobilebanking.repositories.NoticeRepository;

@Service
public class NoticeApi implements INoticeApi {

	@Autowired
	private NoticeRepository noticeRepository;
	
	@Autowired
	private BankRepository bankRepository;
	@Autowired
	private BankOAuthClientRepository bankAuthRepository;
	
	@Override
	public boolean saveNotice(String title ,String notice ,long bankId) {
		Bank bank = bankRepository.findOne(bankId);
		Notice notices  = new Notice();
		notices.setTitle(title);
		notices.setNotice(notice);
		notices.setBank(bank);
		notices.setIsViewed(false);
		noticeRepository.save(notices);
		return true;
	}

	@Override
	public List<NoticeDTO> listNotice(String clientId) {
		BankOAuthClient bankAouthClient = bankAuthRepository.findByOAuthClientId(clientId);
		Bank bank = bankAouthClient.getBank();
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		List<Notice> noticeList = noticeRepository.findByBank(bank);
		List<NoticeDTO> noticeDtoList = new ArrayList<>();
		if(!noticeList.isEmpty()){
		for(Notice notice :noticeList){
			NoticeDTO noticeDto = new NoticeDTO();
			noticeDto.setId(notice.getId());
			noticeDto.setDescription(notice.getNotice());
			noticeDto.setTitle(notice.getTitle());
			noticeDto.setCreated(dateFormat.format(notice.getCreated()));
			if(notice.getIsViewed()!=null)
			noticeDto.setViewed(notice.getIsViewed());
			noticeDtoList.add(noticeDto);
			
		}
		Collections.sort(noticeDtoList, new Comparator<NoticeDTO>() {
			@Override
			public int compare(NoticeDTO o1, NoticeDTO o2) {
				
				return o1.getCreated().compareTo(o2.getCreated());
				
			}
	    });
	
	return noticeDtoList;
		}
		
		return null;
	}

	@Override
	public boolean editNotice(Long id,String title,String notices) {
		Notice nootice = noticeRepository.findOne(id);
		nootice.setNotice(notices);
		nootice.setTitle(title);
		noticeRepository.save(nootice);
		return true;
	}

	@Override
	public NoticeDTO getNoticeById(Long id) {
		Notice notice = noticeRepository.findOne(id);
		NoticeDTO noticeDto = new NoticeDTO();
		noticeDto.setId(notice.getId());
		noticeDto.setDescription(notice.getNotice());
		noticeDto.setTitle(notice.getTitle());
		return noticeDto;
	}

	@Override
	public List<NoticeDTO> listNoticeByDate(String date,String clientId) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		try {
			Date created = dateFormat.parse(date);
			List<Notice> noticeList = noticeRepository.findByCreatedDate(created);
			List<NoticeDTO> noticeDtoList = new ArrayList<>();
			if(!noticeList.isEmpty()){
			for(Notice notice :noticeList){
				NoticeDTO noticeDto = new NoticeDTO();
				noticeDto.setId(notice.getId());
				noticeDto.setDescription(notice.getNotice());
				noticeDto.setTitle(notice.getTitle());
				noticeDto.setCreated(dateFormat.format(notice.getCreated()));
				noticeDto.setViewed(notice.getIsViewed());
				noticeDtoList.add(noticeDto);
				
			}
			 Collections.sort(noticeDtoList, new Comparator<NoticeDTO>() {
					@Override
					public int compare(NoticeDTO o1, NoticeDTO o2) {
						return o1.getCreated().compareTo(o2.getCreated());
					}
			    });
			return noticeDtoList;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	@Override
	public List<NoticeDTO> listNotice(long bankId) {
		Bank bank = bankRepository.findOne(bankId);
		List<Notice> noticeList = noticeRepository.findByBank(bank);
		List<NoticeDTO> noticeDtoList = new ArrayList<>();
		if(!noticeList.isEmpty()){
		for(Notice notice :noticeList){
			NoticeDTO noticeDto = new NoticeDTO();
			noticeDto.setId(notice.getId());
			noticeDto.setDescription(notice.getNotice());
			noticeDto.setTitle(notice.getTitle());
			noticeDto.setCreated(notice.getCreated().toString().substring(0,16));
			noticeDtoList.add(noticeDto);
		}
		}
		return noticeDtoList;
	}

	@Override
	public Boolean updateNotice(List<Long> ids) {
		for(Long id : ids){
		Notice nootice = noticeRepository.findOne(id);
		nootice.setIsViewed(true);
		noticeRepository.save(nootice);
		}
		return true;
	}


}
