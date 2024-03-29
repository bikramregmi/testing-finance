package com.wallet.serviceprovider.ntc;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "soapposapi", targetNamespace = "http://soapposapi.eservglobal.com/", wsdlLocation = "https://10.0.1.10:8443/soapposapi?wsdl")
public class Soapposapi
    extends Service
{

    private final static URL SOAPPOSAPI_WSDL_LOCATION;
    private final static WebServiceException SOAPPOSAPI_EXCEPTION;
    private final static QName SOAPPOSAPI_QNAME = new QName("http://soapposapi.eservglobal.com/", "soapposapi");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("https://10.0.1.10:8443/soapposapi?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        SOAPPOSAPI_WSDL_LOCATION = url;
        SOAPPOSAPI_EXCEPTION = e;
    }

    public Soapposapi() {
        super(__getWsdlLocation(), SOAPPOSAPI_QNAME);
    }

    public Soapposapi(WebServiceFeature... features) {
        super(__getWsdlLocation(), SOAPPOSAPI_QNAME, features);
    }

    public Soapposapi(URL wsdlLocation) {
        super(wsdlLocation, SOAPPOSAPI_QNAME);
    }

    public Soapposapi(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, SOAPPOSAPI_QNAME, features);
    }

    public Soapposapi(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public Soapposapi(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns EndPointInterface
     */
    @WebEndpoint(name = "EndPointInterfacePort")
    public EndPointInterface getEndPointInterfacePort() {
        return super.getPort(new QName("http://soapposapi.eservglobal.com/", "EndPointInterfacePort"), EndPointInterface.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns EndPointInterface
     */
    @WebEndpoint(name = "EndPointInterfacePort")
    public EndPointInterface getEndPointInterfacePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://soapposapi.eservglobal.com/", "EndPointInterfacePort"), EndPointInterface.class, features);
    }

    private static URL __getWsdlLocation() {
        if (SOAPPOSAPI_EXCEPTION!= null) {
            throw SOAPPOSAPI_EXCEPTION;
        }
        return SOAPPOSAPI_WSDL_LOCATION;
    }

}
