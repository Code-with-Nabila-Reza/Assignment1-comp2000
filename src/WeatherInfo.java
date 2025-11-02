public class WeatherInfo {
   private long  timestamp;
   private String weather_attribute;
   private int x;
   private int y;
   private double value;

   public WeatherInfo(long timestamp, String weather_attribute, int x, int y, double value){
           this.timestamp = timestamp;
           this.weather_attribute = weather_attribute;
           this.x=x;
           this.y=y;
           this.value=value;
   }

   public long get_timestamp(){
    return timestamp;
   }

   public String get_attribute(){
    return weather_attribute;
   }

   public int get_x(){
    return x;
   }
    
   public int get_y(){
    return y;
   }

   public double get_value(){
    return value;
   }
   
   //for debugging and later use
   @Override
    public String toString() {
        return String.format("Weather[%s at (%d,%d): %.2f]", weather_attribute, x, y, value);
    }
   }

