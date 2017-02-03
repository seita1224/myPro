package com.example.hiro.myapplication;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * 日本の「国民の祝日」を表す列挙型クラス
 *
 * [注意事項]
 *   ・2015/5/23時点の『国民の祝日に関する法律』に基づいています。
 *    そのため今後の法律改正により正常に動作しなくなる可能性があります。
 *   ・2151年以降の「春分の日」、「秋分の日」は求めることができません。
 */

public enum JapaneseNationalHoliday {

    ComingOfAgeDay                    ("成人の日") {
        @Override
        public Calendar dateOf(int year) {
            if (1949 <= year && year <= 1999) {
                return toCalendar(year, 1, 15);
            } else if (2000 <= year) {
                return mondayOf(year, 1, 2);
            }
            return null;
        }
    },

    MotherDay                    ("母の日") {
        @Override
        public Calendar dateOf(int year) {
            if (1913 <= year) {
                return sundayOf(year, 5, 2);
            }
            return null;
        }
    },
    FatherDay                    ("父の日") {
        @Override
        public Calendar dateOf(int year) {
            if (1966 <= year) {
                return sundayOf(year, 6, 3);
            }
            return null;
        }
    },
    RespectForTheAgedDay            ("敬老の日") {
        @Override
        public Calendar dateOf(int year) {
            if (1966 <= year && year <= 2002) {
                return toCalendar(year, 9, 15);
            } else if (2003 <= year) {
                return mondayOf(year, 9, 3);
            }
            return null;
        }
    },

    Xmas                    ("クリスマス") {
        @Override
        public Calendar dateOf(int year) {
            if (1900 <= year) {
                return toCalendar(year, 12, 25);
            }
            return null;
        }
    },
    ;
    private static final double DIFF_DAY_OF_YEAR        = 0.242194;
    private final String name_;

    private JapaneseNationalHoliday(String name) {
        name_ = name;
    }

    /**
     * 指定した年におけるこの祝日の日付を取得します。
     * 祝日が存在しない場合はnullを返す。
     *
     * @param year    年
     * @return 日付
     * @throws IllegalArgumentException    「春分の日」「秋分の日」を計算できないyearを指定した場合
     */
    public abstract Calendar dateOf(int year);

    /**
     * 指定した日付がこの祝日の日付と一致するか判定します。
     *
     * @param cal    判定する日付
     * @return 一致する場合は true
     */
    public boolean is(Calendar cal) {
        return isSameDate(cal, dateOf(cal.get(Calendar.YEAR)));
    }

    @Override
    public String toString() {
        return name_;
    }

    private static int calcVernalEquinoxDay(int year) {
        int diff1 = year - 1980;
        int diff2 = 0;
        double standard = 0;
        if (year <= 1979) {
            standard = 20.8357;
            diff2 = year - 1983;
        } else if (year <= 2099) {
            standard = 20.8431;
            diff2 = year - 1980;
        } else if (year <= 2150) {
            standard = 21.8510;
            diff2 = year - 1980;
        } else {
            throw new IllegalArgumentException(year + "th year is illegal value.");
        }
        return (int)(standard + DIFF_DAY_OF_YEAR * diff1 - (int)(diff2 / 4));
    }

    private static int calcAutumnalEquinoxDay(int year) {
        int diff1 = year - 1980;
        int diff2 = 0;
        double standard = 0;
        if (year <= 1979) {
            standard = 23.2588;
            diff2 = year - 1983;
        } else if (year <= 2099) {
            standard = 23.2488;
            diff2 = year - 1980;
        } else if (year <= 2150) {
            standard = 24.2488;
            diff2 = year - 1980;
        } else {
            throw new IllegalArgumentException(year + "th year is illegal value.");
        }
        return (int)(standard + DIFF_DAY_OF_YEAR * diff1 - (int)(diff2 / 4));
    }

    private static boolean isSameDate(Calendar cal1, Calendar cal2) {
        if (cal1 == null) {
            throw new NullPointerException("Calendar object is null.");
        }
        if (cal2 == null) {
            return false;
        }
        return cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }

    private static Calendar toCalendar(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day);
        return cal;
    }

    private static Calendar mondayOf(int year, int month, int ordinal) {
        //Calendar cal = Calendar.getInstance();
        Calendar cal = new GregorianCalendar(year, month-1 , 1);
        //cal.set(year, month - 1, 1);
        //cal.set(Calendar.WEEK_OF_MONTH, 1);
        cal.set(Calendar.DAY_OF_WEEK_IN_MONTH, ordinal);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal;
    }

    private static Calendar sundayOf(int year, int month, int ordinal) {
        //Calendar cal = Calendar.getInstance();
        Calendar cal = new GregorianCalendar(year, month-1 , 1);
        //cal.set(year, month - 1, 1);
        //cal.set(Calendar.WEEK_OF_MONTH, 1);
        cal.set(Calendar.DAY_OF_WEEK_IN_MONTH, ordinal);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return cal;
    }

}
