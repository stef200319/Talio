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


@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private long columnId;
    private Integer position;

//    private CardDetails cardDetails;



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
//        this.cardDetails =
                new CardDetails(this.id, this.title);
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

}
