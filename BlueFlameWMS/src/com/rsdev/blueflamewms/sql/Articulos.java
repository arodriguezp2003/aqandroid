package com.rsdev.blueflamewms.sql;


public class Articulos 
{
   	private String activo;
   	private String codcor;
   	private String descripcion;
   	private String id;
   	private String idcliente;
   	private String idunidad;
   	private String sku;

 	public String getActivo(){
		return this.activo;
	}
	public void setActivo(String activo){
		this.activo = activo;
	}
 	public String getCodcor(){
		return this.codcor;
	}
	public void setCodcor(String codcor){
		this.codcor = codcor;
	}
 	public String getDescripcion(){
		return this.descripcion;
	}
	public void setDescripcion(String descripcion){
		this.descripcion = descripcion;
	}
 	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}
 	public String getIdcliente(){
		return this.idcliente;
	}
	public void setIdcliente(String idcliente){
		this.idcliente = idcliente;
	}
 	public String getIdunidad(){
		return this.idunidad;
	}
	public void setIdunidad(String idunidad){
		this.idunidad = idunidad;
	}
 	public String getSku(){
		return this.sku;
	}
	public void setSku(String sku){
		this.sku = sku;
	}
	
 
	public  String CreateDB()
	{
		return "CREATE TABLE ARTICULOS (id INTEGER PRIMARY KEY, " +
				"idcliente INTEGER," +
				"codcor TEXT," +
				"sku TEXT," +
				"descripcion TEXT," +
				"idunidad INTEGER," +
				"activo INTEGER)";
	}
}
