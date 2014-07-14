package cl.tkpass.validador.sql;

import java.util.Date;



public class Brazaletes {
	public int id;
	public String CodTkPASS;
	public String Brazalete;
	public Date Fecha;
	public boolean Enviado;
	
	public int getId() {
		return id;
	}
	public String getCodTkPASS() {
		return CodTkPASS;
	}
	public void setCodTkPASS(String codTkPASS) {
		CodTkPASS = codTkPASS;
	}
	public boolean isEnviado() {
		return Enviado;
	}
	public void setEnviado(boolean enviado) {
		Enviado = enviado;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBrazalete() {
		return Brazalete;
	}
	public void setBrazalete(String brazalete) {
		Brazalete = brazalete;
	}
	public Date getFecha() {
		return Fecha;
	}
	public void setFecha(Date fecha) {
		Fecha = fecha;
	}
	
	public  String CreateDB()
	{
		return "CREATE TABLE Brazaletes (id INTEGER PRIMARY KEY, " +
				"CodTkPASS TEXT," +
				"Brazalete TEXT," +
				"Fecha TEXT," +
				"Enviado INTEGER)";
	}
}
