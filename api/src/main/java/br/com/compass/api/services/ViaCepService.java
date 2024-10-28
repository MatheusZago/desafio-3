package br.com.compass.api.services;

import br.com.compass.api.model.Address;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ViaCepService {

    public Address getAddress(String zipCode) {
        Address address = null;
        HttpGet request = new HttpGet("https://viacep.com.br/ws/" + zipCode + "/json/");

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().disableRedirectHandling().build();
             CloseableHttpResponse response = httpClient.execute(request)) {

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity);
                    Gson gson = new Gson();
                    address = gson.fromJson(result, Address.class);
                }
            } else {
                // Trate o erro de status não 200 conforme necessário
                throw new IOException("Failed to retrieve address: HTTP status code " + statusCode);
            }

        } catch (ClientProtocolException e) {
            throw new RuntimeException("Client protocol exception occurred", e);
        } catch (IOException e) {
            throw new RuntimeException("IO exception occurred", e);
        }

        return address;
    }
}
