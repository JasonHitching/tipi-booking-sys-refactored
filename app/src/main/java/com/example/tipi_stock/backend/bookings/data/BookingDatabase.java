package com.example.tipi_stock.backend.bookings.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Booking.class}, version = 7)
@TypeConverters({DateConverter.class})
public abstract class BookingDatabase extends RoomDatabase {

    public abstract BookingDao bookingDao();

    private static volatile BookingDatabase dbInstance;

    static final ExecutorService databaseExecutor =
            Executors.newFixedThreadPool(4);

    /**
     * Method for obtaining reference to cached Room database
     * @param context application context
     * @return a database instance
     */
    public static BookingDatabase getDbInstance(final Context context) {
        // If a database instance hasn't been created already
        if (dbInstance == null) {
            synchronized (BookingDatabase.class) {
                if (dbInstance == null) {
                    dbInstance = Room.databaseBuilder(context.getApplicationContext(),
                            BookingDatabase.class, "booking_db")
                            .fallbackToDestructiveMigration()
                            .addCallback(insertCallback)
                            .build();
                }
            }
        }
        return dbInstance;
    }

    // Callback for populating data each time the database opens
    private static final RoomDatabase.Callback insertCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            prepopulateBookingData();
        }
    };

    /**
     * Pre-populate the Room database with data
     * POTENTIALLY SEPERATE DATA INTO A .DB FILE?
     */
    public static void prepopulateBookingData() {
        // Write to the database on a worker thread (so as not to clog up the main thread)
        databaseExecutor.execute(() -> {
            BookingDao dao = dbInstance.bookingDao();

            // Start with a fresh database each load
            dao.deleteAllBookings();

            Booking stretchBooking = new Booking.BookingBuilder()
                    .withType("Stretch Tent")
                    .withFirstName("Joe")
                    .withLastName("Copping")
                    .withAddress("1 Pleasant Drive GT53DP")
                    .withCost(800)
                    .withDate(LocalDate.of(2021, 8, 1))
                    .withNumDays(5)
                    .build();
            dao.insertBooking(stretchBooking);

            Booking tipiBooking = new Booking.BookingBuilder()
                    .withType("Tipi")
                    .withFirstName("Jason")
                    .withLastName("Hitching")
                    .withAddress("76 Inway Drive KL25TP")
                    .withCost(233)
                    .withDate(LocalDate.of(2021, 6, 3))
                    .withNumDays(10)
                    .build();
            dao.insertBooking(tipiBooking);

            Booking marqueeBooking = new Booking.BookingBuilder()
                    .withType("Marquee")
                    .withFirstName("Daniel")
                    .withLastName("Rose")
                    .withAddress("26 Flyway Drive TY5YYH")
                    .withCost(500)
                    .withDate(LocalDate.of(2022, 1, 22))
                    .withNumDays(2)
                    .build();
            dao.insertBooking(marqueeBooking);

            Booking marqueeBooking2 = new Booking.BookingBuilder()
                    .withType("Marquee")
                    .withFirstName("Daniel")
                    .withLastName("Rose")
                    .withAddress("26 Flyway Drive TY5YYH")
                    .withCost(500)
                    .withDate(LocalDate.of(2022, 1, 22))
                    .withNumDays(2)
                    .build();
            dao.insertBooking(marqueeBooking2);

            Booking tipiBooking2 = new Booking.BookingBuilder()
                    .withType("Tipi")
                    .withFirstName("Jason")
                    .withLastName("Hitching")
                    .withAddress("76 Inway Drive KL25TP")
                    .withCost(233)
                    .withDate(LocalDate.of(2021, 6, 3))
                    .withNumDays(10)
                    .build();
            dao.insertBooking(tipiBooking2);

            Booking tipiBooking3 = new Booking.BookingBuilder()
                    .withType("Tipi")
                    .withFirstName("Jason")
                    .withLastName("Hitching")
                    .withAddress("76 Inway Drive KL25TP")
                    .withCost(233)
                    .withDate(LocalDate.of(2021, 6, 3))
                    .withNumDays(10)
                    .build();
            dao.insertBooking(tipiBooking3);

            Booking tipiBooking4 = new Booking.BookingBuilder()
                    .withType("Tipi")
                    .withFirstName("Jason")
                    .withLastName("Hitching")
                    .withAddress("76 Inway Drive KL25TP")
                    .withCost(233)
                    .withDate(LocalDate.of(2021, 6, 3))
                    .withNumDays(10)
                    .build();
            dao.insertBooking(tipiBooking4);

            Booking stretchBooking2 = new Booking.BookingBuilder()
                    .withType("Stretch Tent")
                    .withFirstName("Joe")
                    .withLastName("Copping")
                    .withAddress("1 Pleasant Drive GT53DP")
                    .withCost(800)
                    .withDate(LocalDate.of(2021, 8, 1))
                    .withNumDays(5)
                    .build();
            dao.insertBooking(stretchBooking2);

            Booking stretchBooking3 = new Booking.BookingBuilder()
                    .withType("Stretch Tent")
                    .withFirstName("Joe")
                    .withLastName("Copping")
                    .withAddress("1 Pleasant Drive GT53DP")
                    .withCost(800)
                    .withDate(LocalDate.of(2021, 8, 1))
                    .withNumDays(5)
                    .build();
            dao.insertBooking(stretchBooking3);

        });
    }
}
