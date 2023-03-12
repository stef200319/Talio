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
    private long listId;
    private Integer position;



    private Card() {
        // for object mappers
    }

    /**
     * @param title of the Card
     * @param listId the list on which the card is going to be present
     */
    public Card(String title, long listId) {
        this.title = title;
        this.listId = listId;
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
     * @return the id of the list the card belongs to (at the time)
     */
    public long getListId() {
        return listId;
    }

    /**
     * @param listId the id of the list that replaces the current list the card belongs to
     */
    public void setListId(long listId) {
        this.listId = listId;
    }

    /**
     * @return the position of the card in the list it belongs to (at the time)
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * @param position the new position of the card in the list it belongs to (at the time), which replaces the old position
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
                ", and the ID of the List this Card belongs to is: " + getListId();
    }

}