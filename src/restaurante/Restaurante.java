package restaurante;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.comanda.Comanda;
import model.consumo.Consumo;
import model.itemcardapio.ItemCardapio;
import repository.cardapio.ReCardapio;
import repository.cardapio.ReCardapioList;
import repository.comanda.ReComanda;
import repository.comanda.ReComandaList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Restaurante {
	private final File file = new File("restaurante.dat");
	private ReComanda recomanda;
	private ReCardapio recardapio;
	private static Restaurante instance = null;

    public Restaurante() {
        if (file.exists()==true) {
            loadData();
        } else {
            recomanda = new ReComandaList();
            recardapio = new ReCardapioList();
        }
    }
    
    
	public static Restaurante getInstance() {

		if (instance == null) {
			instance = new Restaurante();
			
		}

		return instance;
	}
    
    ///CARDÁPIO//////////////////////////////////////////////////////////////////////////////
	
    public ItemCardapio criarCardapio(ItemCardapio ic) {
    	recardapio.adicionarItem(ic);
    	return ic;
    }
    
    
    public ItemCardapio removeCardapio(ItemCardapio ic) {
    	recardapio.removerItem(ic);
    	return ic;
    }
    
    public ItemCardapio buscarCardapio(int id) {
    	return recardapio.buscarOpcao(id); 
    	
    }
    
    public List<ItemCardapio> getAllCardapio(){
    	return recardapio.getAll();
    }


    /*Foi criado um método genérico(imprimirCardapio) para 
     *imprimir itens do cardapio, esse método aceita um valor
     * booleano que indicará se deve imprimir os detalhes do
     * cardápio ou apenas uma visão geral, sendo assim, 
     * stringCardapio e apresentar cardapio apenas retornan 
     * true ou false. 
    */
    public String imprimirCardapio(boolean detalhado) {
        StringBuilder a = new StringBuilder();
        for (ItemCardapio cardapio : recardapio.getAll()) {
            if (detalhado) {
                a.append(cardapio.Show()).append("\n");
            } else {
                a.append(cardapio).append("\n");
            }
        }
        return a.toString();
    }

    public String stringCardapio() {
        return imprimirCardapio(false);
    }

    public String apresentarCardapio() {
        return imprimirCardapio(true);
    }


    ///COMANDA//////////////////////////////////////////////////////////////////////////////
    
    public Comanda criarComanda(Comanda c) {
    	recomanda.criarComanda(c);
    	return c;
    }
    
    public Comanda removerComanda(Comanda c) {
        recomanda.removerComanda(c);
        return c;
    }

    public Comanda buscarComanda(int id) {
        return recomanda.buscarComanda(id);
    }
    
    public List<Comanda> getAllComanda(){
    	return recomanda.getAll();
    }
    
    public String stringComandas() {
    	 String a="";
    	 for (Comanda comanda : recomanda.getAll()) {
              a+=comanda+"\n";
         }
         return a;
    }
    
    public String calcularTotalComanda(int id) {
        Comanda comanda = buscarComanda(id);
        
        if (comanda != null) {
        	 String a = "total: R$ "+comanda.getTotal();
        	
        	 Map<Consumo, Integer> contagem = new HashMap<>();

             // Atualizar as contagens das strings
             for (Consumo csm : comanda.getAll()) {
                 if (contagem.containsKey(csm)) {
                     contagem.put(csm, contagem.get(csm) + 1);
                 } else {
                     contagem.put(csm, 1);
                 }
             }

             // Imprimir as strings e suas quantidades correspondentes
             for (Map.Entry<Consumo, Integer> entry : contagem.entrySet()) {
            	 Consumo csm = entry.getKey();
                 int quantidade = entry.getValue();
                 a+="\n"+csm;
             }
        	
            return a;
        }
        return null;
    }
    
    
  ///CONSUMO//////////////////////////////////////////////////////////////////////////////

    /*public void adicionarnoConsumo(int numeroComanda, int id) {
        Comanda comanda = buscarComanda(numeroComanda);
        Consumo consumo = new Consumo(recardapio.buscarOpcao(id), id);
        if (comanda != null) {
            comanda.adicionarConsumo(consumo);
        }
    }*/

    public void adicionarnoConsumo(int numeroComanda, int id) {
        Comanda comanda = buscarComanda(numeroComanda);
        ItemCardapio itemCardapio = recardapio.buscarOpcao(id);
    
        Validador.validarComanda(comanda, numeroComanda);
        Validador.validarItemCardapio(itemCardapio, id);
    
        Consumo consumo = new Consumo(itemCardapio, id);
        comanda.adicionarConsumo(consumo);
    }
    

    public void removerDoConsumo(int idComanda, int id, int quant) {
        Comanda comanda = buscarComanda(idComanda);
        if (comanda.retornarConsumo(id) != null) {
        	for (Consumo consumo : comanda.getAll()) {
        		if(id==consumo.getId()){
        			   comanda.removerDoConsumo(id, quant);
        		}
        	}
        }
    }
    

   
    
    
    //GET SET///////////////////////////////////////////////////////////////////////////////////////////


	public ReComanda getRecomanda() {
		return recomanda;
	}


	public void setRecomanda(ReComanda recomanda) {
		this.recomanda = recomanda;
	}
	

	public ReCardapio getRecardapio() {
		return recardapio;
	}


	public void setRecardapio(ReCardapio recardapio) {
		this.recardapio = recardapio;
	}


	private void loadData() {
		try {
			FileInputStream f = new FileInputStream(file);
			ObjectInputStream o = new ObjectInputStream(f);

			recomanda = (ReComanda) o.readObject();
			recardapio = (ReCardapio) o.readObject();
			

			o.close();
			f.close();
		} catch (ClassNotFoundException e) {
			System.err.println("Definição da classe não encontrada");
		} catch (IOException e) {
			System.err.println("Erro ao carregar dados do arquivo");
		}
	}
	
	public void exit() {
		try {
			FileOutputStream f = new FileOutputStream(file);
			ObjectOutputStream o = new ObjectOutputStream(f);

			// Salvar meus dados
			o.writeObject(recomanda);
			o.writeObject(recardapio);
		

			o.close();
			f.close();
		} catch (IOException e) {
			System.err.println("Erro de serialização de objeto");
			e.printStackTrace();
		}
	}
   
    
}

