package dominio;

import dominio.repositorio.RepositorioProducto;
import dominio.repositorio.RepositorioGarantiaExtendida;
import java.util.*;

public class Vendedor {

    public static final String EL_PRODUCTO_TIENE_GARANTIA = "El producto ya cuenta con una garantia extendida";

    private RepositorioProducto repositorioProducto;
    private RepositorioGarantiaExtendida repositorioGarantia;

    public Vendedor(RepositorioProducto repositorioProducto, RepositorioGarantiaExtendida repositorioGarantia) {
        this.repositorioProducto = repositorioProducto;
        this.repositorioGarantia = repositorioGarantia;

    }

    public void generarGarantia(String codigo, String nombreCliente) {

    	   //throw new UnsupportedOperationException("Método pendiente por implementar");
    	int contVocales = 0;
    	Producto producto = repositorioProducto.obtenerPorCodigo(codigo);    	
    	double valor = producto.getPrecio();
    	double precioGarantia;
    	int contadorLunes = 0;
    	Date fechaActual =  new Date();
    	Date fechaSolicitudGarantia = fechaActual; 
    	Date fechaFinGarantia;
    	Calendar calFechaFinGarantia = Calendar.getInstance();
    	calFechaFinGarantia.setTime(fechaActual);
    	
    	Calendar calFechaSolicitudGarantia = Calendar.getInstance();
    	calFechaSolicitudGarantia.setTime(fechaActual);
    	
    			
    	for (int i = 0; i < codigo.length(); i++) {
    		if ((codigo.charAt(i)=='a') || (codigo.charAt(i)=='e') || (codigo.charAt(i)=='i') || (codigo.charAt(i)=='o') || (codigo.charAt(i)=='u')){
    			contVocales++;
    		  }
    	}
    	
    	if(contVocales >= 3) {
    		throw new UnsupportedOperationException("Este producto no cuenta con garantía extendida");
    	}
    	    	
    	if (valor > 500000) {
    		precioGarantia = valor + ((valor * 20)/100);
    		calFechaFinGarantia.add(calFechaFinGarantia.DAY_OF_YEAR, 200);
    		    		   		
    		while (!calFechaSolicitudGarantia.after(calFechaFinGarantia)) {
    			int dia = calFechaSolicitudGarantia.DAY_OF_WEEK; 
    			if (dia == 2) {
    				contadorLunes ++;
    			}    				
            }
    		
    		int diaFinal = calFechaFinGarantia.DAY_OF_WEEK; 
    		if (diaFinal == 1) {
    			calFechaFinGarantia.add(calFechaFinGarantia.DAY_OF_YEAR, 1);
			} 
    		
    		calFechaFinGarantia.add(calFechaFinGarantia.DAY_OF_YEAR, contadorLunes);
    		fechaFinGarantia = calFechaFinGarantia.getTime();
    		
    	} else {
    		precioGarantia = valor + ((valor * 10)/100);
    		calFechaFinGarantia.add(calFechaFinGarantia.DAY_OF_YEAR, 100);
    		fechaFinGarantia = calFechaFinGarantia.getTime();
    	}
    	
    	repositorioGarantia.agregar(new GarantiaExtendida(producto, fechaSolicitudGarantia, fechaFinGarantia, precioGarantia, nombreCliente) );


    }

    public boolean tieneGarantia(String codigo, String nombreCliente) {
    	
    	Producto garantia = repositorioGarantia.obtenerProductoConGarantiaPorCodigo(codigo);  
    		
    	 if (garantia == null ) {
    		 return false;
    	 } else {
    		 return true;
    	 }    	  	
    	
    }

}
