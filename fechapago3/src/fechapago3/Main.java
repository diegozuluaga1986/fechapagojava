package fechapago3;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Scanner;
public class Main {
	
	private static Calendar calendario = Calendar.getInstance();
	private static SimpleDateFormat format= new SimpleDateFormat("dd-MM-yyyy");
	private static String[] festiveDays = {"01-01-2023","01-05-2023","16-10-2023"};
	private static String[] MES = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
	private static String[] DIA = {"Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};
					
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println ("Por favor introduzca una fecha:"); 
		Scanner entradaEscaner = new Scanner (System.in);
		String fechaIni = entradaEscaner.nextLine ();
		
		System.out.println( "la fecha de pago es: "+ fechaPago(fechaIni));
		
	}
	
	private static String fechaPago(String fechaIni) {
		try {
			int contFestivoForward = 0;
	    	int contFestivobackrward = 0;
			Date fechahoy = format.parse(fechaIni);
			calendario.setTime(fechahoy);

		    switch (DIA[calendario.get(Calendar.DAY_OF_WEEK) - 1]) { 
		    case "Sabado":
		    	contFestivoForward = quantityFestive(2, 7, true, 2, true);
		    	contFestivobackrward = quantityFestive(1, 6, false, -1, true);
		    	
		    	if (contFestivoForward < contFestivobackrward) {
		    		calendario.add(calendario.DATE, contFestivoForward +2);
		    	} else {
		    		calendario.add(calendario.DATE, (contFestivoForward * -1) -1);
		    	}
		    	
		    	return format.format(calendario.getTime());
		    case "Domingo":
		    	contFestivoForward = quantityFestive(1, 6, true, 1, false);
		    	contFestivobackrward = quantityFestive(2, 7, false, -2, false);
		    	
		    	if (contFestivoForward < contFestivobackrward) {
		    		calendario.add(calendario.DATE, contFestivoForward +1);
		    	} else {
		    		calendario.add(calendario.DATE, (contFestivoForward * -1) -2);
		    	}
		    	
		    	return format.format(calendario.getTime());
		    default:
		    	return format.format(fechahoy);
		    }
		} catch (ParseException e) {
            return format.format(e);
            
        }
	}
	
	private static int quantityFestive(int forIni, int forEnd, boolean isForward, int addDay, boolean isSaturday) {
			int numAddDate = isSaturday && !isForward ? -1 : 1;
			int cont = 0;
	        for (int i = forIni; i < forEnd; i++){
	        	if(!isForward) {
	        		numAddDate = (i== 2) ? addDay : -1;
	        	} else {
	        		numAddDate = isSaturday && (i== 2) ? addDay : 1;
	        	}
	        	calendario.add(calendario.DATE, numAddDate);
	        	int posicion = Arrays.asList(festiveDays).indexOf(format.format(calendario.getTime()));
		    	
	        	if (posicion >=0) {
	        		cont += 1;
		    	} else {
		    		calendario.add(calendario.DATE, !isForward ? i: (i*-1));
		    		break;
		    	}
	    	}
	        
	        return cont;
	    }
}
