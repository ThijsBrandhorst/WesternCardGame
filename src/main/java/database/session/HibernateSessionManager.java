/**
 *
 */
package database.session;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class HibernateSessionManager {


	private static SessionFactory sessionFactory;
	private static StandardServiceRegistry registry;

	/**
	 * Creates a new SessionFactory with Hibernate.cfg.xml configuration and
	 * hibernate.properties.
	 */
	static {

        if (sessionFactory == null) {
            try {

                registry = new StandardServiceRegistryBuilder().configure().build();

                MetadataSources sources = new MetadataSources(registry);

                Metadata metadata = sources.getMetadataBuilder().build();

                sessionFactory = metadata.getSessionFactoryBuilder().build();

            } catch (Exception e) {
                e.printStackTrace();
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }

	}

	/**
	 * Static method to get to open a session using the factory.
	 *
	 * @return a Session object for database transactions.
	 * @throws HibernateException
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * Shuts down the sessionfactory object and releases all resources.
	 */
	public static void shutdown() {
		sessionFactory.close();
	}

}