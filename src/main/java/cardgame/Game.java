package cardgame;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import database.factories.DAOFactory;

@Entity
@Table(name = "game")
public class Game {

    @Id
    @Column(name = "id")
    private Integer id = 1;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Score> scores = new ArrayList<>();

    @Transient
    private Card currentCard;
    @Transient
    private Card nextCard;
    @Transient
    private Deck deck = new Deck();
    @Transient
    private String playerName;

    public enum HigherOrLower { HIGHER, LOWER }

    public boolean gameTurn(HigherOrLower higherOrLower) {
        if (!deck.areCardsLeft()) return false;

        if ((higherOrLower == HigherOrLower.HIGHER && nextCard.isHigherOrEqual(currentCard)) || 
            (higherOrLower == HigherOrLower.LOWER && !nextCard.isHigherOrEqual(currentCard))) {
            currentCard = deck.getNextCard();
            nextCard = deck.getNextCard();
            int newScore = scores.isEmpty() ? 1 : scores.get(scores.size() - 1).getScore() + 1;
            scores.add(new Score(this, playerName, newScore));
            return true;
        }

        return false;
    }

    public void setup(String playerName) {
        this.playerName = playerName;
        currentCard = deck.getNextCard();
        nextCard = deck.getNextCard();
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public Card getNextCard() {
        return nextCard;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "" + (scores.isEmpty() ? 0 : scores.get(scores.size() - 1));
    }

    public int getLastDigitOfScore() {
        return scores.isEmpty() ? 0 : scores.get(scores.size() - 1).getScore();
    }

    public void save() {
        System.out.println("Saving game with scores: " + scores);
        DAOFactory.getTheFactory().getGameDAO().saveOrUpdate(this);
    }

    public void saveFinalScore() {
        if (!scores.isEmpty()) {
            Score highestScore = scores.get(scores.size() - 1);
            highestScore.setPlayerName(this.playerName);
            System.out.println("Saving final high score: " + highestScore.getScore() + ", player name: " + highestScore.getPlayerName());
            DAOFactory.getTheFactory().getScoreDAO().saveOrUpdate(highestScore);
        }
    }

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
}
