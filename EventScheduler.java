package DSAProject;

import java.util.*;


public interface EventScheduler {
    void scheduleAppointment(Scanner scanner, List<Appointment> appointments, Queue<Appointment> appointmentQueue);

    void viewAppointments(Queue<Appointment> appointmentQueue);

    void saveAppointmentsToFile(List<Appointment> appointments);

    void completeAndRemoveAppointment(Queue<Appointment> appointmentQueue, List<Appointment> appointments);

    void loadAppointmentsFromFile(List<Appointment> appointments, Queue<Appointment> appointmentQueue);

    void checkReminders(Queue<Appointment> appointmentQueue);

    void startReminderThread(Queue<Appointment> appointmentQueue);
}
