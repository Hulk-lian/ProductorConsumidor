package jtcode;

public class BufferCircular implements IBufer {
	
	private int buffer[]={-1,-1,-1};
	private int contadorOcupado=0;
	private int posLectura=0;
	private int posEscritura=0;
	
	@Override
	public synchronized void escribir(int valor) {
		String hiloLLamador= Thread.currentThread().getName();
		
		while (contadorOcupado== buffer.length) {
			
			try {
				System.err.println(hiloLLamador+" trata de escribir");
				mostrarEstado("Buffer lleno. "+hiloLLamador+" espera");
				mostrarSalida();
			
				wait();
			} catch (InterruptedException e) {e.printStackTrace();}
		}
		buffer[posEscritura]=valor;
		contadorOcupado++;
		posEscritura=(posEscritura + 1)%buffer.length;
		mostrarSalida();
		mostrarEstado(hiloLLamador+" consigue escribir "+valor);
		
		notify();
		//notify porque solosabes que va a pasar 1 despierta al que esta mas tiempo en el wait
		//solo se quiere que pase uno, el que sea, pero que solo se despierte uno
	}

	@Override
	public synchronized int leer() {
		
		String hiloLLamador= Thread.currentThread().getName();
		
		while (contadorOcupado== 0) {
			try {
			System.err.println(hiloLLamador+" trata de leer");
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
		StringBuffer linea= new StringBuffer(cadena);
		linea.setLength(80);
		linea.append(this.buffer + "  "+ contadorOcupado );
		System.out.println(linea+"\n");
		
	}

	@Override
	public String mostrarSalida() {
		String salida="(huecos ocupados "+contadorOcupado+")\nhuecos: ";
		
		for (int i = 0; i < buffer.length; i++) {
			salida+="  "+buffer[i]+ "  ";
		}
		
		salida+="\n        ";
		
		for (int i = 0; i < buffer.length; i++) {
			salida +="---- ";
		}
		salida+="\n        ";
		for (int i = 0; i < buffer.length; i++) {
			if(i==posEscritura && posEscritura==posLectura){
				salida += " E/L ";
			}
			else if (i==posEscritura) {
				salida+="  E  ";
			}
			else if (i==posLectura) {
				salida+="  L  ";
			}
			else {
				salida+="     ";
			}
		}
		salida+="\n        ";
		System.out.println(salida);
		return salida;
	}

}
