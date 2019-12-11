package Controllers;

import Utils.SemanaDTO;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.*;

public class CalendarioController {
    public ModelAndView calendarView(Request request, Response response) {
        Middlewares.authenticated(request, response);
        Map<String, Object> parametros = new HashMap<>();
        Calendar cal = Calendar.getInstance();
        int monthNumber = cal.get(Calendar.MONTH);
        String monthName = this.monthName(monthNumber);
        int yearNumber = cal.get(Calendar.YEAR);
        parametros.put("section", "Calendario");
        parametros.put("monthName", monthName);
        parametros.put("monthNumber", monthNumber);
        parametros.put("year", yearNumber);
        parametros.put("semanas", createSemanas(yearNumber, monthNumber));
        return new ModelAndView(parametros, "Calendar.hbs");
    }

    public ModelAndView customizedCalendarView(Request request, Response response) {
        Middlewares.authenticated(request, response);
        Map<String, Object> parametros = new HashMap<>();
        int monthNumber = Integer.parseInt(request.params("month"));
        int yearNumber = Integer.parseInt(request.params("year"));
        if (monthNumber < 0 || monthNumber > 11 || yearNumber < 0){
            monthNumber = 0;
            yearNumber = 2020;
        }
        String monthName = this.monthName(monthNumber);
        parametros.put("section", "Calendario");
        parametros.put("monthName", monthName);
        parametros.put("monthNumber", monthNumber);
        parametros.put("year", yearNumber);
        parametros.put("semanas", createSemanas(yearNumber, monthNumber));
        return new ModelAndView(parametros, "Calendar.hbs");
    }

    /**
     * Creo la lista de semanas para generar el calendario
     * @param yearNumber año sobre el que hallo la lista de semanaas
     * @param monthNumber numero de mes al que le hallo la lista de semanas
     * @return retorno una lista de semanas
     */
    private List<SemanaDTO> createSemanas(int yearNumber, int monthNumber){
        List<SemanaDTO> semanas = new ArrayList<>();

        Calendar month = Calendar.getInstance();
        month.clear();
        month.set(Calendar.YEAR, yearNumber);
        month.set(Calendar.MONTH, monthNumber);

        //Obtengo el ultimo dia del mes solicitado
        int lastMonthDay = month.getActualMaximum(Calendar.DAY_OF_MONTH);
        //Obtengo el dia de la semana que empieza el mes solicitado
        int firstMonthDayOfWeek = month.get(Calendar.DAY_OF_WEEK);

        int prevMonthNumber;
        int prevYearNumber;
        if(monthNumber == 0){
            prevMonthNumber = 11;
            prevYearNumber = yearNumber - 1;
        } else {
            prevMonthNumber = monthNumber - 1;
            prevYearNumber = yearNumber;
        }

        Calendar prevMonth = Calendar.getInstance();
        prevMonth.clear();
        prevMonth.set(Calendar.YEAR, prevYearNumber);
        prevMonth.set(Calendar.MONTH, prevMonthNumber);

        //Obtengo el ultimo dia del mes anterior al solicitado
        int lastPrevMonthDay = prevMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
        int day = 0;
        do {
            SemanaDTO semana = new SemanaDTO();
            if (day == 0) {
                day = semana.startingWeek(firstMonthDayOfWeek, lastPrevMonthDay);
            } else {
                day += semana.normalWeek(day, lastMonthDay);
            }
            semanas.add(semana);
        } while (day != lastMonthDay);
        return semanas;
    }

    private String monthName(int monthNumber) {
        String[] monthName = {"Enero", "Febrero",
                "Marzo", "Abril", "Mayo", "Junio", "Julio",
                "Agosto", "Septiembre", "Octubre", "Noviembre",
                "Diciembre"};

        return monthName[monthNumber];
    }
}
