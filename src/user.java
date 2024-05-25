public class user {
    private String username;
    private String name;
    private int followersCount;
    private int followingCount;
    private String language;
    private String region;
    private myArrayList<String> tweets;
    private myArrayList<String> following;
    private myArrayList<String> followers;
    private myArrayList<String> interests;


    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public user(String username, String name, int followersCount, int followingCount, String language, String region,
                myArrayList<String> tweets, myArrayList<String> following, myArrayList<String> followers
    ) {
        this.username = username;
        this.name = name;
        this.followersCount = followersCount;
        this.followingCount = followingCount;
        this.language = language;
        this.region = region;

        this.tweets = tweets;
        this.following = following;
        this.followers = followers;
        this.interests = new myArrayList<>();
    }

    // Getter ve setter metotları

    public String getUserName() {
        return username;
    }
    public myArrayList<String> getInterests() {
        return interests;
    }

    public void setInterests(myArrayList<String> interests) {
        this.interests = interests;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public myArrayList<String> getTweets() {
        return tweets;
    }

    public void setTweets(myArrayList<String> tweets) {
        this.tweets = tweets;
    }

    public myArrayList<String> getFollowing() {
        return following;
    }

    public void setFollowing(myArrayList<String> following) {
        this.following = following;
    }

    public myArrayList<String> getFollowers() {
        return followers;
    }

    public void setFollowers(myArrayList<String> followers) {
        this.followers = followers;
    }

    public String getName() {
        return name;
    }

    // Diğer getter ve setter metotları...

    @Override
    public String toString() {
        return
                "username='" + username + '\'' +
                        ", name='" + name + '\'' +
                        ", followersCount=" + followersCount +
                        ", followingCount=" + followingCount +
                        ", language=" + language +
                        ", region=" + region +
                        ", tweets=" + tweets +
                        ", following=" + following +
                        ", followers=" + followers;
    }
}