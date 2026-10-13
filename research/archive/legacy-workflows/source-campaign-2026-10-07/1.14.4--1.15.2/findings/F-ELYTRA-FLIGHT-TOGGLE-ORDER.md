# F-ELYTRA-FLIGHT-TOGGLE-ORDER: disabling ability flight suppresses same-tick Elytra start

- Older version A: exact Java Edition 1.14.4
- Newer version B: exact Java Edition 1.15.2
- Mechanic / coverage slice IDs: INV-TICK, INV-STATE, INV-EQUIPMENT; S-LOCAL-PRETRAVEL
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.14.4, 1.15.2]
- Runtime validation: not performed

## Paired evidence

- A `ready/1.14.4/mojmap/net/minecraft/client/player/LocalPlayer.java::LocalPlayer#aiStep()`, lines 692-711, toggles `abilities.flying` on a fresh jump press, then independently evaluates its Elytra-start condition. The latter accepts a descending player with no current ability flight and sends `START_FALL_FLYING` when the equipped Elytra is enabled. SHA-256 `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f`.
- B `ready/1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java::LocalPlayer#aiStep()`, lines 684-719, records `bl8 = true` when ability flight changes state, including when the jump press turns flight off. The subsequent Elytra-start condition requires `!bl8`, so it skips the Elytra attempt on that same tick. SHA-256 `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd`.
- The adjacent general Elytra-start differences are recorded separately in `F-ELYTRA-START`; this finding isolates the same-tick arbitration caused by the ability-flight transition flag.
- Exact A/B artifact manifests: A `ready/1.14.4/mojmap.artifacts.sha256` SHA-256 `308cc33ef6dffc047432ade6affc88eccc9de94a736ee9a98571fd92915c2970`; B `ready/1.15.2/artifacts.sha256` SHA-256 `208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406`.

## Source-level difference

For a player who is ability-flying, is airborne and descending, has an enabled Elytra, and presses jump on a fresh edge while eligible to toggle flight off, A toggles flight off and then reaches its Elytra request branch in the same `aiStep`. B also toggles flight off, but the `bl8` transition marker prevents the later Elytra branch from running that tick. This establishes a one-tick difference in whether the local client sends `START_FALL_FLYING`; it does not establish server acceptance or a later glide trajectory.

## Reachability and dependencies

The condition is reachable when the player has may-fly ability, flight is currently enabled and not forced by always-flying mode, the player is not swimming, the previous sampled jump was released, the newly sampled jump is pressed, and the player is descending with an enabled Elytra. In B, the toggle branch sets `bl8` before the Elytra test; A has no corresponding transition marker. The existing `F-ELYTRA-START` evidence covers the Elytra slot and enable check; this finding is limited to the ability-toggle ordering and its same-tick suppression.

## Consequence and uncertainty

The endpoint sources prove the different command-request path under these preconditions. They do not prove the server's response or any exact release between 1.14.4 and 1.15.2. No runtime validation was performed.

## Handoff

Preserve the endpoint's ability-toggle/Elytra arbitration when selecting historical behavior. Inspect intervening exact releases if an implementation needs a finer cutover.
