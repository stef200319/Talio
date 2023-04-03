/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private long columnId;
    private Integer position;

    @ManyToMany
    @JoinTable(
            name = "owned_tags_card",
            joinColumns = @JoinColumn(name = "card_id"),
            inverseJoinColumns = @JoinColumn(name = "cardtag_id")
    )
    private Set<CardTag> tags;

    private Card() {
        // for object mappers
    }

    /**
     * @param title of the Card
     * @param columnId the column on which the card is going to be present
     */
    public Card(String title, long columnId) {
        this.title = title;
        this.columnId = columnId;
        this.tags = new HashSet<>();
    }

    /**
     * @return the id of the card stored in the database
     */
    public long getId() {
        return id;
    }

    /**
     * @param id new id of the entry
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the title of the card
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title which should replace the card's current title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the id of the column the card belongs to (at the time)
     */
    public long getColumnId() {
        return columnId;
    }

    /**
     * @param columnId the id of the column that replaces the current column the card belongs to
     */
    public void setColumnId(long columnId) {
        this.columnId = columnId;
    }

    /**
     * @return the position of the card in the column it belongs to (at the time)
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * @param position the new position of the card in the column it belongs to (at the time),
     *                 which replaces the old position
     */
    public void setPosition(Integer position) {
        this.position = position;
    }

    /**
     * add cardTag to card
     * @param cardTag cardTag to add
     */
    public void addCardTag(CardTag cardTag) {
        if (tags.contains(cardTag) || cardTag == null) return;
        tags.add(cardTag);
    }

    /**
     * delete cardTag from card
     * @param cardTag cardTag to delete
     * @return the deleted cardTag
     */
    public CardTag deleteCardTag(CardTag cardTag) {
        if (!tags.contains(cardTag) || cardTag == null) return null;
        tags.remove(cardTag);
        return cardTag;
    }

    /**
     * getter for the cardTags
     * @return a list of the CardTags
     */
    public List<CardTag> getCardTags() {
        if (tags == null) return new ArrayList<>();
        return new ArrayList<>(tags);
    }

    /**
     * Settter for the card tags
     * @param cardTags
     */
    public void setCardTags(Set<CardTag> cardTags) {
        this.tags = cardTags;
    }

    /**
     * @param obj other object that you want to compare to this
     * @return whether the objects are the same
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     * @return a hashcode of the current object
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * @return a string of all the properties in the method
     */
    @Override
    public String toString() {
        return "The title of this Card is: " + getTitle() +
                ", and the ID of the Column this Card belongs to is: " + getColumnId();
    }

}
