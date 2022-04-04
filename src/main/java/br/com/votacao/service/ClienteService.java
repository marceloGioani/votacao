package br.com.votacao.service;

import org.springframework.web.client.RestTemplate;

@SuppressWarnings("hiding")
public class ClienteService<T> {

	public static <T> T getMicroServico(String url, Class<T> responseType) {

		Class<T> responseClass = (responseType instanceof Class ? (Class<T>) responseType : null);
		RestTemplate restTemplate = new RestTemplate();
		T resu = restTemplate.getForObject(url, responseClass);

		return resu;
	}

	public static <T> T postMicroServico(String url, Object request, Class<T> responseType) {

		RestTemplate restTemplate = new RestTemplate();

		Class<T> responseClass = (responseType instanceof Class ? (Class<T>) responseType : null);

		T resu = restTemplate.postForObject(url, request, responseClass);

		return resu;
	}

}
