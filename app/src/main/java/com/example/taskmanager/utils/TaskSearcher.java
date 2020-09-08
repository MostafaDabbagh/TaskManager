package com.example.taskmanager.utils;

import com.example.taskmanager.model.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class TaskSearcher {

    public static List<Task> searchInList(Task task, List<Task> taskList) {
        List<Task> resultList = taskList;
        resultList = searchInTitles(task.getTitle(), resultList);
        resultList = searchInDescriptions(task.getDescrptionn(), resultList);
        resultList = searchInDates(task.getDate(), resultList);
        return resultList;
    }

    private static List<Task> searchInTitles(String title, List<Task> taskList) {
        List<Task> resultList = taskList;
        if (title != null && !title.equals("")) {
            resultList = new ArrayList<>();
            for (int i = 0; i < taskList.size(); i++) {
                if (taskList.get(i).getTitle().contains(title)) {
                    resultList.add(taskList.get(i));
                    taskList.remove(i);
                    i--;
                }
            }
        }
        return resultList;
    }

    private static List<Task> searchInDescriptions(String description, List<Task> taskList) {
        List<Task> resultList = taskList;
        if (description != null && !description.equals("")) {
            resultList = new ArrayList<>();
            for (int i = 0; i < taskList.size(); i++) {
                if (taskList.get(i).getDescrptionn().contains(description)) {
                    resultList.add(taskList.get(i));
                    taskList.remove(i);
                    i--;
                }
            }
        }
        return resultList;
    }

    private static List<Task> searchInDates(Date date, List<Task> taskList) {
        List<Task> resultList = taskList;
        if (date != null) {
            resultList = new ArrayList<>();
            GregorianCalendar inputDateGC = new GregorianCalendar();
            inputDateGC.setTime(date);
            for (int i = 0; i < taskList.size(); i++) {
                GregorianCalendar listElementTaskGC = new GregorianCalendar();
                listElementTaskGC.setTime(taskList.get(i).getDate());
                if (inputDateGC.get(Calendar.YEAR) == listElementTaskGC.get(Calendar.YEAR)
                        && inputDateGC.get(Calendar.MONTH) == listElementTaskGC.get(Calendar.MONTH)
                        && inputDateGC.get(Calendar.DAY_OF_MONTH) == listElementTaskGC.get(Calendar.DAY_OF_MONTH)) {
                    resultList.add(taskList.get(i));
                    taskList.remove(i);
                    i--;
                }
            }
        }
        return resultList;
    }
}
