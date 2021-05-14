package dds.monedero.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

public class Cuenta {

  private double saldo = 0;
  private List<Deposito> depositos = new ArrayList<>();
  private List<Extraccion> extracciones= new ArrayList<>();

  public Cuenta() {
    saldo = 0;
  }

  public Cuenta(double montoInicial) {
    saldo = montoInicial;
  }

  public void setMovimientos(List<Extraccion> extracciones, List<Deposito> depositos) {
    this.depositos= depositos;
    this.extracciones= extracciones;
  }

  public void poner(double cuanto) {
    if (cuanto <= 0) {
      throw new MontoNegativoException(cuanto + ": el monto a ingresar debe ser un valor positivo");
    }
    
    if (getDepositos().stream().filter(deposito -> deposito.esDeLaFecha(LocalDate.now())).count() >= 3) {
      throw new MaximaCantidadDepositosException("Ya excedio los " + 3 + " depositos diarios");
    }
    agregarDeposito(LocalDate.now(),cuanto);
  }

  public void sacar(double cuanto) {
    if (cuanto <= 0) {
      throw new MontoNegativoException(cuanto + ": el monto a ingresar debe ser un valor positivo");
    }
    if (getSaldo() - cuanto < 0) {
      throw new SaldoMenorException("No puede sacar mas de " + getSaldo() + " $");
    }
    double montoExtraidoHoy = getMontoExtraidoA(LocalDate.now());
    double limite = 1000 - montoExtraidoHoy;
    if (cuanto > limite) {
      throw new MaximoExtraccionDiarioException("No puede extraer mas de $ " + 1000
          + " diarios, límite: " + limite);
    }
    
    agregarExtraccion(LocalDate.now(),cuanto);
    
  }
  

  private void agregarDeposito(LocalDate fecha, double cuanto) {
    Deposito deposito = new Deposito(fecha, cuanto);
    deposito.setSaldo(this);
    depositos.add(deposito);
    
  }
  
  private void agregarExtraccion(LocalDate fecha, double cuanto) {
	  Extraccion extraccion = new Extraccion(fecha, cuanto);
	    extraccion.setSaldo(this);
	    extracciones.add(extraccion);
  }

  public double getMontoExtraidoA(LocalDate fecha) {
    return getExtracciones().stream()
        .filter(extraccion -> extraccion.esDeLaFecha(fecha))
        .mapToDouble(Movimiento::getMonto)
        .sum();
  }

  public List<Extraccion> getExtracciones() {
    return extracciones;
  }

  public List<Deposito> getDepositos() {
	    return depositos;
  }
  
  public double getSaldo() {
    return saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }

}
