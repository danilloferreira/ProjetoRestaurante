package facade;
import java.util.List;
import model.comanda.Comanda;
import model.consumo.Consumo;
import model.itemcardapio.ItemCardapio;
import repository.cardapio.ItemJaCriadoException;
import repository.cardapio.ItemNaoEncontradoException;
import repository.cardapio.RepositorioCardapio;
import repository.cardapio.RepositorioCardapioList;
import repository.comanda.ComandaNaoCadastradaException;
import repository.comanda.MesaComandaAbertaException;
import repository.comanda.RepositorioComanda;
import repository.comanda.RepositorioComandaList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class RestauranteFacade {
	private final File file = new File("restaurante.dat");
	private RepositorioComanda repositorioComanda;
	private RepositorioCardapio repositorioCardapio;
	private static RestauranteFacade instance = null;

    public RestauranteFacade() {
        if (file.exists()==true) {
            loadData();
        }
        if((repositorioComanda==null)||(repositorioCardapio==null)) {
            repositorioComanda = new RepositorioComandaList();
            repositorioCardapio = new RepositorioCardapioList();
        }
    }
    
    
	public static RestauranteFacade getInstance() {

		if (instance == null) {
			instance = new RestauranteFacade();
			
		}

		return instance;
	}
    
    ///CARDÁPIO//////////////////////////////////////////////////////////////////////////////
	
    public ItemCardapio criarCardapio(ItemCardapio ic) throws ItemJaCriadoException {
    	repositorioCardapio.adicionarItem(ic);
    	return ic;
    }
    
    
    public ItemCardapio removeCardapio(ItemCardapio ic)throws RestauranteException, ItemNaoEncontradoException {
    	repositorioCardapio.removerItem(ic);
    	return ic;
    }
    
    public ItemCardapio buscarCardapio(int id) throws ItemNaoEncontradoException {
    	return repositorioCardapio.buscarOpcao(id); 
    }
    
    public List<ItemCardapio> getAllCardapio(){
    	return repositorioCardapio.getAll();
    }
    
    ///COMANDA//////////////////////////////////////////////////////////////////////////////
    
    public Comanda criarComanda(Comanda c) throws MesaComandaAbertaException {
    	repositorioComanda.criarComanda(c);
    	return c;
    }
    
    public Comanda removerComanda(Comanda c) throws ComandaNaoCadastradaException {
        repositorioComanda.removerComanda(c);
        return c;
    }

    public Comanda buscarComanda(int id) throws ComandaNaoCadastradaException {
        return repositorioComanda.buscarComanda(id);
    }
    
    public List<Comanda> getAllComanda(){
    	return repositorioComanda.getAll();
    }
    
    
    
  ///CONSUMO//////////////////////////////////////////////////////////////////////////////

    public void adicionarnoConsumo(int numeroComanda, int id) throws ComandaNaoCadastradaException, ItemNaoEncontradoException {
        Comanda comanda = buscarComanda(numeroComanda);
        Consumo consumo = new Consumo(repositorioCardapio.buscarOpcao(id), id);
        if (comanda != null) {
            comanda.adicionarConsumo(consumo);
        }
    }

    
    //GET SET///////////////////////////////////////////////////////////////////////////////////////////


	public RepositorioComanda getRepositorioComanda() {
		return repositorioComanda;
	}


	public void setRepositorioComanda(RepositorioComanda recomanda) {
		this.repositorioComanda = recomanda;
	}
	

	public RepositorioCardapio getRecardapio() {
		return repositorioCardapio;
	}


	public void setRecardapio(RepositorioCardapio recardapio) {
		this.repositorioCardapio = recardapio;
	}


	private void loadData() {
		try {
			FileInputStream f = new FileInputStream(file);
			ObjectInputStream o = new ObjectInputStream(f);

			repositorioComanda = (RepositorioComanda) o.readObject();
			repositorioCardapio = (RepositorioCardapio) o.readObject();
			

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
			o.writeObject(repositorioComanda);
			o.writeObject(repositorioCardapio);
		

			o.close();
			f.close();
		} catch (IOException e) {
			System.err.println("Erro de serialização de objeto");
			e.printStackTrace();
		}
	}
   
    
}

