# Changelog

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.21-1.4.3] - 2024-10-05
### Added
- Supplier Urn Upgrade. Loginar urns with this upgrade will replace consumed items (such as a tool or stack of blocks). For example, the urn could contain multiple stacks of planks, and it will replace each stack in your hand as you use them up.
- Advancements
### Fixed
- Keybindings for Backpack and Swapper urns overriding other screens

## [1.21-1.4.2] - 2024-09-15
## Added
- The potion pouch. It stores up to 9 potions and can be used to drink or throw them. It has a half-second cooldown after use to prevent accidental throws.
- A keybinding (default I) to open the first backpack urn in the player's inventory
### Changed
- Increased drop rate for fire pearls
### Fixed
- Loginar dungeons not generating
- Swapper urn screen not showing item tooltips

## [1.21-1.4.1] - 2024-08-04
- IMPORTANT: Please read note for previous version if updating from an earlier Minecraft version!
### Fixed
- Loginars not spawning naturally

## [1.21-1.4.0] - 2024-08-04
- IMPORTANT: **If trying to update a world from an older Minecraft versions, make a backup first!** Try placing any loginar urns on the ground before updating. The block should retain its data, but the item will not.
- Ported to Minecraft 1.21 (Neoforge)
### Added
- A tag for items that cannot be stored inside urns (empty by default)
- Fire Pearl, a rare drop from loginars
- Fire Flinger, an item that throws fireballs
### Changed
- Urns can no longer store shulker boxes and vice versa

## [1.20.4-1.3.0] - 2024-06-18
- Ported to Minecraft 1.20.4

## [1.20.1-1.2.1] - 2023-09-03
### Fixed
- Crash when crafting an urn with a mod gem [#5]

## [1.20.1-1.2.0] - 2023-06-16
- Ported to Minecraft 1.20.1

## [1.19.4-1.2.0] - 2023-05-20
- Ported to Minecraft 1.19.4
### Added
- Gem bag, flower basket, and ore crate. These store and collect specific types of items specified by tags (`loginar:gem_bag_can_store`, `loginar:flower_basket_can_store`, and `loginar:ore_crate_can_store`). If you are familiar with the gem bag and flower basket in Silent's Gems, these work the same way.

## [1.19.3-1.1.0] - 2023-03-26
- Ported to Minecraft 1.19.3

## [1.1.0] - 2023-02-25
### Added
- Lunch box. It stores 9 stacks of food. You can eat straight from it without removing the food first.

## [1.0.0] - 2023-01-29
### Added
- Animations to the loginar model
- Vacuum urn upgrade now works. It pulls nearby items towards a placed urn like a magnet, and stores anything that gets close.
- Italian translation (giok3r)
### Removed
- The unimplemented "hotbar swapper upgrade". It may or may not appear in the mod someday.
### Changed
- Increased loginar base health from 20 to 30

## [0.2.3] - 2022-12-28
### Added
- Loginar urns now display their upgrades in their tooltip
### Fixed
- Crash when pressing the "Swap Urn Items" key when not in a world (main menu, etc.) [#3]
- Applying a second upgrade to an urn with an upgrade breaking all the upgrades [#2]
- Not being able to cook loginar calamari in a furnace
- Keybinding category localization
- Urn menu screens now display the urns actual name in all cases (backpack and swapper menus included)

## [0.2.2] - 2022-12-24
### Changed
- Each loginar urn size now has a unique model. The large urn retains the original model. Collision updated to match new models.

## [0.2.1] - 2022-12-22
### Added
- Item swapper upgrade. By pressing a key (default X), opens a menu that allows the player to swap their held item with a selected item from within the urn. Does not work when holding an urn.

## [0.2.0] - 2022-12-17
### Added
- Urn Upgrades. Craft them with an urn to apply. Urns have limited upgrade slots based on their size.
- Backpack Urn Upgrade. Allows the urn to be opened and used without placing it, like how many backpack mods work.
### Changed
- Urn GUI texture (it's loginar-colored now!)
### Fixed
- Urn items stacking together

## [0.1.7] - 2022-12-10
### Added
- Recipes for large, huge, and super loginar urns
### Fixed
- Some urn recipes not working correctly (failing to upgrade, changing gem color unintentionally) [#1]

## [0.1.6] - 2022-12-03
### Added
- Sound effects for the loginar (idle, hurt, death, and attack) and urns (opening sound)

## [0.1.5] - 2022-11-04
### Fixed
- Being able to store urns inside urns

## [0.1.4] - 2022-10-28
### Added
- A "loginar dungeon" structure that spawns underground below Y 0. Can contain up to three urns with loot in them.

## [0.1.3] - 2022-10-26
### Fixed
- Unable to connect to servers

## [0.1.2] - 2022-10-26
### Changed
- Improved Loginar AI. It now runs from players and fights back occasionally.
- Loginar's antenna lamp now glows in the dark

## [0.1.1] - 2022-10-23
### Added
- Urns can now acquire gem colors from Silent Gear materials (only if Silent Gear is installed)
- Urns can now have their clay parts dyed any color by crafting them with dyes
### Fixed
- Urns not having the correct colors when placed as blocks

## [0.1.0] - 2022-10-21
- First alpha release!
- Loginar mob (missing some AI, but it spawns and drops items)
- Loginar urns (missing upgrades and block colors are incorrect, but they work properly otherwise)
