package model.comanda;

import repository.RepositoryException;

public class ConsumoNaoEncontradoException extends RepositoryException{
	public  ConsumoNaoEncontradoException(){
        super("Consumo nao Encontrado!");
    }

}
