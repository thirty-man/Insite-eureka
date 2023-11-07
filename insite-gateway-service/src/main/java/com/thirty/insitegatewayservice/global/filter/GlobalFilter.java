package com.thirty.insitegatewayservice.global.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

//globalfilter 모든 라우터에서 실행됨
//커스텀보다 pre가 먼저 실행되고 post는 커스텀보다 늦게 끝남
//yml에서 글로벌을 등록해야 하며 config클래스에 값을 할당해줘야 함
@Component //커스텀 필터 만들기
@Slf4j//글로벌 필터는 args를 받을 수 있는데 커스텀필터도 로깅필터로 만들면 args받을 수 있음
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {
	public GlobalFilter() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		//custom pre filter
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			ServerHttpResponse response = exchange.getResponse();
			log.info("global pre filter baseMessage id -> {}", config.getBaseMessage());
			if (config.isPreLogger()) {
				log.info("global pre filter start request id -> {}", request.getId());

			}
			//global post filter
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				if (config.isPostLogger()) {
					log.info("global post filter: response code -> {}", response.getStatusCode());
				}
			}));
		};
	}

	@Data
	public static class Config {
		private String baseMessage;
		private boolean preLogger;
		private boolean postLogger;
	}
}
