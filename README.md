# voxspell
#### A spelling application targeted at native English learners aged 7-10
developed for Java 8 with <3 by Harry (and formerly Nathan)
this version is for evaluation purposes and has a Evaluation-only unlock all level mode (see usage guide)

##quick start
Download/clone as .zip, extract and run the run.sh script
(requires Oracle Java 1.8 and festival text-to-speech)

###wordlists
There will be a default wordlist implemented for children to test. In order to choose between different
wordlists the user must save a ".txt" file inside the "lib" folder of the VOXSPELL manually. This should be 
alongside the default "NZCER-spelling-lists.txt" list. The voxpsell assumes that the text file is in the 
same format as given in the default spelling list, that is, that each new level starts with a %Level and
that there are no more than 11 levels. There should also be at least 10 words in each level. If no words are
given for the particular level, the button has no action.

###levels
Only level 1 is unlocked on first launch. The next level is automatically and permanently unlocked when
at least 9 out of 10 words are spelt correctly on the previous level. You can pick any unlocked level
to start the quiz on. The developer only button temporarily enables all levels. You can reset all
of your unlocked levels with the reset button.

###spelling
You can listen to the word again without penalty by clicking the "Hear again" button. Entering invalid
characters will not count as a wrong spelling and you will be allowed to try again without penalty.
Some words contain apostrophes - if your spelling does not contain it while the word does, you will
be told and asked to retry without penalty (and vice versa).

Scoring 9 or more on a level enables you to watch a video reward.

###reviewing
You can review all the words you got wrong at the end of each level you have completed. You can also review
the words you got wrong in the previous review quiz. Unless you were reviewing after scoring 9/10, you cannot
access the next level by simply reviewing.
If you qualify for the next level (getting 9 out of 10 on the quiz) and then review the one word you got wrong,
you still have access to the video reward and next level.

###statistics
The stats are intended for parents/teachers to review the words that the user has gotten wrong. A brief
display of the words spelt during the current level will appear at the end. 

Full statistics of all
sessions are available. These are resettable.

##project structure

```
src
    ├── tests ** test suite for selected classes **
    └── voxspell
        ├── Voxspell.java                           // entry point for application
        ├── engine ** package for back-end / functionality **
        │   ├── DataIO.java                         // deals with file IO and saving
        │   ├── Festival.java                       // deals with festival tts and voice changing
        │   ├── LevelData.java                      // static link class to store global application state (levels, etc.)
        │   ├── SceneManager.java                   // class that handles scene transfers
        │   ├── Word.java                           // class to represent a word
        │   └── WordList.java                       // class to parse file and get lists of words
        └── scenes ** package for front-end and controllers **
            ├── assets ** folder for images **
            ├── MainController.java                 // controller for main/welcome scene
            ├── EndSessionController.java           // end of level controller
            ├── SpellingController.java             // controller for spelling scene
            ├── StatsController.java                // controller for statistic scene
            ├── VideoController.java                // controller for video reward scene
            ├── endSession.fxml                     // scene on reaching the end of a level
            ├── main.fxml                           // main/welcome scene for level selection
            ├── spelling.fxml                       // spelling scene for quizzes
            ├── stats.fxml                          // statistics scene
            └── video.fxml                          // video reward scene
```

## assets
All assets are from royalty-free sources
Loading gif: ajaxload.info
Background: openclipart.org
