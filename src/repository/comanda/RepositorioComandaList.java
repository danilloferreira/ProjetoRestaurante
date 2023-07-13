package repository.comanda;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.comanda.Comanda;
import pattern.IdGeneratorStrategy;
import repository.cardapio.GeneratorCardapio;


public class RepositorioComandaList implements RepositorioComanda, Serializable {
	IdGeneratorStrategy idGenerator;
	List<Comanda> comandas;
	

	public RepositorioComandaList() {
		comandas = new ArrayList<Comanda>();
		idGenerator = new GeneratorComanda();
	}

	@Override
	public void criarComanda(Comanda c) throws MesaComandaAbertaException{
		c.setNumero(idGenerator.nextId());
		try {
			buscarComanda(c.getMesa());
			throw new MesaComandaAbertaException();
		}catch(ComandaNaoCadastradaException ex) {
			comandas.add(c);
		}
		
	}

	@Override
	public void removerComanda(Comanda c)throws ComandaNaoCadastradaException {
		if(!comandas.remove(c)) {
			throw new ComandaNaoCadastradaException();
		}
		
	}

	@Override
	public Comanda buscarComanda(int mesa) throws ComandaNaoCadastradaException{
		for (Comanda comanda : comandas) {
            if (comanda.getMesa() == mesa) {
            	return comanda;
            }
        }
		throw new ComandaNaoCadastradaException();
	}

	@Override
	public void alterarComanda(int mesa) throws ComandaNaoCadastradaException{
		buscarComanda(mesa);
		//ignora, tava no do professor
	}

	@Override
	public List<Comanda> getAll() {
		return new ArrayList<>(comandas);
	}

}
