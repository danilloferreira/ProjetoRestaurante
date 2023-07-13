package model.comanda;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import model.consumo.Consumo;
public class Comanda implements Serializable{
	
    private int numero;
    private int mesa;
    private List<Consumo> consumos;
    private double total;
    private String status;

    public Comanda(int mesa) {
        this.numero = 0;
        this.mesa = mesa;
        this.consumos = new ArrayList<>();
        this.total = 0.0;
        this.status="Pendente";
    }
    
    //////////////////////////////////////////////////////////////////
    public String detalhamento() {
    	 StringBuilder a = new StringBuilder();
         StringBuilder b = new StringBuilder();
         for (Consumo consumo : consumos) {
             String tipoItem = consumo.getItem().getTipo();
             if (tipoItem.equalsIgnoreCase("Produto Industrializado")) {
             	a.append(consumo+"\n");
             }else if((tipoItem.equalsIgnoreCase("Comida Tradicional"))) {
             	b.append(consumo+"\n");
             }
         }
         return a.toString()+b.toString();
    }
    
    
    public String relatorio(){
		StringBuilder relatorio = new StringBuilder();

		double [] resultadoPI = calcularDescontoETotalPorTipo(consumos, "Produto Industrializado");
		double totalPI = resultadoPI[0];
		double descontoPI = resultadoPI[1];
		double totalComDescontoPI = resultadoPI[2];

		double [] resultadoCT = calcularDescontoETotalPorTipo(consumos, "Comida Tradicional");
		double totalCT = resultadoCT[0];
		double descontoCT = resultadoCT[1];
		double totalComDescontoCT = resultadoCT[2];
		
		relatorio.append("============================");
        relatorio.append("\nRESUMO   DE   CONSUMOS\n");
        relatorio.append("============================\n");
        relatorio.append(detalhamento());
        
		if(totalPI>0) {
		relatorio.append("-----------------------------------------------------------");
	    relatorio.append("\nAlimentos Industrializado:\n");
    	relatorio.append("Total: R$").append(totalPI);
    	relatorio.append(", Desconto: ").append((descontoPI / totalPI) * 100).append("%");
    	relatorio.append(", Total com Desconto: R$").append(totalComDescontoPI).append("\n");
		}
        if(totalCT>0) {
        relatorio.append("-----------------------------------------------------------");
	    relatorio.append("\nAlimentos Tradicionais:\n");
    	relatorio.append("Total: R$").append(totalCT);
    	relatorio.append(", Desconto: ").append((descontoCT / totalCT) * 100).append("%");
    	relatorio.append(", Total com Desconto: R$").append(totalComDescontoCT).append("\n");
        }
    	double totalGeral = totalPI + totalCT;
    	double totalGeralComDesconto = totalComDescontoPI + totalComDescontoCT;

    	relatorio.append("-----------------------------------------------------------");
        relatorio.append("\nTotal a Pagar\n");
        relatorio.append("- - - - - - - - - \n");
    	relatorio.append("Total: R$").append(totalGeral);
   	 	relatorio.append("\nTotal com Desconto: R$").append(totalGeralComDesconto).append("\n");

    	return relatorio.toString();

	}

	private double[] calcularDescontoETotalPorTipo(List<Consumo> consumos, String tipoItem){
		double total = 0.0;
		double desconto = 0.0;
		int quant = 0;

		for (Consumo consumo : consumos){
			if (consumo.getItem().getTipo().equalsIgnoreCase(tipoItem)){
				quant += consumo.getQuant();
				total += consumo.getValor();
			}
		}

		if (quant > 0){
			if (tipoItem.equalsIgnoreCase("Produto Industrializado")){
				if (quant <= 15 && quant > 5){
					desconto = total * 0.1;
				} else if (quant <= 25 && quant > 15){
					desconto = total * 0.15;
				} else if (quant > 25){
					desconto = total * 0.2;
				}
			} else if (tipoItem.equalsIgnoreCase("Comida Tradicional")){
				if (quant <= 5 && quant > 2){
					desconto = total * 0.15;
				} else if (quant <= 10 && quant > 5){
					desconto = total * 0.20;
				} else if (quant > 10){
					desconto = total * 0.25;
				}
			}
		}

		double totalComDesconto = total - desconto;
		return new double [] {total, desconto, totalComDesconto};
	}

	public void pagar() {
		double[] resultadoPI = calcularDescontoETotalPorTipo(consumos, "Produto Industrializado");
		double totalPI = resultadoPI[0];
		double descontoPI = resultadoPI[1];
		double[] resultadoCT = calcularDescontoETotalPorTipo(consumos, "Comida Tradicional");
		double totalCT = resultadoCT[0];
		double descontoCT = resultadoCT[1];
		double totalGeral = totalPI + totalCT;
		double totalGeralComDesconto = totalGeral - descontoPI - descontoCT;
		this.total = totalGeralComDesconto;
		this.status = "Pago";
	}

    
    /////////////////////////////////////////////////////////////////////
    
    public List<Consumo> getAll() {
    	List<Consumo> copia = new ArrayList<>(consumos);
        return copia;
    }
    
    ///////////////////////////////////////////////////////////////////
    
    public void adicionarConsumo(Consumo consumo) {
    	Consumo teste;
		try {
			teste = retornarConsumo(consumo.getId());
			int quant = consumo.getQuant()+teste.getQuant();
    		teste.setQuant(quant);
		} catch (ConsumoNaoEncontradoException e) {
			consumos.add(consumo);
		}
		atualizaTotal();
    }
    
    ////////////////////////////////////////////////////////////////////
    
    public void removerConsumo(Consumo consumo) {
        consumos.remove(consumo);
        atualizaTotal();
    }
    
    ///////////////////////////////////////////////////////////////////
    
    public void atualizaTotal() {
    	double total=0.0;
    	for (Consumo consumo: consumos) {
    		total+=consumo.getValor();
    	}
    	this.total=total;
    }
    
    ////////////////////////////////////////////////////////////////////////
    
    public Consumo removerDoConsumo(int id, int quant) {
        
		for (Consumo consumo : consumos) {
            if (consumo.getId() == id) {
            	consumo.setQuant(consumo.getQuant()-quant);
            	double valorItem=consumo.getItem().getValor();
            	consumo.setValor(valorItem*consumo.getQuant());
            }
            
        }
		return null;
		
	}
    
    //////////////////////////////////////////////////////////////////////////
    
    public Consumo retornarConsumo(int id) throws ConsumoNaoEncontradoException {
        
		for (Consumo consumo : consumos) {
            if (consumo.getId() == id) {
            	return consumo;
            }
            
        }
		throw new ConsumoNaoEncontradoException();
		
	}
    
    
    //GETSET///////////////////////////////////////////////////////////////////////////////
    

	@Override
	public String toString() {
		return "comanda " + numero +", mesa "+mesa+", total: R$ " + total +", status: " + status;
	}
	
	
	public int getMesa() {
		return mesa;
	}



	public void setMesa(int mesa) {
		this.mesa = mesa;
	}



	public int getNumero() {
	        return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	 public double getTotal() {
	        return total;
	 }

	public void setTotal(double total) {
		this.total = total;
	}

	public List<Consumo> getConsumos() {
		return consumos;
	}

	public void setConsumos(List<Consumo> consumos) {
		this.consumos = consumos;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


    
}