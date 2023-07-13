package repository.cardapio;

import repository.RepositoryException;

public class ItemNaoEncontradoException extends RepositoryException{
	public  ItemNaoEncontradoException(){
        super("Item nao encontrado!");
    }
}
