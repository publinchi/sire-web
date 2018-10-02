package com.cobiscorp.ecobis.ficohsa.connector.vp.tarjeta.utils;

import java.net.URLStreamHandler;

public abstract class TarjetasURLStreamHandler extends URLStreamHandler
{
    public int connectTimeout;
    public int readTimeout;

    public int getConnectTimeout()
    {
        return this.connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout)
    {
        this.connectTimeout = connectTimeout;
    }

    public int getReadTimeout()
    {
        return this.readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }
}