package org.sebsy.grasps;

import org.junit.jupiter.api.Test;
import org.sebsy.grasps.beans.Reservation;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationControllerTest {

    @Test
    public void testCreerReservation_ClientNonPremium_Cinema() {
        Params params = new Params();
        params.setIdentifiantClient("3");
        params.setDateReservation("04/07/2025 20:00:00");
        params.setTypeReservation("CI");
        params.setNbPlaces(2);

        ReservationController controller = new ReservationController();
        Reservation reservation = controller.creerReservation(params);

        assertNotNull(reservation);
        assertFalse(reservation.getClient().isPremium());

        double expectedTotal = 2 * 10.9;
        assertEquals(expectedTotal, reservation.getTotal(), 0.001);
    }

    @Test
    public void testCreerReservation_ClientPremium_Theatre() {
        Params params = new Params();
        params.setIdentifiantClient("1");
        params.setDateReservation("04/07/2025 21:00:00");
        params.setTypeReservation("TH");
        params.setNbPlaces(3);

        ReservationController controller = new ReservationController();
        Reservation reservation = controller.creerReservation(params);

        assertNotNull(reservation);
        assertTrue(reservation.getClient().isPremium());

        double montant = 150.0;
        double reduction = 15.0;
        double expectedTotal = 3 * montant * (1 - reduction / 100.0);

        assertEquals(expectedTotal, reservation.getTotal(), 0.001);
    }

    @Test
    public void testCreerReservation_TypeReservationInexistant() {
        Params params = new Params();
        params.setIdentifiantClient("1");
        params.setDateReservation("04/07/2025 21:00:00");
        params.setTypeReservation("ZZ");
        params.setNbPlaces(1);

        ReservationController controller = new ReservationController();

        assertThrows(NullPointerException.class, () -> controller.creerReservation(params));
    }

    @Test
    public void testCreerReservation_ClientInexistant() {
        Params params = new Params();
        params.setIdentifiantClient("999");
        params.setDateReservation("04/07/2025 21:00:00");
        params.setTypeReservation("CI");
        params.setNbPlaces(1);

        ReservationController controller = new ReservationController();

        assertThrows(NullPointerException.class, () -> controller.creerReservation(params));
    }
}
