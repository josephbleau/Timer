# Timer (A Bukkit Plugin)
## Description
Timer is a simple countdown timing plugin for the CraftBukkit server that allows a user to specify timers of varying lengths and view their statuses.
## Commands
* **/timer add [name] [time string]** - Add and start a new timer.
* **/timer stop [name]** - Stop a timer.
* **/timer clear [name]** - Remove a timer.
* **/timer list** - List all existing timers.
* **/timer show [player name] [timer name]** - Alert a player to the remaining time of a timer.
## Time string format.
The most simple accepted time string is a plain number. This will be interpreted as time in seconds. In addition to this format you may specify a time with time symbols. The time symbols are:
* d - Days
* h - Hours
* m - Minutes
* s - Seconds

An example time string with time symbols: 1d20m.

Your time string may include any or none of the time symbols, but they must appear in the order they are listed above.
