
package it.unibo.soseng.mdm.acme.generated.bank;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "BankPort", targetNamespace = "bank.acme.conference.com.wsdl")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface BankPort {


    /**
     * 
     * @param body
     * @return
     *     returns it.unibo.soseng.mdm.acme.generated.bank.UserLoginResponse
     */
    @WebMethod(action = "userLogin")
    @WebResult(name = "userLoginResponse", targetNamespace = "bank.acme.conference.com.xsd", partName = "body")
    public UserLoginResponse userLogin(
        @WebParam(name = "userLogin", targetNamespace = "bank.acme.conference.com.xsd", partName = "body")
        UserLogin body);

    /**
     * 
     * @param body
     * @return
     *     returns it.unibo.soseng.mdm.acme.generated.bank.UserLogoutResponse
     */
    @WebMethod(action = "userLogout")
    @WebResult(name = "userLogoutResponse", targetNamespace = "bank.acme.conference.com.xsd", partName = "body")
    public UserLogoutResponse userLogout(
        @WebParam(name = "userLogout", targetNamespace = "bank.acme.conference.com.xsd", partName = "body")
        UserLogout body);

    /**
     * 
     * @param body
     * @return
     *     returns it.unibo.soseng.mdm.acme.generated.bank.TransferPaymentResponse
     */
    @WebMethod(action = "transferPayment")
    @WebResult(name = "transferPaymentResponse", targetNamespace = "bank.acme.conference.com.xsd", partName = "body")
    public TransferPaymentResponse transferPayment(
        @WebParam(name = "transferPayment", targetNamespace = "bank.acme.conference.com.xsd", partName = "body")
        TransferPayment body);

}
