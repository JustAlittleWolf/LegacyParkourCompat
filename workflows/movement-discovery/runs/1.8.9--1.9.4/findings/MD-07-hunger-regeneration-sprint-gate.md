# MD-07: Natural regeneration changes the food input to sprint gating

- Older version A: 1.8.9
- Newer version B: 1.9.4
- Mechanic / coverage slice ID: stage 2.1 player-specific state and sprint gate
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: survival player with natural regeneration enabled, missing health, full food/saturation, and a later sprint attempt
- First changed release: unknown within (1.8.9, 1.9.4]
- Runtime validation: not performed

## Paired evidence

- A `HungerManager.tick()` has no saturation-based fast-healing branch. With natural regeneration enabled, food level at least 18, and missing health, it increments `starvationTimer`; at 80 ticks it heals 1 health, adds 3 exhaustion, then resets the timer. A `HungerManager.java` SHA-256 `27919BBF3E82199C54C3718256C80C40D4C8E675934A8FF9506BA10E3EA1B017`.
- B `HungerManager.tick()` adds a branch before the shared 80-tick healing path: while natural regeneration is enabled, saturation is positive, health is missing, and food is at least 20, it heals `min(saturation, 4) / 4` after 10 ticks and adds that same amount as exhaustion. B `HungerManager.java` SHA-256 `A9C1B33D2064E1E1D7FF0BACB60D62856C109E9AF232D27165755BFE7FE7DB1B`.
- Both `PlayerEntity` tick methods invoke their hunger manager on the server side (A line 277; B line 216). The local player's sprint gates in A `LocalClientPlayerEntity.java` line 550 and B line 661 both compute eligibility from `foodLevel > 6 || abilities.canFly`. Their source hashes are recorded in MD-04.

## Source-level difference

At full food with positive saturation and missing health, B restores health more quickly and spends saturation through added exhaustion. A instead uses its slower 80-tick natural-regeneration branch and 3-point exhaustion cost. This changes when hunger can later cross the shared `foodLevel > 6` sprint threshold.

## Reachability and dependencies

The movement consequence requires normal survival hunger, natural regeneration, damage, and later sprint input. The sprint condition itself is unchanged; the state feeding it changes through the hunger update. Hunger is authoritative on the server and is synchronized to the client, whose local sprint gate reads that value. This finding concerns sprint eligibility only, not the broader health or hunger behavior.

## Consequence and uncertainty

With sustained regeneration from full food and saturation, B's exhaustion reaches saturation depletion sooner than A's slower healing path, and food can subsequently fall through the shared sprint threshold. Exact threshold timing depends on health loss, saturation, exhaustion, and other sources of exhaustion; no tick simulation was performed.

## Handoff

Independent delta: 1.9.4 introduces faster saturation-powered natural regeneration and its exhaustion cost. Because sprint requires food above six, this can change whether a player can sprint later in the same survival session.
