# Word-game-cheats
Java application to find words in Game Pigeon's Word Hunt boards.

In I-Phone game pigeon, there are word games. In order to beat my friends, I used DFS to track combinations between letters to see if they matched up to a real word. However, checking every combination in a 4x4 grid would take too long, so I used trie nodes to optimize this. By connecting words that extend off each other in the dictionary, I could easily make another DFS to check if I should keep pursuing a path. For example, the word fda is not a real word, so i should stop trying other words that start with fda, like fdas or fdad.

For sorting, I implemented quick sorts, which is one of the fastest sorts because of how it partitions arrays to sort.
