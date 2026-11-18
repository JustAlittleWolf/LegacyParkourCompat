# Final exact claim review

Reviewed only:

- Author comparison: `late/source/comparison.md`, raw SHA-256 `3affe7a67d8e98065135a68dfe5b3eb33e75cc1f2296ef56735329bbec2956f1`.
- Blind freeze: `late/blind-audit/frozen.md`, raw SHA-256 `2eeaa398bfd19354f5fbd2bb0ad1c4e571f2ed2038f36b06ebb42cf3155a4822`.

## Verdicts

1. **Caller timing and client/server gate — accepted for the assigned claim.** The author and frozen audit agree that the frost helpers execute after `travel`, in remove-then-add order on both sides through 1.21.4, and are inside a `ServerLevel` gate in 1.21.5. This is one caller-level operation change. The caller line ranges differ in all five version references: author versus frozen is 1.20.5 `2687-2700` / `2683-2698`, 1.21 `2682-2695` / `2678-2693`, 1.21.2 `2780-2796` / `2771-2791`, 1.21.4 `2791-2804` / `2782-2802`, and 1.21.5 `2779-2791` / `2766-2785`. The behavior claim agrees, but this document-only review cannot reconcile the exact anchors.

2. **Modifier ID, operation, and gates — accepted.** The UUID-to-`minecraft:powder_snow` representation change by 1.21, `ADD_VALUE`, the transient modifier, float amount, and non-air / positive-frozen-ticks / non-null-attribute gates match the frozen claim. The ID representation change does not establish a separate movement finding.

3. **Speed read and synchronization — partial; exact cause unresolved.** The frozen audit establishes the synchronized `DATA_TICKS_FROZEN` integer and explicitly does not establish synchronization of the transient speed modifier. The author's `Attributes` / `ServerEntity` / packet / client-listener path is beyond the frozen evidence and cannot be accepted or rejected here. The author's claim that `Player.getSpeed()` directly reads `MOVEMENT_SPEED` also is not established by the frozen audit: it records `Player.aiStep` refreshing a speed cache after `super.aiStep()` and the inherited `LivingEntity.getSpeed()` body, but does not trace virtual dispatch at the local player's movement read. Therefore the frozen sentence that the modifier is “available to the next travel read” is not independently established for the concrete `Player` dispatch by this freeze. Keep the synchronized-integer boundary; leave the attribute packet timing and concrete next-player-read cause unresolved under this review's input restriction.

## Scope

No nested finding is added: the identifier representation change is not independently source-evidenced as a movement delta, and the sole accepted delta is the 1.21.5 caller-side gate change. No primary source beyond the two authorized documents was consulted; no runtime or packet-arrival tick is claimed.