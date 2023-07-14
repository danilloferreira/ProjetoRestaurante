package app;
import java.io.IOException;
import java.util.Scanner;
import facade.RestauranteException;
import facade.RestauranteFacade;
import model.comanda.Comanda;
import model.consumo.Consumo;
import model.itemcardapio.ComidaIndustrial;
import model.itemcardapio.ComidaTradicional;
import model.itemcardapio.ItemCardapio;
import repository.RepositoryException;
import repository.comanda.MesaComandaAbertaException;

public class App {
	private static Scanner s = new Scanner(System.in);
	private static RestauranteFacade r;
	private static int csz;

	public static void main(String[] args) {
		r = RestauranteFacade.getInstance();
		int x=0;
		do{
			csz=r.getRepositorioComanda().getAll().size();
			System.out.println("MENU PRINCIPAL");
			System.out.println("==== =========");
			System.out.println("[Comandas: "+csz+"]");
			System.out.println("<1> Cadastro do Cardapio");
			System.out.println("<2> Cadastro de Comanda");
			System.out.println("<3> Fechar o Dia");
			System.out.println("<0> Sair");
			System.out.println();
			System.out.print("Escolha uma opcao: ");
			s = new Scanner(System.in);
			
			try {
				x = Integer.valueOf(s.nextLine());
			} catch (Exception e) {
				x = 0;
			}
			
			switch (x) {
			case 0:
				clear();
				break;
			case 1:
				cardapio();
				break;
			case 2:
				comanda();
				break;
			case 3:
				fecharDia();
				break;
			}
		}while(x!=0);
		
		r.exit();
		System.out.println("\nFIM\n");

	}
	
	
	/////CADASTRO CARDAPIO/////////////////////////////////////////////////////////////////////
	public static void cardapio(){
		int x;
		do {
			clear();
			System.out.println("\nCARDAPIO\n=====================");
			System.out.println("<1> Adicionar Opcao");
			System.out.println("<2> Remover Opcao");
			System.out.println("<3> Detalhar");
			System.out.println("<0> Voltar");
			System.out.println();
			System.out.print("Escolha uma opcao: ");
			try {
				x = Integer.valueOf(s.nextLine());
			} catch (Exception e) {
				x = 0;
			}
			s = new Scanner(System.in);
			switch (x) {
			case 0:
				clear();
				break;
			case 1:
				criarCardapio();
				break;
			case 2:
				removerCardapio();
				break;
			case 3:
			 clear();
			 if(r.getAllCardapio().size()>0) {
				System.out.println(detalharCardapio());
			 }else {
				System.out.println("Cardapio Vazio");
			 }
				System.out.println("ENTER: Voltar");
				s.nextLine();
				break;
			}
			
			
			}while(x!=0);
			x=-1;
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////
	public static void criarCardapio(){
	    int x=1, quant=0;
	    String nome=null, tipo=null, descri=null;
	    double custo=0.0, valorVenda=0.0;
	    
		while(x!=0) {	
			clear();
			ItemCardapio item = null;
			s = new Scanner(System.in);
			System.out.println("\nNome do Item: ");  nome = s.nextLine();
			System.out.println("Custo do Item: ");   custo = s.nextDouble();
			System.out.println("Valor de Venda: ");  valorVenda = s.nextDouble();
			
			do {
				s = new Scanner(System.in);
				System.out.print("Tipo de Itens: (T)radicional, (I)ndustrializado ou <ENTER> para sair? ");
				tipo = s.nextLine();
				if (tipo.equalsIgnoreCase("T")) {
					System.out.println("Descricao: ");descri = s.nextLine();
					item = new ComidaTradicional(nome, custo, valorVenda, descri);
				} else if (tipo.equalsIgnoreCase("I")) {
					System.out.println("Quantidade: ");quant = s.nextInt();
					item = new ComidaIndustrial(nome, custo, valorVenda, quant);
				}
			} while (!(item != null || tipo.equals("")));
			
			clear();
			if (item != null) {
				try {
					item = r.criarCardapio(item);
					System.out.println("Item " + item.getId() +", "+ item.getNome() + " criada!");
				} catch (RepositoryException ex) {
					System.err.println(ex.getMessage());
				} 
			} else {
				System.out.println("Nenhum item adicionado ao cardapio");
			}
			System.out.println("<ENTER> OK");s.nextLine();
			clear();
			System.out.println(detalharCardapio());
			System.out.println("<1> Adicionar\n<0> Voltar\n"); x = s.nextInt();
			s = new Scanner(System.in);//LIMPAR O SCANNER
	   }
	}
	
	/////////////////////////////////////////////////////////////////////////////
	
	public static void removerCardapio(){
	    int x=0, id=0;
	    
		do{
			clear();
			s = new Scanner(System.in);
			System.out.println(detalharCardapio());
			try {
				System.out.println("ID: ");  id = s.nextInt();
				ItemCardapio item = r.buscarCardapio(id);
				clear();
				System.out.println();
				System.out.println("ID: " + item.getId());
				System.out.println("Tipo: " + item.getTipo());
				System.out.println("Valor: " + item.getValor());
				System.out.println("Lucro Bruto: " + (item.getValor()-item.getCusto()));
				System.out.println();
				System.out.print("Excluir este Item? (s/n)?");
				s = new Scanner(System.in);
				String resposta = s.nextLine();
				if (resposta.equalsIgnoreCase("s")) {
					try {
						r.removeCardapio(item);
						System.out.println("\nItem Excluido!");
					} catch (RepositoryException | RestauranteException ex) {
						System.err.println(ex.getMessage());
					} 
					System.out.println("<ENTER> Voltar");s.nextLine();
				}
				clear();
			} catch (RepositoryException ex) {
				clear();
				System.err.println(ex.getMessage());
			} 
			System.out.println(detalharCardapio());
			System.out.println("<1> Remover Outro\n<0> Voltar\n"); x = s.nextInt();
		}while(x!=0);
	}
	
	////////////////////////////////////////////////////////////////////////////////////
	
	 public static String detalharCardapio() {
	   	 StringBuilder a = new StringBuilder();
	     StringBuilder b = new StringBuilder();
	     a.append("-----------------------------------------------\nTradicional\n===================\n");
	     b.append("-----------------------------------------------\nIndustrializado\n===================\n");
	     for (ItemCardapio cardapio : r.getAllCardapio()) {
	         String tipoItem = cardapio.getTipo();
	         if (tipoItem.equalsIgnoreCase("Produto Industrializado")) {
	         	b.append(cardapio).append("\n-----------------------------------------------\n");
	         }else if((tipoItem.equalsIgnoreCase("Comida Tradicional"))) {
	        	a.append(cardapio+"\n-----------------------------------------------\n");
	         }
	     }
	     return a.toString()+b.toString();
	}
		
	 
	///CADASTRO DE COMANDAS////////////////////////////////////////////////////////////////////////////////
	public static void comanda(){
		int x=0;
		do{
			clear();
			s = new Scanner(System.in);
			System.out.println("CADASTRO DE COMANDAS");
			System.out.println("=======================");
			csz=r.getRepositorioComanda().getAll().size();
			
			if(csz<=0) {System.out.println("Repositorio Vazio\n---------------------------");}
			System.out.println("<1> Cadastrar Nova Comanda");
			if(csz>0) {System.out.println("<2> Consultar Comanda");}
			System.out.println("<0> Voltar");
			System.out.println("\nEscolha: ");
			x=s.nextInt();
			if(x==1) {
				criarComanda();
			}else if(x==2) {
				consultarComanda();
			}
    	}while(x!=0);
		clear();
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////
	public static void criarComanda() {
		int mesa;
		clear();
		System.out.println("Por favor, digite o numero da mesa:");
		mesa=s.nextInt();
		Comanda comanda = new Comanda(mesa);
        try {
        	r.criarComanda(comanda);
        	System.out.println("Comanda #"+comanda.getNumero()+", mesa "+comanda.getMesa()+", criada!!");
		} catch (RepositoryException ex) {
			System.err.println(ex.getMessage());
		} 
        s = new Scanner(System.in);
		System.out.println("<ENTER> Voltar"); s.nextLine();
	}
	///////////////////////////////////////////////////////////////////////////////////////////
	public static String listarComandas() {
   	 String a="";
   	 for (Comanda comanda : r.getAllComanda()) {
             a+=comanda+"\n-----------------------------------------------\n";
        }
        return a;
    }
	///////////////////////////////////////////////////////////////////////////////////////////
	public static void consultarComanda() {
		int mesa=0, x=0, status;
		s = new Scanner(System.in);
		clear();
		System.out.println("CONSULTA DE COMANDA");
		System.out.println("=======================");
		System.out.println("\n"+listarComandas());
    	System.out.println("Mesa: ");
    	mesa=s.nextInt();
    	try {
    		Comanda comanda=r.buscarComanda(mesa);
        	do{
        		    clear();
        		    System.out.println("CONSULTA DE COMANDA");
        		    System.out.println("=======================");
        		    int a=comanda.getAll().size();
        		    String str = comanda.getStatus();
        			System.out.println(comanda);
        			if(str.equalsIgnoreCase("Pendente")){
        				status=1;
    	    			if(a>0) {
    	    				
    	    				if(r.getAllCardapio().size()>0) {
    	    				       System.out.println("\n<1> Pedido\n<2> Remover Pedido\n<3> Detalhar Comanda\n<4> Excluir Comanda\n<5> Pagar\n<0> Voltar\n");
    	    				}else {
    	    					   System.out.println("\n<2> Remover Pedido\n<3> Detalhar Comanda\n<4> Excluir Comanda\n<5> Pagar<0> Voltar\n");
    	    				}
    	    			}else {
    	    				if(r.getAllCardapio().size()>0) {
    	 				       System.out.println("\n<1> Pedido\n<3> Detalhar Comanda\n<4> Excluir Comanda\n<0> Voltar\n");
    	 				    }else {
    	 					   System.out.println("\n<3> Detalhar Comanda\n<4> Excluir Comanda\n<0> Voltar\n");
    	 				    }
    	    		    }
        			}else {
        				status = 0;
        				System.out.println("\n<3> Detalhar Comanda\n<4> Excluir Comanda\n<0> Voltar\n");
        			}
        			x=s.nextInt();
        			s = new Scanner(System.in);
    		    	if((x==1)&&(status==1)) {adicionarConsumo(comanda);
    		    	}else if((x==2)&&(a>0)&&(status==1)) {removerConsumo(comanda);
    		    	}else if(x==3) {
    		    		clear();
    		    		if(a>0) {
    		    			System.out.println(comanda.relatorio()+"\nENTER: Voltar");s.nextLine();
    		    	    }else {
    		    	    	System.out.println("Comanda Vazia\nENTER: Voltar");s.nextLine();}
    		    	}else if(x==4) {
    		    		removerComanda(comanda);
    		    		csz=r.getRepositorioComanda().getAll().size();
    		    		x=0;
    		    	}else if((x==5)&&(status==1)) {
    		    		pagar(comanda);
    		    	}
    	    	    
        	}while((x!=0)&&(csz>0));clear();
        	
		} catch (RepositoryException ex) {
			System.err.println(ex.getMessage());
			s = new Scanner(System.in);//limpar o scanner
			System.out.println("ENTER: Voltar");s.nextLine();
		} 
    	
    	x=0;
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
public static void removerComanda(Comanda comanda) {
	clear();
	System.out.print("Exclui essa comanda? (s/n)?");
	String resposta = s.nextLine();
	if (resposta.equalsIgnoreCase("s")) {
		    try {
			    r.removerComanda(comanda);
				System.out.println("Comanda excluida!");
			} catch (RepositoryException ex) {
				System.err.println(ex.getMessage());
			} 
		System.out.println("<ENTER> Voltar");s.nextLine();
	}
}
/////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void pagar(Comanda comanda) {
		clear();
		s = new Scanner(System.in);
		System.out.println(comanda.relatorio());
		System.out.print("Pagar? (s/n)?");
		String resposta = s.nextLine();
		if (resposta.equalsIgnoreCase("s")) {
			comanda.pagar();
		}
		
	}
	
	///CONSUMO///////////////////////////////////////////////////////////////////////////////////////
	
	public static void adicionarConsumo(Comanda comanda){
		s = new Scanner(System.in);
		int x=0, id=0, quant = 0;
		x=1;
		while(x==1) {
			clear();
			s = new Scanner(System.in);
			System.out.println(apresentarCardapio()+"\nID: "); id=s.nextInt();
			System.out.println("Quantidade: "); quant=s.nextInt();
			try {
				if(quant>0) {
					ItemCardapio item = r.buscarCardapio(id);
					if(item.getTipo().equalsIgnoreCase("Produto Industrializado")) {
						int quantItem=((ComidaIndustrial)item).getQuant();
						if(quantItem>0) {
							if(quantItem>=quant) {
								((ComidaIndustrial)item).setQuant(quantItem-quant);
								Consumo consumo = new Consumo(item, quant);
								comanda.adicionarConsumo(consumo);
								System.out.println(comanda);
							}else {
								System.out.println("Quantidade acima do disponivel");
							}
						}else {
							System.out.println("Esgotado");
						}
					}else {
						Consumo consumo = new Consumo(item, quant);
					    comanda.adicionarConsumo(consumo);
					    System.out.println(comanda);}
				}else {
					clear();
					System.out.println("Por favor, digite uma quantidade acima de 0");
				}
			} catch (RepositoryException ex) {
				System.err.println(ex.getMessage());
			} 
			
			System.out.println("\n<1> Adicionar\n<0> Voltar"); x = s.nextInt();
		}
	}
	
	/////////////////////////////////////////////////////////////////////////
	public static String apresentarCardapio() {
		 StringBuilder a = new StringBuilder();
	     StringBuilder b = new StringBuilder();
	     a.append("-----------------------------------------------\nTradicional\n===================\n");
	     b.append("-----------------------------------------------\nIndustrializado\n===================\n");
	     for (ItemCardapio cardapio : r.getAllCardapio()) {
	         String tipoItem = cardapio.getTipo();
	         if (tipoItem.equalsIgnoreCase("Produto Industrializado")) {
	         	b.append(cardapio.Show()+"\n-----------------------------------------------\n");
	         }else if((tipoItem.equalsIgnoreCase("Comida Tradicional"))) {
	        	a.append(cardapio.Show()+"\n-----------------------------------------------\n");
	         }
	     }
	     return a.toString()+b.toString();
     }
    /////////////////////////////////////////////////////////////////////////
	
	public static void removerConsumo(Comanda comanda){
		s = new Scanner(System.in);
		int x=0, id=0, quant = 0;
		x=1;
		while(x==1) {
			clear();
			s = new Scanner(System.in);
			System.out.println(comanda.relatorio()+"\nID: "); id=s.nextInt();
			System.out.println("Quantidade: "); quant=s.nextInt();
			try {
				Consumo consumo = comanda.retornarConsumo(id);
				int quantConsumo=consumo.getQuant();
				if(quant<quantConsumo) {
				     consumo.setQuant(quantConsumo-quant);
				}else {
					comanda.removerConsumo(consumo);
				}
				clear();
			} catch (RepositoryException ex) {
				clear();
				System.err.println(ex.getMessage());
			} 
			comanda.atualizaTotal();
			System.out.println(comanda); 
			System.out.println("<1> Remover outro Item\n<0> Voltar\n"); x = s.nextInt();
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////////////
	public static void fecharDia(){
		clear();
		double valorBruto=0.0, lucro=0.0, custo = 0.0, prejuiso = 0.0;
		for(Comanda comanda: r.getAllComanda()) {
			if(comanda.getStatus().equalsIgnoreCase("Pago")) {
				valorBruto += comanda.getTotal();
				for(Consumo consumo: comanda.getAll()) {
						custo+=(consumo.getItem().getCusto()*consumo.getQuant());
				}
			}else if(comanda.getStatus().equalsIgnoreCase("Pendente")){
				for(Consumo consumo: comanda.getAll()) {
					prejuiso+=(consumo.getItem().getCusto()*consumo.getQuant());
			   }
			}
		}
		lucro=valorBruto-(prejuiso+custo);
		
		System.out.println("TOTAL DO DIA");
		System.out.println("============================");
		System.out.println("Valor Bruto: "+ valorBruto);
		System.out.println("Custo: "+ (custo+prejuiso));
		if(lucro<0) {
			lucro*=-1;
			System.out.println("Prejuiso: "+ lucro);
		}else {
			System.out.println("Lucro: "+ lucro);
		}
		System.out.println("\n<ENTER> voltar");
		s=new Scanner(System.in);
		s.nextLine();
		clear();
	}
	
	public static void clear() {
	
		for(int i=0; i<20;i++) {
			System.out.println("\n");
		}
      
	}

}
