package com.sire.sri.batch.recepcion.constant;

import com.sire.sri.batch.constant.Constant;

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

    public static String codEmpresa = "COD_EMPRESA = ? AND ";

    public static String FACTURA_SQL_ORACLE = Constant.FACTURA_C_SQL
            + "(SELECT * FROM V_FACTURA_ELECTRONICA_C WHERE ESTADO_SRI='GRABADA' "
            + "ORDER BY FECHA_FACTURA) "
            + "WHERE " + codEmpresa
            + "ESTADO_SRI='GRABADA' AND ROWNUM <= 20 ORDER BY FECHA_FACTURA";

    public static String FACTURA_SQL_MYSQL = Constant.FACTURA_C_SQL
            + "(SELECT * FROM V_FACTURA_ELECTRONICA_C WHERE ESTADO_SRI='GRABADA' "
            + "ORDER BY FECHA_FACTURA) AS V_FACTURA_ELECTRONICA_C "
            + "WHERE " + codEmpresa
            + "ESTADO_SRI='GRABADA' ORDER BY FECHA_FACTURA LIMIT 20";

    public static String RETENCION_SQL = Constant.RETENCION_C_SQL
            + codEmpresa + "ESTADO_SRI='GRABADA' AND ROWNUM <= 20 ORDER BY FECHA_RETENCION";

    public static String NOTA_CREDITO_SQL = Constant.NOTA_CREDITO_C_SQL
            + codEmpresa + "ESTADO_SRI='GRABADA' AND ROWNUM <= 20 ORDER BY FECHA_EMISION";

    public static String NOTA_DEBITO_SQL = Constant.NOTA_DEBITO_SQL
            + codEmpresa + "ESTADO_SRI='GRABADA' AND ROWNUM <= 20 ORDER BY FECHA_EMISION";

    public static String GUIA_REMISION_SQL = Constant.GUIA_REMISION_C_SQL
            + codEmpresa + "ESTADO_SRI='GRABADA' AND ROWNUM <= 20 ORDER BY FECHA_INICIO_TRANSPORTE";

}
