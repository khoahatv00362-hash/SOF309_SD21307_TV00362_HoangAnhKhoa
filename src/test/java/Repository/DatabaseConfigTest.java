package Repository;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DatabaseConfigTest {
    @Test
    void dbPropertiesGhiDeBienMoiTruongKhiFileCoCauHinh() {
        Properties props = baseProperties();
        Map<String, String> env = new HashMap<>();
        env.put("QLSV_DB_HOST", "env-host");
        env.put("QLSV_DB_USER", "env-user");
        env.put("QLSV_DB_PASSWORD", "env-password");

        DatabaseConfig config = DatabaseConfig.from(props, env);

        assertEquals("file-host", config.getHost());
        assertEquals("file-user", config.getUser());
        assertEquals("file-password", config.getPassword());
        assertEquals("QL_DaoTao", config.getDatabaseName());
    }

    @Test
    void bienMoiTruongDuocDungKhiDbPropertiesKhongCoGiaTri() {
        Map<String, String> env = new HashMap<>();
        env.put("QLSV_DB_HOST", "env-host");
        env.put("QLSV_DB_USER", "env-user");
        env.put("QLSV_DB_PASSWORD", "env-password");

        DatabaseConfig config = DatabaseConfig.from(new Properties(), env);

        assertEquals("env-host", config.getHost());
        assertEquals("env-user", config.getUser());
        assertEquals("env-password", config.getPassword());
    }

    @Test
    void jdbcUrlKhongChuaTaiKhoanHayMatKhau() {
        DatabaseConfig config = DatabaseConfig.from(baseProperties(), new HashMap<>());

        String url = config.getJdbcUrl();

        assertTrue(url.contains("jdbc:sqlserver://file-host:1433"));
        assertTrue(url.contains("databaseName=QL_DaoTao"));
        assertTrue(url.contains("encrypt=false"));
        assertTrue(url.contains("trustServerCertificate=true"));
        assertFalse(url.contains("file-user"));
        assertFalse(url.contains("file-password"));
    }

    @Test
    void khongCoFileVanCoGiaTriMacDinhDeDungVoiEnv() {
        DatabaseConfig config = DatabaseConfig.from(new Properties(), new HashMap<>());

        assertEquals("localhost", config.getHost());
        assertEquals("1433", config.getPort());
        assertEquals("QL_DaoTao", config.getDatabaseName());
    }

    @Test
    void productionBuildDongGoiDbPropertiesDeDbConnectDocDuocCauHinh() throws Exception {
        String pom = new String(Files.readAllBytes(Paths.get("pom.xml")),
                StandardCharsets.UTF_8)
                .toLowerCase(Locale.ROOT)
                .replaceAll("\\s+", "");

        assertFalse(pom.contains("<exclude>db.properties</exclude>"),
                "db.properties phải được sao chép vào target/classes");
        assertFalse(pom.contains(
                        "<packagingexcludes>web-inf/classes/db.properties"
                                + "</packagingexcludes>"),
                "db.properties phải có trong WEB-INF/classes của WAR");
    }

    private Properties baseProperties() {
        Properties props = new Properties();
        props.setProperty("db.host", "file-host");
        props.setProperty("db.port", "1433");
        props.setProperty("db.name", "QL_DaoTao");
        props.setProperty("db.user", "file-user");
        props.setProperty("db.password", "file-password");
        props.setProperty("db.encrypt", "false");
        props.setProperty("db.trustServerCertificate", "true");
        return props;
    }
}
