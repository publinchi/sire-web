package ec.gob.sri.comprobantes.modelo;

import ec.gob.sri.comprobantes.modelo.factura.Factura;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoteXml {

    private String version;
    private String claveAcceso;
    private String ruc;
//    private List<ComprobanteXml> comprobantes;
    private List comprobantes; // TODO: Deprecar

    public LoteXml() {
        this.comprobantes = new ArrayList();
    }

    public String getClaveAcceso() {
        return this.claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

//    public List<ComprobanteXml> getComprobantes() {
//        return this.comprobantes;
//    }
//    public void setComprobantes(List<ComprobanteXml> comprobantes) {
//        this.comprobantes = comprobantes;
//    }
    public String getRuc() {
        return this.ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List getComprobantes() {
        return comprobantes;
    }

    public void setComprobantes(List comprobantes) {
        this.comprobantes = comprobantes;
    }

}
