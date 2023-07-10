package repository.comanda;

import java.io.Serializable;

import pattern.IdGeneratorStrategy;

public class GeneratorComanda implements IdGeneratorStrategy, Serializable{
	private int nextNumber;

	  public GeneratorComanda() {
	    this.nextNumber = 1;
	  }
	  
	  public int nextId() {
	    return nextNumber++;
	  }
}
