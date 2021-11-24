package output.Query;

import fileio.ActionInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.ShowInput;
import java.util.*;
import java.util.stream.Collectors;
import output.Result;
import output.Store.*;

public class QueryVideoLongest extends Query {
    @Override
    public final Result query(final ActionInputData actionInputData, final Input input) {
        Result result = new Result();
        StringBuffer message = new StringBuffer().append("Query result: [");
        Map<ShowInput, Integer> unsortedNameShow = new HashMap<>();
        Map<ShowInput, Integer> sortedNameShow;
        List<String> nameList = new ArrayList<>();
        List<StoreQueryVideoLongest> storeQueryVideoLongestList = new ArrayList<>();
        if (actionInputData.getObjectType().equals("movies")) {
            for (MovieInputData movieInputData : input.getMovies()) {
                int checkMatchYear = 0;
                int checkMatchGenre = 0;
                if (actionInputData.getFilters().get(0).get(0) == null) {
                    checkMatchYear++;
                } else {
                    for (int i = 0; i < actionInputData.getFilters().get(0).size(); i++) {
                        if (movieInputData.getYear() == Integer.parseInt(actionInputData.
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
                    unsortedNameShow.put(movieInputData, movieInputData.getDuration());
                    storeQueryVideoLongestList.add(new StoreQueryVideoLongest(movieInputData.getTitle(), movieInputData.getDuration()));
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
                        if (serialInputData.getGenres().contains(actionInputData
                                .getFilters().get(1).get(0))) {
                            checkMatchGenre++;
                            break;
                        }
                    }
                }
                if (checkMatchGenre == 1 && checkMatchYear == 1) {
                    int sum = 0;
                    for (int i = 0; i < serialInputData.getSeasons().size(); i++) {
                        sum += serialInputData.getSeasons().get(i).getDuration();
                    }
                    unsortedNameShow.put(serialInputData, sum);
                    storeQueryVideoLongestList.add(new StoreQueryVideoLongest(serialInputData.getTitle(), sum));
                }
            }
        }

        storeQueryVideoLongestList.sort(new Comparator<StoreQueryVideoLongest>() {
            @Override
            public int compare(StoreQueryVideoLongest o1, StoreQueryVideoLongest o2) {
                int result = 0;
                if (!o1.getNumberOfTime().equals(o2.getNumberOfTime())) {
                    result = o1.getNumberOfTime().compareTo(o2.getNumberOfTime());
                } else {
                    result = o1.getNameShow().compareTo(o2.getNameShow());
                }
                return result;
            }
        });
        if (actionInputData.getSortType().equals("desc")){
            Collections.reverse(storeQueryVideoLongestList);
        }
        for (var entry : storeQueryVideoLongestList) {
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
