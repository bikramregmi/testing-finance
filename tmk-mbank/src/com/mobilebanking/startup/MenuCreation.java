package com.mobilebanking.startup;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mobilebanking.entity.Menu;
import com.mobilebanking.entity.MenuTemplate;
import com.mobilebanking.entity.UserTemplate;
import com.mobilebanking.model.MenuType;
import com.mobilebanking.model.Status;
import com.mobilebanking.model.UserType;
import com.mobilebanking.repositories.MenuRepository;
import com.mobilebanking.repositories.MenuTemplateRepository;
import com.mobilebanking.repositories.UserTemplateRepository;

@Component
public class MenuCreation {
	@Autowired
	private MenuRepository menuRepository;

	@Autowired
	private MenuTemplateRepository menuTemplateRepository;

	@Autowired
	private UserTemplateRepository userTemplateRepository;

	public void startupCreator() {

		List<String> menus = new ArrayList<String>();
		menus.add("tmksupermenu,Dashboard-dashboard");

		menus.add("tmksupermenu,Users-users");
		menus.add("Users,Add User-adduser");
		menus.add("Users,List User-listuser");

		menus.add("tmksupermenu,Customer-customer");
		menus.add("Customer,Add Customer-customer/add");
		menus.add("Customer,List Customer-customer/list");
		menus.add("Customer,List Unregistered Customer-customer/list/pendingRegistration");
		menus.add("Customer,Renew Customer-customer/list/expired");
		

		menus.add("tmksupermenu,Commission-commission");
		menus.add("Commission,Add Commission-commission/add");
		menus.add("Commission,List Commission-commission/list");

		menus.add("tmksupermenu,Bank-bank");
		menus.add("Bank,Add Bank-addBank");
		menus.add("Bank,List Bank-listBank");

		menus.add("tmksupermenu,Merchant-merchant");
		menus.add("Merchant,Add Merchant-merchant/add");
		menus.add("Merchant,List Merchant-merchant/list");
		menus.add("Merchant,Add Service-merchant/service/add");
		menus.add("Merchant,List Service-merchant/service/list");

		menus.add("tmksupermenu,Ledger-ledger");
		menus.add("Ledger,Load Bank Balance-ledger/loadbalance");
		menus.add("Ledger,Load Bank Credits-ledger/creditlimits");
		menus.add("Ledger,Decrease Bank Balance-ledger/decrease_balance");
		menus.add("Ledger,Load Cardless Bank Credits-ledger/cardlessbankcreditlimits");
		menus.add("Ledger,Ledger History-ledger/ledger_history");
		menus.add("Ledger,Bankwise Ledger-ledger/view_bank_ledger");
		// menus.add("Ledger, Download Report-ledger/download_ledger");

		menus.add("tmksupermenu,Service Category-servicecategorysuper");
		menus.add("Service Category,Add Service Category-servicecategory");
		menus.add("Service Category,List Service Category-listcategory");

		menus.add("tmksupermenu,Channel Partner-channelpartner");
		menus.add("Channel Partner,Add Channel Partner-addchannelpartner");
		menus.add("Channel Partner,List Channel Partner-listchannelpartner");

		menus.add("tmksupermenu,Web Service-webService");
		menus.add("Web Service,Register Client-oauth/registerclient");
		menus.add("Web Service,List Client-oauth/viewclient");

		menus.add("tmksupermenu,Document IDs-documentIds");
		menus.add("Document IDs,Add Document ID-documentids/add");
		menus.add("Document IDs,List Document ID-documentids/list");

		menus.add("tmksupermenu,Menu-menu");
		menus.add("Menu,List Menu-listMenu");

		menus.add("tmksupermenu,Menu Template-menuTemplate");
		menus.add("Menu Template,Add Menu Template-addMenuTemplate");
		menus.add("Menu Template,List Menu Template-listMenuTemplate");

		menus.add("tmksupermenu,Custom Messages-customMessages");
		menus.add("Custom Messages,Add Custom Messages-smsmode/add");
		menus.add("Custom Messages,List Custom Messages-smsmode/list");

		menus.add("tmksupermenu,Bulk SMS-bulkSms");
		menus.add("Bulk SMS,Send Bulk SMS-/sendBulkAlert");
		
		menus.add("tmksupermenu,SMS Alert-smsalert");
		menus.add("SMS Alert,Send Individual SMS-smsalert/individual");
		menus.add("SMS Alert,Send Bulk SMS-smsalert/bulk");
		menus.add("SMS Alert,List Bulk SMS-smsalert/bulk/list");

		menus.add("tmksupermenu,State-state");
		menus.add("State,Add State-addState");
		menus.add("State,List State-listState");

		menus.add("tmksupermenu,City-city");
		menus.add("City,Add City-addCity");
		menus.add("City,List City-listCity");

		menus.add("tmksupermenu,Customer Profile-customerProfile");
		menus.add("Customer Profile,Add Customer Profile-customerProfile/add");
		menus.add("Customer Profile,List Customer Profile-customerProfile/list");

		menus.add("tmksupermenu,Transaction Report-transactionReport");
		menus.add("Transaction Report,Financial Report-transaction/report/financial");
		menus.add("Transaction Report,Non Financial Report-transaction/report/nonfinancial");
		menus.add("Transaction Report,Commission Report-transaction/report/commission");
		menus.add("Transaction Report,Ambiguous Report-transaction/ambiguous");

		menus.add("tmksupermenu,Bank Branch-bankbranch");
		menus.add("Bank Branch,Add Branch-bank/branch/add");
		menus.add("Bank Branch,List Branch-bank/branch/list");

//		menus.add("tmksupermenu,Mini-Statement");
//		menus.add("Mini-Statement,List Statement-bank/branch/list");

		menus.add("tmksupermenu,Sevices-services");
		menus.add("Sevices,List Services-paymentservices");

		menus.add("tmksupermenu,LicenseCountLog-licensecountlog");
		menus.add("LicenseCountLog,List LicenseCountLog-listLicenseCount/list");

		menus.add("tmksupermenu,SmsCountLog-smscountlog");
		menus.add("SmsCountLog,List SmsCountLog-listSmsCount/list");

		menus.add("tmksupermenu,Notice-notice");
		menus.add("Notice,Add Notice-notice/add");
		menus.add("Notice,List Notice-listNotice");
		// menus.add("Notification,Notifications-notification");

		menus.add("tmksupermenu,Cheque Requests-chequerequestsuper");
		menus.add("Cheque Requests,Cheque Request-chequerequest");
		menus.add("Cheque Requests,Cheque Block Request-chequeblockrequest");

		menus.add("tmksupermenu,SMS Log-smsLog");
		menus.add("SMS Log,SMS Log Report-getSmsReport");
		// added by amrit
		menus.add("SMS Log,Transaction Alert Report-transaction_alert_report");

		// end added
		menus.add("tmksupermenu,Settlement Log-settlement");
		menus.add("Settlement Log,Settlement Log Report-pendingSettlement");

		menus.add("tmksupermenu,Cardless Bank-supercardlessbank");
		menus.add("Cardless Bank,Add Cardless Bank-cardlessbank/add");
		menus.add("Cardless Bank,List Cardless Bank-cardlessbank");

		menus.add("tmksupermenu,Nea Office-superneaoffice");
		menus.add("Nea Office,Add Nea Office-officecode/add");
		menus.add("Nea Office,List Nea Office-officecode");

		menus.add("tmksupermenu,Notification Groups-supernotificationGroup");
		menus.add("Notification Groups,Add Notification Group-notificationGroup/add");
		menus.add("Notification Groups,List Notification Group-notificationGroup/list");

		menus.add("tmksupermenu,Notifications-supernotifications");
		menus.add("Notifications,Notification-notification");

		menus.add("tmksupermenu,FCM Server Setting-superfcmserversetting");
		menus.add("FCM Server Setting,Add Server Setting-fcmServerSetting/add");
		menus.add("FCM Server Setting,List Server Setting-fcmServerSetting/list");

		menus.add("tmksupermenu,Reports-reports");
		// menus.add("Reports, Download Reports-reports/download_reports");
		menus.add("Reports,Customer Registration Report-reports/c_r_report");// 1
		menus.add("Reports,Bank Account Detail Report-reports/b_a_d_report");// 2
		menus.add("Reports,Business Summary Report-reports/b_s_report");// 3
		menus.add("Reports,Revenue Sharing Report-reports/r_s_report");// 4
		// menus.add("Reports, Bank Balance Checking
		// Report-reports/b_b_c_report");//5
		menus.add("Reports,SMS Business Report-reports/s_b_report");// 6
		menus.add("Reports,Reversal Transaction Report-reports/r_t_report");// 7
		// menus.add("Reports, paypoint Report-reports/p_report");//8 this is
		// same in revenue report with paypoint merchant
		menus.add("Reports,Download NRB Reports-reports/download_nrb_reports");
		menus.add("Reports,Customer Log Report-reports/c_l_report");
		
		
		menus.add("tmksupermenu,Homescreen Image-superhomescreenimage");
		menus.add("Homescreen Image,Add Homescreen Image-homescreenimage");
		
		menus.add("tmksupermenu,Cancel Request-cancelrequestsuper");
		menus.add("Cancel Request,List Cancel Request-cancelrequets");

		for (String m : menus) {

			String[] first = m.split(",");

			if (first[0].equals("tmksupermenu")) {
				String[] second = first[1].split("-");
				if(second[1].equals("superfcmserversetting")){
					System.out.println("ERROR HERE");
				}
				if (menuRepository.findByMenuByUrl(second[1].trim()) == null) {
					System.out.println();
					Menu menu = new Menu();
					menu.setName(second[0].trim());
					menu.setUrl(second[1].trim());
					menu.setStatus(Status.Active);
					menu.setMenuType(MenuType.SuperMenu);
					menuRepository.save(menu);
				}
			} else {

				String[] second = first[1].split("-");
				// System.out.println("The second name 1st part is
				// :"+second[0]+" and second part is : "+second[1]);
				if (menuRepository.findByMenuByUrl(second[1].trim()) == null) {
					Menu menu = new Menu();
					menu.setName(second[0].trim());
					menu.setUrl(second[1].trim());
					menu.setStatus(Status.Active);
					menu.setMenuType(MenuType.SubMenu);
					System.out.println("current element " + first[0]);
					Menu superMenu = menuRepository.findByMenu(first[0]);
					menu.setSuperId(superMenu.getId());

					menuRepository.save(menu);
				}
			}

		}

		menutemplatecreator();

	}

	private void menutemplatecreator() {
		List<String> menuTemplates = new ArrayList<String>();
		menuTemplates.add(
				"AdminRole-adduser,listuser,customer/list,cardlessbank,cardlessbank/add,licensecountlog/list,commission/add,commission/list,addBank,listBank,merchant/add,merchant/list,merchant/service/add,merchant/service/list,ledger/loadbalance,ledger/creditlimits,servicecategory,listcategory,addchannelpartner,listchannelpartner,oauth/registerclient,oauth/viewclient,documentids/add,documentids/list,listMenu,addMenuTemplate,listMenuTemplate,smsmode/add,smsmode/list,addState,listState,addCity,listCity,transaction/report/financial,transaction/report/nonfinancial,transaction/report/commission,transaction/ambiguous,getSmsReport,/reports/download_reports-Admin");
		menuTemplates.add(
				"BankRole-adduser,listuser,customer/add,customer/list,ministatement/list,bank/branch/add,bank/branch/list,smsmode/add,smsmode/list,paymentservices,notice/add,listNotice,chequerequest,chequeblockrequest,getSmsReport,transaction/report/financial,transaction/report/nonfinancial-Bank");
		menuTemplates.add(
				"BankBranchRole-customer/add,customer/list,ministatement/list,paymentservices,chequerequest,chequeblockrequest,getSmsReport,transaction/report/financial,transaction/report/nonfinancial-BankBranch");
		menuTemplates.add("CardLessBankRole- -CardLessBank");
		menuTemplates.add("ChannelPartnerRole- -ChannelPartner");
		Long i = 1L;
		for (String mt : menuTemplates) {

			String[] splitted = mt.split("-");
			if (menuTemplateRepository.findOne(i) == null) {
				MenuTemplate menuTemplate = new MenuTemplate();
				menuTemplate.setName(splitted[0]);
				menuTemplate = menuTemplateRepository.save(menuTemplate);
				createUserTemplate(splitted[0] + "," + splitted[2]);
				for (String menu : splitted[1].split(",")) {
					addMenuTotemp(menuTemplate.getId(), menu);
				}
			}
			i++;
		}

	}

	public void addMenuTotemp(long id, String url) {

		MenuTemplate menuTemplate = menuTemplateRepository.findOne(id);
		Menu menu = menuRepository.findByMenuByUrl(url);
		menuTemplate.getMenu().add(menu);
		menuTemplateRepository.save(menuTemplate);
		this.parentResolver(id, url);

	}

	private void parentResolver(long id, String url) {

		Menu menu = menuRepository.getParent(id, MenuType.SuperMenu, url);
		if (menu != null) {
			MenuTemplate menuTemplate = menuTemplateRepository.findOne(id);
			menuTemplate.getMenu().add(menu);
			menuTemplateRepository.save(menuTemplate);
		}

	}

	private void createUserTemplate(String str) {

		String[] first = str.split(",");
		MenuTemplate menuTemplate = menuTemplateRepository.findByMenuTemplate(first[0]);
		UserTemplate userTemplate = new UserTemplate();
		userTemplate.setUserTemplateName(first[0]);
		userTemplate.setMenuTemplate(menuTemplate);
		userTemplate.setUsertype(UserType.valueOf(first[1]));
		userTemplateRepository.save(userTemplate);
	}
}
