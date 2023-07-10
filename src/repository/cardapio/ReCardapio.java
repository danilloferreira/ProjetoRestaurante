package repository.cardapio;
import java.util.List;
import model.itemcardapio.ItemCardapio;

public interface ReCardapio {
	void adicionarItem(ItemCardapio ic);
	void removerItem(ItemCardapio ic);
	List<ItemCardapio> getAll();
	int SZ();
	void reajustarCardapio();
	ItemCardapio buscarOpcao(int id);
}
