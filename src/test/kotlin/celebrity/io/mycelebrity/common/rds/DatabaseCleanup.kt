package celebrity.io.mycelebrity.common.rds

import jakarta.persistence.Entity
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.Table
import jakarta.persistence.metamodel.EntityType
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.shaded.com.google.common.base.CaseFormat

@Component
class DatabaseCleanup : InitializingBean {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    private lateinit var tableNames: List<String>

    override fun afterPropertiesSet() {
        tableNames = entityManager.metamodel.entities
            .filter { e -> e.javaType.getAnnotation(Entity::class.java) != null }
            .map { e: EntityType<*> -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, e.name) }
    }

    @Transactional
    fun execute() {
        entityManager.clear()
        entityManager.flush()

        entityManager.createNativeQuery("SET foreign_key_checks = 0").executeUpdate()
        tableNames.forEach { tableName ->
            entityManager.createNativeQuery("TRUNCATE TABLE $tableName").executeUpdate()
        }
        entityManager.createNativeQuery("SET foreign_key_checks = 1").executeUpdate()
    }
}
