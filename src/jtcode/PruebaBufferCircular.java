package jtcode;

class Productor extends Thread{
	private IBufer compartido;
	
	public Productor(IBufer elbufer){
		super("Productor");
		this.compartido=elbufer;
	}
	
	@Override
	public void run(){
		for(int i=0;i< 20;i++){
			try {
				Thread.sleep((int)Math.random()*3001);
				compartido.escribir(i);
				
			} catch (InterruptedException e) {e.printStackTrace();}
			
		}
		System.out.println(getName() + "termino de producir datos");
	}
}
class Consumidor extends Thread{
	
private IBufer compartido;
	
	public Consumidor(IBufer elbufer){
		super("Consumidor");
		this.compartido=elbufer;
	}
	
	@Override
	public void run(){
		int suma=0;
		for(int i=0;i< 10;i++){
			try {
				Thread.sleep((int)Math.random()*3001);
				suma+=compartido.leer();
				
			} catch (InterruptedException e) {e.printStackTrace();}
			
		}
		System.out.println(getName() + "termino de leer un total de "+suma);
	}
	
}
public class PruebaBufferCircular {

	public static void main(String[] args) {
		IBufer elBuffer=new BufferCircular();
		
		Productor prod=new Productor(elBuffer);
		Consumidor consu= new Consumidor(elBuffer);
		Consumidor consu2= new Consumidor(elBuffer);
		
		prod.start();
		consu.start();
		consu2.start();
		
		try {
			consu.join();prod.join();
			consu2.join();
			
		} catch (InterruptedException e) {e.printStackTrace();}
		
	}

}
