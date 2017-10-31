package br.com.evandropires.meetuphql.entidade;

import javax.persistence.*;

/**
 * Created by evandro on 30/10/17.
 */
@Entity
public class Imovel {

	@Id
	@GeneratedValue
	@SequenceGenerator(name = "endereco_sequence")
	private Integer id;

	private String descricao;

	@ManyToOne(fetch = FetchType.LAZY)
	private Pessoa pessoa;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

}
