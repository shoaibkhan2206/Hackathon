
/**
 * The program starts by initializing the userTVUsageData and userTVs maps.

The addUserData() method takes a user's name, their TV usage data, and the list of TVs they own as
 input and stores it in the userTVUsageData and userTVs maps.

The cosineSimilarity() method calculates the cosine similarity between two sets of TV usage data.

The getLEDTVRecommendations() method takes a user's name as input and returns a list of 
recommended LED TVs.
It starts by getting the target user's TV usage data.
It then calculates the cosine similarity between the target user's TV usage data and the TV usage data of all other users.
It then sorts the users based on the similarity score in descending order.
It selects the top 5 (or less if there are not enough users) most similar users.
Finally, it adds all the TVs owned by the most similar users to the recommendations list and returns it.

 * 
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LEDTVRecommendation {

  private Map<String, Map<String, Double>> userTVUsageData;
    // Store the list of LED TVs owned by each user
  private Map<String, List<String>> userTVs;

  // Initialize the maps to store the user data
  public LEDTVRecommendation() {
    this.userTVUsageData = new HashMap<>();
    this.userTVs = new HashMap<>();
  }
  // Add data for a new user

  public void addUserData(String user, Map<String, Double> tvUsageData, List<String> tvs) {
    this.userTVUsageData.put(user, tvUsageData);
    this.userTVs.put(user, tvs);
  }
// Compute the cosine similarity between two sets of electric bill and hours run per day data
/*
*This method is used to add data for a new user. The method takes three parameters: user, tvUsageData, and tvs. 
*user is the user ID (as a String), tvUsageData is a map that stores the electric bill and hours run per day data for
*the user, and tvs is a list of the LED TVs owned by the user. 
*The method adds the user data by using the put method to add the tvUsageData map and tvs list to the appropriate maps
*/

  // Compute the cosine similarity between two sets of electric bill and hours run per day data
  private double cosineSimilarity(Map<String, Double> tvUsageData1, Map<String, Double> tvUsageData2) {
    double dotProduct = 0.0;
    double magnitude1 = 0.0;
    double magnitude2 = 0.0;
    
        // Compute the dot product and magnitude of each vector
    for (String key : tvUsageData1.keySet()) {
      dotProduct += tvUsageData1.get(key) * tvUsageData2.get(key);
      magnitude1 += Math.pow(tvUsageData1.get(key), 2);
      magnitude2 += Math.pow(tvUsageData2.get(key), 2);
    }
    magnitude1 = Math.sqrt(magnitude1);
    magnitude2 = Math.sqrt(magnitude2);
    if (magnitude1 != 0.0 && magnitude2 != 0.0) {
      return dotProduct / (magnitude1 * magnitude2);
    } else {
      return 0.0;
    }
  }

  public List<String> getLEDTVRecommendations(String user) {
    List<String> recommendations = new ArrayList<>();
    Map<String, Double> targetUserTVUsageData = this.userTVUsageData.get(user);
    Map<String, Double> similarities = new HashMap<>();
    for (String otherUser : this.userTVUsageData.keySet()) {
      if (!otherUser.equals(user)) {
        Map<String, Double> otherUserTVUsageData = this.userTVUsageData.get(otherUser);
        double similarity = cosineSimilarity(targetUserTVUsageData, otherUserTVUsageData);
        similarities.put(otherUser, similarity);
      }
    }
    List<String> mostSimilarUsers = similarities.entrySet().stream()
        .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
        .map(Map.Entry::getKey)
        .collect(Collectors.toList());
    int n = 5;
    for (int i = 0; i < n && i < mostSimilarUsers.size(); i++) {
      String similarUser = mostSimilarUsers.get(i);
      recommendations.addAll(this.userTVs.get(similarUser));
    }
    return recommendations;
  }

public static void main(String[] args) {
    LEDTVRecommendation ledtvRecommendation = new LEDTVRecommendation();
    
    Map<String, Double> user1TVUsageData = new HashMap<>();
    user1TVUsageData.put("BrandA", 0.5);
    user1TVUsageData.put("BrandB", 0.3);
    user1TVUsageData.put("BrandC", 0.2);
    List<String> user1TVs = Arrays.asList("BrandA TV", "BrandB TV", "BrandC TV");
    
    Map<String, Double> user2TVUsageData = new HashMap<>();
    user2TVUsageData.put("BrandA", 0.4);
    user2TVUsageData.put("BrandB", 0.4);
    user2TVUsageData.put("BrandC", 0.2);
    List<String> user2TVs = Arrays.asList("BrandA TV", "BrandB TV");
    
    Map<String, Double> user3TVUsageData = new HashMap<>();
    user3TVUsageData.put("BrandA", 0.1);
    user3TVUsageData.put("BrandB", 0.8);
    user3TVUsageData.put("BrandC", 0.1);
    List<String> user3TVs = Arrays.asList("BrandB TV", "BrandC TV");
    
    ledtvRecommendation.addUserData("User1", user1TVUsageData, user1TVs);
    ledtvRecommendation.addUserData("User2", user2TVUsageData, user2TVs);
    ledtvRecommendation.addUserData("User3", user3TVUsageData, user3TVs);
    
    List<String> recommendations = ledtvRecommendation.getLEDTVRecommendations("User1");
    System.out.println("LED TV Recommendations for User1: " + recommendations);
    }
}
