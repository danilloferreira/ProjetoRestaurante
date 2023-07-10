package repository.cardapio;
import pattern.IdGeneratorStrategy;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.comanda.Comanda;
import model.itemcardapio.ItemCardapio;
import model.pedido.Pedido;

public class ReCardapioList implements ReCardapio, Serializable{
	IdGeneratorStrategy idGenerator;
	List<ItemCardapio> itens;
	
	public ReCardapioList() {
		itens = new ArrayList<>();
		idGenerator = new GeneratorCardapio();
	}

	@Override
	public void adicionarItem(ItemCardapio item) {
		item.setId(idGenerator.nextId());
		itens.add(item);
		
	}

	@Override
	public void removerItem(ItemCardapio item) {
		itens.remove(item);
		
	}

	@Override
	public List<ItemCardapio> getAll() {
		return new ArrayList<>(itens);
	}	
	
	@Override
	public int SZ() {
		return itens.size();
	}	
	
	public void reajustarCardapio() {
		int id=0;
		for (ItemCardapio item : itens) {
            if (item.getId() != id) {
            	item.setId(id);
            }
            id++;
        }
		
	}
	
	public ItemCardapio buscarOpcao(int id) {
		for (ItemCardapio item : itens) {
            if (item.getId() == id) {
            	return item;
            }
            
        }
		return null;
		
	}
	
}


