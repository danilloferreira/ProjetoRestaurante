package repository.comanda;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.comanda.Comanda;
import pattern.IdGeneratorStrategy;
import repository.cardapio.GeneratorCardapio;

public class ReComandaList implements ReComanda, Serializable {
	IdGeneratorStrategy idGenerator;
	List<Comanda> comandas;
	

	public ReComandaList() {
		comandas = new ArrayList<Comanda>();
		idGenerator = new GeneratorComanda();
	}

	@Override
	public void criarComanda(Comanda c) {
		c.setNumero(idGenerator.nextId());
		comandas.add(c);
	}

	@Override
	public void removerComanda(Comanda c) {
		comandas.remove(c);
		
	}

	@Override
	public Comanda buscarComanda(int mesa) {
		for (Comanda comanda : comandas) {
            if (comanda.getMesa() == mesa) {
            	return comanda;
            }
        }
		return null;
	}

	@Override
	public void alterarComanda(int mesa) {
		buscarComanda(mesa);
		
	}

	@Override
	public List<Comanda> getAll() {
		return new ArrayList<>(comandas);
	}

}
