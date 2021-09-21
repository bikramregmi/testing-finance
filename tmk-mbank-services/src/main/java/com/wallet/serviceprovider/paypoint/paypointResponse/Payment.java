package com.wallet.serviceprovider.paypoint.paypointResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name="payment")
public class Payment {
	
	@XmlElement
	 private String _j;

	@XmlElement
	    private String _billDate;
	@XmlElement
	    private String _amountfact;
	@XmlElement
	    private String _i;
	@XmlElement
	    private String _commisvalue;
	@XmlElement
	    private String _amount;
	@XmlElement
	    private String _amountstep;
	@XmlElement
	    private String _requestId;
	@XmlElement
	    private String _commission;
	@XmlElement
	    private String _fio;
	@XmlElement
	    private String _destination;
	@XmlElement
	    private String _amountmin;
	@XmlElement
	    private String _id;
	@XmlElement
	    private String _billAmount;
	@XmlElement
	    private String _show_counter;
	@XmlElement
	    private String _description;
	@XmlElement
	    private String _codserv;
	@XmlElement
	    private String _amountmask;
	@XmlElement
	    private String _amountmax;
	@XmlElement
	    private String _status;
	    public String get_j ()
	    {
	        return _j;
	    }

	    public void set_j (String _j)
	    {
	        this._j = _j;
	    }

	    public String get_billDate ()
	    {
	        return _billDate;
	    }

	    public void set_billDate (String _billDate)
	    {
	        this._billDate = _billDate;
	    }

	    public String get_amountfact ()
	    {
	        return _amountfact;
	    }

	    public void set_amountfact (String _amountfact)
	    {
	        this._amountfact = _amountfact;
	    }

	    public String get_i ()
	    {
	        return _i;
	    }

	    public void set_i (String _i)
	    {
	        this._i = _i;
	    }

	    public String get_commisvalue ()
	    {
	        return _commisvalue;
	    }

	    public void set_commisvalue (String _commisvalue)
	    {
	        this._commisvalue = _commisvalue;
	    }

	    public String get_amount ()
	    {
	        return _amount;
	    }

	    public void set_amount (String _amount)
	    {
	        this._amount = _amount;
	    }

	    public String get_amountstep ()
	    {
	        return _amountstep;
	    }

	    public void set_amountstep (String _amountstep)
	    {
	        this._amountstep = _amountstep;
	    }

	    public String get_requestId ()
	    {
	        return _requestId;
	    }

	    public void set_requestId (String _requestId)
	    {
	        this._requestId = _requestId;
	    }

	    public String get_commission ()
	    {
	        return _commission;
	    }

	    public void set_commission (String _commission)
	    {
	        this._commission = _commission;
	    }

	    public String get_fio ()
	    {
	        return _fio;
	    }

	    public void set_fio (String _fio)
	    {
	        this._fio = _fio;
	    }

	    public String get_destination ()
	    {
	        return _destination;
	    }

	    public void set_destination (String _destination)
	    {
	        this._destination = _destination;
	    }

	    public String get_amountmin ()
	    {
	        return _amountmin;
	    }

	    public void set_amountmin (String _amountmin)
	    {
	        this._amountmin = _amountmin;
	    }

	    public String get_id ()
	    {
	        return _id;
	    }

	    public void set_id (String _id)
	    {
	        this._id = _id;
	    }

	    public String get_billAmount ()
	    {
	        return _billAmount;
	    }

	    public void set_billAmount (String _billAmount)
	    {
	        this._billAmount = _billAmount;
	    }

	    public String get_show_counter ()
	    {
	        return _show_counter;
	    }

	    public void set_show_counter (String _show_counter)
	    {
	        this._show_counter = _show_counter;
	    }

	    public String get_description ()
	    {
	        return _description;
	    }

	    public void set_description (String _description)
	    {
	        this._description = _description;
	    }

	    public String get_codserv ()
	    {
	        return _codserv;
	    }

	    public void set_codserv (String _codserv)
	    {
	        this._codserv = _codserv;
	    }

	    public String get_amountmask ()
	    {
	        return _amountmask;
	    }

	    public void set_amountmask (String _amountmask)
	    {
	        this._amountmask = _amountmask;
	    }

	    public String get_amountmax ()
	    {
	        return _amountmax;
	    }

	    public void set_amountmax (String _amountmax)
	    {
	        this._amountmax = _amountmax;
	    }

	    public String get_status ()
	    {
	        return _status;
	    }

	    public void set_status (String _status)
	    {
	        this._status = _status;
	    }
}
