package model.itemcardapio;

import java.io.Serializable;

public class ComidaIndustrial extends ItemCardapio implements Serializable{
	int quant;
    public ComidaIndustrial(String nome, double custo, int quant) {
        super(nome, custo);
        this.quant = quant;
    }

    @Override
    public void valor() {
        setValor(getCusto() * 1.4);
    }

	@Override
	public String getTipo() {
		return "Produto Industrializado";
	}
	@Override
	public String getAtributo() {
		if(quant>0) {
			return ", Disponivel: "+quant;
		}else {
			return", Status: Esgotado";
		}
	}
	
	public int getQuant() {
		return quant;
	}
	
	public void setQuant(int quant) {
		this.quant = quant;
	}

	
}
