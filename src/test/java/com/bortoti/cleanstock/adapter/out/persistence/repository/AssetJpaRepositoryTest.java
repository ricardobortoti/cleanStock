package com.bortoti.cleanstock.adapter.out.persistence.repository;

import com.bortoti.cleanstock.CleanStockApplication;
import com.bortoti.cleanstock.adapter.out.persistence.entity.AssetJpaEntity;
import com.bortoti.cleanstock.testcontainers.MysqlTestContainer;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.script.ScriptException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;



@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class AssetJpaRepositoryTest {

    /*
    @PersistenceContext
    private EntityManager em;
*/

    @BeforeAll
    private static void initDatabaseProperties() {
        mysqlTestContainer.start();
    }

    @ClassRule
    public static MySQLContainer<MysqlTestContainer> mysqlTestContainer = MysqlTestContainer.getInstance();

    @Autowired
    AssetJpaRepository assetJpaRepository;

    @Test
    void persistSmokeTest() {
        for (int i = 0; i<5; i++) {
            AssetJpaEntity assetJpaEntity =
                    new AssetJpaEntity(
                            UUID.randomUUID(),
                            "TICK%s".formatted(i)
                    );
            assetJpaRepository.save(assetJpaEntity);
        }
/*
        Query q = em.createNativeQuery("SELECT VERSION()");
        List l = q.getResultList();
 */
        var all = assetJpaRepository.findAll();

        assertEquals(5, all.size());
    }

}