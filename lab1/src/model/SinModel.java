package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class SinModel {

    public interface ModelListener {
        void onModelChanged();
    }

    private LocalDate birthDate;
    private int totalSins = 0;
    private int deadlySins = 0;
    private int dailySins = 0;

    private final List<ModelListener> listeners = new ArrayList<>();

    public void addListener(ModelListener l)    { listeners.add(l); }
    public void removeListener(ModelListener l) { listeners.remove(l); }

    private void fireModelChanged() {
        for (ModelListener l : listeners) l.onModelChanged();
    }

    public LocalDate getBirthDate() { return birthDate; }
    public int getTotalSins()       { return totalSins; }
    public int getDeadlySins()      { return deadlySins; }
    public int getDailySins()       { return dailySins; }

    public void setBirthDate(LocalDate date) {
        this.birthDate = date;
        recalculate();
        fireModelChanged();
    }

    private void recalculate() {
        if (birthDate == null) {
            totalSins = deadlySins = dailySins = 0;
            return;
        }

        LocalDate today = LocalDate.now();
        if (birthDate.isAfter(today)) {
            totalSins = deadlySins = dailySins = 0;
            return;
        }

        long daysAlive  = ChronoUnit.DAYS.between(birthDate, today);
        long yearsAlive = ChronoUnit.YEARS.between(birthDate, today);

        // повседневные грехи — 3 в день
        dailySins = (int) (daysAlive * 3);

        // смертные — 7 за каждый год, кратный 7
        deadlySins = (int) ((yearsAlive / 7) * 7);

        int sum = dailySins + deadlySins;

        // родился в воскресенье — спрос строже
        if (birthDate.getDayOfWeek().getValue() == 7) {
            sum = (int) Math.round(sum * 1.1);
        }

        totalSins = sum;
    }
}