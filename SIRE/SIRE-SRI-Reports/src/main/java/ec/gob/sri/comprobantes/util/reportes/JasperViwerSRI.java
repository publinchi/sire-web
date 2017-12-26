package ec.gob.sri.comprobantes.util.reportes;

import java.awt.event.ActionEvent;
import java.io.InputStream;
import java.util.Locale;
import java.util.ResourceBundle;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JRViewer;

public class JasperViwerSRI
        extends JRViewer {

    public JasperViwerSRI(JasperPrint jrPrint) {
        super(jrPrint);
    }

    public JasperViwerSRI(JasperPrint jrPrint, Locale locale) {
        super(jrPrint, locale);
    }

    public JasperViwerSRI(InputStream is, boolean isXML)
            throws JRException {
        super(is, isXML);
    }

    public JasperViwerSRI(String fileName, boolean isXML, Locale locale)
            throws JRException {
        super(fileName, isXML, locale);
    }

    public JasperViwerSRI(JasperPrint jrPrint, Locale locale, ResourceBundle resBundle) {
        super(jrPrint, locale, resBundle);
    }

    void btnNextActionPerformed(ActionEvent evt) {
        throw new RuntimeException("Compiled Code");
    }
}
