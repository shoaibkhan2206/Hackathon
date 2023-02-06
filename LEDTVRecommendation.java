
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
/*
*This code is a method for recommending LED TVs to a user based on their TV usage data and the TV usage data of other users. Here is a line-by-line explanation:

*List<String> recommendations = new ArrayList<>(); creates an empty list called "recommendations" that will eventually hold the recommended LED TVs.

*Map<String, Double> targetUserTVUsageData = this.userTVUsageData.get(user); retrieves the TV usage data of the target user, which is passed in as a parameter, from a map called "userTVUsageData". The key of the map is a user ID and the value is a map of TV usage data, where the key is the name of a TV and the value is the usage data (represented as a double).

*Map<String, Double> similarities = new HashMap<>(); creates an empty map called "similarities" that will hold the similarity scores between the target user and all other users.

*for (String otherUser : this.userTVUsageData.keySet()) starts a for loop that goes through all the keys in the "userTVUsageData" map. "otherUser" is a variable that holds each key (i.e., the user ID) during each iteration of the loop.

*if (!otherUser.equals(user)) checks if the "otherUser" is not the same as the target user. If it is not, then the following code is executed.

*Map<String, Double> otherUserTVUsageData = this.userTVUsageData.get(otherUser); retrieves the TV usage data of the "otherUser" from the "userTVUsageData" map.

*double similarity = cosineSimilarity(targetUserTVUsageData, otherUserTVUsageData); calculates the similarity score between the target user and the "otherUser" using a method called "cosineSimilarity".

*similarities.put(otherUser, similarity); adds the similarity score to the "similarities" map, where the key is the "otherUser" and the value is the similarity score.

*List<String> mostSimilarUsers = similarities.entrySet().stream() starts a stream that goes through all the entries in the "similarities" map.

*.sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())) sorts the entries in the stream in descending order based on the similarity scores (i.e., the values of the map).

*.map(Map.Entry::getKey) maps each entry in the stream to its key (i.e., the user ID).

*.collect(Collectors.toList()); collects all the keys into a list called "mostSimilarUsers".

*int n = 5; declares an integer variable "n" and sets it to 5. This represents the number of most similar users to consider for recommendation.

*for (int i = 0; i < n && i < mostSimilarUsers.size(); i++) starts a for loop that goes through the first "n" elements in the "mostSimilarUsers" list. "i" is a variable that holds the index during each iteration of the loop.

*String similarUser = mostSimilarUsers.get(i); retrieves the user ID of the


*/
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
