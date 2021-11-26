# info:

https://github.com/TungMunte/tema_1

# ABOUT_THE_CODE

* main
    * Main :
        - iterate every action in ActionInputDataList
        - with every action , we will check action_type , type of the action

* output
    * Command : contains solutions for favorite, rating and view
        * Command : abstract class
        * CommandFavortie :
            - iterate UserInputDataList to find user - iterate History List of user found - check name of show is in
              Favorite List or not - if movie or show is in History List, not in Favorite List -> add element to
              Favorite List of user found
        * CommandRating :
            * checkUserUnseen :

            - iterate userInputDataList to find name of user - iterate Hisotry List of user to check movie is in History
              List of user or not * commandUserUnseen :
            - iterate userInputDataList to find name of user - use findUnseenShow method to find unwatched show of user
                - add unwatched show of current user to Hashmap unseenShowOfUser * checkUserRated :
            - iterate actions before current action - check if previous actions having same username , same show name or
              same season * commandUserRated :
            - return result object * command :
            - add username to userNameActiv to use for QueryUserRating class - iterate userInputDataList to find user -
              iterate History List of user to find name show - use findSerial , findMovie method to add show to
              movieInputDataListMap for later using in QueryVideoRating class * findSerial :
            - iterate serialInputDataList to find serial - add serial and rating point in movieInputDataListMap for
              later using in QueryVideoRating class * findMovie :
            - iterate movieInputDataList to find movie - add movie and rating point in movieInputDataListMap for later
              using in QueryVideoRating class

        * CommmandView :
            - iterate userInputDataList to find user
            - check in History List that user if movie is watched or not
            - if not , add movie to History List of user and add number of view is 1
            - if movie is watched , plus number of view

    * Query : contains solutions for requirements of query
        * Query : Abstract class
        * QueryActorAverage :
            - add previous actions into actionInputDataList
            - find action having action_type is command and type is rating put them into ratingActionForMovie List ,
              ratingActionForSerial List
            - sort ratingActionForMovie List based on name of movie and username
            - sort ratingActionForSerial List based on name of serial and username
            - add correct action to successRatingMovie List if the current action does not have same name , same movie
              name with previous action
            - add correct action to successRatingMovie List if the current action does not have same name , same movie
              name , same season with previous action
            - calculate medium grade for every movie and serial and add name show, grade of show to nameShowAndGrade
              List and sort nameShowAndGrade List based on medium grade
            - iterate users in input.getActors() , calculate grade and add name of actor and grade to nameActorAndGrade
              then sort nameActorAndGrade List based on grade and name of actor
        * QueryActorAwards :
            - iterate input.getActors()
            - add name of awards and number of awards to actorAward Map
            - check in actorAward Map contains all awards required in filter or not
            - add name of actor , total number of all awards to storeQueryActorAwardsList List
            - storeQueryActorAwardsList List based on number of awards and name of actor
        * QueryActorFilterDescription
            - iterate input.getActors()
            - if current actor contains all words in description , add into actorList List
        * QueryUserRating :
            - iterate userNameActiv List and iterate actionOfUser List inside
            - add name of user, number of active times into storeQueryUserRatingList List
            - sort storeQueryUserRatingList List based on number of active times and name of user
        * QueryVideoFavorite :
            - iterate input.getMovies() and input.getSerials()
            - check if current show having same year and same genre or not
            - add them into storeNameShow List
            - iterate storeNameShow List and iterate input.getUsers() inside
            - if current favorite list of current user having show, countApprearacne plus 1
            - if countApprearacne != 0 , add countApprearacne and name show into storeQueryVideoFavoriteList List
            - sort storeQueryVideoFavoriteList List based on countApprearacne and name show
        * QueryVideoLongest
            - iterate input.getMovies() and input.getSerials()
            - check if current show having same year and same genre or not
            - calculate total durations of each movie and show and add name of show and total durations to
              storeQueryVideoLongestList List
            - sort storeQueryVideoLongestList based on name show and total duration
        * QueryVideoMostViewed
            - iterate input.getMovies() and input.getSerials()
            - check if current show having same year and same genre or not
            - calculate total view of each movie and show and add name of show and total view to
              storeQueryMostViewedList List
            - sort storeQueryMostViewedList based on name show and total view
        * QueryVideoRating
            - iterate movieInputDataListMap List
            - check if current show having same year and same genre or not
            - calculate medium grade of each show and add name of show and medium grade of each show to
              storeQueryVideoRatingList List
            - sort storeQueryVideoRatingList based on name show and medium grade

    * Recommend : contains solutions for requirements of recommend
        * RecommendBestUnseen
            - iterate input.getUsers() to find user from current action
            - iterate unseenShowOfUser to find show user did not watch
            - add name of show , rating point of show and index position to storeRecommendBestUnseenList
            - sort storeRecommendBestUnseenList based on rating point and position index from orderApprearance
        * RecommendFavorite
            - iterate input.getUsers() to find user from current action
            - iterate orderApprearance List and add name of show not in History List of user to unseenShow List
            - iterate unseenShow List and iterate input.getUsers() inside except for current user , to find the
              unwatched show is on the Favorite List of how many users
            - add name of show, sum of times appears in favorite list of other users , position index to
              storeRecommendFavoriteList
            - sort storeRecommendFavoriteList based on sum of times appears in favorite list of other users and position
              indext in orderApprearance List
        * RecommendPopular :
            - iterate input.getUsers() to find user from current action
            - iterate orderApprearance List and add name of show not in History List of user to unseenShow List
            - iterate unseenShow and iterate input.getUsers() except for current user to find sum of views of each show
            - add name of show , sum of views, position index to storeRecommendPopularList
            - sort storeRecommendPopularList based on sum of views, position index
        * RecommendSearch
            - iterate input.getUsers() to find user from current action
            - iterate input.getMovies() and input.getSerials() to find show having same genre and add them to
              showInputListSameGerne
            - iterate showInputListSameGerne to remove show already in History List of current user
            - iterate unseenShowOfUser to find unwatched shows of current user and iterate showInputListSameGerne inside
              to find show having same genre and add name of show and rating point of show into storeRecommendSearchList
            - sort storeRecommendSearchList based on rating point and position index
        * RecommendStandard
            - iterate input.getUsers() to find user from current action\
            - iterate orderApprearance to find the first show not in History list of current user

    * Store : store members used for sort List