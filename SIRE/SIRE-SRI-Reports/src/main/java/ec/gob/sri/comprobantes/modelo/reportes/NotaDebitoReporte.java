package ec.gob.sri.comprobantes.modelo.reportes;

import ec.gob.sri.comprobantes.administracion.modelo.Compensaciones;
import ec.gob.sri.comprobantes.administracion.modelo.ImpuestoValor;
import ec.gob.sri.comprobantes.modelo.notadebito.Impuesto;
import ec.gob.sri.comprobantes.modelo.notadebito.NotaDebito;
import ec.gob.sri.comprobantes.modelo.notadebito.NotaDebito.InfoAdicional;
import ec.gob.sri.comprobantes.modelo.notadebito.NotaDebito.InfoAdicional.CampoAdicional;
import ec.gob.sri.comprobantes.modelo.notadebito.NotaDebito.InfoNotaDebito;
import ec.gob.sri.comprobantes.modelo.notadebito.NotaDebito.InfoNotaDebito.Impuestos;
import ec.gob.sri.comprobantes.modelo.notadebito.NotaDebito.InfoNotaDebito.Pago;
import ec.gob.sri.comprobantes.modelo.notadebito.NotaDebito.InfoNotaDebito.Pago.DetallePago;
import ec.gob.sri.comprobantes.modelo.notadebito.NotaDebito.InfoNotaDebito.compensacion;
import ec.gob.sri.comprobantes.modelo.notadebito.NotaDebito.InfoNotaDebito.compensacion.detalleCompensaciones;
import ec.gob.sri.comprobantes.modelo.notadebito.NotaDebito.Motivos;
import ec.gob.sri.comprobantes.modelo.notadebito.NotaDebito.Motivos.Motivo;
import ec.gob.sri.comprobantes.sql.CompensacionSQL;
import ec.gob.sri.comprobantes.sql.FormasPagoSQL;
import ec.gob.sri.comprobantes.sql.ImpuestoValorSQL;
import ec.gob.sri.comprobantes.util.TipoImpuestoEnum;
import ec.gob.sri.comprobantes.util.TipoImpuestoIvaEnum;
import ec.gob.sri.comprobantes.util.reportes.ReporteUtil;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NotaDebitoReporte {

    private NotaDebito notaDebito;
    private List<DetallesAdicionalesReporte> detallesAdiciones;
    private List<InformacionAdicional> infoAdicional;
    private List<FormasPago> formasPago;
    private List<TotalesComprobante> totalesComprobante;

    public NotaDebitoReporte(NotaDebito notaDebito) {
        this.notaDebito = notaDebito;
    }

    public List<DetallesAdicionalesReporte> getDetallesAdiciones()
            throws SQLException, ClassNotFoundException {
        this.detallesAdiciones = new ArrayList();
        for (NotaDebito.Motivos.Motivo motivo : this.notaDebito.getMotivos().getMotivo()) {
            DetallesAdicionalesReporte detAd = new DetallesAdicionalesReporte();
            detAd.setRazonModificacion(motivo.getRazon());
            detAd.setValorModificacion(motivo.getValor().toString());
            detAd.setInfoAdicional(getInfoAdicional());
            if (getFormasPago() != null) {
                detAd.setFormasPago(getFormasPago());
            }
            detAd.setTotalesComprobante(getTotalesComprobante());
            this.detallesAdiciones.add(detAd);
        }
        return this.detallesAdiciones;
    }

    private String obtenerDetalleFormaPago(String codigo) {
        FormasPagoSQL formasPagoSQL = new FormasPagoSQL();
        List<ec.gob.sri.comprobantes.administracion.modelo.FormasPago> formaPago = new ArrayList();
        try {
            formaPago = formasPagoSQL.obtenerDescripcionFormasPago(codigo);
            if (formaPago != null) {
                return ((ec.gob.sri.comprobantes.administracion.modelo.FormasPago) formaPago.get(0)).getDescripcion();
            }
            return "";
        } catch (SQLException ex) {
            Logger.getLogger(FacturaReporte.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FacturaReporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public void setDetallesAdiciones(List<DetallesAdicionalesReporte> detallesAdiciones) {
        this.detallesAdiciones = detallesAdiciones;
    }

    public NotaDebito getNotaDebito() {
        return this.notaDebito;
    }

    public void setNotaDebito(NotaDebito notaDebito) {
        this.notaDebito = notaDebito;
    }

    public List<InformacionAdicional> getInfoAdicional() {
        if ((this.notaDebito.getInfoAdicional() != null) && (!this.notaDebito.getInfoAdicional().getCampoAdicional().isEmpty())) {
            this.infoAdicional = new ArrayList();
            for (NotaDebito.InfoAdicional.CampoAdicional info : this.notaDebito.getInfoAdicional().getCampoAdicional()) {
                InformacionAdicional ia = new InformacionAdicional(info.getValue(), info.getNombre());
                this.infoAdicional.add(ia);
            }
        }
        return this.infoAdicional;
    }

    public void setInfoAdicional(List<InformacionAdicional> infoAdicional) {
        this.infoAdicional = infoAdicional;
    }

    public List<FormasPago> getFormasPago() {
        System.out.println("--->" + getNotaDebito());
        if (getNotaDebito().getInfoNotaDebito().getPagos() != null) {
            this.formasPago = new ArrayList();
            if ((getNotaDebito().getInfoNotaDebito().getPagos().getPagos() != null) && (!getNotaDebito().getInfoNotaDebito().getPagos().getPagos().isEmpty())) {
                for (NotaDebito.InfoNotaDebito.Pago.DetallePago pa : getNotaDebito().getInfoNotaDebito().getPagos().getPagos()) {
                    this.formasPago.add(new FormasPago(obtenerDetalleFormaPago(pa.getFormaPago()), pa.getTotal().setScale(2).toString()));
                }
            }
        }
        return this.formasPago;
    }

    public void setFormasPago(List<FormasPago> formasPago) {
        this.formasPago = formasPago;
    }

    public List<TotalesComprobante> getTotalesComprobante()
            throws SQLException, ClassNotFoundException {
        this.totalesComprobante = new ArrayList();
        BigDecimal importeTotal = BigDecimal.ZERO.setScale(2);
        BigDecimal compensaciones = BigDecimal.ZERO.setScale(2);
        TotalComprobante tc = getTotalesND(this.notaDebito.getInfoNotaDebito());
        for (IvaDiferenteCeroReporte iva : tc.getIvaDistintoCero()) {
            this.totalesComprobante.add(new TotalesComprobante("SUBTOTAL " + iva.getTarifa() + "%", iva.getSubtotal(), false));
        }
        this.totalesComprobante.add(new TotalesComprobante("SUBTOTAL IVA 0%", tc.getSubtotal0(), false));
        this.totalesComprobante.add(new TotalesComprobante("SUBTOTAL NO OBJETO IVA", tc.getSubtotalNoSujetoIva(), false));
        this.totalesComprobante.add(new TotalesComprobante("SUBTOTAL EXENTO IVA", tc.getSubtotalExentoIVA(), false));
        this.totalesComprobante.add(new TotalesComprobante("SUBTOTAL SIN IMPUESTOS", this.notaDebito.getInfoNotaDebito().getTotalSinImpuestos(), false));
        this.totalesComprobante.add(new TotalesComprobante("ICE", tc.getTotalIce(), false));
        for (IvaDiferenteCeroReporte iva : tc.getIvaDistintoCero()) {
            this.totalesComprobante.add(new TotalesComprobante("IVA " + iva.getTarifa() + "%", iva.getValor(), false));
        }
        this.totalesComprobante.add(new TotalesComprobante("IRBPNR", tc.getTotalIRBPNR(), false));
        if (this.notaDebito.getInfoNotaDebito().getCompensaciones() != null) {
            for (NotaDebito.InfoNotaDebito.compensacion.detalleCompensaciones compensacion : this.notaDebito.getInfoNotaDebito().getCompensaciones().getCompensaciones()) {
                compensaciones = compensaciones.add(compensacion.getValor());
            }
            importeTotal = this.notaDebito.getInfoNotaDebito().getValorTotal().add(compensaciones);
        }
        if (!compensaciones.equals(BigDecimal.ZERO.setScale(2))) {
            this.totalesComprobante.add(new TotalesComprobante("VALOR TOTAL", importeTotal, false));
            for (NotaDebito.InfoNotaDebito.compensacion.detalleCompensaciones compensacion : this.notaDebito.getInfoNotaDebito().getCompensaciones().getCompensaciones()) {
                if (!compensacion.getValor().equals(BigDecimal.ZERO.setScale(2))) {
                    CompensacionSQL compensacionSQL = new CompensacionSQL();
                    String detalleCompensacion = ((Compensaciones) compensacionSQL.obtenerCompensacionesPorCodigo(compensacion.getCodigo()).get(0)).getTipoCompensacion();
                    this.totalesComprobante.add(new TotalesComprobante("(-) " + detalleCompensacion, compensacion.getValor(), true));
                }
            }
            this.totalesComprobante.add(new TotalesComprobante("VALOR A PAGAR", this.notaDebito.getInfoNotaDebito().getValorTotal(), false));
        } else {
            this.totalesComprobante.add(new TotalesComprobante("VALOR TOTAL", this.notaDebito.getInfoNotaDebito().getValorTotal(), false));
        }
        return this.totalesComprobante;
    }

    private TotalComprobante getTotalesND(NotaDebito.InfoNotaDebito infoNotaDebito) {
        List<IvaDiferenteCeroReporte> ivaDiferenteCero = new ArrayList();

        BigDecimal totalIva = new BigDecimal(0.0D);
        BigDecimal totalIva0 = new BigDecimal(0.0D);
        BigDecimal totalICE = new BigDecimal(0.0D);
        BigDecimal totalExentoIVA = new BigDecimal(0.0D);
        BigDecimal totalSinImpuesto = new BigDecimal(0.0D);
        TotalComprobante tc = new TotalComprobante();
        for (Impuesto ti : infoNotaDebito.getImpuestos().getImpuesto()) {
            Integer cod = new Integer(ti.getCodigo());
            if ((TipoImpuestoEnum.IVA.getCode() == cod.intValue()) && (ti.getValor().doubleValue() > 0.0D)) {
                String codigoPorcentaje = obtenerPorcentajeIvaVigente(ti.getCodigoPorcentaje());
                IvaDiferenteCeroReporte iva = new IvaDiferenteCeroReporte(ti.getBaseImponible(), codigoPorcentaje, ti.getValor());
                ivaDiferenteCero.add(iva);
            }
            if ((TipoImpuestoEnum.IVA.getCode() == cod.intValue()) && (TipoImpuestoIvaEnum.IVA_VENTA_0.getCode().equals(ti.getCodigoPorcentaje()))) {
                totalIva0 = totalIva0.add(ti.getBaseImponible());
            }
            if ((TipoImpuestoEnum.IVA.getCode() == cod.intValue()) && (TipoImpuestoIvaEnum.IVA_NO_OBJETO.getCode().equals(ti.getCodigoPorcentaje()))) {
                totalSinImpuesto = totalSinImpuesto.add(ti.getBaseImponible());
            }
            if ((TipoImpuestoEnum.IVA.getCode() == cod.intValue()) && (TipoImpuestoIvaEnum.IVA_EXCENTO.getCode().equals(ti.getCodigoPorcentaje()))) {
                totalExentoIVA = totalExentoIVA.add(ti.getBaseImponible());
            }
            if (TipoImpuestoEnum.ICE.getCode() == cod.intValue()) {
                totalICE = totalICE.add(ti.getValor());
            }
        }
        tc.setSubtotal0(totalIva0);
        tc.setSubtotal(totalIva.add(totalIva0).add(totalExentoIVA).add(totalSinImpuesto).setScale(2));
        tc.setTotalIce(totalICE);
        if (ivaDiferenteCero.isEmpty()) {
            ivaDiferenteCero.add(LlenaIvaDiferenteCero());
        }
        tc.setIvaDistintoCero(ivaDiferenteCero);
        tc.setSubtotalExentoIVA(totalExentoIVA.setScale(2));
        tc.setSubtotalNoSujetoIva(totalSinImpuesto);
        return tc;
    }

    private IvaDiferenteCeroReporte LlenaIvaDiferenteCero() {
        BigDecimal valor = BigDecimal.ZERO.setScale(2);
        String porcentajeIva = ObtieneIvaRideFactura(this.notaDebito.getInfoNotaDebito().getImpuestos(), DeStringADate(this.notaDebito.getInfoNotaDebito().getFechaEmision()));
        return new IvaDiferenteCeroReporte(valor, porcentajeIva, valor);
    }

    public Date DeStringADate(String fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String strFecha = fecha;
        Date fechaDate = null;
        try {
            return formato.parse(strFecha);
        } catch (ParseException ex) {
        }
        return fechaDate;
    }

    private String ObtieneIvaRideFactura(NotaDebito.InfoNotaDebito.Impuestos impuestos, Date fecha) {
        for (Impuesto impuesto : impuestos.getImpuesto()) {
            Integer cod = new Integer(impuesto.getCodigo());
            if ((TipoImpuestoEnum.IVA.getCode() == cod.intValue()) && (impuesto.getValor().doubleValue() > 0.0D)) {
                return obtenerPorcentajeIvaVigente(impuesto.getCodigoPorcentaje());
            }
        }
        return obtenerPorcentajeIvaVigente(fecha).toString();
    }

    private String obtenerPorcentajeIvaVigente(String cod) {
        try {
            ImpuestoValorSQL impvalorSQL = new ImpuestoValorSQL();
            BigDecimal porcentaje = BigDecimal.valueOf(((ImpuestoValor) impvalorSQL.obtenerDatosIvaCodigoPorcentaje(cod).get(0)).getPorcentaje().doubleValue());
            return porcentaje.setScale(0).toString();
        } catch (SQLException ex) {
            Logger.getLogger(ReporteUtil.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReporteUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    private String obtenerPorcentajeIvaVigente(Date fechaEmision) {
        try {
            ImpuestoValorSQL impvalorSQL = new ImpuestoValorSQL();
            BigDecimal porcentaje = BigDecimal.valueOf(((ImpuestoValor) impvalorSQL.obtenerDatosIvaVigente(fechaEmision).get(0)).getPorcentaje().doubleValue());
            return porcentaje.setScale(0).toString();
        } catch (SQLException ex) {
            Logger.getLogger(ReporteUtil.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReporteUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
}
