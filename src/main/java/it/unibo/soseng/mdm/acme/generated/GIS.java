
package it.unibo.soseng.mdm.acme.generated;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * Ladon generated service definition
 * 
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "GIS", targetNamespace = "http://tempuri.org/", wsdlLocation = "http://172.17.0.2:8888/GIS/soap/description")
public class GIS
    extends Service
{

    private final static URL GIS_WSDL_LOCATION;
    private final static WebServiceException GIS_EXCEPTION;
    private final static QName GIS_QNAME = new QName("http://tempuri.org/", "GIS");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://172.17.0.2:8888/GIS/soap/description");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        GIS_WSDL_LOCATION = url;
        GIS_EXCEPTION = e;
    }

    public GIS() {
        super(__getWsdlLocation(), GIS_QNAME);
    }

    public GIS(WebServiceFeature... features) {
        super(__getWsdlLocation(), GIS_QNAME, features);
    }

    public GIS(URL wsdlLocation) {
        super(wsdlLocation, GIS_QNAME);
    }

    public GIS(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, GIS_QNAME, features);
    }

    public GIS(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public GIS(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns GISPortType
     */
    @WebEndpoint(name = "GIS")
    public GISPortType getGIS() {
        return super.getPort(new QName("http://tempuri.org/", "GIS"), GISPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns GISPortType
     */
    @WebEndpoint(name = "GIS")
    public GISPortType getGIS(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org/", "GIS"), GISPortType.class, features);
    }

    private static URL __getWsdlLocation() {
        if (GIS_EXCEPTION!= null) {
            throw GIS_EXCEPTION;
        }
        return GIS_WSDL_LOCATION;
    }

}
