package model.itemcardapio;

import java.io.Serializable;

public class ComidaTradicional extends ItemCardapio implements Serializable{
	String descri;
    public ComidaTradicional(String nome, double custo, double valorVenda, String descri) {
        super(nome, custo, valorVenda);
        this.descri=descri;
    }

   /* @Override
    public void valor() {
        setValor(getCusto() * 1.75);
    }*/
    
    
    @Override
	public String getTipo() {
		return "Comida Tradicional";
	}
    @Override
	public String getAtributo() {
		return "\n Descricao: "+descri;
	}

	public String getDescri() {
		return descri;
	}

	public void setDescri(String descri) {
		this.descri = descri;
	}
   
    
}
