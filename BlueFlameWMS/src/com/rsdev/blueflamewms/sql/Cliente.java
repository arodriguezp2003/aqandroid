package com.rsdev.blueflamewms.sql;


public class Cliente {
	private int id ;
	private String rut;
	private String nombre;
	private String fantasia;
	
	public int getId()
	{
	    return this.id;
	}

	public void setId(int id)
	{	  
	    this.id = id;
	}	
	public String getNombre()
	{
	    return this.nombre;
	}

	public void setNombre(String nombre)
	{	  
	    this.nombre = nombre;
	}
	
	public String getRut()
	{
		return this.rut;
	}

	public void setRut(String rut)
	{
		this.rut = rut;
	}

	public String getFantasia()
	{
	    return this.fantasia;
	}

	public void setFantasia(String fantasia)
	{	  
	    this.fantasia = fantasia;
	}
}
