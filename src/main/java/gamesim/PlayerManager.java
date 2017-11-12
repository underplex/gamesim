package gamesim;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Manages a circle of players, each represented by {@code T}.
 * <p>
 * This is meant for a specific purpose, that is, to manage players with circular turn order in a game.
 * <p>
 * Note that players are expected to be distinct based on their respective {@code equals} methods. Attempting to {@code add(T player)} with the same instance of {@code T} will
 * fail to change the state of this object.
 * <p>
 * @author Brandon Irvine
 *
 * @param <T> class or interface representing players to be managed
 */
public class PlayerManager<T> {

	protected List<T> roster;
	
	public PlayerManager(){
		this.roster = new ArrayList<T>();
	}
	
	public PlayerManager(Collection<? extends T> players){
		this();
		this.addAll(players);
	}
	
	/**
	 * Returns the player in the player circle after {@code prevPlayer}.
	 * <p>
	 * This method doesn't change the state of this manager.
	 * <p>
	 * For a circle with only one player, that player himself will be returned.
	 * <p>
	 * If {@code null} is passed, {@code null} will be returned, since this is meaningless.
	 * @param prevPlayer T the previous player
	 * @return
	 */
	public T next(T prevPlayer){
		T rT = null;
		if (this.roster.size() == 0){
			rT = null;
		} else if (!roster.contains(prevPlayer)){
			rT = null;
		} else if (this.roster.size() == 1){
			rT = roster.get(0);
		} else {
			int i = roster.indexOf(prevPlayer);
			if (i == roster.size() - 1){
				rT = roster.get(0);
			} else {
				rT = roster.get(i + 1);
			}
		}
		return rT;
	}
	
	/**
	 * Returns the first player or {@code null} if no players are in the circle.
	 * <p>
	 * This might be changed if the first player has been removed, making the second player the first player, etc.
	 */
	public T first(){
		if (roster.isEmpty()){
			return null;
		}
		return roster.get(0);
	}
	
	/**
	 * Makes the given player the first player and returns true, or returns false if the player isn't already part of the circle.
	 * <p>
	 * Sometimes in a game, the player in the role of "first" changes, but all the relative positions stay the same. This method changes the first player designation, but will fail if
	 * the argument passed is not already a player in the circle, since this method isn't intended to change the players in the circle, only their order.
	 * <p>
	 * If {@code null} is passed or the player isn't part of the circle, {@code false} will be returned. If the player passed is already first, this method will return {@code true} but nothing about the 
	 * circle will change.
	 */
	public boolean changeFirst(T player){
		boolean rBool = false;
		if (player == null){
			rBool = false;
		} else if (!this.roster.contains(player)){
			rBool = false;
		} else {
			if (!this.first().equals(player)){
				int p = roster.indexOf(player);
				List<T> changed = new ArrayList<T>();
				for (int i = p; i < roster.size(); i++){
					changed.add(roster.get(i));
				}
				for (int i = 0; i < p; i++){
					changed.add(roster.get(i));
				}
			}	
			rBool = true;
		}
		return rBool;
	}
	
	/**
	 * Inserts a player into the circle if possible and returns true, or returns false if impossible.
	 * @param player T to be inserted
	 * @return true iff the player was inserted into circle
	 */
	public boolean add(T player){
		boolean rBool = false;
		if (player == null){
			rBool = false;
		}
		if (this.roster.contains(player)){
			rBool = false;
		} else {
			roster.add(player);
			rBool = true;
		}
		return rBool;
	}

	/**
	 * Attempts to insert all players into the circle.
	 * <p>
	 * Returns true if none were duplicates and this was possible, or returns false if impossible or some were duplicates.
	 * @param player T to be inserted
	 * @return true iff the players were added to the circle without incident
	 */
	public boolean addAll(Collection<? extends T> players){
		boolean rBool = true;
		for (T player : players){
			boolean flag = this.add(player);
			if (!flag)  rBool = false;
		}
		return rBool;
	}
	
	/**
	 * Removes a player into the circle and returns true, or returns false if the player isn't there already.
	 * @param player T to be removed
	 * @return true iff the player was found and removed from circle
	 */
	public boolean remove(T player){
		boolean rBool = false;
		if (player == null){
			rBool = false;
		}
		if (!this.roster.contains(player)){
			rBool = false;
		} else {
			roster.remove(player);
			rBool = true;
		}
		return rBool;
	}

	
	/**
	 * Returns number of players in circle, or 0 if for whatever reason the circle is empty or doesn't exist.
	 * @return
	 */
	public int size(){
		return roster != null ? this.roster.size() : 0;
	}
	
	/**
	 * Drops all players from the circle and returns true iff any were dropped, or false if none were.
	 * @return true iff any were dropped this way
	 */
	public boolean clear(){
		boolean rBool = false;
		if (roster == null){
			rBool = false;
		} else if (roster.size() == 0){
			rBool = false;
		} else {
			roster = new ArrayList<T>();
			rBool = true;
		}
		return rBool;
	}
	
	/**
	 * Returns players in player order as a List, with the first player at 0.
	 * <p>
	 * The returned list is a defensive copy.
	 * @return List of players in turn order.
	 */
	public List<T> toList(){
		return new ArrayList<T>(this.roster);
	}
}
