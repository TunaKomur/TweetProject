import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        JsonArray jsonArray = null;

        try {
            // JSON dosyasını oku
            jsonArray = JsonParser.parseReader(new FileReader("deneme2.json")).getAsJsonArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Kullanıcıları içeren myArrayList'i oluştur
        myArrayList<user> userList = new myArrayList<>();

        // Kullanıcıları içeren hashMap'i oluştur
        hashMap<String, user> userhashMap = new hashMap<>();

        // JsonArray içindeki her bir kullanıcı öğesini işle
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();

            // Kullanıcı özelliklerini al
            String username = jsonObject.get("username").getAsString();
            String name = jsonObject.get("name").getAsString();
            int followersCount = jsonObject.get("followers_count").getAsInt();
            int followingCount = jsonObject.get("following_count").getAsInt();
            String language = jsonObject.get("language").getAsString();
            String region = jsonObject.get("region").getAsString();
            JsonArray tweetsJson = jsonObject.getAsJsonArray("tweets");
            JsonArray followersJson = jsonObject.getAsJsonArray("followers");
            JsonArray followingJson = jsonObject.getAsJsonArray("following");

            // Tweet'leri myArrayList'e çek
            myArrayList<String> tweets = new myArrayList<>();
            for (int j = 0; j < tweetsJson.size(); j++) {
                String tweet = tweetsJson.get(j).getAsString();
                tweets.add(tweet);
            }

            // Followers'ları myArrayList'e çek
            myArrayList<String> followers = new myArrayList<>();
            for (int j = 0; j < followersJson.size(); j++) {
                String follower = followersJson.get(j).getAsString();
                followers.add(follower);
            }

            // Following'leri myArrayList'e çek
            myArrayList<String> following = new myArrayList<>();
            for (int j = 0; j < followingJson.size(); j++) {
                String follow = followingJson.get(j).getAsString();
                following.add(follow);
            }

            // Kullanıcı nesnesini oluştur
            user user = new user(username, name, followersCount, followingCount, language, region, tweets, followers, following);

            // Kullanıcıyı myArrayList'e ekle
            userList.add(user);

            // Kullanıcıyı hashMap'e ekle (username anahtar olarak)
            userhashMap.put(user.getUserName(), user);

        }
/*
        // Örnek: Kullanıcıları ekrana yazdır
        System.out.println("user List:");
        for (int i = 0; i < userList.size(); i++) {
            user user = userList.get(i);
            System.out.println(user);
        }*/
        hashMap.writehashMapToFile(userhashMap, "hashMap_output.txt");

        // Graph oluştur
        graph twitterGraph = new graph();

        // Kullanıcıları ve takipçi ilişkilerini graph'a ekler
        for (user currentUser : userList) {
            String currentUserUsername = currentUser.getUserName();

            // Kullanıcıyı graph'a ekle
            twitterGraph.addNode(currentUserUsername);
            myArrayList<String> followersList = currentUser.getFollowers();
            for (String follower : followersList) {

                // Kullanıcının takipçi ilişkisini ekle
                twitterGraph.addNode(follower);
                twitterGraph.addEdge(currentUserUsername, follower);
            }
        }

        // Graph'ı dosyaya yazdır
        twitterGraph.printGraphToFile("graph_output.txt");



        /////////////////////////////////////////////////////////////////////////

        // Her bir kullanıcının en çok kullandığı 3 kelimeyi bul ve ilgi alanları olarak belirle

        hashMap<String, myArrayList<String>> userInterests = new hashMap<>();
        for (user currentUser : userList) {
            myArrayList<String> top3Words = findTop3Words(currentUser.getTweets());
            userInterests.put(currentUser.getUserName(), top3Words);
        }
        // Bütün kullanıcıları karşılaştır ve ilgi alanları aynı olanları eşleştir
        for (int i = 0; i < userList.size() - 1; i++) {
            user user1 = userList.get(i);

            for (int j = i + 1; j < userList.size(); j++) {
                user user2 = userList.get(j);

                // İki kullanıcının ilgi alanları arasındaki ortak elemanları bul
                myArrayList<String> interests1 = userInterests.get(user1.getUserName());
                myArrayList<String> interests2 = userInterests.get(user2.getUserName());

                // Eğer ilgi alanları aynı ise eşleşmiş olarak işaretle
                if (interests1 != null && interests2 != null && interests1.equals(interests2)) {
                    System.out.println("Eşleşen Kullanıcılar:");
                    System.out.println(user1.getUserName() + " ve " + user2.getUserName());
                    System.out.println("Ortak İlgi Alanları: " + interests1);
                    System.out.println("-------------------------");
                }
            }
        }
        // Eşleşen kullanıcıları dosyaya yaz
        printMatchedUsersToFile(userList, "matched_users_output.txt");
    }


    private static void printMatchedUsersToFile(myArrayList<user> userList, String fileName) {
        try (FileWriter writer = new FileWriter(fileName);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

            for (int i = 0; i < userList.size() - 1; i++) {
                user user1 = userList.get(i);

                for (int j = i + 1; j < userList.size(); j++) {
                    user user2 = userList.get(j);

                    // İki kullanıcının ilgi alanları arasındaki ortak elemanları bul
                    myArrayList<String> interests1 = findTop3Words(user1.getTweets());
                    myArrayList<String> interests2 = findTop3Words(user2.getTweets());

                    // Eğer ilgi alanları aynı ise eşleşmiş olarak işaretle
                    if (areListsEqual(interests1, interests2)) {
                        bufferedWriter.write("Eşleşen Kullanıcılar:\n");
                        bufferedWriter.write(user1.getUserName() + " ve " + user2.getUserName() + "\n");
                        bufferedWriter.write("Ortak İlgi Alanları: " + interests1 + "\n");
                        bufferedWriter.write("-------------------------\n");
                    }
                }
            }

            System.out.println("Eşleşen kullanıcılar dosyaya yazıldı.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean areListsEqual(myArrayList<String> list1, myArrayList<String> list2) {
        // En az bir ortak eleman varsa true, yoksa false döndür
        for (String item : list1) {
            if (list2.contains(item)) {
                return true;
            }
        }
        return false;
    }

    private static myArrayList<String> findTop3Words(myArrayList<String> tweets) {
        // Her bir kelimenin sayısını saymak için bir harita kullan
        hashMap<String, Integer> wordCountMap = new hashMap<>();

        // Tweet'lerdeki kelimeleri say
        for (String tweet : tweets) {
            String[] words = tweet.split("\\s+");
            for (String word : words) {
                // Küçük harfe çevir ve gereksiz karakterleri temizle
                word = word.toLowerCase().replaceAll("[^a-zA-Z]", "");

                // Haritanın içinde kelimenin sayısını güncelle
                int count = wordCountMap.getOrDefault(word, 0);
                wordCountMap.put(word, count + 1);
            }
        }

        // En çok kullanılan 3 kelimeyi bul
        myArrayList<String> top3Words = new myArrayList<>();
        for (int i = 0; i < 3; i++) {
            String maxWord = findMaxWord(wordCountMap);

            // Eğer maxWord zaten top3Words içinde varsa, bir sonraki en çok geçen kelimeye geç
            if (top3Words.contains(maxWord)) {
                continue;
            }

            top3Words.add(maxWord);
            wordCountMap.remove(maxWord);
        }

        return top3Words;
    }

    public static String findMaxWord(hashMap<String, Integer> wordCountMap) {
        int maxCount = 0;
        String maxWord = null;

        for (hashMap.Node<String, Integer> entry : wordCountMap.table) {
            while (entry != null) {
                String word = entry.key;
                int count = entry.value;

                if (count > maxCount) {
                    maxCount = count;
                    maxWord = word;
                }
                entry = entry.next;
            }
        }

        return maxWord;
    }

    /////////////////////////////////////////////////////////////////////////
}