package custom;

import com.mysema.query.jpa.impl.JPAQuery;
import custom.domain.CustomEntity;
import custom.domain.CustomEntity_;
import custom.domain.QCustomEntity;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Dmitry Tsydzik
 * @since Date: 26.01.14
 */
public class MetaModelsTest {

    private static EntityManager entityManager;

    @BeforeClass
    public static void createEntityManager() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TestPersistenceUnit");
        entityManager = entityManagerFactory.createEntityManager();

        CustomEntity customEntity = new CustomEntity();
        customEntity.setParent(customEntity);

        entityManager.getTransaction().begin();
        entityManager.persist(customEntity);
        entityManager.getTransaction().commit();

        assertNotNull(customEntity.getId());
    }

    @Test
    public void testQueryDslMetaModel() throws Exception {
        QCustomEntity customEntity = QCustomEntity.customEntity;
        JPAQuery query = new JPAQuery(entityManager);

        List<CustomEntity> list = query.from(customEntity)
                .where(customEntity.id.eq(customEntity.parent.id))
                .list(customEntity);

        assertEquals(1, list.size());
    }

    @Test
    public void testJpaMetaModelGenerated() throws Exception {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CustomEntity> query = cb.createQuery(CustomEntity.class);

        Root<CustomEntity> root = query.from(CustomEntity.class);
        query.where(cb.equal(root.get(CustomEntity_.id),
                root.get(CustomEntity_.parent).get(CustomEntity_.id)
        ));

        List<CustomEntity> resultList = entityManager.createQuery(query).getResultList();
        assertEquals(1, resultList.size());
    }
}
