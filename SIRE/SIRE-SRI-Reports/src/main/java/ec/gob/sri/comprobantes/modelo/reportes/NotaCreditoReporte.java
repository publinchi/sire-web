package ec.gob.sri.comprobantes.modelo.reportes;

import ec.gob.sri.comprobantes.administracion.modelo.Compensaciones;
import ec.gob.sri.comprobantes.administracion.modelo.ImpuestoValor;
import ec.gob.sri.comprobantes.modelo.notacredito.NotaCredito;
import ec.gob.sri.comprobantes.modelo.notacredito.NotaCredito.Detalles;
import ec.gob.sri.comprobantes.modelo.notacredito.NotaCredito.Detalles.Detalle;
import ec.gob.sri.comprobantes.modelo.notacredito.NotaCredito.Detalles.Detalle.DetallesAdicionales;
import ec.gob.sri.comprobantes.modelo.notacredito.NotaCredito.Detalles.Detalle.DetallesAdicionales.DetAdicional;
import ec.gob.sri.comprobantes.modelo.notacredito.NotaCredito.InfoAdicional;
import ec.gob.sri.comprobantes.modelo.notacredito.NotaCredito.InfoAdicional.CampoAdicional;
import ec.gob.sri.comprobantes.modelo.notacredito.NotaCredito.InfoNotaCredito;
import ec.gob.sri.comprobantes.modelo.notacredito.NotaCredito.InfoNotaCredito.compensacion;
import ec.gob.sri.comprobantes.modelo.notacredito.NotaCredito.InfoNotaCredito.compensacion.detalleCompensaciones;
import ec.gob.sri.comprobantes.modelo.notacredito.TotalConImpuestos;
import ec.gob.sri.comprobantes.modelo.notacredito.TotalConImpuestos.TotalImpuesto;
import ec.gob.sri.comprobantes.sql.CompensacionSQL;
import ec.gob.sri.comprobantes.sql.ImpuestoValorSQL;
import ec.gob.sri.comprobantes.util.TipoImpuestoEnum;
import ec.gob.sri.comprobantes.util.TipoImpuestoIvaEnum;
import ec.gob.sri.comprobantes.util.reportes.ReporteUtil;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NotaCreditoReporte {

    private NotaCredito notaCredito;
    private List<DetallesAdicionalesReporte> detallesAdiciones;
    private List<InformacionAdicional> infoAdicional;
    private List<TotalesComprobante> totalesComprobante;

    public NotaCreditoReporte(NotaCredito notaCredito) {
        this.notaCredito = notaCredito;
    }

    public NotaCredito getNotaCredito() {
        return this.notaCredito;
    }

    public void setNotaCredito(NotaCredito notaCredito) {
        this.notaCredito = notaCredito;
    }

    public List<DetallesAdicionalesReporte> getDetallesAdiciones()
            throws SQLException, ClassNotFoundException {
        this.detallesAdiciones = new ArrayList();
        for (NotaCredito.Detalles.Detalle det : getNotaCredito().getDetalles().getDetalle()) {
            DetallesAdicionalesReporte detAd = new DetallesAdicionalesReporte();
            detAd.setCodigoPrincipal(det.getCodigoInterno());
            detAd.setCodigoAuxiliar(det.getCodigoAdicional());
            detAd.setDescripcion(det.getDescripcion());
            detAd.setCantidad(det.getCantidad().toPlainString());
            detAd.setPrecioTotalSinImpuesto(det.getPrecioTotalSinImpuesto().toString());
            detAd.setPrecioUnitario(det.getPrecioUnitario());
            detAd.setDescuento(det.getDescuento().toString());
            int i = 0;
            if ((det.getDetallesAdicionales() != null) && (det.getDetallesAdicionales().getDetAdicional() != null)) {
                for (NotaCredito.Detalles.Detalle.DetallesAdicionales.DetAdicional detAdicional : det.getDetallesAdicionales().getDetAdicional()) {
                    if (i == 0) {
                        detAd.setDetalle1(detAdicional.getNombre());
                    }
                    if (i == 1) {
                        detAd.setDetalle2(detAdicional.getNombre());
                    }
                    if (i == 2) {
                        detAd.setDetalle3(detAdicional.getNombre());
                    }
                    i++;
                }
            }
            detAd.setInfoAdicional(getInfoAdicional());
            detAd.setTotalesComprobante(getTotalesComprobante());
            this.detallesAdiciones.add(detAd);
        }
        return this.detallesAdiciones;
    }

    public void setDetallesAdiciones(List<DetallesAdicionalesReporte> detallesAdiciones) {
        this.detallesAdiciones = detallesAdiciones;
    }

    public List<InformacionAdicional> getInfoAdicional() {
        if (this.notaCredito.getInfoAdicional() != null) {
            this.infoAdicional = new ArrayList();
            if ((this.notaCredito.getInfoAdicional().getCampoAdicional() != null) && (!this.notaCredito.getInfoAdicional().getCampoAdicional().isEmpty())) {
                for (NotaCredito.InfoAdicional.CampoAdicional ca : this.notaCredito.getInfoAdicional().getCampoAdicional()) {
                    this.infoAdicional.add(new InformacionAdicional(ca.getValue(), ca.getNombre()));
                }
            }
        }
        return this.infoAdicional;
    }

    public void setInfoAdicional(List<InformacionAdicional> infoAdicional) {
        this.infoAdicional = infoAdicional;
    }

    public List<TotalesComprobante> getTotalesComprobante()
            throws SQLException, ClassNotFoundException {
        this.totalesComprobante = new ArrayList();
        BigDecimal importeTotal = BigDecimal.ZERO.setScale(2);
        TotalComprobante tc = getTotalesNC(this.notaCredito.getInfoNotaCredito());
        BigDecimal compensaciones = BigDecimal.ZERO.setScale(2);
        for (IvaDiferenteCeroReporte iva : tc.getIvaDistintoCero()) {
            this.totalesComprobante.add(new TotalesComprobante("SUBTOTAL " + iva.getTarifa() + "%", iva.getSubtotal(), false));
        }
        this.totalesComprobante.add(new TotalesComprobante("SUBTOTAL IVA 0%", tc.getSubtotal0(), false));
        this.totalesComprobante.add(new TotalesComprobante("SUBTOTAL NO OBJETO IVA", tc.getSubtotalNoSujetoIva(), false));
        this.totalesComprobante.add(new TotalesComprobante("SUBTOTAL EXENTO IVA", tc.getSubtotalExentoIVA(), false));
        this.totalesComprobante.add(new TotalesComprobante("SUBTOTAL SIN IMPUESTOS", this.notaCredito.getInfoNotaCredito().getTotalSinImpuestos(), false));
        this.totalesComprobante.add(new TotalesComprobante("ICE", tc.getTotalIce(), false));
        for (IvaDiferenteCeroReporte iva : tc.getIvaDistintoCero()) {
            this.totalesComprobante.add(new TotalesComprobante("IVA " + iva.getTarifa() + "%", iva.getValor(), false));
        }
        this.totalesComprobante.add(new TotalesComprobante("IRBPNR", tc.getTotalIRBPNR(), false));
        if (this.notaCredito.getInfoNotaCredito().getCompensaciones() != null) {
            for (NotaCredito.InfoNotaCredito.compensacion.detalleCompensaciones compensacion : this.notaCredito.getInfoNotaCredito().getCompensaciones().getCompensaciones()) {
                compensaciones = compensaciones.add(compensacion.getValor());
            }
            importeTotal = this.notaCredito.getInfoNotaCredito().getValorModificacion().add(compensaciones);
        }
        if (!compensaciones.equals(BigDecimal.ZERO.setScale(2))) {
            this.totalesComprobante.add(new TotalesComprobante("VALOR TOTAL", importeTotal, false));
            for (NotaCredito.InfoNotaCredito.compensacion.detalleCompensaciones compensacion : this.notaCredito.getInfoNotaCredito().getCompensaciones().getCompensaciones()) {
                if (!compensacion.getValor().equals(BigDecimal.ZERO.setScale(2))) {
                    CompensacionSQL compensacionSQL = new CompensacionSQL();
                    String detalleCompensacion = ((Compensaciones) compensacionSQL.obtenerCompensacionesPorCodigo(compensacion.getCodigo()).get(0)).getTipoCompensacion();
                    this.totalesComprobante.add(new TotalesComprobante("(-) " + detalleCompensacion, compensacion.getValor(), true));
                }
            }
            this.totalesComprobante.add(new TotalesComprobante("VALOR A PAGAR", this.notaCredito.getInfoNotaCredito().getValorModificacion(), false));
        } else {
            this.totalesComprobante.add(new TotalesComprobante("VALOR TOTAL", this.notaCredito.getInfoNotaCredito().getValorModificacion(), false));
        }
        return this.totalesComprobante;
    }

    private TotalComprobante getTotalesNC(NotaCredito.InfoNotaCredito infoNc)
            throws SQLException, ClassNotFoundException {
        List<IvaDiferenteCeroReporte> ivaDiferenteCero = new ArrayList();
        BigDecimal totalIva = new BigDecimal(0.0D);
        BigDecimal totalIva0 = new BigDecimal(0.0D);

        BigDecimal totalExentoIVA = new BigDecimal(0.0D);
        BigDecimal totalICE = new BigDecimal(0.0D);
        BigDecimal totalSinImpuesto = new BigDecimal(0.0D);
        BigDecimal totalIRBPNR = new BigDecimal(0.0D);
        TotalComprobante tc = new TotalComprobante();
        for (TotalConImpuestos.TotalImpuesto ti : infoNc.getTotalConImpuestos().getTotalImpuesto()) {
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
            if (TipoImpuestoEnum.IRBPNR.getCode() == cod.intValue()) {
                totalIRBPNR = totalIRBPNR.add(ti.getValor());
            }
        }
        if (ivaDiferenteCero.isEmpty()) {
            ivaDiferenteCero.add(LlenaIvaDiferenteCero());
        }
        tc.setIvaDistintoCero(ivaDiferenteCero);
        tc.setSubtotal0(totalIva0);
        tc.setTotalIce(totalICE);
        tc.setSubtotal(totalIva0.add(totalIva).add(totalExentoIVA).add(totalSinImpuesto));
        tc.setSubtotalExentoIVA(totalExentoIVA);
        tc.setTotalIRBPNR(totalIRBPNR);
        tc.setSubtotalNoSujetoIva(totalSinImpuesto);
        return tc;
    }

    private IvaDiferenteCeroReporte LlenaIvaDiferenteCero() {
        BigDecimal valor = BigDecimal.ZERO.setScale(2);
        String porcentajeIva = ObtieneIvaRide(this.notaCredito.getInfoNotaCredito().getTotalConImpuestos(), DeStringADate(this.notaCredito.getInfoNotaCredito().getFechaEmision()));
        return new IvaDiferenteCeroReporte(valor, porcentajeIva, valor);
    }

    private String ObtieneTarifaIva(String codigoPorcenatje)
            throws SQLException, ClassNotFoundException {
        ImpuestoValorSQL impvalorSQL = new ImpuestoValorSQL();
        return BigDecimal.valueOf(((ImpuestoValor) impvalorSQL.obtenerDatosIvaCodigoPorcentaje(codigoPorcenatje).get(0)).getPorcentaje().doubleValue()).setScale(0).toString();
    }

    private String ObtieneIvaRide(TotalConImpuestos impuestos, Date fecha) {
        for (TotalConImpuestos.TotalImpuesto impuesto : impuestos.getTotalImpuesto()) {
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
}
