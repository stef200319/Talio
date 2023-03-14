## Feedback on Backlog Draft for Group 11

### Submission

*Mark: Pass*
The submission consists of a single PDF file, uploaded in the correct directory and with the correct name.

### Backlog Structure

*Feedback: Pass.*
The document has the correct structure (list of stakeholders, terminology, list of user stories, mockups). All of these are covered with a reasonable amount of details.


### Epics

*Feedback: Insufficient.*
While user stories are sorted by priority using the MoSCoW method (following the format from last year's backlog), this assignment required the use of epics, as described in the Requirements Engineering lecture and the [assignment description](https://se.ewi.tudelft.nl/oopp/assignments/backlog/#epics).
For the final version of the Backlog, please make sure that the following points are met:
- The first epic is the Minimal App (minimal viable product);
- The subsequent epics correspond to features;
- Every epic has a clarifying description;
- The epics form a complete and prioritised representation of all of the features for this project.

Additionally (and optionally), consider using mock-ups for epics. These will aid both the reader of the backlog in better understanding the descriptions of the epics, and you in ensuring that there are no overlaps or gaps.

### User Stories

*Mark: Good*
All of the user stories follow the correct **format** (As a ... I want to ... so I can ...). Additionally, they are **focused on the user perspective**. Great job on perfectly meeting these criteria!

Regarding the **scope** of the user stories, they generally correspond to individual interactions, but they present slight overlap: "*to be able to access another user's board, so that I can work together with other users in organising my/their/our tasks, on boards I didn’t create.*", "*to allow other users to be able to access my board, so that I can work together with other users in organising my/their/our tasks, on the board I created.*", and "*my board to be able to be accessed and modified by multiple users, so that I can collaborate with other users in organising my/their/our tasks.*" refer to the same functionality. 
*Note*: The first two user stories are placed in a different priority group from the third one. For the final version, please make sure that user stories related to the same feature are part of the same epic!
Similarly: "*that the board I created has a unique key, so that other users can use the key of the board to access it.*" and "*that the boards other users have created have unique keys, so that I can use the keys of those boards to access them.*" refer to the same functionality.
While I understand that your approach was to separate the perspective of one user from the rest, keep in mind that when it comes to implementing the user stories, there will not be differences between what user A can do and what all the others can. 

There are no large gaps (areas where it is unsure what the application should do). However, there are a few ambiguities which can be improved:
- "*so I can sort my tasks by status.*": maybe you meant display, or are you going to implement some kind of priority for each status by which all columns will be authomatically sorted?
- "*to complete the task at hand as a whole*": the completeness of a task is only introduced in the Advanced Features section, when describing nested task columns (a different feature of the project). Can all tasks be marked as complete, or does that only apply to nested tasks? What happens to a completed task - is it deleted or is it just displayed differently, or in another section
- "*to give a list a name, so that I know what status the list represents, of the cards it contains.*" - the structure of this sentence is unclear. Please try to rephrase it so that it's easier to understand.
- "*to remove a list from the board, so I can clear up the board from columns that don’t represent valid status’ of any of the cards on the board anymore.*" - what happens when a list is deleted? Do all of its tasks disappear as well, or are they moved to another list?
- "*to set tag(s) to any task, so I have a way to group my tasks based on the characteristics of the tasks themselves.*" - what does "group my tasks" mean here? A separate list for every tag?
- "*to use keyboard shortcuts within the application, so I can work with the application more efficiently.*" - what keyboard shortcuts can be used? What are their effects? 

Moreover, in order to ensure the **completeness** of the user stories, make sure that they fully cover the epics, their descriptions and, if included, the mockups.

Beside the pointed out overlaps, the user stories are **coherent** - there are no contradicting statements. However, make sure that if description of user story B relies on a property of the project that is introduced in user story A, A comes before B in the backlog. Also, similar user stories should have similar priorities (in your backlog, editing a list is a MUST, while changing the name of a board is a COULD).


### Acceptance Criteria
*Mark: Very Good*

- *Conciseness:* The size of most user stories is small and their effect is clear, so their acceptance criteria are implicit. 
- *Clarity:* User stories/acceptance criteria are formulated in a measurable/observable way that allows to decide when a story is done/finished.

In general, it is easy to understand when a user story is finished. However, I didn't mark this criteria as excellent because of the above-mentioned ambiguities.