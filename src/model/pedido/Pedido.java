package model.pedido;

import java.io.Serializable;

public class Pedido implements Serializable{
    private String item;
    private int numero;
    private double preco;

    public Pedido(String item, double preco, int numero) {
        this.item = item;
        this.preco = preco;
        this.numero=numero;
    }

    public String getItem() {
        return item;
    }

    public double getPreco() {
        return preco;
    }
    
    

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	@Override
	public String toString() {
		return "id: "+numero+", item: " + item + ", valor: R$ " + preco;
	}
    
    
    
}
