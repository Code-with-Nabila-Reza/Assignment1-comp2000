public class DefaultWeatherStrategy implements WeatherStrategy {
    @Override
    public int applyWeatherEffects(int basePoints, double rainfall, double temperature, double windStrength) {
        // default-> there is no weather effects 
        //so return basepoints for the cell that was received
        return basePoints;
    }
}