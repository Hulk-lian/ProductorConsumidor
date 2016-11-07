package jtcode;

public class BufferCircular implements IBufer {
	
	private int buffer[]={-1,-1,-1};
	private int contadorOcupado=0;
	private int posLectura=0;
	private int posEscritura=0;
	
	@Override
	public void escribir(int valor) {
		String hiloLLamador= Thread.currentThread().getName();
		while (contadorOcupado== buffer.length) {
			System.out.println(hiloLLamador+"trata de escribir");
			mostrarEstado("Buffer lleno. "+hiloLLamador+" espera");
			mostrarSalida();
			try {
				wait();
			} catch (InterruptedException e) {e.printStackTrace();}
		}
		buffer[posEscritura]=valor;
		contadorOcupado++;
		posEscritura=(posEscritura + 1)%buffer.length;
		mostrarSalida();
		mostrarEstado(hiloLLamador+" consigue escribir "+valor);
		
		notify();
		//notify porque es un buffer para cada cliente
	}

	@Override
	public int leer() {
		
		String hiloLLamador= Thread.currentThread().getName();
		
		while (contadorOcupado== 0) {
			try {
			System.out.println(hiloLLamador+"trata de leer");
			mostrarEstado("Buffer vacio. "+hiloLLamador+" debe esperar");
			mostrarSalida();
			
				wait();
			} catch (InterruptedException e) {e.printStackTrace();}
		}
		int valor = buffer[posEscritura];
		contadorOcupado--;
		posLectura=(posLectura + 1)%buffer.length;
		mostrarSalida();
		mostrarEstado(hiloLLamador+" consigue leer "+valor);
		
		notify();
		
		return valor;
	}

	@Override
	public void mostrarEstado(String cadena) {
		
	}

	@Override
	public void mostrarSalida() {
		
	}

}
