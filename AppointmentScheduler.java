package DSAProject;

import java.io.*;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class AppointmentScheduler implements EventScheduler{
        public static final String FILENAME = "appointments.txt";
        private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
        private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");


        @Override
        public void scheduleAppointment(Scanner scanner, List<Appointment> appointments, Queue<Appointment> appointmentQueue) {
            System.out.println("Enter appointment title: ");
            String title = scanner.nextLine();

            String date = "";
            boolean validDate = false;

            while(!validDate){
                System.out.println("Enter appointment date (dd-mm-yyyy):");
                date = scanner.nextLine();
                validDate = validateDate(date);
                if(!validDate){
                    System.out.println("Invalid date format. Please use dd-mm-yyyy format.");
                }
            }

            String time = "";
            boolean validTime = false;

            while(!validTime){
                System.out.println("Enter appointment time (hh:mm): ");
                time = scanner.nextLine();
                validTime = validateTime(time);
                if(!validTime){
                    System.out.println("Invalid time format (hh:mm): ");
                }
            }

            System.out.println("Enter description:");
            String description = scanner.nextLine();

            Appointment appointment = new Appointment(title, date, time, description);
            appointments.add(appointment);
            appointmentQueue.add(appointment);
            System.out.println("-------------------------");
            System.out.println("Appointment scheduled successfully.");
            System.out.println("-------------------------");
        }

        @Override
        public void viewAppointments(Queue<Appointment> appointmentQueue) {
        if(appointmentQueue.isEmpty()){
            System.out.println("No appointments scheduled.");
        }else{
            for(Appointment appointment : appointmentQueue){
                System.out.println("These are the appointments scheduled:");
                System.out.println("-------------------------");
                System.out.println(appointment);
                System.out.println("-------------------------");
                }
            }
        }

        @Override
        public void saveAppointmentsToFile(List<Appointment> appointments) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
                for (Appointment appointment : appointments) {
                    writer.write(appointment.getTitle());
                    writer.newLine();
                    writer.write(appointment.getDate());
                    writer.newLine();
                    writer.write(appointment.getTime());
                    writer.newLine();
                    writer.write(appointment.getDescription());
                    writer.newLine();
                }
            }catch (IOException e){
                    e.printStackTrace();
                }
            }

        @Override
        public void completeAndRemoveAppointment(Queue<Appointment> appointmentQueue, List<Appointment> appointments) {
            if(!appointmentQueue.isEmpty()){
                Appointment completedEvent = appointmentQueue.poll();
                System.out.println("-------------------------");
                System.out.println("The following appointment has been successfully completed and removed: ");
                System.out.println(completedEvent);
                System.out.println("-------------------------");
                appointments.remove(completedEvent);
                saveAppointmentsToFile(appointments);
            }else{
                System.out.println("-------------------------");
                System.out.println("No appointments scheduled.");
                System.out.println("-------------------------");
            }
        }

        @Override
        public void loadAppointmentsFromFile(List<Appointment> appointments, Queue<Appointment> appointmentQueue) {
            try(BufferedReader reader = new BufferedReader(new FileReader(FILENAME))){
                String line;
                while((line = reader.readLine()) != null){
                    String title = line;
                    String date = reader.readLine();
                    String time = reader.readLine();
                    String description = reader.readLine();

                    Appointment appointment = new Appointment(title, date, time, description);
                    appointments.add(appointment);
                    appointmentQueue.add(appointment);
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        private boolean validateDate(String date){
            try{
                DATE_FORMAT.parse(date);
                return true;
            }catch (ParseException e){
                return false;
            }
        }
        private boolean validateTime(String time){
            try{
                TIME_FORMAT.parse(time);
                return true;
            }catch(ParseException e){
                return false;
            }
        }
        private Calendar getCalendarFromAppointment(Appointment appointment){
            Calendar calendar = Calendar.getInstance();
            String[] timeParts = appointment.getTime().split(":");
            int hour = Integer.parseInt(timeParts[0]);
            int minute = Integer.parseInt(timeParts[1]);

            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);

            return calendar;
        }
        public void checkReminders(Queue<Appointment> appointmentQueue){
            if(appointmentQueue.isEmpty()){
                System.out.println("No appointments scheduled.");
                return;
            }

            Calendar now = Calendar.getInstance();

            for(Appointment appointment : appointmentQueue){
                Calendar appointmentTime = getCalendarFromAppointment(appointment);

                if(now.get(Calendar.HOUR_OF_DAY) == appointmentTime.get(Calendar.HOUR_OF_DAY)
                        && now.get(Calendar.MINUTE) == appointmentTime.get(Calendar.MINUTE)){
                    System.out.println();
                    System.out.println("\n-------------------------");
                    System.out.println("****REMINDER: IT'S TIME FOR THE APPOINTMENT!****");
                    System.out.println(appointment);
                    System.out.println("-------------------------");
                    System.out.println();
                    System.out.println("       ------ Appointment Scheduler Menu ------");
                    System.out.println();
                    System.out.println("1. Schedule an Appointment");
                    System.out.println("2. View scheduled appointments");
                    System.out.println("3. Remove the first scheduled appointment in the queue");
                    System.out.println("4. Exit");
                    System.out.print(">>>> ");

                }
            }
        }
        public void startReminderThread(Queue<Appointment> appointmentQueue){
            Thread reminderThread = new Thread(() ->{
                while(true){
                    checkReminders(appointmentQueue);
                    try {
                        TimeUnit.MINUTES.sleep(1);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            });
            reminderThread.setDaemon(true);
            reminderThread.start();
        }
}
