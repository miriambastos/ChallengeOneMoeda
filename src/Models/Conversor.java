package Models;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Conversor {
    private String urlInicial = "";
    private String APIKEY = "";

    public double pegaTaxaConversao(String moedaBase, String moedaAlvo) throws IOException, InterruptedException {
        urlInicial = "https://v6.exchangerate-api.com/v6/" + APIKEY + "/latest/" + moedaBase;
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder(URI.create(urlInicial)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();
        Gson gson = new Gson();
        Moeda moeda = gson.fromJson(json, Moeda.class);
        double taxa = moeda.conversion_rates().get(moedaAlvo);

        return taxa;
    }

    public double converteValor(double valor, double taxa){
        return valor * taxa;
    }
}