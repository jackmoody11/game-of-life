# Conway's Game of Life
A full explanation of Conway's Game of Life can be found [here](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life).

## Features Included
- Ability to change the size of the field from 10x10 up to 500x500.
- Ability to manually set / clear any cell in order to set up patterns
- Ability to clear the entire field.
- Ability to fill the field randomly.

![Randomly Fill](/images/Randomly_Fill.gif)
- Ability to advance the game by pressing a button.
- Written with a Model View Controller architecture
- Ability to set the "survive" and "birth" thresholds to custom values. 
The default threshold values in the classic game brings a dead cell to life if 
the number of neighboring live cells is greater than to 2 (low birth threshold) 
and less than 4 (high birth threshold) and otherwise stays dead. A living cell 
survives if the number of neighboring live cells is greater than 1 
(low survive threshold) and less than or equal to 3 (high survive threshold) and 
otherwise will die.
- Ability to toggle "torus" mode on or off. In torus mode, the field is treated 
as if it wraps around the edges back to the other edge.
- A start/stop button that advances the game automatically using a separate 
thread with a delay between updates settable between 10 milliseconds and 1 second (1000 milliseconds).

![Simulation](/images/Simulation.gif)