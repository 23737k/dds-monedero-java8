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
  
  public void agregateA(Cuenta cuenta) {
	    cuenta.setSaldo(calcularValor(cuenta));
	  }
  
  public abstract double calcularValor(Cuenta cuenta);
  
}




class Extraccion extends Movimiento{
	
	
	@Override
	public void agregateA(Cuenta cuenta) {
		super(cuenta);
		cuenta.agregarDeposito(fecha, monto);
	  }
  
	
	public Extraccion(LocalDate fecha, double monto) {
		super(fecha, monto);
	}

	public double calcularValor(Cuenta cuenta) {
		 return cuenta.getSaldo() - getMonto();
	}
	
	public boolean fueExtraido(LocalDate fecha) {
	    return esDeLaFecha(fecha);
	  }
}





class Deposito extends Movimiento{

	public Deposito(LocalDate fecha, double monto) {
		super(fecha, monto);
	}

	public double calcularValor(Cuenta cuenta) {
		return cuenta.getSaldo() + getMonto();
	}
	
	public boolean fueDepositado(LocalDate fecha) {
	    return esDeLaFecha(fecha);
	  }
	
}

