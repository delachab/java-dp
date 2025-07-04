package org.sebsy.grasps;

import org.sebsy.grasps.beans.Client;
import org.sebsy.grasps.beans.Reservation;
import org.sebsy.grasps.beans.TypeReservation;
import org.sebsy.grasps.daos.ClientDao;
import org.sebsy.grasps.daos.TypeReservationDao;
import org.sebsy.grasps.services.ReservationFactory;
import org.sebsy.grasps.services.ReservationPricingService;
import org.sebsy.grasps.utils.DateParser;

import java.time.LocalDateTime;

/**
 * Contrôleur qui prend en charge la gestion des réservations client
 */
public class ReservationController {

    private final ClientDao clientDao = new ClientDao();
    private final TypeReservationDao typeReservationDao = new TypeReservationDao();

    /**
     * Méthode qui créée une réservation pour un client à partir des informations transmises
     *
     * @param params contient toutes les infos permettant de créer une réservation
     * @return Reservation
     */
    public Reservation creerReservation(Params params) {

        // 1) Récupération des infos
        String identifiantClient = params.getIdentifiantClient();
        String dateReservationStr = params.getDateReservation();
        String typeReservation = params.getTypeReservation();
        int nbPlaces = params.getNbPlaces();

        // 2) Conversion de la date
        LocalDateTime dateReservation = DateParser.parse(dateReservationStr);

        // 3) Recherche client et type réservation
        Client client = clientDao.extraireClient(identifiantClient);
        TypeReservation type = typeReservationDao.extraireTypeReservation(typeReservation);

        // 4) Création de la réservation
        Reservation reservation = ReservationFactory.creerReservation(client, dateReservation, nbPlaces);

        // 5) Calcul du montant
        double total = ReservationPricingService.calculerTotal(client, type, nbPlaces);
        reservation.setTotal(total);

        return reservation;
    }
}
