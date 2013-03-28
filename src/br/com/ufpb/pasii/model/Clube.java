package br.com.ufpb.pasii.model;

public class Clube {
	private int id;
	private String nome;
	private String escudo;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEscudo() {
		return escudo;
	}
	public void setEscudo(String escudo) {
		this.escudo = escudo;
	}
	
	public Clube(int id, String nome, String escudo) {
		this.id = id;
		this.nome = nome;
		this.escudo = escudo;
	}
	
	public Clube(){
		
	}
	
	
	
}
