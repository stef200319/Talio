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


import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;



@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private long columnId;
    private Integer position;

    private String description;


    @ManyToMany
    @JoinTable(
            name = "owned_tags_card",
            joinColumns = @JoinColumn(name = "card_id"),
            inverseJoinColumns = @JoinColumn(name = "cardtag_id")
    )
    private Set<CardTag> tags;
    private String bgColour;
    private String defaultBgColour = "#F2F3F4";

    private String borderColour;
    private String defaultBorderColour = "#000000";

    private String fontType;
    private String defaultFontType = "Segoe UI";
    private Boolean fontStyleBold;
    private Boolean fontStyleItalic;
    private String fontColour;
    private String defaultFontColour = "#000000";


    @OneToMany(cascade = CascadeType.ALL)
    private List<Subtask> subtasks;

    /**
     * Card constructor
     */

    public Card() {
        // for object mappers
    }


    /**
     * @param title of the Card
     * @param columnId the column on which the card is going to be present
     */
    public Card(String title, long columnId) {
        this.title = title;
        this.columnId = columnId;
        this.bgColour = defaultBgColour;
        this.borderColour = defaultBorderColour;
        this.fontType = defaultFontType;
        this.fontStyleBold=false;
        this.fontStyleItalic=false;
        this.fontColour = defaultFontColour;

        this.subtasks = new ArrayList<Subtask>();
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
     * @param cardTags set of card tags
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

        /**
     * Get the list of subtasks for the card
     * @return the list of subtasks for the card
     */
    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    /**
     * Set the list of subtasks for the card
     * @param subtasks the new list of subtasks for the card
     */
    public void setSubtasks(List<Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    /**
     * Get the description of the card
     * @return the description of the card
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the card
     * @param description the new description of the card
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @return the String of the Background Colour of a card
     */
    public String getBgColour() {
        return bgColour;
    }
    /**
     * Set the background colour of a Card
     * @param colour new background colour to replace the card's old one
     */
    public void setBgColour(String colour) {
        this.bgColour = colour;
    }
    /**
     * @return the String of the Border Colour of a card
     */
    public String getBorderColour() {
        return borderColour;
    }
    /**
     * Set the borderColour of a Card
     * @param borderColour new borderColour to replace the card's old one
     */
    public void setBorderColour(String borderColour) {
        this.borderColour = borderColour;
    }
    /**
     * @return the String of the Font-type of a card
     */
    public String getFontType() {
        return fontType;
    }
    /**
     * Set the font-type of a Card
     * @param fontType new font type to replace the card's old one
     */
    public void setFontType(String fontType) {
        this.fontType = fontType;
    }
    /**
     * @return whether the Card is Bold
     */
    public boolean isFontStyleBold() {
        return fontStyleBold;
    }

    /**
     * Set the card to be Bold or not
     * @param fontStyleBold value of the Boldness of the card
     */
    public void setFontStyleBold(boolean fontStyleBold) {
        this.fontStyleBold = fontStyleBold;
    }
    /**
     * @return whether the Card is Italic
     */
    public boolean isFontStyleItalic() {
        return fontStyleItalic;
    }


    /**
     * Set the card to be Italic or not
     * @param fontStyleItalic value of the Italicness of the card
     */
    public void setFontStyleItalic(boolean fontStyleItalic) {
        this.fontStyleItalic = fontStyleItalic;
    }
    /**
     * @return the String of the Font Colour of a card
     */
    public String getFontColour() {
        return fontColour;
    }

    /**
     * Set the font colour of a Card
     * @param fontColour new font colour to replace the card's old one
     */
    public void setFontColour(String fontColour) {
        this.fontColour = fontColour;
    }

    /**
     * @return The default Background Colour of a Card
     */
    public String getDefaultBgColour() {
        return defaultBgColour;
    }

    /**
     * @return The default Border Colour of a Card
     */
    public String getDefaultBorderColour() {
        return defaultBorderColour;
    }

    /**
     * @return The default Font-Type of a Card
     */
    public String getDefaultFontType() {
        return defaultFontType;
    }

    /**
     * @return The default Font Colour of a Card
     */
    public String getDefaultFontColour() {
        return defaultFontColour;
    }

    /**
     * Retrieves the value of the "fontStyleBold" property, which indicates whether the font style is set to bold.
     *
     * @return {@code true} if the font style is set to bold, {@code false} otherwise.
     */
    public Boolean getFontStyleBold() {
        return fontStyleBold;
    }

    /**
     * Retrieves the value of the "fontStyleItalic" property, which indicates whether the font style is set to italic.
     *
     * @return {@code true} if the font style is set to italic, {@code false} otherwise.
     */
    public Boolean getFontStyleItalic() {
        return fontStyleItalic;
    }

}
