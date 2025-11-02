public class CatWeatherStrategy implements WeatherStrategy {
    @Override
 public int applyWeatherEffects(int points, double rainfall, double temperature, double windStrength) {
    if (rainfall > 0.45) {
        points -= 2; // Cats dislikes rain
    }
    if (rainfall > 0.6) {
        points -= 1; // Cats hate heavy rain or flooding
        //additional 1 point penalty adds(total -3)
    }
    
    if (temperature > 0.55 || temperature < 0.4) {
        points -= 1; // Extreme temperatures bother cats
    }
    
    return points;
  }
}