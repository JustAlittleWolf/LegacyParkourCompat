# Frozen-roster dependency addendum

After freezing `roster.md`, source review of `VineBlock.canBeReplaced` identified two direct dependencies needed to close its replaceability result: `Blocks.java` supplies Vine's `Material.REPLACEABLE_PLANT` property, and `Material.java` defines that property's `isReplaceable` flag. `Blocks.java` is already in the frozen roster. `Material.java` was added to `source-hashes.tsv` for both versions. No proposing-worker report had been opened when these dependency paths were added.
