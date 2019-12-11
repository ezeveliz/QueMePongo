package Utils;

public class SemanaDTO {
    public int getDomingo() {
        return domingo;
    }

    public int getLunes() {
        return lunes;
    }

    public int getMartes() {
        return martes;
    }

    public int getMiercoles() {
        return miercoles;
    }

    public int getJueves() {
        return jueves;
    }

    public int getViernes() {
        return viernes;
    }

    public int getSabado() {
        return sabado;
    }

    private int domingo;
    private int lunes;
    private int martes;
    private int miercoles;
    private int jueves;
    private int viernes;
    private int sabado;

    /**
     * Creo la primera semana del mes
     * @param firstMonthDayOfWeek dia en el que empieza la semana
     * @param lastPrevMonthDay dia en el que termina el mes anterior
     * @return cantidad de dias del mes seleccionado en la primera semana
     */
    public int startingWeek(int firstMonthDayOfWeek, int lastPrevMonthDay) {
        int day;
        switch (firstMonthDayOfWeek) {
            case 1:
                domingo = 1;
                lunes = 2;
                martes = 3;
                miercoles = 4;
                jueves = 5;
                viernes = 6;
                sabado = 7;
                day = 7;
                break;
            case 2:
                domingo = lastPrevMonthDay;
                lunes = 1;
                martes = 2;
                miercoles = 3;
                jueves = 4;
                viernes = 5;
                sabado = 6;
                day = 6;
                break;
            case 3:
                domingo = lastPrevMonthDay - 1;
                lunes = lastPrevMonthDay;
                martes = 1;
                miercoles = 2;
                jueves = 3;
                viernes = 4;
                sabado = 5;
                day = 5;
                break;
            case 4:
                domingo = lastPrevMonthDay - 2;
                lunes = lastPrevMonthDay - 1;
                martes = lastPrevMonthDay;
                miercoles = 1;
                jueves = 2;
                viernes = 3;
                sabado = 4;
                day = 4;
                break;
            case 5:
                domingo = lastPrevMonthDay - 3;
                lunes = lastPrevMonthDay - 2;
                martes = lastPrevMonthDay - 1;
                miercoles = lastPrevMonthDay;
                jueves = 1;
                viernes = 2;
                sabado = 3;
                day = 3;
                break;
            case 6:
                domingo = lastPrevMonthDay - 4;
                lunes = lastPrevMonthDay - 3;
                martes = lastPrevMonthDay - 2;
                miercoles = lastPrevMonthDay - 1;
                jueves = lastPrevMonthDay;
                viernes = 1;
                sabado = 2;
                day = 2;
                break;
            case 7:
                domingo = lastPrevMonthDay - 5;
                lunes = lastPrevMonthDay - 4;
                martes = lastPrevMonthDay - 3;
                miercoles = lastPrevMonthDay - 2;
                jueves = lastPrevMonthDay - 1;
                viernes = lastPrevMonthDay;
                sabado = 1;
                day = 1;
                break;
            default:
                //JAMAS se tendria que poder llegar aca
                throw new IllegalStateException("Unexpected value: " + firstMonthDayOfWeek);
        }
        return day;
    }

    /**
     * Creo las semanas que no sean la primera https://i1.wp.com/www.undeaddev.com/wp-content/uploads/2016/03/kitty-string.jpg
     * @param day cantidad de dias acumulados hasta ahora
     * @param lastMonthDay dia en el que termina el mes
     * @return cant de dias agregados
     */
    public int normalWeek(int day, int lastMonthDay) {
        int daysAdded = 0;
        domingo = ++day;
        daysAdded++;
        if (day + 1 > lastMonthDay){
            lunes = 1;
            martes = 2;
            miercoles = 3;
            jueves = 4;
            viernes = 5;
            sabado = 6;
        } else {
            lunes = ++day;
            daysAdded++;
            if (day + 1 > lastMonthDay){
                martes = 1;
                miercoles = 2;
                jueves = 3;
                viernes = 4;
                sabado = 5;
            } else {
                martes = ++day;
                daysAdded++;
                if (day + 1 > lastMonthDay){
                    miercoles = 1;
                    jueves = 2;
                    viernes = 3;
                    sabado = 4;
                } else {
                    miercoles = ++day;
                    daysAdded++;
                    if (day + 1 > lastMonthDay){
                        jueves = 1;
                        viernes = 2;
                        sabado = 3;
                    } else {
                        jueves = ++day;
                        daysAdded++;
                        if (day + 1 > lastMonthDay){
                            viernes = 1;
                            sabado = 2;
                        } else {
                            viernes = ++day;
                            daysAdded++;
                            if (day + 1 > lastMonthDay){
                                sabado = 1;
                            } else {
                                sabado = ++day;
                                daysAdded++;
                            }
                        }
                    }
                }
            }
        }
        return daysAdded;
    }
}
