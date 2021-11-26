package output.Query;

import fileio.ActionInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import java.util.*;
import output.Result;
import output.Store.StoreQueryVideoFavorite;

public class QueryVideoFavorite extends Query {
    @Override
    public final Result query(final ActionInputData actionInputData, final Input input) {
        Result result = new Result();
        StringBuffer message = new StringBuffer().append("Query result: [");
        List<String> storeNameShow = new ArrayList<>();
        List<String> nameList = new ArrayList<>();
        List<StoreQueryVideoFavorite> storeQueryVideoFavoriteList = new ArrayList<>();
        if (actionInputData.getObjectType().equals("movies")) {
            for (MovieInputData movieInputData : input.getMovies()) {
                int checkMatchYear = 0;
                int checkMatchGenre = 0;
                if (actionInputData.getFilters().get(0).get(0) == null) {
                    checkMatchYear++;
                } else {
                    for (int i = 0; i < actionInputData.getFilters().get(0).size(); i++) {
                        if (movieInputData.getYear() == Integer.
                                parseInt(actionInputData.
                                        getFilters().get(0).get(i))) {
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
                        if (serialInputData.getYear() == Integer.
                                parseInt(actionInputData.
                                        getFilters().get(0).get(i))) {
                            checkMatchYear++;
                            break;
                        }
                    }
                }
                if (actionInputData.getFilters().get(1).get(0) == null) {
                    checkMatchGenre++;
                } else {
                    for (int i = 0; i < actionInputData.getFilters().
                            get(1).size(); i++) {
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
        for (int i = 0; i < storeNameShow.size(); i++) {
            int countApprearacne = 0;
            for (UserInputData userInputData : input.getUsers()) {
                if (userInputData.getFavoriteMovies().
                        contains(storeNameShow.get(i))) {
                    countApprearacne++;
                }
            }
            if (countApprearacne != 0) {
                storeQueryVideoFavoriteList.add(
                        new StoreQueryVideoFavorite(storeNameShow.get(i),
                                countApprearacne));
            }
        }
        Comparator<StoreQueryVideoFavorite> comparator = new Comparator<StoreQueryVideoFavorite>() {
            @Override
            public int compare(final StoreQueryVideoFavorite o1,
                               final StoreQueryVideoFavorite o2) {
                int result = 0;
                if (!o1.getNumberOfFavorite().
                        equals(o2.getNumberOfFavorite())) {
                    result = o1.getNumberOfFavorite().
                            compareTo(o2.getNumberOfFavorite());
                } else {
                    result = o1.getNameVideo().compareTo(o2.getNameVideo());
                }
                return result;
            }
        };
        Collections.sort(storeQueryVideoFavoriteList, comparator);
        if (actionInputData.getSortType().equals("desc")) {
            Collections.reverse(storeQueryVideoFavoriteList);
        }

        for (var object : storeQueryVideoFavoriteList) {
            nameList.add(object.getNameVideo());
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
