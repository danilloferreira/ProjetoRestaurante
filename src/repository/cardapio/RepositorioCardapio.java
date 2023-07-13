package repository.cardapio;
import java.util.List;
import model.itemcardapio.ItemCardapio;

public interface RepositorioCardapio {
	void adicionarItem(ItemCardapio ic) throws ItemJaCriadoException;
	void removerItem(ItemCardapio ic) throws ItemNaoEncontradoException;
	ItemCardapio buscarOpcao(int id) throws ItemNaoEncontradoException;
	ItemCardapio buscarOpcao(String nome) throws ItemNaoEncontradoException;
	int SZ();
	List<ItemCardapio> getAll();
	
}
