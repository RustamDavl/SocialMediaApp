package com.rustdv.socialmediaapp.entity;

import java.util.NoSuchElementException;

public enum Months {

    JANUARY("января"),
    FEBRUARY("февраля"),
    MARCH("марта"),
    APRIL("апреля"),
    MAY("мая"),
    JUNE("июня"),
    JULY("июля"),
    AUGUST("августа"),
    SEPTEMBER("сентября"),
    OCTOBER("октября"),
    NOVEMBER("ноября"),
    DECEMBER("декабря");
    private String month;

    Months() {}
    Months(String month) {

        this.month = month;
    }

    private String getName() {
        return this.month;
    }

    public static String getMonthByOrder(int order) {
        return switch (order) {
            case 1 -> JANUARY.getName();
            case 2 -> FEBRUARY.getName();
            case 3 -> MARCH.getName();
            case 4 -> APRIL.getName();
            case 5 -> MAY.getName();
            case 6 -> JUNE.getName();
            case 7 -> JULY.getName();
            case 8 -> AUGUST.getName();
            case 9 -> SEPTEMBER.getName();
            case 10 -> OCTOBER.getName();
            case 11 -> NOVEMBER.getName();
            case 12 -> DECEMBER.getName();
            default -> {
                throw new NoSuchElementException("there is no month with order " + order);
            }
        };
    }

}