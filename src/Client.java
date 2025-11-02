import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.List;

public class Client { 
    //gets data from server
    //and the client class is modified as a publisher for 
    //implementing the observer pattern

    //list of subscribers
    private List<WeatherObserver> observers = new ArrayList<>();

    public void addObserver(WeatherObserver observer){
        observers.add(observer);
    }

    private void notifyObservers(WeatherInfo info){
        //notfiying all observers of the change
        observers.forEach(observer -> observer.on_weather_update(info));
    }

    //method for receiving weather data from server
    public void WeatherStream(){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://13.238.167.130/weather"))
                .header("Accept", "text/event-stream")
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofInputStream())
                .thenApply(HttpResponse::body)
                .thenAccept(inputStream -> {
                    try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                        reader.lines().forEach(line -> {
                            //reader.lines--gets stream<strings> for all lines
                        if (!line.trim().isEmpty()) {//if the line isnt empty
                            WeatherInfo info = parseWeatherInfo(line);

                            if (info != null) { //info obj
                                //for implemeting observer pattern
                                //notify subscribers of new weather info
                                notifyObservers(info);
                            }
                        }
                        });
                    } 
                    catch (IOException e) {
                        System.err.println("Error reading stream: " + e.getMessage());
                    }
                });
    }

     //for parsing lines of weather data
    private WeatherInfo parseWeatherInfo(String line){
       try{
        String[] parts = line.trim().split("\\s+");
        //split line by spaces
        
        if (parts.length == 5) {
            long timestamp = Long.parseLong(parts[0]);//string->long
            String type = parts[1];
            int x = Integer.parseInt(parts[2]);//string->int
            int y = Integer.parseInt(parts[3]);
            double value = Double.parseDouble(parts[4]);
            
            return new WeatherInfo(timestamp, type, x, y, value);
        }

      }

    catch (Exception e) {
        //if parsing fails for some reason
        System.err.println("Error parsing weather info: " + e.getMessage());
    }
    return null;  //if parsing failed--->return null
}
}