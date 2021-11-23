package output.Query;

import fileio.ActionInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import java.util.*;
import java.util.stream.Collectors;
import output.Result;

public final class QueryVideoMostViewed extends Query {
    @Override
    public Result query(final ActionInputData actionInputData, final Input input) {
        Result result = new Result();
        StringBuffer message = new StringBuffer().append("Query result: [");
        List<String> storeNameShow = new ArrayList<>();
        Map<String, Integer> unsortedNameShow = new HashMap<>();
        Map<String, Integer> sortedNameShow;
        List<String> nameList = new ArrayList<>();

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
                unsortedNameShow.put(s, sum);
            }
        }

        if (actionInputData.getSortType().equals("acs")) {
            sortedNameShow = unsortedNameShow.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.
                            toMap(Map.Entry::getKey,
                                    Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        } else {
            sortedNameShow = unsortedNameShow.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .collect(Collectors.
                            toMap(Map.Entry::getKey,
                                    Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        }

        for (var entry : sortedNameShow.entrySet()) {
            nameList.add(entry.getKey());
        }

        if (actionInputData.getNumber() < sortedNameShow.size()) {
            for (int i = 0; i < actionInputData.getNumber(); i++) {
                if (i == 0) {
                    message.append(nameList.get(i));
                } else {
                    message.append(", ").append(nameList.get(i));
                }
            }
        } else {
            for (int i = 0; i < sortedNameShow.size(); i++) {
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
