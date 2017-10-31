package br.com.evandropires.meetuphql;

import br.com.evandropires.meetuphql.entidade.Endereco;
import br.com.evandropires.meetuphql.entidade.Imovel;
import br.com.evandropires.meetuphql.entidade.Pessoa;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 * Created by evandro on 30/10/17.
 */
@RunWith(JUnit4.class)
public class MeetupHqlTest {

	private EntityManager entityManager = EntityManagerUtil.getEntityManager();

	@Test
	public void pessoaTest() {
		try {
			entityManager.getTransaction().begin();

			Pessoa incluir = inserirPessoa();
			entityManager.flush();

			Pessoa pessoa = entityManager.find(Pessoa.class, 1);
			Assert.assertNotNull(pessoa);

			pessoa = entityManager.find(Pessoa.class, 2);
			Assert.assertNull(pessoa);
		} finally {
			entityManager.getTransaction().rollback();
		}
	}

	@Test
	public void fetchTest() {
		try {
			entityManager.getTransaction().begin();

			inserirPessoa();
			Pessoa incluir = inserirPessoa();
			entityManager.flush(); // Envia da memória para o BD

			entityManager.clear(); // Limpa o cache de primeiro nivel

			System.out.println("### SEM FETCH");
			Query query = entityManager.createQuery("SELECT endereco FROM Endereco AS endereco JOIN endereco.pessoa AS pessoa WHERE pessoa.id = :id");
			query.setParameter("id", incluir.getId());
			query.setMaxResults(1);
			Endereco endereco2 = (Endereco) query.getSingleResult();
			endereco2.getPessoa().getId();

			entityManager.clear(); // Limpa o cache de primeiro nivel

			System.out.println("### COM FETCH");
			query = entityManager.createQuery("SELECT endereco FROM Endereco AS endereco JOIN FETCH endereco.pessoa AS pessoa WHERE pessoa.id = :id");
			query.setParameter("id", incluir.getId());
			query.setMaxResults(1);
			endereco2 = (Endereco) query.getSingleResult();
			endereco2.getPessoa().getId();
		} finally {
			entityManager.getTransaction().rollback();
		}
	}

	@Test
	public void semFetchTest() {
		try {
			entityManager.getTransaction().begin();

			for (int count=0;count<20; count++) {
				inserirPessoa();
			}
			Pessoa incluir = inserirPessoa();
			entityManager.flush(); // Envia da memória para o BD

			entityManager.clear(); // Limpa o cache de primeiro nivel

			System.out.println("### SEM FETCH");
			Query query = entityManager.createQuery("FROM Pessoa ");
			List<Pessoa> pessoas = query.getResultList();
			processarListaPessoa(pessoas);
		} finally {
			entityManager.getTransaction().rollback();
		}
	}

	private void processarListaPessoa(List<Pessoa> pessoas) {
		for (Pessoa pessoa : pessoas) {
			pessoa.getEnderecos().iterator().next();
			pessoa.getImoveis().iterator().next();
		}
	}

	@Test
	public void comFetchTest() {
		try {
			entityManager.getTransaction().begin();

			for (int count=0;count<20; count++) {
				inserirPessoa();
			}
			Pessoa incluir = inserirPessoa();
			entityManager.flush(); // Envia da memória para o BD

			entityManager.clear(); // Limpa o cache de primeiro nivel

			System.out.println("### COM FETCH");
			Query query = entityManager.createQuery("FROM Pessoa AS pessoa JOIN FETCH pessoa.enderecos JOIN FETCH pessoa.imoveis ");
			List<Pessoa> pessoas = query.getResultList();
			processarListaPessoa(pessoas);
		} finally {
			entityManager.getTransaction().rollback();
		}
	}

	private Pessoa inserirPessoa() {
		Pessoa incluir = new Pessoa();
		incluir.setNome("Evandro");
		entityManager.persist(incluir);

		Endereco endereco = new Endereco();
		endereco.setLogradouro("Rua Teste");
		endereco.setNumero(123);
		endereco.setComplemento("apto 123");
		endereco.setBairro("Centro");
		endereco.setUf("SC");
		endereco.setCidade("Florianópolis");
		endereco.setPessoa(incluir);
		entityManager.persist(endereco);

		endereco = new Endereco();
		endereco.setLogradouro("Rua Teste");
		endereco.setNumero(123);
		endereco.setComplemento("apto 123");
		endereco.setBairro("Centro");
		endereco.setUf("SC");
		endereco.setCidade("Florianópolis");
		endereco.setPessoa(incluir);
		entityManager.persist(endereco);

		endereco = new Endereco();
		endereco.setLogradouro("Rua Teste");
		endereco.setNumero(123);
		endereco.setComplemento("apto 123");
		endereco.setBairro("Centro");
		endereco.setUf("SC");
		endereco.setCidade("Florianópolis");
		endereco.setPessoa(incluir);
		entityManager.persist(endereco);

		Imovel imovel = new Imovel();
		imovel.setDescricao("Imovel 1");
		imovel.setPessoa(incluir);
		entityManager.persist(imovel);

		imovel = new Imovel();
		imovel.setDescricao("Imovel 2");
		imovel.setPessoa(incluir);
		entityManager.persist(imovel);

		return incluir;
	}
}
