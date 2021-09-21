
package com.wallet.serviceprovider.unitedsolution;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.us.booking package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CancelReservation_QNAME = new QName("http://booking.us.org/", "CancelReservation");
    private final static QName _CancelReservationResponse_QNAME = new QName("http://booking.us.org/", "CancelReservationResponse");
    private final static QName _CheckBalance_QNAME = new QName("http://booking.us.org/", "CheckBalance");
    private final static QName _CheckBalanceResponse_QNAME = new QName("http://booking.us.org/", "CheckBalanceResponse");
    private final static QName _FlightAvailability_QNAME = new QName("http://booking.us.org/", "FlightAvailability");
    private final static QName _FlightAvailabilityResponse_QNAME = new QName("http://booking.us.org/", "FlightAvailabilityResponse");
    private final static QName _GetFlightDetail_QNAME = new QName("http://booking.us.org/", "GetFlightDetail");
    private final static QName _GetFlightDetailResponse_QNAME = new QName("http://booking.us.org/", "GetFlightDetailResponse");
    private final static QName _GetItinerary_QNAME = new QName("http://booking.us.org/", "GetItinerary");
    private final static QName _GetItineraryResponse_QNAME = new QName("http://booking.us.org/", "GetItineraryResponse");
    private final static QName _GetPnrDetail_QNAME = new QName("http://booking.us.org/", "GetPnrDetail");
    private final static QName _GetPnrDetailResponse_QNAME = new QName("http://booking.us.org/", "GetPnrDetailResponse");
    private final static QName _IssueTicket_QNAME = new QName("http://booking.us.org/", "IssueTicket");
    private final static QName _IssueTicketB2B_QNAME = new QName("http://booking.us.org/", "IssueTicketB2B");
    private final static QName _IssueTicketB2BResponse_QNAME = new QName("http://booking.us.org/", "IssueTicketB2BResponse");
    private final static QName _IssueTicketResponse_QNAME = new QName("http://booking.us.org/", "IssueTicketResponse");
    private final static QName _Refund_QNAME = new QName("http://booking.us.org/", "Refund");
    private final static QName _RefundResponse_QNAME = new QName("http://booking.us.org/", "RefundResponse");
    private final static QName _Reschedule_QNAME = new QName("http://booking.us.org/", "Reschedule");
    private final static QName _RescheduleResponse_QNAME = new QName("http://booking.us.org/", "RescheduleResponse");
    private final static QName _RescheduleSave_QNAME = new QName("http://booking.us.org/", "RescheduleSave");
    private final static QName _RescheduleSaveResponse_QNAME = new QName("http://booking.us.org/", "RescheduleSaveResponse");
    private final static QName _Reservation_QNAME = new QName("http://booking.us.org/", "Reservation");
    private final static QName _ReservationResponse_QNAME = new QName("http://booking.us.org/", "ReservationResponse");
    private final static QName _SalesReport_QNAME = new QName("http://booking.us.org/", "SalesReport");
    private final static QName _SalesReportResponse_QNAME = new QName("http://booking.us.org/", "SalesReportResponse");
    private final static QName _SectorCode_QNAME = new QName("http://booking.us.org/", "SectorCode");
    private final static QName _SectorCodeResponse_QNAME = new QName("http://booking.us.org/", "SectorCodeResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.us.booking
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CancelReservation }
     * 
     */
    public CancelReservation createCancelReservation() {
        return new CancelReservation();
    }

    /**
     * Create an instance of {@link CancelReservationResponse }
     * 
     */
    public CancelReservationResponse createCancelReservationResponse() {
        return new CancelReservationResponse();
    }

    /**
     * Create an instance of {@link CheckBalance }
     * 
     */
    public CheckBalance createCheckBalance() {
        return new CheckBalance();
    }

    /**
     * Create an instance of {@link CheckBalanceResponse }
     * 
     */
    public CheckBalanceResponse createCheckBalanceResponse() {
        return new CheckBalanceResponse();
    }

    /**
     * Create an instance of {@link FlightAvailability }
     * 
     */
    public FlightAvailability createFlightAvailability() {
        return new FlightAvailability();
    }

    /**
     * Create an instance of {@link FlightAvailabilityResponse }
     * 
     */
    public FlightAvailabilityResponse createFlightAvailabilityResponse() {
        return new FlightAvailabilityResponse();
    }

    /**
     * Create an instance of {@link GetFlightDetail }
     * 
     */
    public GetFlightDetail createGetFlightDetail() {
        return new GetFlightDetail();
    }

    /**
     * Create an instance of {@link GetFlightDetailResponse }
     * 
     */
    public GetFlightDetailResponse createGetFlightDetailResponse() {
        return new GetFlightDetailResponse();
    }

    /**
     * Create an instance of {@link GetItinerary }
     * 
     */
    public GetItinerary createGetItinerary() {
        return new GetItinerary();
    }

    /**
     * Create an instance of {@link GetItineraryResponse }
     * 
     */
    public GetItineraryResponse createGetItineraryResponse() {
        return new GetItineraryResponse();
    }

    /**
     * Create an instance of {@link GetPnrDetail }
     * 
     */
    public GetPnrDetail createGetPnrDetail() {
        return new GetPnrDetail();
    }

    /**
     * Create an instance of {@link GetPnrDetailResponse }
     * 
     */
    public GetPnrDetailResponse createGetPnrDetailResponse() {
        return new GetPnrDetailResponse();
    }

    /**
     * Create an instance of {@link IssueTicket }
     * 
     */
    public IssueTicket createIssueTicket() {
        return new IssueTicket();
    }

    /**
     * Create an instance of {@link IssueTicketB2B }
     * 
     */
    public IssueTicketB2B createIssueTicketB2B() {
        return new IssueTicketB2B();
    }

    /**
     * Create an instance of {@link IssueTicketB2BResponse }
     * 
     */
    public IssueTicketB2BResponse createIssueTicketB2BResponse() {
        return new IssueTicketB2BResponse();
    }

    /**
     * Create an instance of {@link IssueTicketResponse }
     * 
     */
    public IssueTicketResponse createIssueTicketResponse() {
        return new IssueTicketResponse();
    }

    /**
     * Create an instance of {@link Refund }
     * 
     */
    public Refund createRefund() {
        return new Refund();
    }

    /**
     * Create an instance of {@link RefundResponse }
     * 
     */
    public RefundResponse createRefundResponse() {
        return new RefundResponse();
    }

    /**
     * Create an instance of {@link Reschedule }
     * 
     */
    public Reschedule createReschedule() {
        return new Reschedule();
    }

    /**
     * Create an instance of {@link RescheduleResponse }
     * 
     */
    public RescheduleResponse createRescheduleResponse() {
        return new RescheduleResponse();
    }

    /**
     * Create an instance of {@link RescheduleSave }
     * 
     */
    public RescheduleSave createRescheduleSave() {
        return new RescheduleSave();
    }

    /**
     * Create an instance of {@link RescheduleSaveResponse }
     * 
     */
    public RescheduleSaveResponse createRescheduleSaveResponse() {
        return new RescheduleSaveResponse();
    }

    /**
     * Create an instance of {@link Reservation }
     * 
     */
    public Reservation createReservation() {
        return new Reservation();
    }

    /**
     * Create an instance of {@link ReservationResponse }
     * 
     */
    public ReservationResponse createReservationResponse() {
        return new ReservationResponse();
    }

    /**
     * Create an instance of {@link SalesReport }
     * 
     */
    public SalesReport createSalesReport() {
        return new SalesReport();
    }

    /**
     * Create an instance of {@link SalesReportResponse }
     * 
     */
    public SalesReportResponse createSalesReportResponse() {
        return new SalesReportResponse();
    }

    /**
     * Create an instance of {@link SectorCode }
     * 
     */
    public SectorCode createSectorCode() {
        return new SectorCode();
    }

    /**
     * Create an instance of {@link SectorCodeResponse }
     * 
     */
    public SectorCodeResponse createSectorCodeResponse() {
        return new SectorCodeResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelReservation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://booking.us.org/", name = "CancelReservation")
    public JAXBElement<CancelReservation> createCancelReservation(CancelReservation value) {
        return new JAXBElement<CancelReservation>(_CancelReservation_QNAME, CancelReservation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelReservationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://booking.us.org/", name = "CancelReservationResponse")
    public JAXBElement<CancelReservationResponse> createCancelReservationResponse(CancelReservationResponse value) {
        return new JAXBElement<CancelReservationResponse>(_CancelReservationResponse_QNAME, CancelReservationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckBalance }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://booking.us.org/", name = "CheckBalance")
    public JAXBElement<CheckBalance> createCheckBalance(CheckBalance value) {
        return new JAXBElement<CheckBalance>(_CheckBalance_QNAME, CheckBalance.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckBalanceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://booking.us.org/", name = "CheckBalanceResponse")
    public JAXBElement<CheckBalanceResponse> createCheckBalanceResponse(CheckBalanceResponse value) {
        return new JAXBElement<CheckBalanceResponse>(_CheckBalanceResponse_QNAME, CheckBalanceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FlightAvailability }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://booking.us.org/", name = "FlightAvailability")
    public JAXBElement<FlightAvailability> createFlightAvailability(FlightAvailability value) {
        return new JAXBElement<FlightAvailability>(_FlightAvailability_QNAME, FlightAvailability.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FlightAvailabilityResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://booking.us.org/", name = "FlightAvailabilityResponse")
    public JAXBElement<FlightAvailabilityResponse> createFlightAvailabilityResponse(FlightAvailabilityResponse value) {
        return new JAXBElement<FlightAvailabilityResponse>(_FlightAvailabilityResponse_QNAME, FlightAvailabilityResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFlightDetail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://booking.us.org/", name = "GetFlightDetail")
    public JAXBElement<GetFlightDetail> createGetFlightDetail(GetFlightDetail value) {
        return new JAXBElement<GetFlightDetail>(_GetFlightDetail_QNAME, GetFlightDetail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFlightDetailResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://booking.us.org/", name = "GetFlightDetailResponse")
    public JAXBElement<GetFlightDetailResponse> createGetFlightDetailResponse(GetFlightDetailResponse value) {
        return new JAXBElement<GetFlightDetailResponse>(_GetFlightDetailResponse_QNAME, GetFlightDetailResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetItinerary }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://booking.us.org/", name = "GetItinerary")
    public JAXBElement<GetItinerary> createGetItinerary(GetItinerary value) {
        return new JAXBElement<GetItinerary>(_GetItinerary_QNAME, GetItinerary.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetItineraryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://booking.us.org/", name = "GetItineraryResponse")
    public JAXBElement<GetItineraryResponse> createGetItineraryResponse(GetItineraryResponse value) {
        return new JAXBElement<GetItineraryResponse>(_GetItineraryResponse_QNAME, GetItineraryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPnrDetail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://booking.us.org/", name = "GetPnrDetail")
    public JAXBElement<GetPnrDetail> createGetPnrDetail(GetPnrDetail value) {
        return new JAXBElement<GetPnrDetail>(_GetPnrDetail_QNAME, GetPnrDetail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPnrDetailResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://booking.us.org/", name = "GetPnrDetailResponse")
    public JAXBElement<GetPnrDetailResponse> createGetPnrDetailResponse(GetPnrDetailResponse value) {
        return new JAXBElement<GetPnrDetailResponse>(_GetPnrDetailResponse_QNAME, GetPnrDetailResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IssueTicket }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://booking.us.org/", name = "IssueTicket")
    public JAXBElement<IssueTicket> createIssueTicket(IssueTicket value) {
        return new JAXBElement<IssueTicket>(_IssueTicket_QNAME, IssueTicket.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IssueTicketB2B }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://booking.us.org/", name = "IssueTicketB2B")
    public JAXBElement<IssueTicketB2B> createIssueTicketB2B(IssueTicketB2B value) {
        return new JAXBElement<IssueTicketB2B>(_IssueTicketB2B_QNAME, IssueTicketB2B.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IssueTicketB2BResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://booking.us.org/", name = "IssueTicketB2BResponse")
    public JAXBElement<IssueTicketB2BResponse> createIssueTicketB2BResponse(IssueTicketB2BResponse value) {
        return new JAXBElement<IssueTicketB2BResponse>(_IssueTicketB2BResponse_QNAME, IssueTicketB2BResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IssueTicketResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://booking.us.org/", name = "IssueTicketResponse")
    public JAXBElement<IssueTicketResponse> createIssueTicketResponse(IssueTicketResponse value) {
        return new JAXBElement<IssueTicketResponse>(_IssueTicketResponse_QNAME, IssueTicketResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Refund }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://booking.us.org/", name = "Refund")
    public JAXBElement<Refund> createRefund(Refund value) {
        return new JAXBElement<Refund>(_Refund_QNAME, Refund.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RefundResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://booking.us.org/", name = "RefundResponse")
    public JAXBElement<RefundResponse> createRefundResponse(RefundResponse value) {
        return new JAXBElement<RefundResponse>(_RefundResponse_QNAME, RefundResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Reschedule }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://booking.us.org/", name = "Reschedule")
    public JAXBElement<Reschedule> createReschedule(Reschedule value) {
        return new JAXBElement<Reschedule>(_Reschedule_QNAME, Reschedule.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RescheduleResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://booking.us.org/", name = "RescheduleResponse")
    public JAXBElement<RescheduleResponse> createRescheduleResponse(RescheduleResponse value) {
        return new JAXBElement<RescheduleResponse>(_RescheduleResponse_QNAME, RescheduleResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RescheduleSave }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://booking.us.org/", name = "RescheduleSave")
    public JAXBElement<RescheduleSave> createRescheduleSave(RescheduleSave value) {
        return new JAXBElement<RescheduleSave>(_RescheduleSave_QNAME, RescheduleSave.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RescheduleSaveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://booking.us.org/", name = "RescheduleSaveResponse")
    public JAXBElement<RescheduleSaveResponse> createRescheduleSaveResponse(RescheduleSaveResponse value) {
        return new JAXBElement<RescheduleSaveResponse>(_RescheduleSaveResponse_QNAME, RescheduleSaveResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Reservation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://booking.us.org/", name = "Reservation")
    public JAXBElement<Reservation> createReservation(Reservation value) {
        return new JAXBElement<Reservation>(_Reservation_QNAME, Reservation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReservationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://booking.us.org/", name = "ReservationResponse")
    public JAXBElement<ReservationResponse> createReservationResponse(ReservationResponse value) {
        return new JAXBElement<ReservationResponse>(_ReservationResponse_QNAME, ReservationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SalesReport }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://booking.us.org/", name = "SalesReport")
    public JAXBElement<SalesReport> createSalesReport(SalesReport value) {
        return new JAXBElement<SalesReport>(_SalesReport_QNAME, SalesReport.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SalesReportResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://booking.us.org/", name = "SalesReportResponse")
    public JAXBElement<SalesReportResponse> createSalesReportResponse(SalesReportResponse value) {
        return new JAXBElement<SalesReportResponse>(_SalesReportResponse_QNAME, SalesReportResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SectorCode }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://booking.us.org/", name = "SectorCode")
    public JAXBElement<SectorCode> createSectorCode(SectorCode value) {
        return new JAXBElement<SectorCode>(_SectorCode_QNAME, SectorCode.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SectorCodeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://booking.us.org/", name = "SectorCodeResponse")
    public JAXBElement<SectorCodeResponse> createSectorCodeResponse(SectorCodeResponse value) {
        return new JAXBElement<SectorCodeResponse>(_SectorCodeResponse_QNAME, SectorCodeResponse.class, null, value);
    }

}
