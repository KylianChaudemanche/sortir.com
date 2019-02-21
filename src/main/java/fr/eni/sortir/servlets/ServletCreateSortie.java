package fr.eni.sortir.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.sortir.dao.DaoFactory;
import fr.eni.sortir.entities.Lieu;
import fr.eni.sortir.entities.Participant;
import fr.eni.sortir.entities.Sortie;
import fr.eni.sortir.entities.Ville;
import fr.eni.sortir.utils.State;

/**
 * Servlet implementation class ServletCreateSortie
 */
@WebServlet(name = "ServletCreateSortie", urlPatterns = { "/createSortie" })
public class ServletCreateSortie extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -6076039526658978701L;

    private final String CITIES = "cities";
    private final String PLACES = "places";
    private final String ACTION = "action";
    private final String SAVE = "save";
    private final String PUBLISH = "publish";
    private final String JSP_CREATE_SORTIE = "/WEB-INF/createSortie.jsp";
    private final String JSP_LOGIN = "/WEB-INF/login.jsp";
    private final String SORTIE_CITY = "sortieCity";
    private final String SORTIE_PLACE = "sortiePlace";
    private final String SORTIE_NAME = "sortieName";
    private final String SORTIE_BEGIN_DATE = "sortieBeginDate";
    private final String SORTIE_CLOSE_INSCRIPTION_DATE = "sortieCloseInscriptionDate";
    private final String SORTIE_MAX_PLACE = "sortieNbMaxPlace";
    private final String SORTIE_DURATION = "sortieDuration";
    private final String SORTIE_DESCRIPTION = "sortieDesc";
    private final String FORMAT_DATE = "yyyy-MM-dd";
    private final String FORMAT_DATETIME = "yyyy-MM-dd'T'hh:mm";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCreateSortie() {
	super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	request.setAttribute(CITIES, DaoFactory.getVilleDao().getAllVille());
	request.setAttribute(PLACES, DaoFactory.getLieuDao().getAllLieu());
	request.getRequestDispatcher(JSP_CREATE_SORTIE).forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	for (String name : request.getParameterMap().keySet()) {
	    if ("".equals(request.getParameter(name))) {
		response.sendError(HttpServletResponse.SC_FORBIDDEN);
		return;
	    }
	}

	int noCity = -1, noPlace = -1, maxPlace = -1, duration = -1;

	try {
	    noCity = Integer.parseInt(request.getParameter(SORTIE_CITY));
	    noPlace = Integer.parseInt(request.getParameter(SORTIE_PLACE));
	    maxPlace = Integer.parseInt(request.getParameter(SORTIE_MAX_PLACE));
	    duration = Integer.parseInt(request.getParameter(SORTIE_DURATION));
	} catch (NumberFormatException nfe) {
	    nfe.printStackTrace();
	    response.sendError(HttpServletResponse.SC_FORBIDDEN);
	    return;
	}

	Ville ville = DaoFactory.getVilleDao().findVille(noCity);
	Lieu lieu = DaoFactory.getLieuDao().findLieu(noPlace);
	if (ville == null || lieu == null) {
	    response.sendError(HttpServletResponse.SC_FORBIDDEN);
	    return;
	}

	SimpleDateFormat sdfDateTime = new SimpleDateFormat(FORMAT_DATETIME);
	SimpleDateFormat sdfDate = new SimpleDateFormat(FORMAT_DATE);
	Date dateBegin = null;
	Date dateCloseInscription = null;
	try {
	    dateBegin = sdfDateTime.parse(request.getParameter(SORTIE_BEGIN_DATE));
	    dateCloseInscription = sdfDate.parse(request.getParameter(SORTIE_CLOSE_INSCRIPTION_DATE));
	} catch (ParseException e) {
	    e.printStackTrace();
	    response.sendError(HttpServletResponse.SC_FORBIDDEN);
	    return;
	}

	if (dateBegin.compareTo(dateCloseInscription) < 0) {
	    response.sendError(HttpServletResponse.SC_FORBIDDEN);
	    return;
	}

	// TODO replace with the value of user connected
	Participant organisateur = DaoFactory.getParticipantDao().findParticipant(2);

	Sortie sortie = new Sortie(request.getParameter(SORTIE_NAME), dateBegin, duration, dateCloseInscription,
		maxPlace, request.getParameter(SORTIE_DESCRIPTION), null, null, lieu, new ArrayList<>(), organisateur);

	switch (request.getParameter(ACTION)) {
	case SAVE:
	    sortie = saveSortie(sortie, State.CREATED);
	    break;
	case PUBLISH:
	    sortie = saveSortie(sortie, State.OPENED);
	    break;
	default:
	    response.sendError(HttpServletResponse.SC_FORBIDDEN);
	    return;
	}

	// TODO replace with correct page redirection
	if (sortie != null) {
	    request.getRequestDispatcher(JSP_LOGIN).forward(request, response);
	} else {
	    response.sendError(HttpServletResponse.SC_FORBIDDEN);
	}
    }

    private Sortie saveSortie(Sortie sortie, State state) {
	sortie.setEtat(DaoFactory.getEtatDao().findEtatByName(state.toString()));
	return DaoFactory.getSortieDao().addSortie(sortie);
    }
}