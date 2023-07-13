package repository.comanda;

import repository.RepositoryException;

public class ComandaNaoCadastradaException extends RepositoryException{
	public  ComandaNaoCadastradaException(){
        super("Mesa sem Comanda");
    }
}
