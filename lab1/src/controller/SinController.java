package controller;

import model.SinModel;
import view.MainFrame;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class SinController {

    private final SinModel model;
    private final MainFrame view;

    public SinController(SinModel model, MainFrame view) {
        this.model = model;
        this.view = view;
    }

    public void onCalculateClicked(MainFrame view) {
        String input = view.askBirthDate();
        if (input == null) return;

        input = input.trim();
        if (input.isEmpty()) {
            view.showError("Дата рождения не введена.");
            return;
        }

        LocalDate date;
        try {
            date = LocalDate.parse(input);
        } catch (DateTimeParseException ex) {
            view.showError("Некорректный формат даты. Пример: 1990-05-17");
            return;
        }

        if (date.isAfter(LocalDate.now())) {
            view.showError("Дата рождения не может быть в будущем.");
            return;
        }
        if (date.getYear() < 1900) {
            view.showError("Дата рождения слишком давняя (проверьте год).");
            return;
        }

        view.setLastBirthDate(date);
        model.setBirthDate(date);
    }
}
