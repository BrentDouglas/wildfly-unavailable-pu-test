package test.other;

import test.model.AnEntity;

import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;

/**
 * @author Brent Douglas <brent.n.douglas@gmail.com>
 */
@Singleton
@Startup
@LocalBean
public class Bean {

    private static final Logger log = Logger.getLogger(Bean.class.getCanonicalName());

    @PersistenceContext(unitName = "ExamplePU")
    private EntityManager entityManager;

    @Schedule(second = "*/1", minute = "*", hour = "*", persistent = false)
    public void run() {
        entityManager.persist(new AnEntity().setName("" + System.currentTimeMillis()));
    }

    @Schedule(second = "*/2", minute = "*", hour = "*", persistent = false)
    public void log() {
        log.info("" + entityManager.createNamedQuery("AnEntity.count", Long.class).getSingleResult());
    }
}
