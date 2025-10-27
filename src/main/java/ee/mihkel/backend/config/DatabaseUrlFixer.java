package ee.mihkel.backend.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseUrlFixer {

    @Value("${DATABASE_URL:}")
    private String databaseUrl;

    @PostConstruct
    public void configureJdbcEnvVars() {
        if (databaseUrl != null && databaseUrl.startsWith("postgres://")) {
            // Convert: postgres://user:pass@host:port/db → jdbc:postgresql://host:port/db
            String cleaned = databaseUrl.substring("postgres://".length());
            String[] userInfoAndHost = cleaned.split("@");
            String userInfo = userInfoAndHost[0];
            String hostPart = userInfoAndHost[1];

            String[] userPass = userInfo.split(":");
            String username = userPass[0];
            String password = userPass[1];

            String jdbcUrl = "jdbc:postgresql://" + hostPart;

            System.setProperty("JDBC_DATABASE_URL", jdbcUrl);
            System.setProperty("JDBC_DATABASE_USERNAME", username);
            System.setProperty("JDBC_DATABASE_PASSWORD", password);

            System.out.println("✅ Converted DATABASE_URL to JDBC format: " + jdbcUrl);
        } else {
            System.out.println("⚠️ DATABASE_URL not set or already JDBC format.");
        }
    }
}