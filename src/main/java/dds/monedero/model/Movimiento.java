package dds.monedero.model;

import java.time.LocalDate;

import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

public abstract class Movimiento {
  protected LocalDate fecha;
  //En ningún lenguaje de programación usen jamás doubles para modelar dinero en el mundo real
  //siempre usen numeros de precision arbitraria, como BigDecimal en Java y similares
  protected double monto;

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
		if (monto <= 0) {
		      throw new MontoNegativoException(monto + ": el monto a retirar debe ser un valor positivo");
		    }
		this.fecha = fecha;
	    this.monto = monto;
	}
	
	public void setSaldo(Cuenta cuenta) {
		Double saldoAnterior= cuenta.getSaldo();		
		if(saldoAnterior-getMonto() < 0) {
			throw new SaldoMenorException("No puede sacar mas de " + saldoAnterior + " $");
		}
		cuenta.setSaldo(saldoAnterior-getMonto());
	}
}




class Deposito extends Movimiento{

	public Deposito(LocalDate fecha, double monto) {
		if (monto <= 0) {
		      throw new MontoNegativoException(monto + ": el monto a ingresar debe ser un valor positivo");
		    }
		this.fecha = fecha;
	    this.monto = monto;
	}

	public void setSaldo(Cuenta cuenta) {
		Double saldoAnterior= cuenta.getSaldo();
		cuenta.setSaldo(saldoAnterior+getMonto());
	}
}

