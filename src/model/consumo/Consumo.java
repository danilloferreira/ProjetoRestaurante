package model.consumo;

import java.io.Serializable;

import model.itemcardapio.ItemCardapio;

public class Consumo implements Serializable{
	private ItemCardapio item;
	private int id;
	private int quant;
	private double valor;
	
	public Consumo(ItemCardapio item, int quant) {
		super();
		this.item = item;
		this.id = item.getId();
		this.quant = quant;
		valor = item.getValor()*quant;
	}
	public void atualizaValor(){
		valor=item.getValor()*quant;
	}
	
	///getSet//////////////////////////////////////////////
	

	public ItemCardapio getItem() {
		return item;
	}

	public void setItem(ItemCardapio item) {
		this.item = item;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuant() {
		return quant;
	}

	public void setQuant(int quant) {
		this.quant = quant;
		atualizaValor();
	}


	public double getValor() {
		return valor;
	}


	public void setValor(double valor) {
		this.valor = valor;
	}
	
	
	
	
	
	
	@Override
	public String toString() {
		return "Id: " + id + ", nome: " + item.getNome()+ ", valor: R$" + valor + ", quant: " + quant  + ", "+item.getTipo();
	}
	
	
	
	
	
	
}


