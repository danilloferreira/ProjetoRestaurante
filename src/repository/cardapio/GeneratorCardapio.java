package repository.cardapio;

import java.io.Serializable;

import pattern.IdGeneratorStrategy;

public class GeneratorCardapio implements IdGeneratorStrategy, Serializable{
	private int nextNumber;

	  public GeneratorCardapio() {
	    this.nextNumber = 1;
	  }
	  
	  public int nextId() {
	    return nextNumber++;
	  }
}
