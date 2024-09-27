package origin.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@Slf4j
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {
    @Autowired
    private WebClient.Builder webClientBuilder;


    public  JwtAuthenticationFilter() {
        super(Config.class);
    }

    public static class Config {}

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();
            String method = request.getMethod().toString();

            if (("/auth".equals(path) && "POST".equalsIgnoreCase(method)) ||
                    ("/registration".equals(path) && "POST".equalsIgnoreCase(method)) ||
                    ("/refresh".equals(path) && "POST".equalsIgnoreCase(method))) {
                return chain.filter(exchange);
            }

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return Mono.error(new RuntimeException("Missing authorization information"));
            }

            String authHeader = request.getHeaders().getOrEmpty(HttpHeaders.AUTHORIZATION).get(0);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return Mono.error(new RuntimeException("Invalid authorization header"));
            }


            return webClientBuilder.build()
                    .get()
                    .uri("http://auth:8089/check")
                    .header(HttpHeaders.AUTHORIZATION, authHeader)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .flatMap(userDetails -> {
                        if(userDetails.keySet().contains("error")){
                            return Mono.error(new RuntimeException(userDetails.get("error").toString()));
                        }else {
                            System.out.println(userDetails.get("username").toString());
                            ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                                    .header("X-Username", userDetails.get("username").toString())
                                    .build();
                            ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
                            return chain.filter(mutatedExchange);
                        }
                    });
        };
    }
}
