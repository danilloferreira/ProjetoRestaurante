package repository.comanda;

import repository.RepositoryException;

public class MesaComandaAbertaException extends RepositoryException{
	public  MesaComandaAbertaException(){
        super("Mesa com Comanda Aberta");
    }
}
