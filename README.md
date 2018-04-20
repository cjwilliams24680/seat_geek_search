Note:
 - The seat geek api requires a client id. If you want to build this app you'll need to register for a client id [here](http://platform.seatgeek.com/) and then add it to your gradle.properties file like this: `SEAT_GEEK_CLIENT_ID=aaaaaaaaaaa`. I added it to my global ~/.gradle/gradle.properties folder but the local one should work as well.

This app acts as a user interface for the Seat Geek event search API. It's written in a combination of Kotlin and Java and uses a number of different libraries including RxJava, Dagger, Retrofit, and Glide. It also uses a combination of Android library elements such as ConstrainLayout, SearchView, CardView, RecyclerView, databinding, and shared element transitions.

![alt text](https://raw.githubusercontent.com/cjwilliams24680/seat_geek_search/master/search_screenshot.png)
![alt text](https://raw.githubusercontent.com/cjwilliams24680/seat_geek_search/master/search_detail_screenshot.png)
