public class DogWeatherStrategy implements WeatherStrategy {
    @Override
    public int applyWeatherEffects(int points, double rainfall, double temperature, double windStrength) {
        if (rainfall > 0.6) points -= 1; 
        // dogs are all-weather animals --> only affected by heavy rain 
        return points;
    }
}