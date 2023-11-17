package com.thirty.insitegatewayservice.global.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

//커스텀필터는 등록을 원하는 라우터에 직접 등록해야함 yml에 fillters: 필터클래스명 등록
@Component //커스텀 필터 만들기
@Slf4j
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {
	public CustomFilter() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		//custom pre filter
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			ServerHttpResponse response = exchange.getResponse();
			log.info("customfilter pre filter request id -> {}", request.getId());
			//custom past filter
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				log.info("custom post filter: response code -> {}", response.getStatusCode());
			}));
		};
	}

	public static class Config {

	}
}
