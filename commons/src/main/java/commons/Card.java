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

import javax.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private long columnId;
    private Integer position;

    private String description;


    private String bgColour;

    private String borderColour;

    private String fontType;
    private Boolean fontStyleBold;
    private Boolean fontStyleItalic;
    private String fontColour;


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
        this.bgColour = "#F2F3F4";
        this.borderColour = "#000000";
        this.fontType = "Segoe UI";
        this.fontStyleBold=false;
        this.fontStyleItalic=false;
        this.fontColour = "#000000";

        this.subtasks = new ArrayList<Subtask>();
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
}
