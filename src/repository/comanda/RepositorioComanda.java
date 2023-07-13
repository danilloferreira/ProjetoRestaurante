package repository.comanda;

import java.util.List;

import model.comanda.Comanda;

public interface RepositorioComanda {
	void criarComanda(Comanda c) throws MesaComandaAbertaException;
	void removerComanda(Comanda c) throws ComandaNaoCadastradaException;
	Comanda buscarComanda(int mesa) throws ComandaNaoCadastradaException;
	void alterarComanda(int mesa) throws ComandaNaoCadastradaException;
	List<Comanda> getAll();
	
}
