package com.mobilebanking.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mobilebanking.api.IBankApi;
import com.mobilebanking.api.IBankBranchApi;
import com.mobilebanking.api.IMerchantApi;
import com.mobilebanking.api.IMerchantServiceApi;
import com.mobilebanking.api.INonFinancialTransactionApi;
import com.mobilebanking.api.ISettlementLogApi;
import com.mobilebanking.api.ITransactionApi;
import com.mobilebanking.entity.User;
import com.mobilebanking.model.BankBranchDTO;
import com.mobilebanking.model.BankDTO;
import com.mobilebanking.model.IsoStatus;
import com.mobilebanking.model.NonFinancialTransactionDTO;
import com.mobilebanking.model.PagablePage;
import com.mobilebanking.model.RestResponseDTO;
import com.mobilebanking.model.TransactionDTO;
import com.mobilebanking.model.TransactionStatus;
import com.mobilebanking.model.UserType;
import com.mobilebanking.model.mobile.ResponseDTO;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;

@Controller
public class TransactionController implements MessageSourceAware {

	private Logger logger = LoggerFactory.getLogger(TransactionController.class);

	private MessageSource messageSource;

	@Autowired
	private ITransactionApi transactionApi;

	@Autowired
	private INonFinancialTransactionApi nonfinancialTransactionApi;

	@Autowired
	private IBankApi bankApi;

	@Autowired
	private IBankBranchApi bankBranchApi;

	@Autowired
	private ISettlementLogApi settlementLogApi;

	@Autowired
	private IMerchantServiceApi merchantServiceApi;

	@Autowired
	private IMerchantApi merchantApi;

	@RequestMapping(method = RequestMethod.GET, value = "/transaction/report/commission")
	public String reportCommissionTransaction(RedirectAttributes redirectAttributes, ModelMap modelMap, Model model,
			@RequestParam(value = "pageNo", required = false) Integer page,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			@RequestParam(value = "bank", required = false) String bank,
			@RequestParam(value = "merchant", required = false) String merchant) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				PagablePage pg = transactionApi.getCommissionTransactions(page, fromDate, toDate, bank, merchant,
						false);
				modelMap.put("bankList", bankApi.getAllBank());
				modelMap.put("merchantList", merchantApi.getAllMerchant());
				modelMap.put("transactionList", pg);
				modelMap.put("transactionStatus", TransactionStatus.values());

				modelMap.put("fromDate", (fromDate == null) ? "" : fromDate);
				modelMap.put("toDate", (toDate == null) ? "" : toDate);
				modelMap.put("bank", (bank == null) ? "" : bank);
				modelMap.put("merchant", (merchant == null) ? "" : merchant);

				return "Transaction/commissionreport";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/transaction/report/financial/export")
	private void exportFinancialReport(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR)
					|| authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)) {
				ServletContext context = request.getSession().getServletContext();
				String path = context.getRealPath("");

				File theDir = new File(path + "\\" + "exportedpdf");

				if (!theDir.exists()) {
					try {
						theDir.mkdir();

					} catch (SecurityException se) {

					}
				}
				String file = transactionApi.exportFinancialTransaction(path + "\\exportedpdf");
				path += "\\exportedpdf\\" + file + ".pdf";

				int BUFF_SIZE = 1024;
				byte[] buffer = new byte[BUFF_SIZE];
				response.setContentType("application/pdf");
				response.setHeader("Content-Type", "application/pdf");
				File pdfFile = new File(path);
				FileInputStream fis = new FileInputStream(pdfFile);
				OutputStream os = response.getOutputStream();
				try {
					response.setContentLength((int) pdfFile.length());
					int byteRead = 0;
					while ((byteRead = fis.read()) != -1) {
						os.write(byteRead);
					}
					os.flush();
				} catch (Exception excp) {
					excp.printStackTrace();
				} finally {
					os.close();
					fis.close();
				}
				pdfFile.delete();
			}
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/transaction/report/nonfinancial/export")
	private void exportNonFinancialReport(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR)
					|| authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)) {
				ServletContext context = request.getSession().getServletContext();
				String path = context.getRealPath("");

				File theDir = new File(path + "\\" + "exportedpdf");

				if (!theDir.exists()) {
					try {
						theDir.mkdir();

					} catch (SecurityException se) {

					}
				}
				String file = nonfinancialTransactionApi.exportNonFinancialTransaction(path + "\\exportedpdf");
				path += "\\exportedpdf\\" + file + ".pdf";

				int BUFF_SIZE = 1024;
				byte[] buffer = new byte[BUFF_SIZE];
				response.setContentType("application/pdf");
				response.setHeader("Content-Type", "application/pdf");
				File pdfFile = new File(path);
				FileInputStream fis = new FileInputStream(pdfFile);
				OutputStream os = response.getOutputStream();
				try {
					response.setContentLength((int) pdfFile.length());
					int byteRead = 0;
					while ((byteRead = fis.read()) != -1) {
						os.write(byteRead);
					}
					os.flush();
				} catch (Exception excp) {
					excp.printStackTrace();
				} finally {
					os.close();
					fis.close();
				}
				pdfFile.delete();
			}
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/transaction/report/financial")
	public String reportTransaction(RedirectAttributes redirectAttributes, ModelMap modelMap, Model model,
			@RequestParam(value = "pageNo", required = false) Integer page,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			@RequestParam(value = "status", required = false) TransactionStatus status,
			@RequestParam(value = "identifier", required = false) String identifier,
			@RequestParam(value = "mobileNo", required = false) String mobileNo,
			@RequestParam(value = "bank", required = false) String bank,
			@RequestParam(value = "serviceType", required = false) String serviceIdentifier,
			@RequestParam(value = "target_no", required = false) String targetNo) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR)
					|| authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)) {
				/*
				 * List<TransactionDTO> filteredTransaction =
				 * (List<TransactionDTO>) model.asMap().get("transactionList");
				 */
				PagablePage pg = transactionApi.getAllTransactions(page, fromDate, toDate, identifier, status, mobileNo,
						bank, serviceIdentifier, false, targetNo);
				if (authority.contains(Authorities.ADMINISTRATOR)) {
					List<BankDTO> bankList = bankApi.getAllBank();
					modelMap.put("bankList", bankList);
					modelMap.put("isAdmin", true);
				} else if (authority.contains(Authorities.BANK)) {
					List<BankBranchDTO> bankBranchList = bankBranchApi
							.listBankBranchByBank(AuthenticationUtil.getCurrentUser().getAssociatedId());
					modelMap.put("branchList", bankBranchList);
				}
				modelMap.put("transactionList", pg);
				modelMap.put("transactionStatus", TransactionStatus.values());
				modelMap.put("serviceList", merchantServiceApi.findAllMerchantService());

				modelMap.put("fromDatep", (fromDate == null) ? "" : fromDate);
				modelMap.put("toDatep", (toDate == null) ? "" : toDate);
				modelMap.put("statusp", (status == null) ? "" : status);
				modelMap.put("mobileNop", (mobileNo == null) ? "" : mobileNo);
				modelMap.put("targetNo", (targetNo == null) ? "" : targetNo);
				modelMap.put("bankp", (bank == null) ? "" : bank);
				modelMap.put("identifierp", (identifier == null) ? "" : identifier);
				modelMap.put("serviceTypep", (serviceIdentifier == null) ? "" : serviceIdentifier);

				return "Transaction/financialreport";
			}
		}
		return "redirect:/";
	}

	// tet

	@RequestMapping(method = RequestMethod.GET, value = "/transaction/report/nonfinancial")
	public String nonFinancialreportTransaction(RedirectAttributes redirectAttributes, ModelMap modelMap, Model model,
			@RequestParam(value = "pageNo", required = false) Integer page,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			@RequestParam(value = "status", required = false) IsoStatus status,
			@RequestParam(value = "identifier", required = false) String identifier,
			@RequestParam(value = "mobileNo", required = false) String mobileNo,
			@RequestParam(value = "bank", required = false) String bank) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR)
					|| authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)) {
				/*
				 * List<NonFinancialTransactionDTO> filteredTransaction =
				 * (List<NonFinancialTransactionDTO>)
				 * model.asMap().get("transactionList");
				 * List<NonFinancialTransactionDTO> transactionList =
				 * nonfinancialTransactionApi.getAllTransaction();
				 * modelMap.put("transactionList", filteredTransaction==null ?
				 * transactionList : filteredTransaction);
				 */
				modelMap.put("isoStatus", IsoStatus.values());
				if (authority.contains(Authorities.ADMINISTRATOR)) {
					modelMap.put("bankList", bankApi.getAllBank());
					modelMap.put("isAdmin", true);
				} else if (authority.contains(Authorities.BANK)) {
					List<BankBranchDTO> bankBranchList = bankBranchApi
							.listBankBranchByBank(AuthenticationUtil.getCurrentUser().getAssociatedId());
					modelMap.put("branchList", bankBranchList);
				}

				PagablePage pg = nonfinancialTransactionApi.getAllTransactions(page, fromDate, toDate, identifier,
						status, mobileNo, bank);

				modelMap.put("transactionList", pg);

				modelMap.put("fromDatep", (fromDate == null) ? "" : fromDate);
				modelMap.put("toDatep", (toDate == null) ? "" : toDate);
				modelMap.put("statusp", (status == null) ? "" : status);
				modelMap.put("mobileNop", (mobileNo == null) ? "" : mobileNo);
				modelMap.put("bankp", (bank == null) ? "" : bank);
				modelMap.put("identifierp", (identifier == null) ? "" : identifier);

				return "Transaction/nonfinancialreport";

			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/transaction/filter")
	public String filterTransaction(RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR)
					|| authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)) {
				String fromDate = request.getParameter("fromDate");
				String toDate = request.getParameter("toDate");
				String identifier = request.getParameter("identifier");
				String status = request.getParameter("status");
				String mobileNo = request.getParameter("mobileNo");
				String bank = request.getParameter("bank");

				List<TransactionDTO> transactionList = transactionApi.filterTransaction(fromDate, toDate, identifier,
						status, mobileNo, bank);
				redirectAttributes.addFlashAttribute("transactionList", transactionList);
				redirectAttributes.addFlashAttribute("fromFilter", String.valueOf(true));
				return "redirect:/transaction/report/financial";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/transaction/filter/nonfinancial")
	public String filterNonFinancialTransaction(RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR)
					|| authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)) {
				String fromDate = request.getParameter("fromDate");
				String toDate = request.getParameter("toDate");
				String identifier = request.getParameter("identifier");
				String status = request.getParameter("status");
				String mobileNo = request.getParameter("mobileNo");
				String bank = request.getParameter("bank");
				List<NonFinancialTransactionDTO> transactionList = nonfinancialTransactionApi
						.filterTransaction(fromDate, toDate, identifier, status, mobileNo, bank);
				redirectAttributes.addFlashAttribute("transactionList", transactionList);
				redirectAttributes.addFlashAttribute("fromFilter", String.valueOf(true));
				return "redirect:/transaction/report/nonfinancial";
			}
		}
		return "redirect:/";
	}

	/*
	 * @RequestMapping(method = RequestMethod.GET, value = "/transaction/get")
	 * public String getTransaction(RedirectAttributes redirectAttributes,
	 * HttpServletRequest request) { if (AuthenticationUtil.getCurrentUser() !=
	 * null) { String authority =
	 * AuthenticationUtil.getCurrentUser().getAuthority(); if
	 * (authority.contains(Authorities.ADMINISTRATOR) ||
	 * authority.contains(Authorities.BANK) &&
	 * authority.contains(Authorities.AUTHENTICATED)) { try { String pageNo =
	 * request.getParameter("pageNo"); List<TransactionDTO> transactionList =
	 * new ArrayList<>(); int lastPage =
	 * transactionApi.getAllTransactions().size() / 10; if
	 * (transactionApi.getAllTransactions().size() % 10 != 0) { lastPage += 1; }
	 * if (pageNo.equals("first")) { transactionList =
	 * transactionApi.getByPageNumber(1); pageNo = "1"; } else if
	 * (pageNo.equals("last")) { transactionList =
	 * transactionApi.getByPageNumber(lastPage); pageNo =
	 * String.valueOf(lastPage); } else { transactionList =
	 * transactionApi.getByPageNumber(Integer.parseInt(pageNo)); } if
	 * (transactionList == null) { pageNo = "1"; } else if
	 * (transactionList.isEmpty()) { pageNo = "1"; }
	 * redirectAttributes.addFlashAttribute("fromFilter",
	 * String.valueOf(false));
	 * redirectAttributes.addFlashAttribute("currentPage", pageNo);
	 * redirectAttributes.addFlashAttribute("lastPage",
	 * String.valueOf(lastPage));
	 * redirectAttributes.addFlashAttribute("transactionList", transactionList);
	 * return "redirect:/transaction/report/financial"; } catch (Exception e) {
	 * return "redirect:/transaction/report/financial"; } } } return
	 * "redirect:/"; }
	 */

	@RequestMapping(method = RequestMethod.GET, value = "/transaction/ambiguous")
	public String ambiguousTransaction(ModelMap modelMap) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				List<TransactionDTO> transactionList = transactionApi
						.getTransactionByStatus(TransactionStatus.Ambiguous);
				modelMap.put("transactionList", transactionList);
				return "Transaction/ambiguousTransaction";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/transaction/checkambiguoustransactionstatus")
	public String checkAmbiguousStatus(ModelMap modelmap, HttpServletRequest request,
			RedirectAttributes redirectAttributes,
			@RequestParam("transactionidentifier") String transactionIdentifier) {
		try {
			if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (authority.contains(Authorities.ADMINISTRATOR)
						|| authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)) {
					String status = transactionApi.checkAmbiguousTransactionStatus(transactionIdentifier);
					modelmap.put("status", status);
					if (status.equals("success") || status.equals("failure")) {
						redirectAttributes.addFlashAttribute("message", "Transaction Status Updated Successfully");
						return "redirect:/transaction/ambiguous";
					} else {
						List<TransactionDTO> transactionList = transactionApi
								.getTransactionByStatus(TransactionStatus.Ambiguous);
						modelmap.put("transactionList", transactionList);
						return "Transaction/ambiguousTransaction";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/transaction/manualUpdateAmbiguousTransaction")
	public String manualUpdateAmbiguousTransaction(ModelMap modelmap, RedirectAttributes redirectAttributes,
			HttpServletRequest request, @RequestParam("transactionIdentifier") String transactionIdentifier,
			@RequestParam("status") boolean status) {
		try {
			if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (authority.contains(Authorities.ADMINISTRATOR)
						|| authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)) {
					transactionApi.manualUpdateAmbiguousTransaction(transactionIdentifier, status);
					redirectAttributes.addFlashAttribute("message", "Transaction Status Updated Successfully");
					return "redirect:/transaction/ambiguous";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/transaction/reverserTransaction")
	public String reverseTransaction(ModelMap modelmap, RedirectAttributes redirectAttributes,
			HttpServletRequest request, @RequestParam("transactionIdentifier") String transactionIdentifier) {
		try {
			if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
					transactionApi.paypointFailureReversal(transactionIdentifier);
					redirectAttributes.addFlashAttribute("message", "Transaction Status Updated Successfully");
					return "redirect:/transaction/ambiguous";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/transactiondetail")
	public String getTransactionDetail(ModelMap modelmap, HttpServletRequest request,
			@RequestParam(value = "transactionId", required = true) String transactionId) {
		try {
			if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();

				if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)
						|| authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)) {

					// TransactionDTO transaction =
					// transactionApi.getTransactionByTransactionIdentifier(transactionId);
					modelmap.put("settlementLogList", settlementLogApi.findByTransactionIdentifier(transactionId));
					modelmap.put("transaction", transactionId);
					modelmap.put("userType", AuthenticationUtil.getCurrentUser().getUserType());
					return "/Transaction/transactonDetails";

				}
			}
		} catch (Exception e) {
			logger.debug("exception" + e);
			e.printStackTrace();
			return "redirect:/";
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/gettransactiondetail")
	public ResponseEntity<ResponseDTO> getTransactionDetail(@RequestParam(value = "transactionId") String transactionId,
			HttpServletRequest request, HttpServletResponse response) {

		ResponseDTO restResponseDto = new ResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)
					|| authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)) {
				TransactionDTO transaction = transactionApi.getTransactionByTransactionIdentifier(transactionId);
				restResponseDto.setDetails(transaction);
			}
		}
		return new ResponseEntity<>(restResponseDto, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/checktransaction")
	public ResponseEntity<ResponseDTO> checkTransaction(@RequestParam("transactionId") String transactionIdentifier) {
		ResponseDTO restResponseDto = new ResponseDTO();
		try {
			if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
					String status = transactionApi.checkProblematicTransaction(transactionIdentifier);
					if (status.equals("failure")) {
						restResponseDto.setMessage(
								"The transaction is marked as Problematic, Do you want to reverse transaction?");
						restResponseDto.setStatus("failure");

					} else if (status.equals("success")) {
						restResponseDto.setMessage("The transaction was marked as Sucessfull.");
						restResponseDto.setStatus("success");
					} else {
						restResponseDto.setMessage("Service is currently not available. Please try again later.");
						restResponseDto.setStatus("success");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(restResponseDto, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(restResponseDto, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getSettlement")
	public ResponseEntity<ResponseDTO> getSettlement(@RequestParam("transaction") String transactionIdentifier) {
		ResponseDTO response = new ResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				response.setDetails(settlementLogApi.findByTransactionIdentifier(transactionIdentifier));
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getTransactionForreport")
	public ResponseEntity<ResponseDTO> getTransactionReport(
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			@RequestParam(value = "status", required = false) TransactionStatus status,
			@RequestParam(value = "identifier", required = false) String identifier,
			@RequestParam(value = "mobileNo", required = false) String mobileNo,
			@RequestParam(value = "bank", required = false) String bank,
			@RequestParam(value = "serviceType", required = false) String serviceIdentifier,
			@RequestParam(value = "targer_no", required = false) String targetNo) {
		ResponseDTO response = new ResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if ((authority.contains(Authorities.ADMINISTRATOR) || authority.contains(Authorities.BANK))
					&& authority.contains(Authorities.AUTHENTICATED)) {
				response.setDetails(transactionApi.getAllTransactions(0, fromDate, toDate, identifier, status, mobileNo,
						bank, serviceIdentifier, true, targetNo).getObject());
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}

/*	@RequestMapping(method = RequestMethod.GET, value = "/getCommissionForreport")
	public ResponseEntity<ResponseDTO> getCommissionReport(
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			@RequestParam(value = "bank", required = false) String bank,
			@RequestParam(value = "merchant", required = false) String merchant) {
		ResponseDTO response = new ResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				response.setDetails(transactionApi.getCommissionTransactions(0, fromDate, toDate, bank, merchant, true)
						.getObject());
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}*/
	//edited for report
	@RequestMapping(method = RequestMethod.GET, value = "/getCommissionForreport")
	public ResponseEntity<ResponseDTO> getCommissionReport(
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			@RequestParam(value = "bank", required = false) String bank,
			@RequestParam(value = "merchant", required = false) String merchant) {
		ResponseDTO response = new ResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if ((authority.contains(Authorities.ADMINISTRATOR)||(authority.contains(Authorities.BANK))) && authority.contains(Authorities.AUTHENTICATED)) {
				if(authority.contains(Authorities.BANK)) {
					bank = String.valueOf(bankApi.getBankById(AuthenticationUtil.getCurrentUser().getId()));
				}
				response.setDetails(transactionApi.getCommissionTransactions(0, fromDate, toDate, bank, merchant, true)
						.getObject());
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getRevenueForreport")
	public ResponseEntity<ResponseDTO> getRevenuereport(
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			@RequestParam(value = "bank", required = false) String bank,
			@RequestParam(value = "merchant", required = false) String merchant) {
		ResponseDTO response = new ResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if ((authority.contains(Authorities.ADMINISTRATOR)||authority.contains(Authorities.BANK)) && authority.contains(Authorities.AUTHENTICATED)) {
				if(authority.contains(Authorities.BANK)) {
					bank=String.valueOf(AuthenticationUtil.getCurrentUser().getAssociatedId());
				}
				response.setDetails(transactionApi.getRevenue(fromDate, toDate, bank, merchant));
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getReversalReport")
	public ResponseEntity<RestResponseDTO> getReversalReport(
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			@RequestParam(value = "bank", required = false) String bank) {
		RestResponseDTO response = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)
					|| authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)) {
				try {
					User currentUser = AuthenticationUtil.getCurrentUser();
					if (currentUser.getUserType().equals(UserType.Bank)) {
						bank = String.valueOf(currentUser.getAssociatedId());
					}
					response.setDetail(transactionApi.getReversalReport(fromDate, toDate, bank));
					response.setMessage("Success");
					return new ResponseEntity<>(response, HttpStatus.OK);
				} catch (Exception e) {
					e.printStackTrace();
					response.setMessage("Internal Server Error");
					return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		}
		response.setMessage("Unauthorized");
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getprintdetail")
	public ResponseEntity<RestResponseDTO> getPrintDetail(@RequestParam("transactionId") String transactionId) {
		RestResponseDTO response = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)
					|| authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)) {
				try {
					response.setDetail(transactionApi.getPrinDetails(transactionId));
					response.setMessage("Success");
					return new ResponseEntity<>(response, HttpStatus.OK);
				} catch (Exception e) {
					e.printStackTrace();
					response.setMessage("Internal Server Error");
					return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		}
		response.setMessage("Unauthorized");
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/transactiondetail/channelpartner")
	public String getTransactionDetailForChannelPartner(ModelMap modelmap, HttpServletRequest request) {
		try {
			if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (authority.contains(Authorities.CHANNELPARTNER) && authority.contains(Authorities.AUTHENTICATED)) {
					modelmap.put("bankList", bankApi.getAllBank());
					return "/channelPartnerView/Transaction/detail";
				}
			}
		} catch (Exception e) {
			logger.debug("exception" + e);
			e.printStackTrace();
			return "redirect:/";
		}
		return "redirect:/";
	}
	

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;

	}

}
