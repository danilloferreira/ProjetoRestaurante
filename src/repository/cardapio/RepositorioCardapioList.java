package repository.cardapio;
import pattern.IdGeneratorStrategy;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//import model.comanda.Comanda;
import model.itemcardapio.ItemCardapio;
//import model.pedido.Pedido;

public class RepositorioCardapioList implements RepositorioCardapio, Serializable{
	IdGeneratorStrategy idGenerator;
	List<ItemCardapio> itens;
	
	public RepositorioCardapioList() {
		itens = new ArrayList<>();
		idGenerator = new GeneratorCardapio();
	}

	@Override
	public void adicionarItem(ItemCardapio item) throws ItemJaCriadoException {
		item.setId(idGenerator.nextId());
		try {
			buscarOpcao(item.getNome());
			throw new ItemJaCriadoException();
		}catch(ItemNaoEncontradoException ex) {
			itens.add(item);
		}
			
		
	}

	@Override
	public void removerItem(ItemCardapio item) throws ItemNaoEncontradoException {
		if(!itens.remove(item)) {
			throw new ItemNaoEncontradoException();
		}
		
	}

	@Override
	public List<ItemCardapio> getAll() {
		return new ArrayList<>(itens);
	}	
	
	@Override
	public int SZ() {
		return itens.size();
	}	
	
	@Override
	public ItemCardapio buscarOpcao(int id) throws ItemNaoEncontradoException{
		for (ItemCardapio item : itens) {
            if (item.getId() == id) {
            	return item;
            }
            
        }
		throw new ItemNaoEncontradoException();
	}
	
	@Override
	public ItemCardapio buscarOpcao(String nome) throws ItemNaoEncontradoException{
		for (ItemCardapio item : itens) {
            if (item.getNome().equalsIgnoreCase(nome)) {
            	return item;
            }
            
        }
		throw new ItemNaoEncontradoException();
		
	}
	
}


