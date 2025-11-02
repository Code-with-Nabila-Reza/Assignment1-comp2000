public class BirdWeatherStrategy implements WeatherStrategy {
    @Override
    public int applyWeatherEffects(int points, double rainfall, double temperature, double windStrength) {
    if (windStrength > 0.5) {
        points -= 2; //wind makes flying difficult
    }
    if (windStrength > 0.7 ) {
        points -= 1; // Strong wind very challenging for birds 
        //so addition 1 point penalty aadds
    }
    
    if (rainfall > 0.45) {
        points -= 1; // heavy rain slightly affects birds but not much
    }
    
    return points;
  }
}