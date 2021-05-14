package dds.monedero.model;

import java.time.LocalDate;

public abstract class Movimiento {
  protected LocalDate fecha;
  //En ningún lenguaje de programación usen jamás doubles para modelar dinero en el mundo real
  //siempre usen numeros de precision arbitraria, como BigDecimal en Java y similares
  protected double monto;
 
  public Movimiento(LocalDate fecha, double monto) {
    this.fecha = fecha;
    this.monto = monto;
  }

  public double getMonto() {
    return monto;
  }

  public LocalDate getFecha() {
    return fecha;
  }
  
  public boolean esDeLaFecha(LocalDate fecha) {
	    return this.fecha.equals(fecha);
	  }
  
  public abstract void setSaldo(Cuenta cuenta);
}




class Extraccion extends Movimiento{  
	
	public Extraccion(LocalDate fecha, double monto) {
		super(fecha, monto);
	}
	
	@Override
	public void setSaldo(Cuenta cuenta) {
		Double saldoAnterior= cuenta.getSaldo();
		cuenta.setSaldo(saldoAnterior-getMonto());
	}
}




class Deposito extends Movimiento{

	public Deposito(LocalDate fecha, double monto) {
		super(fecha, monto);
	}
	
	@Override
	public void setSaldo(Cuenta cuenta) {
		Double saldoAnterior= cuenta.getSaldo();
		cuenta.setSaldo(saldoAnterior+getMonto());
	}
}

