package com.sire.sri.batch.recepcion.constant;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author pestupinan
 */
public class RecepcionConstant {

    public static String FACTURA_SQL = "SELECT COD_EMPRESA, RUC_EMPRESA, RAZON_SOCIAL_EMPRESA, "
            + "NOMBRE_COMERCIAL, COD_DOCUMENTO, NUM_FACTURA_INTERNO, ESTABLECIMIENTO, "
            + "PUNTO_EMISION, SECUENCIAL, DIRECCION_MATRIZ, DIRECCION_ESTABLECIMIENTO, "
            + "CONTRIBUYENTE_ESPECIAL, LLEVA_CONTABILIDAD, RAZON_SOCIAL_COMPRADOR, "
            + "FECHA_FACTURA, TIPO_IDENTIFICACION_COMPRADOR, IDENTIFICACION_COMPRADOR, "
            + "DIRECCION_COMPRADOR, TELEFONO_COMPRADOR, EMAIL_COMPRADOR, "
            + "TOTAL_SIN_IMPUESTOS, TOTAL_DESCUENTOS, PROPINA, IMPORTE_TOTAL, "
            + "CLAVE_ACCESO, CODIGO_IMPUESTO, CODIGO_PORCENTAJE, BASE_IMPONIBLE, "
            + "VALOR, MONEDA, OBSERVACION FROM V_FACTURA_ELECTRONICA_C WHERE "
            + "ESTADO_SRI='GRABADA' AND ROWNUM <= 20 ORDER BY FECHA_FACTURA";

    public static String RETENCION_SQL = "SELECT COD_EMPRESA, RUC_EMPRESA, RAZON_SOCIAL_EMPRESA, "
            + "NOMBRE_COMERCIAL, COD_DOCUMENTO, NUM_RETENCION_INTERNO, CLAVE_ACCESO, "
            + "ESTABLECIMIENTO, PUNTO_EMISION, SECUENCIAL, DIRECCION_MATRIZ, "
            + "DIRECCION_ESTABLECIMIENTO, CONTRIBUYENTE_ESPECIAL, LLEVA_CONTABILIDAD, "
            + "RAZON_SOCIAL_SUJETO_RETENIDO, DIRECCION_RETENIDO, TELEFONO_RETENIDO, "
            + "EMAIL_RETENIDO, FECHA_RETENCION, TIPO_IDENT_SUJETO_RETENIDO, "
            + "IDENTIFICACION_SUJETO_RETENIDO, PERIODO_FISCAL, ESTADO_SRI, "
            + "CLAVE_ACCESO_LOTE FROM V_RETENCION_ELECTRONICA_C WHERE "
            + "ESTADO_SRI='GRABADA' AND ROWNUM <= 20 ORDER BY FECHA_RETENCION";

    public static String NOTA_CREDITO_SQL = "SELECT COD_EMPRESA, RUC_EMPRESA, "
            + "RAZON_SOCIAL_EMPRESA, NOMBRE_COMERCIAL, COD_DOCUMENTO, NUM_FACTURA_INTERNO, "
            + "CLAVE_ACCESO, ESTABLECIMIENTO, PUNTO_EMISION, SECUENCIAL, DIRECCION_MATRIZ, "
            + "DIRECCION_ESTABLECIMIENTO, CONTRIBUYENTE_ESPECIAL, LLEVA_CONTABILIDAD, "
            + "RAZON_SOCIAL_COMPRADOR, DIRECCION_COMPRADOR, TELEFONO_COMPRADOR, "
            + "EMAIL_COMPRADOR, OBSERVACION, FECHA_FACTURA, TIPO_IDENTIFICACION_COMPRADOR, "
            + "IDENTIFICACION_COMPRADOR, TOTAL_SIN_IMPUESTOS, TOTAL_DESCUENTOS, "
            + "PROPINA, IMPORTE_TOTAL, CODIGO_IMPUESTO, CODIGO_PORCENTAJE, BASE_IMPONIBLE, "
            + "VALOR, MONEDA, ESTADOR_SRI, CLAVE_ACCESO_LOTE "
            + "FROM SIREPOLLO.V_NOTA_CREDITO_ELECTRONICA_C WHERE "
            + "ESTADO_SRI='GRABADA' AND ROWNUM <= 20 ORDER BY FECHA_FACTURA";

    public static String NOTA_DEBITO_SQL;

    public static String GUIA_REMISION_SQL;

}
