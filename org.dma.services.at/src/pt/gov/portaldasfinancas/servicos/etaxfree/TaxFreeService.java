
package pt.gov.portaldasfinancas.servicos.etaxfree;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "TaxFreeService", targetNamespace = "http://servicos.portaldasfinancas.gov.pt/eTaxFree/TaxFreeService")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface TaxFreeService {


    /**
     * 
     * @param parameters
     * @return
     *     returns pt.gov.portaldasfinancas.servicos.etaxfree.taxfreeservice.TaxFreeSubmissionResponseType
     */
    @WebMethod(operationName = "TaxFreeSubmission", action = "http://servicos.portaldasfinancas.gov.pt/eTaxFree/TaxFreeService/TaxFreeSubmission")
    @WebResult(name = "TaxFreeSubmissionResponse", targetNamespace = "http://servicos.portaldasfinancas.gov.pt/eTaxFree/TaxFreeService", partName = "parameters")
    public TaxFreeSubmissionResponseType taxFreeSubmission(
        @WebParam(name = "TaxFreeSubmissionRequest", targetNamespace = "http://servicos.portaldasfinancas.gov.pt/eTaxFree/TaxFreeService", partName = "parameters")
        TaxFreeSubmissionRequestType parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns pt.gov.portaldasfinancas.servicos.etaxfree.taxfreeservice.TaxFreeAnnulmentResponseType
     */
    @WebMethod(operationName = "TaxFreeAnnulment", action = "http://servicos.portaldasfinancas.gov.pt/eTaxFree/TaxFreeService/TaxFreeAnnulment")
    @WebResult(name = "TaxFreeAnnulmentResponse", targetNamespace = "http://servicos.portaldasfinancas.gov.pt/eTaxFree/TaxFreeService", partName = "parameters")
    public TaxFreeAnnulmentResponseType taxFreeAnnulment(
        @WebParam(name = "TaxFreeAnnulmentRequest", targetNamespace = "http://servicos.portaldasfinancas.gov.pt/eTaxFree/TaxFreeService", partName = "parameters")
        TaxFreeAnnulmentRequestType parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns pt.gov.portaldasfinancas.servicos.etaxfree.taxfreeservice.ContingencySubmissionResponseType
     */
    @WebMethod(operationName = "ContingencySubmission", action = "http://servicos.portaldasfinancas.gov.pt/eTaxFree/TaxFreeService/ContingencySubmission")
    @WebResult(name = "ContingencySubmissionResponse", targetNamespace = "http://servicos.portaldasfinancas.gov.pt/eTaxFree/TaxFreeService", partName = "parameters")
    public ContingencySubmissionResponseType contingencySubmission(
        @WebParam(name = "ContingencySubmissionRequest", targetNamespace = "http://servicos.portaldasfinancas.gov.pt/eTaxFree/TaxFreeService", partName = "parameters")
        ContingencySubmissionRequestType parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns pt.gov.portaldasfinancas.servicos.etaxfree.taxfreeservice.CertificationSubmissionResponseType
     */
    @WebMethod(operationName = "ForeignCertificationSubmission", action = "http://servicos.portaldasfinancas.gov.pt/eTaxFree/TaxFreeService/ForeignCertificationSubmission")
    @WebResult(name = "ForeignCertificationSubmissionResponse", targetNamespace = "http://servicos.portaldasfinancas.gov.pt/eTaxFree/TaxFreeService", partName = "parameters")
    public CertificationSubmissionResponseType foreignCertificationSubmission(
        @WebParam(name = "ForeignCertificationSubmissionRequest", targetNamespace = "http://servicos.portaldasfinancas.gov.pt/eTaxFree/TaxFreeService", partName = "parameters")
        ForeignCertificationSubmissionRequestType parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns pt.gov.portaldasfinancas.servicos.etaxfree.taxfreeservice.CertificationSubmissionResponseType
     */
    @WebMethod(operationName = "DomesticCertificationSubmission", action = "http://servicos.portaldasfinancas.gov.pt/eTaxFree/TaxFreeService/DomesticCertificationSubmission")
    @WebResult(name = "DomesticCertificationSubmissionResponse", targetNamespace = "http://servicos.portaldasfinancas.gov.pt/eTaxFree/TaxFreeService", partName = "parameters")
    public CertificationSubmissionResponseType domesticCertificationSubmission(
        @WebParam(name = "DomesticCertificationSubmissionRequest", targetNamespace = "http://servicos.portaldasfinancas.gov.pt/eTaxFree/TaxFreeService", partName = "parameters")
        DomesticCertificationSubmissionRequestType parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns pt.gov.portaldasfinancas.servicos.etaxfree.taxfreeservice.TaxFreeCommQueryResponseType
     */
    @WebMethod(operationName = "TaxFreeCommQuery", action = "http://servicos.portaldasfinancas.gov.pt/eTaxFree/TaxFreeService/TaxFreeCommQuery")
    @WebResult(name = "TaxFreeCommQueryResponse", targetNamespace = "http://servicos.portaldasfinancas.gov.pt/eTaxFree/TaxFreeService", partName = "parameters")
    public TaxFreeCommQueryResponseType taxFreeCommQuery(
        @WebParam(name = "TaxFreeCommQueryRequest", targetNamespace = "http://servicos.portaldasfinancas.gov.pt/eTaxFree/TaxFreeService", partName = "parameters")
        TaxFreeCommQueryRequestType parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns pt.gov.portaldasfinancas.servicos.etaxfree.taxfreeservice.TaxFreeSearchResponseType
     */
    @WebMethod(operationName = "TaxFreeSearch", action = "http://servicos.portaldasfinancas.gov.pt/eTaxFree/TaxFreeService/TaxFreeSearch")
    @WebResult(name = "TaxFreeSearchResponse", targetNamespace = "http://servicos.portaldasfinancas.gov.pt/eTaxFree/TaxFreeService", partName = "parameters")
    public TaxFreeSearchResponseType taxFreeSearch(
        @WebParam(name = "TaxFreeSearchRequest", targetNamespace = "http://servicos.portaldasfinancas.gov.pt/eTaxFree/TaxFreeService", partName = "parameters")
        TaxFreeSearchRequestType parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns pt.gov.portaldasfinancas.servicos.etaxfree.taxfreeservice.RefundQueryByRefundCompanyResponseType
     */
    @WebMethod(operationName = "RefundQueryByRefundCompany", action = "http://servicos.portaldasfinancas.gov.pt/eTaxFree/TaxFreeService/RefundQueryByRefundCompany")
    @WebResult(name = "RefundQueryByRefundCompanyResponse", targetNamespace = "http://servicos.portaldasfinancas.gov.pt/eTaxFree/TaxFreeService", partName = "parameters")
    public RefundQueryByRefundCompanyResponseType refundQueryByRefundCompany(
        @WebParam(name = "RefundQueryByRefundCompanyRequest", targetNamespace = "http://servicos.portaldasfinancas.gov.pt/eTaxFree/TaxFreeService", partName = "parameters")
        RefundQueryByRefundCompanyRequestType parameters);

}