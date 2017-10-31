package br.com.evandropires.meetuphql.entidade;

import javax.persistence.*;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by evandro on 30/10/17.
 */
@Entity
public class Pessoa {

	@Id
	@GeneratedValue
	@SequenceGenerator(name = "pessoa_sequence")
	private Integer id;

	private String nome;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pessoa")
	private Set<Endereco> enderecos = new TreeSet<Endereco>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pessoa")
	private Set<Imovel> imoveis = new TreeSet<Imovel>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(Set<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Set<Imovel> getImoveis() {
		return imoveis;
	}

	public void setImoveis(Set<Imovel> imoveis) {
		this.imoveis = imoveis;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
