# No Drop Plugin
## Overview
This is a Spigot 1.14.4 Plugin made for [FruitServers](https://www.fruitservers.net/) to prevent accidental item drops by players.

## Dependencies
* Spigot (tested with v1.14.4)

## Debugging
This program has been tested on Spigot 1.14.4, without any issues. If you run into any errors please make sure you are on this version because it is known to work. If you still are having issues on the aforementioned version, just send me a message on my [GitHub](https://github.com/mattdocherty314)

## Program Use
Load the plugin by placing it inside the plugin folder.
### Commands
`/nd` - Adds the held item to the no drop list.
`/nd remove` - Removes the item from the no drop list.
`/nd check` - Checks if the held item is in the no drop list.
### Permissions
`nodrop.drop` - Enables `/nd` command.
`nodrop.remove` - Enables `/nd remove` command.
`nodrop.check` - Enables `/nd check` command.
`nodrop.limit.*` - Limits the player to * items in the no drop list. 

## Version History
### v1.0.0 (not released)
* Finished base version

### v1.0.1
* Fixed commands creating a partial config

## TODO
* Efficient use of configuration space