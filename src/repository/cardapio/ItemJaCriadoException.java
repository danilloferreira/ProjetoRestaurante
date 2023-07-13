package repository.cardapio;

import repository.RepositoryException;

public class ItemJaCriadoException extends RepositoryException{
	public  ItemJaCriadoException(){
        super("O cardapio possui um  item com mesmo nome!");
    }
}
