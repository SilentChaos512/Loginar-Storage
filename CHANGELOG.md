# Changelog

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]
### Added
- Italian translation (giok3r)

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
