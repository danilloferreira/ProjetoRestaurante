package restaurante;
import model.comanda.Comanda;
import model.itemcardapio.ItemCardapio;

public class Validador {
    public static void validarComanda(Comanda comanda, int numeroComanda) {
        if (comanda == null) {
            throw new IllegalArgumentException("Comanda com o número " + numeroComanda + " não encontrada.");
        }
    }

    public static void validarItemCardapio(ItemCardapio itemCardapio, int id) {
        if (itemCardapio == null) {
            throw new IllegalArgumentException("Item do cardápio com o id " + id + " não encontrado.");
        }
    }
}
