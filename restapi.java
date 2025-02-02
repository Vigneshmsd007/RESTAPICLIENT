import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
                              

    public class restapi {

        private static final String API_KEY = "your_api_key_here"; // Get your API key from OpenWeatherMap
        private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";

        public static void main(@org.jetbrains.annotations.NotNull String[] args) {
            // Check if the city name is provided as a command line argument
            if (args.length < 1) {
                System.out.println("Please provide a city name.");
                return;
            }

            String cityName = args[0]; // Get the city name from the argument

            // Fetch and display weather data
            try {
                String response = fetchWeatherData(cityName);
                if (response != null) {
                    displayWeatherData(response);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Fetch weather data from OpenWeatherMap API
        public static String fetchWeatherData(String cityName) throws Exception {
            String urlString = BASE_URL + "?q=" + cityName + "&appid=" + API_KEY + "&units=metric"; // Add units=metric for Celsius
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            return response.toString();
        }

        // Parse the JSON response and display weather information
        public static void displayWeatherData(String jsonResponse) {
            try {
                JSONObject jsonObject = new JSONObject(jsonResponse);
                JSONObject main = jsonObject.getJSONObject("main");
                JSONObject weather = jsonObject.getJSONArray("weather").getJSONObject(0);

                String cityName = jsonObject.getString("name");
                double temperature = main.getDouble("temp");
                int humidity = main.getInt("humidity");
                String weatherDescription = weather.getString("description");

                System.out.println("Weather in " + cityName + ":");
                System.out.println("Temperature: " + temperature + "°C");
                System.out.println("Humidity: " + humidity + "%");
                System.out.println("Condition: " + weatherDescription);

            } catch (Exception e) {
                System.out.println("Error parsing the weather data.");
                e.printStackTrace();
            }
        }
    }

}
