package output.Query;

import fileio.ActionInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import java.util.*;
import java.util.stream.Collectors;
import output.Result;
import output.Store.*;

public final class QueryVideoMostViewed extends Query {
    @Override
    public Result query(final ActionInputData actionInputData, final Input input) {
        Result result = new Result();
        StringBuffer message = new StringBuffer().append("Query result: [");
        List<String> storeNameShow = new ArrayList<>();
        List<String> nameList = new ArrayList<>();
        List<StoreQueryMostViewed> storeQueryMostViewedList = new ArrayList<>();
        if (actionInputData.getObjectType().equals("movies")) {
            for (MovieInputData movieInputData : input.getMovies()) {
                int checkMatchYear = 0;
                int checkMatchGenre = 0;
                if (actionInputData.getFilters().get(0).get(0) == null) {
                    checkMatchYear++;
                } else {
                    for (int i = 0; i < actionInputData.getFilters().get(0).size(); i++) {
                        if (movieInputData.getYear() == Integer.parseInt(actionInputData.
                                getFilters().get(0).get(0))) {
                            checkMatchYear++;
                            break;
                        }
                    }
                }
                if (actionInputData.getFilters().get(1).get(0) == null) {
                    checkMatchGenre++;
                } else {
                    for (int i = 0; i < actionInputData.getFilters().get(1).size(); i++) {
                        if (movieInputData.getGenres().contains(actionInputData
                                .getFilters().get(1).get(0))) {
                            checkMatchGenre++;
                            break;
                        }
                    }
                }
                if (checkMatchGenre == 1 && checkMatchYear == 1) {
                    storeNameShow.add(movieInputData.getTitle());
                }
            }
        } else {
            for (SerialInputData serialInputData : input.getSerials()) {
                int checkMatchYear = 0;
                int checkMatchGenre = 0;
                if (actionInputData.getFilters().get(0).get(0) == null) {
                    checkMatchYear++;
                } else {
                    for (int i = 0; i < actionInputData.getFilters().get(0).size(); i++) {
                        if (serialInputData.getYear() == Integer.parseInt(actionInputData.
                                getFilters().get(0).get(0))) {
                            checkMatchYear++;
                            break;
                        }
                    }
                }
                if (actionInputData.getFilters().get(1).get(0) == null) {
                    checkMatchGenre++;
                } else {
                    for (int i = 0; i < actionInputData.getFilters().get(1).size(); i++) {
                        if (serialInputData.getGenres().contains(actionInputData
                                .getFilters().get(1).get(0))) {
                            checkMatchGenre++;
                            break;
                        }
                    }
                }
                if (checkMatchGenre == 1 && checkMatchYear == 1) {
                    storeNameShow.add(serialInputData.getTitle());
                }
            }
        }

        for (String s : storeNameShow) {
            int sum = 0;
            for (UserInputData userInputData : input.getUsers()) {
                if (userInputData.getHistory().containsKey(s)) {
                    sum += userInputData.getHistory().get(s);
                }
            }
            if (sum != 0) {
                storeQueryMostViewedList.add(new StoreQueryMostViewed(s, sum));
            }
        }

        Comparator<StoreQueryMostViewed> comparator = new Comparator<StoreQueryMostViewed>() {
            @Override
            public int compare(StoreQueryMostViewed o1, StoreQueryMostViewed o2) {
                int result = 0;
                if (!o1.getNumberOfView().equals(o2.getNumberOfView())) {
                    result = o1.getNumberOfView().compareTo(o2.getNumberOfView());
                } else {
                    result = o1.getNameShow().compareTo(o2.getNameShow());
                }
                return result;
            }
        };
        Collections.sort(storeQueryMostViewedList, comparator);
        if (actionInputData.getSortType().equals("desc")) {
            Collections.reverse(storeQueryMostViewedList);
        }

        for (var entry : storeQueryMostViewedList) {
            nameList.add(entry.getNameShow());
        }

        if (actionInputData.getNumber() < nameList.size()) {
            for (int i = 0; i < actionInputData.getNumber(); i++) {
                if (i == 0) {
                    message.append(nameList.get(i));
                } else {
                    message.append(", ").append(nameList.get(i));
                }
            }
        } else {
            for (int i = 0; i < nameList.size(); i++) {
                if (i == 0) {
                    message.append(nameList.get(i));
                } else {
                    message.append(", ").append(nameList.get(i));
                }
            }
        }

        message.append("]");
        result.setId(actionInputData.getActionId());
        result.setMessage(message);
        return result;
    }
}
