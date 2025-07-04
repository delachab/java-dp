package org.sebsy.grasps.services;

import org.sebsy.grasps.beans.Client;
import org.sebsy.grasps.beans.Reservation;

import java.time.LocalDateTime;

public class ReservationFactory {

    public static Reservation creerReservation(Client client, LocalDateTime date, int nbPlaces) {
        Reservation reservation = new Reservation(date);
        reservation.setClient(client);
        reservation.setNbPlaces(nbPlaces);
        client.getReservations().add(reservation);
        return reservation;
    }
}
