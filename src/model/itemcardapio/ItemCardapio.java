package model.itemcardapio;

import java.io.Serializable;

public abstract class ItemCardapio implements Serializable{
	private int id;
	private String nome;
	private double custo;//custo de produção
	private double valor;//valor de venda
	
	public ItemCardapio(String nome, double custo) {
		super();
		this.id = 0;
		this.nome = nome;
		this.custo = custo;
		valor();
	}
	
	public abstract String getTipo();
	public abstract String getAtributo();
	
	//////getSet/////////////////////////////////////////////////////

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public double getCusto() {
		return custo;
	}

	public void setCusto(double custo) {
		this.custo = custo;
	}
	
	public String Show() {
		return "id: " + id + ", nome: " + nome + ", valor: R$" + valor + ", "+getTipo()+getAtributo();
	}

	@Override
	public String toString() {
		return "id: " + id + ", nome: " + nome + ", valor: R$" + valor + ", lucro: R$" + (valor-custo) + ", "+getTipo()+getAtributo();
	}

	public abstract void valor();
	

	
	
}
