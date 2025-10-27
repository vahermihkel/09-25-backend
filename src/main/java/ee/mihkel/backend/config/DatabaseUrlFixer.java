package ee.mihkel.backend.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class DatabaseUrlFixer {

    @Value("${DATABASE_URL:}")
    private String databaseUrl;

    @PostConstruct
    public void fixDatabaseUrl() {
        if (StringUtils.hasText(databaseUrl) && databaseUrl.startsWith("postgres://")) {
            String jdbcUrl = databaseUrl.replace("postgres://", "jdbc:postgresql://");
            System.setProperty("SPRING_DATASOURCE_URL", jdbcUrl);
            System.out.println("✅ Fixed Render DATABASE_URL → " + jdbcUrl);
        }
    }
}

