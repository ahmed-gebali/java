package de.benjaminborbe.poker.table.client.model;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable, IsSerializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String username;

	private int credits;

	private ArrayList<Card> cards = new ArrayList<Card>();

	public Player() {
	}

	public boolean isActivePlayer() {
		return activePlayer;
	}

	public void setActivePlayer(final boolean activePlayer) {
		this.activePlayer = activePlayer;
	}

	private boolean activePlayer = false;

	public ArrayList<Card> getCards() {
		return cards;
	}

	public void setCards(final ArrayList<Card> cards) {
		this.cards = cards;
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(final int credits) {
		this.credits = credits;
	}

	public void addCard(final Card Card) {
		cards.add(Card);
	}

} 
	

