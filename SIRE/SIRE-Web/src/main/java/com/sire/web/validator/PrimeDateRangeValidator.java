package com.sire.web.validator;

import com.sire.utils.FacesMessageUtil;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.primefaces.component.calendar.Calendar;

@FacesValidator("primeDateRangeValidator")
public class PrimeDateRangeValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            return;
        }

        //Leave the null handling of fechaDocumento to required="true"
        Object fechaDocumentoValue = component.getAttributes().get("fechaDocumento");
        if (fechaDocumentoValue == null) {
            return;
        }

        Date fechaDocumento = (Date) fechaDocumentoValue;
        java.util.Calendar c = java.util.Calendar.getInstance();
        if (fechaDocumento.after(c.getTime())) {
            throw new ValidatorException(
                    FacesMessageUtil.newBundledFacesMessage(FacesMessage.SEVERITY_ERROR, "", "msg.dateRange", ((Calendar) component).getLabel(), c.getTime()));
        }
    }
}
