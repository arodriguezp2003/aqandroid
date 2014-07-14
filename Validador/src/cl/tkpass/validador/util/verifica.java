package cl.tkpass.validador.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



import android.content.Context;

import cl.tkpass.validador.sql.BaseHandler;
import cl.tkpass.validador.sql.Brazaletes;

public class verifica {
		private Context context ;
		public  String Mensaje;
		public int Tipo;
		public verifica(Context con) {
			context= con;
		}
		public boolean Verificar_Brazalete(String data) throws ParseException
		{
			//1 verifcar si el brazalete existe en la db
			BaseHandler h = new BaseHandler(this.context);
			List<Brazaletes> lt = new ArrayList<Brazaletes>();
			data = data.replace('/','-');
			
			lt = h.BrazaleteBR(data);
			if(lt.size()>0)
			{
				//Verificar si se valido dentro de los 5 minutos
				Brazaletes br = new Brazaletes();
				br = lt.get(0);
				
				
				Date FechaV = br.getFecha();
				
				long t=FechaV.getTime();
				Date FechaNueva=new Date(t + (5 * 60000));
				
				if(!FechaNueva.before(FechaV))
				{
					this.Mensaje =  "Tu Brazalete ya fue Validado";
					this.Tipo = -1;
					return false;
				}
				
			}
			try 
				{
				
				String[] p = data.split("-");
				if(p.length==1)
				{
					this.Mensaje ="El Brazalete esta da�ado el Codigo QR, ac�rcate al Info Center mas Cercano";
		        	return false;
				}
				
	            String servicio = p[2];
	            //String key = p[3];
	            String Nombre = p[4];
	            String _fecha = p[1].substring(0, 2) + "/" + p[1].substring(2, 4) + "/" + p[1].substring(4);
	         
	            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	            Date FechaBr =  format.parse(_fecha);
	         
	            Date FechaHoy = new Date();
	            FechaHoy = format.parse(format.format(FechaHoy));
	            
	            if(servicio.contains("006"))
	            {
	            	if(this.cFechas(format.format(FechaHoy), _fecha)!=0)
	            	{
	            		this.Mensaje ="Fecha invalida, ac�rcate al Info Center mas Cercano";
	            		return false;
	            	}
	            }
	            if(servicio.contains("054"))
	            {
	            	if(this.cFechas(format.format(FechaHoy), _fecha)!=0)
	            	{
	            		this.Mensaje ="Fecha invalida, ac�rcate al Info Center mas Cercano";
	            		return false;
	            	}      
	            	SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy hh:mm");
	            	String _fecha15 = format.format(FechaHoy) + " 15:20";
	            	Date   _hoy = new Date();
	            	Date   _pass = format2.parse(_fecha15);
	            	
	            	
	            	if(!_hoy.after(_pass))
	            	{
	            		this.Mensaje = ("Este es un Servicio Express, debe abordar a las 15:20 ");
	            		return false; 
	            	}
	            }            
	            
	            if(servicio.contains("055"))
	            {
	            	if(this.cFechas(format.format(FechaHoy), _fecha)!=0)
	            	{
	            		
	            		
	            		
	            			Calendar c = Calendar.getInstance(); 
	            	     	c.setTime(FechaBr); 
	            		    c.add(Calendar.DATE, 1);
	            		    FechaBr = c.getTime();
	            		
	            		
	            		  if (this.cFechas(format.format(FechaBr), format.format(FechaHoy))!=0)
	                      {
	            			  this.Mensaje =("Fecha invalida, ac�rcate al Info Center mas Cercano");
	            			  return false;
	                      }
		            	
	            		
	            		
	            	}            	           	
	            }
	            
	            if(servicio.contains("064"))
	            {
	            	if(this.cFechas(FechaHoy.toString(), _fecha)!=0)
	            	{
	            		Calendar c = Calendar.getInstance(); 
	            		c.setTime(FechaBr); 
	            		c.add(Calendar.DATE, 1);
	            		FechaBr = c.getTime();
	            		  if (this.cFechas(FechaBr.toString(), FechaHoy.toString())!=0)
	                      {
	            			  this.Mensaje =("Fecha invalida, ac�rcate al Info Center mas Cercano");
	            			  return false;
	                      }
	            		
	            	}            	
	            }
	            
	            h.BrazaletesAdd(data);
	        	this.Mensaje =Nombre;
	        	return true;
			} catch (ParseException e) {
				this.Mensaje ="El Brazalete esta da�ado el Codigo QR, ac�rcate al Info Center mas Cercano";
	        	return false;
			}
			
		}
		private int cFechas(String fecha1, String fechaActual) { 
			  System.out.println("Parametro String Fecha 1 = "+fecha1+"\n" +
			    "Parametro String fechaActual = "+fechaActual+"\n"); 
			  int resultado=-1;
			  try {
			   /**Obtenemos las fechas enviadas en el formato a comparar*/
			   SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
			   Date fechaDate1 = formateador.parse(fecha1);
			   Date fechaDate2 = formateador.parse(fechaActual);
			    
			   System.out.println("Parametro Date Fecha 1 = "+fechaDate1+"\n" +
			     "Parametro Date fechaActual = "+fechaDate2+"\n");
			    
			    if ( fechaDate1.before(fechaDate2) ){
			    resultado= 1;
			    }else{
			     if ( fechaDate2.before(fechaDate1) ){
			      resultado= 2;
			     }else{
			      resultado= 0;
			     }
			    }
			  } catch (ParseException e) {
			   System.out.println("Se Produjo un Error!!!  "+e.getMessage());
			  } 
			  return resultado;
			 }

		
}
