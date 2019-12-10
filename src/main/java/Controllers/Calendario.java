package Controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Calendario {
    public ModelAndView calendarView(Request request, Response response) {
        //TODO: ver si se puede implementar esta garompa como un middleware para no tener que copiarlo en todos lados
        Map<String, Object> parametros = new HashMap<>();
        if(request.session().isNew()){
            request.session(true);
            request.session().attribute("logged", false);
            request.session().attribute("logError", false);
            response.redirect("/login");
        }
        Calendar cal = Calendar.getInstance();
        int monthNumber = cal.get(Calendar.MONTH);
        String monthName = this.monthName(monthNumber);
        parametros.put("section", "Calendario");
        parametros.put("monthName", monthName);
        parametros.put("monthNumber", monthNumber);
        parametros.put("year", cal.get(Calendar.YEAR));
        return new ModelAndView(parametros, "Calendar.hbs");
    }

    public ModelAndView customizedCalendarView(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        if(request.session().isNew()){
            request.session(true);
            request.session().attribute("logged", false);
            request.session().attribute("logError", false);
            response.redirect("/login");
        }
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
        return new ModelAndView(parametros, "Calendar.hbs");
    }

    private String monthName(int monthNumber) {
        String[] monthName = {"Enero", "Febrero",
                "Marzo", "Abril", "Mayo", "Junio", "Julio",
                "Agosto", "Septiembre", "Octubre", "Noviembre",
                "Diciembre"};

        return monthName[monthNumber];
    }
}
