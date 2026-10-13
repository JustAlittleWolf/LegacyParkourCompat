# Landing callbacks reuse a stale turtle egg state

- Older version A: `1.16.5`
- Newer version B: `1.17.1`
- Mechanic / coverage slice IDs: `S5-03-CONTACT-CALLBACK-INVENTORY`, `S4-01-TURTLE-EGG-POST-LANDING-SHAPE`
- Classification: callback state-snapshot difference with downstream collision-shape consequence
- Confidence: source-confirmed
- Scope disposition: the block-state lifecycle change is not itself an emulatable movement mechanic; it is recorded because the resulting state selects the player's later collision shape
- First changed release: unknown within (1.16.5, 1.17.1]
- Runtime validation: not performed

## Paired evidence

- A `world/entity/Entity.java`, `move(...)`, lines 517–534, gets the supporting `BlockState`, calls `checkFallDamage(...)`, then calls `block.stepOn(level, pos, entity)` using no saved state argument. A's `checkFallDamage(...)`, lines 894–905, calls the supporting block's `fallOn(...)` on a landing with positive `fallDistance`.
- B `world/entity/Entity.java`, `move(...)`, lines 565–585, gets one `BlockState`, passes it to `checkFallDamage(...)`, then also passes that same object to `block.stepOn(level, pos, blockState, entity)`. B's `checkFallDamage(...)`, lines 998–1009, passes the same state to `fallOn(...)` on a landing with positive `fallDistance`. Both `Entity.java` hashes are recorded in the run manifest.
- A `world/level/block/TurtleEggBlock.java`, `fallOn(...)` lines 49–54 and `stepOn(...)` lines 43–46, call `destroyEgg(...)`. Its helper, lines 57–65, makes the server-side random check and then reads the current state from `level.getBlockState(blockPos)` before decrementing eggs. Source SHA-256 `9345C6162F4DCF306E84B91EA5AB54FF18076006422285BE258FD88B18D1F901`.
- B `TurtleEggBlock.fallOn(...)` lines 52–57 and `stepOn(...)` lines 46–50 pass the `BlockState` supplied by `Entity.move` into `destroyEgg(...)`. That helper, lines 60–66, checks and decrements that supplied snapshot without rereading the level. Source SHA-256 `B0677B17479CFE6FEF4E8440F0270AD6A7A14AEF468AA5DFC7708DAB819A0340`.
- In both versions, `canDestroyEgg(...)` returns true for a `Player`; on the server, the fall and step callbacks use separate random gates (`nextInt(3) == 0` and `nextInt(100) == 0`). `decreaseEggs(...)` destroys a one-egg state and decrements a state with multiple eggs. `getShape(...)` returns the smaller one-egg shape when `EGGS == 1` and the larger multiple-egg shape otherwise; the rule is identical in A and B.

## Source-level difference

For a player landing on a two-egg state, if both callback random gates pass, A's `fallOn` decrements two eggs to one and `stepOn` rereads that one-egg state and destroys it. B's `fallOn` also decrements the saved two-egg state to one, but `stepOn` reuses the original two-egg snapshot and writes one egg again. The resulting block differs: A has air; B retains one egg. Both versions' `getShape` rules then give the surviving B block a nonempty one-egg collision shape.

## Reachability and dependencies

The player movement path is `Entity.move` → `checkFallDamage` / `fallOn` → `stepOn`. A player is explicitly eligible in `canDestroyEgg`; `stepOn` is skipped when the player is stepping carefully. The source condition is a server-side landing with positive accumulated fall distance, a two-or-more-egg state, both random checks passing, and the player not stepping carefully. The shape provider itself is unchanged; the difference is the state snapshot supplied by the B callback path.

## Consequence and uncertainty

The source establishes a reachable block-state lifecycle difference and a resulting state-dependent collision-shape difference. It does not establish an observed trajectory. Under the project's one-way scope, do not treat the changed egg-count lifecycle as a block-state emulation task; retain the downstream collision dependency for the source audit.

## Handoff

Keep this separate from the generic shape-consumer correspondence. `S5-03` remains in progress until the other changed shared callbacks and B-only owners are dispositioned.
