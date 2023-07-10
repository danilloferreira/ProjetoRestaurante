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

 
    
    public String relatorio() {
        double total = 0.0;
        double desconto = 0.0;
        double valorpi = 0.0, valorpt = 0.0, dsc = 0.0;
        int pi = 0, pt = 0;
        StringBuilder a = new StringBuilder();
        StringBuilder b = new StringBuilder();
        StringBuilder relatorio = new StringBuilder();

        for (Consumo consumo : consumos) {
            double valorItem = consumo.getValor();
            String tipoItem = consumo.getItem().getTipo();
            total+=valorItem;
            if (tipoItem.equalsIgnoreCase("Produto Industrializado")) {
            	pi+=consumo.getQuant();
            	valorpi+=valorItem;
            	a.append(consumo+"\n");
            }else if((tipoItem.equalsIgnoreCase("Comida Tradicional"))) {
            	pt+=consumo.getQuant();
            	valorpt+=valorItem;
            	b.append(consumo+"\n");
            }
        }
        relatorio.append(a.toString()+b.toString());
        
        if(pi>0) {
	        relatorio.append("\nAlimentos Industrializado\n");
	        relatorio.append("===========================\n");
	        relatorio.append("Total: R$"+valorpi);
	        if (pi <= 15 && pi > 5) { valorpi*=0.1;dsc=10;
	        } else if (pi <= 25 && pi > 15) {valorpi*=0.15;dsc=15;
	        } else if (pi > 25) {valorpi*=0.2;dsc=20;
	        } else {valorpi*=0.0;dsc=0;}
	        relatorio.append(", Desconto: "+dsc+"%, Total com Desconto: R$"+((valorpi/(dsc/100))-valorpi)+"\n");
        }
        if(pt>0) {
	        relatorio.append("\nAlimentos Tradicionais\n");
	        relatorio.append("===========================\n");
	        relatorio.append("Total: R$"+valorpt);    
	        if (pt <= 5 && pt > 2) { valorpt*=0.15;dsc=15;
	        } else if (pt <= 10 && pt > 5) {valorpt*=0.20;dsc=20;
	        } else if (pt > 10) {valorpt*=0.25;dsc=25;
	        } else {valorpt*=0.0;dsc=0;}
	        relatorio.append(", Desconto: "+dsc+"%, Total com Desconto: R$"+((valorpt/(dsc/100))-valorpt)+"\n");
        }
        
        desconto = valorpi+valorpt;   
        double totalComDesconto = total - desconto;
        relatorio.append("\nTotal a Pagar\n");
        relatorio.append("===========================");
        relatorio.append("\nTotal: R$").append(total).append("\nTotal com Desconto: R$").append(totalComDesconto).append("\n");

        return relatorio.toString();
    }


    
    public void pagar() {
    	double total = 0.0, desconto = 0.0;
        double valorpi = 0.0, valorpt = 0.0;
        int pi = 0, pt = 0;

        for (Consumo consumo : consumos) {
            double valorItem = consumo.getValor();
            String tipoItem = consumo.getItem().getTipo();
            total+=valorItem;
            if (tipoItem.equalsIgnoreCase("Produto Industrializado")) {
            	pi+=consumo.getQuant();
            	valorpi+=valorItem;
            }else if((tipoItem.equalsIgnoreCase("Comida Tradicional"))) {
            	pt+=consumo.getQuant();
            	valorpt+=valorItem;
            }
        }
        
        if(pi>0) {
	        if (pi <= 25 && pi > 5) { valorpi*=0.1;
	        } else if (pi <= 50 && pi > 25) {valorpi*=0.15;
	        } else if (pi > 50) {valorpi*=0.2;
	        } else {valorpi*=0.0;}
        }
        if(pt>0) { 
	        if (pt <= 25 && pt > 5) { valorpt*=0.15;
	        } else if (pt <= 50 && pt > 25) {valorpt*=0.25;
	        } else if (pt > 50) {valorpt*=0.35;
	        } else {valorpt*=0.0;}
        }
        desconto = valorpi+valorpt;   
    	this.total=total-desconto;
    	this.status="Pago";
    }
    
    
    public List<Consumo> getAll() {
    	List<Consumo> copia = new ArrayList<>(consumos);
        return copia;
    }

    public void adicionarConsumo(Consumo consumo) {
    	Consumo teste = retornarConsumo(consumo.getId());
    	if(teste!=null) {
    		int quant = consumo.getQuant()+teste.getQuant();
    		teste.setQuant(quant);
    	}else {
            consumos.add(consumo);
    	}
        total += consumo.getValor();
    }

    public void removerConsumo(Consumo consumo) {
        consumos.remove(consumo);
        total -= consumo.getValor();
    }
    
    public void atualizaTotal() {
    	double total=0.0;
    	for (Consumo consumo: consumos) {
    		total+=consumo.getValor();
    	}
    	this.total=total;
    }
    
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
    
    public Consumo retornarConsumo(int id) {
        
		for (Consumo consumo : consumos) {
            if (consumo.getId() == id) {
            	return consumo;
            }
            
        }
		return null;
		
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