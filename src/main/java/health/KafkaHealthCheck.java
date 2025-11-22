package health;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Readiness
@ApplicationScoped
public class KafkaHealthCheck implements HealthCheck {
//bisa cek di browser http://localhost:8080/q/health >> untuk cek kafka nya sudah jalan atau tidak
    @Override
    public HealthCheckResponse call() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");

        try (AdminClient adminClient = AdminClient.create(props)) {
            DescribeClusterResult result = adminClient.describeCluster();
            result.clusterId().get();
            return HealthCheckResponse.up("Kafka connection");
        } catch (ExecutionException | InterruptedException e) {
            return HealthCheckResponse.down("Kafka connection failed: " + e.getMessage());
        }
    }
}