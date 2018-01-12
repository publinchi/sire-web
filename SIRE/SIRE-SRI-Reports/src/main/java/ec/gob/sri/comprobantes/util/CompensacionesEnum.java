package ec.gob.sri.comprobantes.util;

public enum CompensacionesEnum
{
  CompensacionSolidaria(1),  DineroElectronico14(2),  TarjetaDebitoPrepago14(3),  TarjetaCredito14(4),  DineroElectronico12(5),  DineroElectronico4pts12(6),  TarjetaDebitoPrepago12(7),  TarjetaCredito12(8);
  
  private int code;
  
  private CompensacionesEnum(int code)
  {
    this.code = code;
  }
  
  public int getCode()
  {
    return this.code;
  }
}
