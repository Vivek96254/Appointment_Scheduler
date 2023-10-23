package DSAProject;

import java.util.*;

public class DriverCode {
    public static void main(String[] args) {
        List<Appointment> appointments = new ArrayList<>();
        Queue<Appointment> appointmentQueue = new LinkedList<>();
        AppointmentScheduler scheduler = new AppointmentScheduler();
        scheduler.loadAppointmentsFromFile(appointments, appointmentQueue);

        scheduler.startReminderThread(appointmentQueue);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println();
            System.out.println();
            System.out.println("       ------ Appointment Scheduler Menu ------");
            System.out.println();
            System.out.println("1. Schedule an Appointment");
            System.out.println("2. View scheduled appointments");
            System.out.println("3. Remove the first scheduled appointment in the queue");
            System.out.println("4. Exit");
            System.out.print(">>>> ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    scheduler.scheduleAppointment(scanner, appointments, appointmentQueue);
                    break;
                case "2":
                    scheduler.viewAppointments(appointmentQueue);
                    break;
                case "3":
                    scheduler.completeAndRemoveAppointment(appointmentQueue, appointments);
                    break;
                case "4":
                    scheduler.saveAppointmentsToFile(appointments);
                    System.out.println("Exiting the Appointment Scheduler.");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
}
