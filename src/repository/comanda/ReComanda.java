package repository.comanda;

import java.util.List;

import model.comanda.Comanda;

public interface ReComanda {
	void criarComanda(Comanda c);
	void removerComanda(Comanda c);
	Comanda buscarComanda(int mesa);
	void alterarComanda(int mesa);
	List<Comanda> getAll();
	
}
